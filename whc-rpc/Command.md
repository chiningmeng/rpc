
## 安装mysql
https://blog.51cto.com/u_15187242/2744720

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

~~~
docker exec -it 7dee1a72fb01 /bin/bash
//然后进入zookeeper 下的bin
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
