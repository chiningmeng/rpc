package com.whc.rpc.remoting.transport.netty.codec;

import com.whc.rpc.enums.SerializationTypeEnum;
import com.whc.rpc.extension.ExtensionLoader;
import com.whc.rpc.remoting.constants.RpcConstants;
import com.whc.rpc.remoting.dto.Message;
import com.whc.rpc.remoting.dto.Request;
import com.whc.rpc.remoting.dto.Response;
import com.whc.rpc.remoting.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class MessageDecoder extends LengthFieldBasedFrameDecoder {

    public MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    public MessageDecoder() {
        // lengthFieldOffset: magic code  4B, 版本号 1B, 5
        // lengthFieldLength: 全长 4B.  4
        // lengthAdjustment: 内容（content）要刨除报文头（header）和长度（length） -9
        // initialBytesToStrip: 因为需要校验magic code，不跳过任何字节  故为0
        this(RpcConstants.MAX_FRAME_LENGTH, 5, 4, -9, 0);
    }
    private Object decodeFrame(ByteBuf in) {
        checkMagicNumber(in);
        checkVersion(in);
        int fullLength = in.readInt();
        byte messageType = in.readByte();
        byte codecType = in.readByte();
        int requestId = in.readInt();
        Message rpcMessage = Message.builder()
                .codec(codecType)
                .requestId(requestId)
                .messageType(messageType).build();
        if (messageType == RpcConstants.HEARTBEAT_REQUEST_TYPE) {
            rpcMessage.setData(RpcConstants.PING);
            return rpcMessage;
        }
        if (messageType == RpcConstants.HEARTBEAT_RESPONSE_TYPE) {
            rpcMessage.setData(RpcConstants.PONG);
            return rpcMessage;
        }
        int bodyLength = fullLength - RpcConstants.HEAD_LENGTH;
        if (bodyLength > 0) {
            byte[] bs = new byte[bodyLength];
            in.readBytes(bs);
            String codecName = SerializationTypeEnum.getName(rpcMessage.getCodec());
            log.info("codec name: [{}] ", codecName);
            Serializer serializer = ExtensionLoader.getExtensionLoader(Serializer.class)
                    .getExtension(codecName);
            if (messageType == RpcConstants.REQUEST_TYPE) {
                Request tmpValue = serializer.deserialize(bs, Request.class);
                rpcMessage.setData(tmpValue);
            } else {
                Response tmpValue = serializer.deserialize(bs, Response.class);
                rpcMessage.setData(tmpValue);
            }
        }
        return rpcMessage;

    }

    private void checkVersion(ByteBuf in) {
        byte version = in.readByte();
        if (version != RpcConstants.VERSION) {
            throw new RuntimeException("version isn't compatible" + version);
        }
    }

    private void checkMagicNumber(ByteBuf in) {
        // 读 4bit magic number
        int len = RpcConstants.MAGIC_NUMBER.length;
        byte[] tmp = new byte[len];
        in.readBytes(tmp);
        for (int i = 0; i < len; i++) {
            if (tmp[i] != RpcConstants.MAGIC_NUMBER[i]) {
                throw new IllegalArgumentException("Unknown magic code: " + Arrays.toString(tmp));
            }
        }
    }

}
