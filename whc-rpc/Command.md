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