package com.whc.loadbalance.loadbalancer;

import com.whc.loadbalance.AbstractLoadBalance;
import com.whc.remoting.dto.Request;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ConsistentHashLoadBalance extends AbstractLoadBalance {
    private final ConcurrentHashMap<String, ConsistentHashSelector> selectors = new ConcurrentHashMap<>();

    @Override
    protected String doSelect(List<String> serviceAddresses, Request rpcRequest) {
        int identityHashCode = System.identityHashCode(serviceAddresses);
        // 取服务名
        String serviceName = rpcRequest.getRpcServiceName();
        ConsistentHashSelector selector = selectors.get(serviceName);
        // 检测服务IP列表是否有更新
        if (selector == null || selector.identityHashCode != identityHashCode) {
            //这里参考的Dubbo的写法，Dubbo默认160个
            selectors.put(serviceName, new ConsistentHashSelector(serviceAddresses, 160, identityHashCode));
            selector = selectors.get(serviceName);
        }
        return selector.select(serviceName + Arrays.stream(rpcRequest.getParameters()));
    }

    static class ConsistentHashSelector {
        private final TreeMap<Long, String> virtualNodes;

        private final int identityHashCode;

        ConsistentHashSelector(List<String> ipAddressList, int replicaNumber, int identityHashCode) {
            this.virtualNodes = new TreeMap<>();
            this.identityHashCode = identityHashCode;

            for (String ipAddress : ipAddressList) {
                for (int i = 0; i < replicaNumber / 4; i++) {
                    // 对 address + i 进行 md5 运算，得到一个长度为16的字节数组
                    byte[] digest = md5(ipAddress + i);
                    // 对 digest 部分字节进行4次位运算，得到四个不同的 long 型正整数
                    for (int h = 0; h < 4; h++) {
                        long m = hash(digest, h);
                        //160份节点，都映射到同一个实际节点
                        virtualNodes.put(m, ipAddress);
                    }
                }
            }
        }

        static byte[] md5(String key) {
            MessageDigest md;
            try {
                md = MessageDigest.getInstance("MD5");
                byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
                md.update(bytes);
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }

            return md.digest();
        }

        static long hash(byte[] digest, int num) {
            return (        //digest的第4(num 为 0时),8(num 为 1),12(num 为 2),16(num 为 3)字节，左移24位
                    (long) (digest[3 + num * 4]) << 24
                            //digest的第3,7,11,15字节，左移16位
                    | (long) (digest[2 + num * 4]) << 16
                            //digest的第2,6,10,14字节，左移8位
                    | (long) (digest[1 + num * 4]) << 8
                            //digest的第1,5,9,13字节
                    | (long) (digest[num * 4]))
                    & 0xFFFFFFFFL;
        }

        public String select(String rpcServiceKey) {
            byte[] digest = md5(rpcServiceKey);
            return selectForKey(hash(digest, 0));
        }

        public String selectForKey(long hashCode) {
            Map.Entry<Long, String> entry = virtualNodes.tailMap(hashCode, true).firstEntry();

            if (entry == null) {
                entry = virtualNodes.firstEntry();
            }

            return entry.getValue();
        }
    }
}
