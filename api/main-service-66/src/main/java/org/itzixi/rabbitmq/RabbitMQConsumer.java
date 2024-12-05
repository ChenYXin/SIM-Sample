package org.itzixi.rabbitmq;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.itzixi.pojo.netty.ChatMsg;
import org.itzixi.service.IChatMessagesService;
import org.itzixi.utils.JsonUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQConsumer {

    @Resource
    private IChatMessagesService chatMessagesService;

    @RabbitListener(queues = {RabbitMQTestConfig.TEST_QUEUE})
    public void watchQueue(String payload, Message message) {
        log.info("Received payload: {}", payload);

        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        log.info("Received routingKey: {}", routingKey);

        if (routingKey.equals(RabbitMQTestConfig.ROUTING_KEY_WECHAT_MSG_SEND)) {
            String msg = payload;
            ChatMsg chatMsg = JsonUtils.jsonToPojo(msg, ChatMsg.class);

            chatMessagesService.saveMsg(chatMsg);
            log.info(chatMsg.toString());
        }
    }
}
