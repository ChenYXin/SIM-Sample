package org.itzixi.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.itzixi.tasks.SMSTask;
import org.itzixi.utils.SMSUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/a")
@Slf4j
public class HelloController {
    @Resource
    private SMSUtils smsUtils;

    @Resource
    private SMSTask smsTask;

    @GetMapping("/hello")
    public String hello() {
        return "hello world 88";
    }

    @GetMapping("/sms")
    public String sms() throws Exception {
        smsUtils.sendSMS("13691942911","9989");
        return "send sms OK";
    }

    @GetMapping("/smsTask")
    public String smsTask() throws Exception {
        smsTask.sendSmsInTask("13691942911","8899");
        return "send sms In Task OK";
    }
}
