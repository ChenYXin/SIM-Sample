# SpringCloud+Netty集群实战千万级 IM系统

## 5个微服务
- gateway 网关服务
- auth 验证服务
- file 文件服务
- main 主服务
- chat 聊天服务

## 功能介绍
- MySQL 8 数据库存储
- Mybatis-Plus 简化数据库操作和代码生成
- Log4j2 日志输出
- PageHelper 分页
- Redis 缓存
- MinIO 文件存储
- Zxing 二维码生成
- OpenFeign 实现微服务远程调用
- Netty 构建高性能聊天通讯
- RabbitMQ 异步通信，实现解耦、削峰填谷和消息广播
- Nacos 服务注册与发现、动态配置管理
- Zookeeper 实现分布式锁控制同一节点资源的并发读写
- Curator ZooKeeper 客户端框架，简化 ZooKeeper 操作，用于分布式应用中的服务协调和配置管理
- Dockerfile 容器化编排

## 流程图

## 功能亮点
- SpringCloud 结合 alibaba 体系构建分层的聚合微服务架构项目，与 Netty 集群进行异步通信并且进行离线消息存储
- Netty 聊天业务集群化，构建 WebSocket 服务器，结合 Zookeeper&Redis&RabbitMQ 实现聊天业务集群化，聊天用户心跳机制，群组会话分配
- Redis 为 Netty 集群动态端口分配媒介，并在Netty 中集成 Jedis 连接池，也同时对黑名单用户进行隔离与限流
- RabbitMQ 实现微服务系统与 Netty 集群异步通信，Netty 集群内部消息广播，聊天离线消息异步解耦与存储
- Zookeeper 实现 Netty 服务节点注册，监听清理无效端口与队列，共享资源的分布式读写锁，Netty 服务在线人数统计
- MinIO 实现分布式对象存储，对Netty 聊天中所产生的图片/语音/视频类型文件消息进行存储
- Docker 为 Netty 集群服务提供便捷的Zookeeper&Redis&RabbitMQ 等中间件的容器化部署