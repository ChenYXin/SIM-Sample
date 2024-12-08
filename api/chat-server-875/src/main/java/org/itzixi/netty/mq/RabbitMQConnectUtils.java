package org.itzixi.netty.mq;

import com.rabbitmq.client.*;
import org.itzixi.netty.websocket.UserChannelSession;
import org.itzixi.pojo.netty.DataContent;
import org.itzixi.utils.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RabbitMQConnectUtils {

    private final List<Connection> connections = new ArrayList<>();
    private final int maxConnection = 20;

    // 开发环境 dev
    private final String host = "127.0.0.1";
    private final int port = 5672;
    private final String username = "imooc";
    private final String password = "123456";
    private final String virtualHost = "wechat-dev";

    // 生产环境 prod
    //private final String host = "";
    //private final int port = 5672;
    //private final String username = "123";
    //private final String password = "123";
    //private final String virtualHost = "123";

    public ConnectionFactory factory;

    public ConnectionFactory getRabbitMqConnection() {
        return getFactory();
    }

    public ConnectionFactory getFactory() {
        initFactory();
        return factory;
    }

    private void initFactory() {
        try {
            if (factory == null) {
                factory = new ConnectionFactory();
                factory.setHost(host);
                factory.setPort(port);
                factory.setUsername(username);
                factory.setPassword(password);
                factory.setVirtualHost(virtualHost);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String message, String queue) throws Exception {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        channel.basicPublish("",
                queue,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes("utf-8"));
        channel.close();
        setConnection(connection);
    }

    public void sendMsg(String message, String exchange, String routingKey) throws Exception {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        channel.basicPublish(exchange,
                routingKey,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes("utf-8"));
        channel.close();
        setConnection(connection);
    }

    public GetResponse basicGet(String queue, boolean autoAck) throws Exception {
        GetResponse getResponse = null;
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        getResponse = channel.basicGet(queue, autoAck);
        channel.close();
        setConnection(connection);
        return getResponse;
    }

    public Connection getConnection() throws Exception {
        return getAndSetConnection(true, null);
    }

    public void setConnection(Connection connection) throws Exception {
        getAndSetConnection(false, connection);
    }

    public void listen(String exchangeName, String queueName) throws Exception {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        //定义交换机，发布订阅模式(广播模式)
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT, true, false, null);
        //定义队列
        channel.queueDeclare(queueName, true, false, false, null);
        //把队列绑定到交换机
        channel.queueBind(queueName, exchangeName, "");
        Consumer consumer = new DefaultConsumer(channel) {
            /**
             * 重写消息配送（交付）方法
             * @param consumerTag  消息的标签（标识）
             * @param envelope 信封（一些消息，比如交换机路由等信息）
             * @param properties 配置信息和内容
             * @param body 收到等消息数据内容
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {

                String msg = new String(body, "utf-8");
                System.out.println("===================== MQ 消费者监听 start =====================");
                System.out.println(msg);
                System.out.println("===================== MQ 消费者监听 end =====================");
                String exchange = envelope.getExchange();
                if (exchange.equalsIgnoreCase(exchangeName)) {
                    DataContent dataContent = JsonUtils.jsonToPojo(msg, DataContent.class);
                    String senderId = dataContent.getChatMsg().getSenderId();
                    String receiverId = dataContent.getChatMsg().getReceiverId();

                    //广播至所有netty集群节点并且发送给用户聊天消息
                    List<io.netty.channel.Channel> receiverChannels =
                            UserChannelSession.getMultiChannels(receiverId);
                    UserChannelSession.sendToTarget(receiverChannels, dataContent);

                    //广播至所有netty集群节点并且同步给自己的其他设备聊天消息
                    String currentChannelId = dataContent.getExtend();
                    List<io.netty.channel.Channel> senderChannels =
                            UserChannelSession.getMyOtherChannels(senderId, currentChannelId);
                    UserChannelSession.sendToTarget(senderChannels, dataContent);
                }
            }
        };
        /**
         * queue:监听的队列名称
         * autoAck:是否自动确认，true：告知mq消费者已经消费的确认通知
         * callback:回调函数，处理监听到的消息
         */
        channel.basicConsume(queueName, true, consumer);
    }

    private synchronized Connection getAndSetConnection(boolean isGet, Connection connection) throws Exception {
        getRabbitMqConnection();

        if (isGet) {
            if (connections.isEmpty()) {
                return factory.newConnection();
            }
            Connection newConnection = connections.get(0);
            connections.remove(0);
            if (newConnection.isOpen()) {
                return newConnection;
            } else {
                return factory.newConnection();
            }
        } else {
            if (connections.size() < maxConnection) {
                connections.add(connection);
            }
            return null;
        }
    }

}
