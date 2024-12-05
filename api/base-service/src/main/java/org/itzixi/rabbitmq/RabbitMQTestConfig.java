package org.itzixi.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTestConfig {
    //定义交换机的名字
    public static final String TEST_EXCHANGE = "test_exchange";
    //定义队列的名字
    public static final String TEST_QUEUE = "test_queue";
    //具体的路由地址
    public static final String ROUTING_KEY_TEST_SEND = "imooc.wechat.test.send";
    //发送信息到消息队列并且接受并保存到数据库的路由地址
    public static final String ROUTING_KEY_WECHAT_MSG_SEND = "imooc.wechat.wechat.msg.send";

    //创建交换机
    @Bean(TEST_EXCHANGE)
    public Exchange exchange() {
        return ExchangeBuilder.topicExchange(TEST_EXCHANGE).durable(true).build();
    }

    //创建队列
    @Bean(TEST_QUEUE)
    public Queue queue() {
        return QueueBuilder.durable(TEST_QUEUE).build();
    }

    //定义队列绑定到交换机的关系
    @Bean
    public Binding binding(@Qualifier(TEST_EXCHANGE) Exchange exchange,
                           @Qualifier(TEST_QUEUE) Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(RabbitMQTestConfig.ROUTING_KEY_TEST_SEND)
                .noargs();//执行绑定关系
    }

}
