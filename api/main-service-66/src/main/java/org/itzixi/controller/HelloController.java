package org.itzixi.controller;

import jakarta.annotation.Resource;
import org.itzixi.pojo.netty.ChatMsg;
import org.itzixi.rabbitmq.RabbitMQTestConfig;
import org.itzixi.utils.JsonUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/m")
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello world 66";
    }

    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/mq")
    public String mq() {
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setMsg("hello rabbitmq~~");
        String msg = JsonUtils.objectToJson(chatMsg);

        rabbitTemplate.convertAndSend(RabbitMQTestConfig.TEST_EXCHANGE,
                "imooc.wechat.test.send",
                msg);
        return "mq ok";
    }
}
