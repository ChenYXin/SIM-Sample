package org.itzixi.tasks;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.itzixi.utils.SMSUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SMSTask {
    @Resource
    SMSUtils smsUtils;

    @Async
    public void sendSmsInTask(String mobile, String code) throws Exception {
//        smsUtils.sendSMS(mobile, code);
        log.info("异步任务重中发送的验证码为：{}", code);
    }
}
