package com.itzixi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/a")
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello world 66";
    }
}
