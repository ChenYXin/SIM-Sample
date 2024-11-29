package org.itzixi.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.tasks.SMSTask;
import org.itzixi.utils.IPUtil;
import org.itzixi.utils.SMSUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passport")
@Slf4j
public class PassportController extends BaseInfoProperties {
    // 127.0.0.1:88/passport/getSMSCode
    @Resource
    private SMSTask smsTask;


    @GetMapping("/getSMSCode")
    public GraceJSONResult getSMSCode(String mobile, HttpServletRequest request) throws Exception {
        if (StringUtils.isBlank(mobile)) {
            return GraceJSONResult.error();
        }

        //获得用户的手机号/ip
        String userIp = IPUtil.getRequestIp(request);
        //限制该用户的手机号/ip在60秒内只能获得一次验证码
        redis.setnx60s(MOBILE_SMSCODE + ":" + userIp, mobile);

        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        smsTask.sendSmsInTask(mobile, code);

        //把验证码存入到redis中，用于后续的注册/校验 ,30分钟
        redis.set(MOBILE_SMSCODE + ":" + mobile, code, 30 * 60);

        return GraceJSONResult.ok();
    }
}
