package org.itzixi.controller;

import org.itzixi.base.BaseInfoProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/g")
public class HelloController extends BaseInfoProperties {


    @GetMapping("/hello")
    public String hello() {
        return "hello gateway";
    }

    @GetMapping("/setRedis")
    public String setRedis(@RequestParam String k, @RequestParam String v) {
        redis.set(k, v);
        return "setRedis OK";
    }

    @GetMapping("/getRedis")
    public String getRedis(@RequestParam String k) {
        return redis.get(k);
    }
}
