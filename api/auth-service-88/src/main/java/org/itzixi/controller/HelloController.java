package org.itzixi.controller;

import jakarta.annotation.Resource;
import org.itzixi.utils.SMSUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/a")
public class HelloController {
    @Resource
    SMSUtils smsUtils;

    @GetMapping("/hello")
    public String hello() {
        return "hello world 88";
    }

    @GetMapping("/sms")
    public String sms() throws Exception {
        smsUtils.sendSMS("13691942911","9989");
        return "send sms OK";
    }
}
