package org.itzixi.controller.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.exceptions.GraceException;
import org.itzixi.exceptions.MyCustomException;
import org.itzixi.grace.result.ResponseStatusEnum;
import org.itzixi.utils.IPUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class SMSInterceptor extends BaseInfoProperties implements HandlerInterceptor {
    //拦截请求，在controller调用之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * false:请求被拦截
         * true：请求放行，正常通过，验证通过
         */

        //获得用户的ip
        String userIp = IPUtil.getRequestIp(request);
        //获得用于判断是否存在的boolean
        boolean isExist=redis.keyIsExist(MOBILE_SMSCODE + ":" + userIp);
        if (isExist) {
            log.error("短信发送频率太高了～！！！");
//            throw new MyCustomException(ResponseStatusEnum.FAILED);
            GraceException.display(ResponseStatusEnum.SMS_NEED_WAIT_ERROR);
            return false;
        }
        return true;
    }

    //请求controller之后，渲染视图之前
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    //请求controller之后，渲染视图之后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
