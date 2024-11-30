package org.itzixi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Slf4j
@Aspect
public class ServiceLogAspect {
    @Around("execution(* org.itzixi.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //需要统计每一个service实现的执行时间，如果执行时间太久，则进行error级别的日志输出
//        long begin = System.currentTimeMillis();
        StopWatch stopWatch = new StopWatch();

        String pointName = joinPoint.getTarget().getClass().getName()
                + "."
                + joinPoint.getSignature().getName();
        stopWatch.start("执行主业务："+pointName);
        Object proceed = joinPoint.proceed();
        stopWatch.stop();

        log.info(stopWatch.prettyPrint());
        log.info(stopWatch.shortSummary());

//        long end = System.currentTimeMillis();
//        long takeTime = end - begin;
        long takeTime = stopWatch.getTotalTimeMillis();
        if (takeTime > 3000) {
            log.error("执行位置{},执行时间太长了，耗费了{}毫秒", pointName, takeTime);
        } else if (takeTime > 2000) {
            log.warn("执行位置{},执行时间稍微有点长，耗费了{}毫秒", pointName, takeTime);
        } else {
            log.info("执行位置{},执行时间正常，耗费了{}毫秒", pointName, takeTime);
        }
        return proceed;
    }
}
