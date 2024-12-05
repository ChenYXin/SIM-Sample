package org.itzixi.netty.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.itzixi.pojo.netty.ChatMsg;
import org.itzixi.utils.JsonUtils;

public class MessagePublisher {

    // 定义交换机的名字
    public static final String TEST_EXCHANGE = "test_exchange";

    // 定义队列的名字
    public static final String TEST_QUEUE = "test_queue";

    // 发送信息到消息队列接受并且保存到数据库的路由地址
    public static final String ROUTING_KEY_WECHAT_MSG_SEND = "imooc.wechat.wechat.msg.send";

    // public static void saveMessageByMQ(String msg) throws Exception {
    //
    //     // 1. 创建连接工厂
    //     ConnectionFactory factory = new ConnectionFactory();
    //     // 1.1 设置连接参数
    //     factory.setHost("127.0.0.1");
    //     factory.setPort(5672);
    //     factory.setVirtualHost("itzixi");
    //     factory.setUsername("lee");
    //     factory.setPassword("lee");
    //
    //     // 1.2 建立连接
    //     Connection connection = factory.newConnection();
    //
    //     // 2. 创建通道channel
    //     Channel channel = connection.createChannel();
    //
    //     // 3. 定义队列
    //     channel.queueDeclare(QUEUE_MSG,
    //                         true,
    //                         false,
    //                         false,
    //                         null);
    //     // 4. 发送消息
    //     channel.basicPublish(EXCHANGE_MSG,
    //                         SAVE_MSG_ROUTING_KEY,
    //                         null,
    //                         msg.getBytes());
    //
    //     // 5. 关闭通道和连接
    //     channel.close();
    //     connection.close();
    // }

    // public static void main(String[] args) throws Exception {
    //     //saveMessageByMQ("Send a chat msg by rabbitmq~~~  another  ");
    //
    //     RabbitMQConnectUtils connectUtils = new RabbitMQConnectUtils();
    //     String msg = "Send a chat msg by rabbitmq~~~  new ";
    //
    //     ChatMsg chatMsg = new ChatMsg();
    //     chatMsg.setMsgId("1001");
    //     chatMsg.setMsg("123xyz");
    //     // String pendingMsg = GsonUtils.object2String(chatMsg);
    //     String pendingMsg = JsonUtils.objectToJson(chatMsg);
    //
    //     connectUtils.sendMsg(pendingMsg, EXCHANGE_MSG, SAVE_MSG_ROUTING_KEY);
    // }

    public static void sendMsgToSave(ChatMsg msg) throws Exception {
        RabbitMQConnectUtils connectUtils = new RabbitMQConnectUtils();
        connectUtils.sendMsg(JsonUtils.objectToJson(msg),
                            TEST_EXCHANGE,
                            ROUTING_KEY_WECHAT_MSG_SEND);
    }


}
