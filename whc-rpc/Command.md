
# mysql
https://blog.51cto.com/u_15187242/2744720
~~~shell
#搜索mysql镜像
docker search mysql
#拉取镜像
docker pull mysql:5.7

docker run -dp 3306:3306 --name mysql -v /mydocker/mysql/conf:/etc/mysql/conf.d -v /mydocker/mysql/logs:/var/log/mysql -v /mydocker/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=Whc194910 -d mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

~~~
## 清空上次测试记录
~~~sql
drop table if exists rpc_test;
CREATE TABLE `rpc_test` (
                       `uid` bigint(20) NOT NULL AUTO_INCREMENT,
                       `trace_id` char(100) DEFAULT NULL COMMENT '请求与响应的唯一标识符',
                       `where_happen` char(20) DEFAULT NULL COMMENT '在client端还是在server端',
                       `is_success` tinyint(1) DEFAULT NULL COMMENT '是否成功',
                       `start_timestamp` bigint(20) DEFAULT NULL COMMENT '开始时间',
                       `end_timestamp` bigint(20) DEFAULT NULL COMMENT '结束时间',
                       `serialize` int(11) DEFAULT NULL COMMENT '序列化耗时',
                       `deserialize` int(11) DEFAULT NULL COMMENT '反序列化耗时',
                       `total` bigint(20) DEFAULT NULL COMMENT '总耗时',
                       `get_connect` int(11) DEFAULT NULL COMMENT '取得连接耗时',
                       `load_balance` int(11) DEFAULT NULL COMMENT '负载均衡耗时',
                       `wait_for_response` int(11) DEFAULT NULL COMMENT '等待响应耗时',
                       `get_server_list` int(11) DEFAULT NULL COMMENT '获取服务端列表耗时',
                       `handle` int(11) DEFAULT NULL COMMENT '服务端处理耗时',
                       `request_size` bigint(20) DEFAULT NULL COMMENT '请求体大小',
                       `response_size` bigint(20) DEFAULT NULL COMMENT '响应体大小',
                       `ip_address` char(100) DEFAULT NULL COMMENT '负载均衡选择的服务ip地址',
                       `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1158805 DEFAULT CHARSET=utf8mb4 COMMENT='毕设rpc性能数据';
~~~
公网ip : 43.152.214.179
本地启动记得改mybatis-config.xml配置
~~~sql

select count(*) from rpc_time where ip_address = '10.0.16.14';


select count(*) from rpc_time where ip_address = '10.0.16.10';


select count(*) from rpc_time where ip_address = '10.0.16.9';


select count(*) from rpc_time where ip_address = '10.0.16.2';
~~~

~~~sql
select avg(serialize) from rpc_time where where_happen = 'Client';


select avg(deserialize) from rpc_time where where_happen = 'Client';


select avg(get_connect) from rpc_time where where_happen = 'Client';


select avg(load_balance) from rpc_time where where_happen = 'Client';


select avg(wait_for_response) from rpc_time where where_happen = 'Client';


select avg(request_size) from rpc_time where where_happen = 'Client';





select avg(serialize) from rpc_time where where_happen = 'Server';


select avg(deserialize) from rpc_time where where_happen = 'Server';


select avg(handle) from rpc_time where where_happen = 'Server';


select avg(response_size) from rpc_time where where_happen = 'Server';

~~~
# Docker

## 进入容器

### exec 在运行的容器中执行命令

|   名称   |   默认   |   描述   |
| ---- | ---- | ---- |
|   --detach   |   -d   |  后台运行模式    |
|    --env  |   -e   |   设置环境变量   |
|  --interactive    |   -i   |  展示容器输入信息STDIN    |
|   --tty   |    -t  |   命令行交互模式   |
|   --user   |  -u    |  设置用户名    |
|    --workdir  |   -w   |   指定容器内的目录   |

# ZooKeeper

https://www.cnblogs.com/zhaokejin/p/15605264.html

进入zookeeper查看节点

~~~shell
# docker exec -it 7dee1a72fb01 /bin/bash
# 然后进入zookeeper 下的bin
cd /app/zookeeper/apache-zookeeper-3.8.0-bin/bin
./zkCli.sh -server 127.0.0.1:2181
~~~

搭建zookeeper集群 
~~~shell
#下载
wget https://downloads.apache.org/zookeeper/zookeeper-3.8.0/apache-zookeeper-3.8.0-bin.tar.gz

#解压
sudo mkdir -p /app/zookeeper/data
sudo tar -zxvf apache-zookeeper-3.8.0-bin.tar.gz -C /app/zookeeper/
#配置账号
sudo groupadd zookeeper
sudo useradd -g zookeeper zookeeper
sudo passwd zookeeper
#修改配置
cd /app/zookeeper/apache-zookeeper-3.8.0-bin
sudo cp conf/zoo_sample.cfg conf/zoo.cfg

vi /app/zookeeper/apache-zookeeper-3.8.0-bin/conf/zoo.cfg
#详情配置见zoo.cfg

#三台服务器分别(这里不行，改为手动vim创建
sudo echo '1' > /app/zookeeper/data/myid
sudo echo '2' > /app/zookeeper/data/myid
sudo echo '3' > /app/zookeeper/data/myid

#文件夹权限赋予zookeeper用户
sudo chown -R zookeeper:zookeeper /app/zookeeper/

#启动服务
su zookeeper
cd /app/zookeeper/apache-zookeeper-3.8.0-bin
bin/zkServer.sh start

#查看zookeeper状态
bin/zkServer.sh status
~~~

zoo.cfg
~~~
# The number of milliseconds of each tick
tickTime=2000
# The number of ticks that the initial
# synchronization phase can take
initLimit=10
# The number of ticks that can pass between
# sending a request and getting an acknowledgement
syncLimit=5
# the directory where the snapshot is stored.
# do not use /tmp for storage, /tmp here is just
# example sakes.
dataDir=/app/zookeeper/data
# the port at which the clients will connect
clientPort=2181
# the maximum number of client connections.
# increase this if you need to handle more clients
#maxClientCnxns=60
#
# Be sure to read the maintenance section of the
# administrator guide before turning on autopurge.
#
# https://zookeeper.apache.org/doc/current/zookeeperAdmin.html#sc_maintenance
#
# The number of snapshots to retain in dataDir
autopurge.snapRetainCount=3
# Purge task interval in hours
# Set to "0" to disable auto purge feature
autopurge.purgeInterval=1

## Metrics Providers
#
# https://prometheus.io Metrics Exporter
#metricsProvider.className=org.apache.zookeeper.metrics.prometheus.PrometheusMetricsProvider
#metricsProvider.httpHost=0.0.0.0
#metricsProvider.httpPort=7000
#metricsProvider.exportJvmInfo=true
server.1=10.0.16.3:2888:3888
server.2=10.0.16.17:2888:3888
server.3=10.0.16.8:2888:3888
#注意：这里服务器ip因为用的云服务器，所以要用内网ip,外网ip会启动不了
~~~

启动zookeeper前要检查jdk是否安装，
[下载jdk](https://blog.csdn.net/qq_44543508/article/details/108864424)
[解决找不到javac命令](https://blog.csdn.net/qq_42720183/article/details/117439447)

# 向服务器上传文件
https://cloud.tencent.com/document/product/213/2133
~~~shell
#client
scp /Users/whc/IdeaProjects/rpc/client/target/client-0.0.1-SNAPSHOT.jar root@129.226.32.128:/home/lighthouse/client-0.0.1-SNAPSHOT.jar 
scp /Users/whc/IdeaProjects/rpc/client/target/client-0.0.1-SNAPSHOT.jar root@43.152.215.172:/home/lighthouse/client-0.0.1-SNAPSHOT.jar 
scp /Users/whc/IdeaProjects/rpc/client/target/client-0.0.1-SNAPSHOT.jar root@43.152.215.173:/home/lighthouse/client-0.0.1-SNAPSHOT.jar 
scp /Users/whc/IdeaProjects/rpc/client/target/client-0.0.1-SNAPSHOT.jar root@43.152.215.17:/home/lighthouse/client-0.0.1-SNAPSHOT.jar 

#server 孟买7～12
scp /Users/whc/IdeaProjects/rpc/server/target/server-0.0.1-SNAPSHOT.jar root@43.152.215.17:/home/lighthouse/server-0.0.1-SNAPSHOT.jar
scp /Users/whc/IdeaProjects/rpc/server/target/server-0.0.1-SNAPSHOT.jar root@43.152.215.173:/home/lighthouse/server-0.0.1-SNAPSHOT.jar
scp /Users/whc/IdeaProjects/rpc/server/target/server-0.0.1-SNAPSHOT.jar root@43.152.212.117:/home/lighthouse/server-0.0.1-SNAPSHOT.jar 
scp /Users/whc/IdeaProjects/rpc/server/target/server-0.0.1-SNAPSHOT.jar root@43.152.215.115:/home/lighthouse/server-0.0.1-SNAPSHOT.jar 
scp /Users/whc/IdeaProjects/rpc/server/target/server-0.0.1-SNAPSHOT.jar root@43.152.214.5:/home/lighthouse/server-0.0.1-SNAPSHOT.jar 
scp /Users/whc/IdeaProjects/rpc/server/target/server-0.0.1-SNAPSHOT.jar root@43.152.214.162:/home/lighthouse/server-0.0.1-SNAPSHOT.jar

~~~

#启动jar
~~~shell
#client
nohup java -jar -Xms1024M -Xmx2048M client-0.0.1-SNAPSHOT.jar >log.out 2>&1 &

#server
nohup java -jar -Xms1024M -Xmx2048M server-0.0.1-SNAPSHOT.jar > log.out 2>&1 &
~~~



# Jemeter

## 下载
~~~shell
# 下载
wget https://dlcdn.apache.org//jmeter/binaries/apache-jmeter-5.4.3.tgz
# 解压
tar zxvf apache-jmeter-5.4.3.tgz


# 配置jmeter环境变量
vi .bash_profile

#（jmeter安装路径）
export JMETER_HOME=/home/lighthouse/apache-jmeter-5.4.3
export CLASSPATH=$JMETER_HOME/lib/ext/ApacheJMeter_core.jar:$JMETER_HOME/lib/jorphan.jar:$JMETER_HOME/lib/logkit-2.0.jar:$CLASSPATH
export PATH=$JMETER_HOME/bin:$PATH:$HOME/bin


/home/lighthouse/apache-jmeter-5.4.3/bin/jmeter -n -t testRequest.jmx  -l log.jtl -e -o /home/lighthouse/result

#参数
#-n This specifies JMeter is to run in non-gui mode
#
#-t [name of JMX file that contains the Test Plan].
#
#-l [name of JTL file to log sample results to].
#
#-r Run all remote servers specified in JMeter.properties (or remote servers specified on command line by overriding properties)
#
#The script also lets you specify the optional firewall/proxy server information:
#
#-H [proxy server hostname or ip address]
#
#-P [proxy server port]

# -n：以非GUI形式运行Jmeter 
# -t：source.jmx 脚本路径 
# -l：result.jtl 运行结果保存路径（.jtl）,此文件必须不存在 
# -e：在脚本运行结束后生成html报告 
# -o：用于存放html报告的目录

#上传压测配置文件给孟买-6
scp /Users/whc/testRequest.jmx root@43.152.215.172:/home/lighthouse/testRequest.jmx


#下载压测记录jtl文件
scp root@43.152.215.172:/home/lighthouse/log.jtl /Users/whc/log.jtl
~~~

