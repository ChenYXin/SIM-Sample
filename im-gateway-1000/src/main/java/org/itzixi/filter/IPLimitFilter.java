package org.itzixi.filter;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.grace.result.ResponseStatusEnum;
import org.itzixi.utils.IPUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RefreshScope //nacos动态配置
public class IPLimitFilter extends BaseInfoProperties implements GlobalFilter, Ordered {
    /**
     * 需求：
     * 判断某个请求的ip在20秒内的请求次数是否超过3次
     * 如果超过3次，则限制访问30秒
     * 等待30秒静默后，才能够继续恢复访问
     */
    @Value("${blackIP.continueCounts}")
    private Integer continueCounts;
    @Value("${blackIP.timeInterval}")
    private Integer timeInterval;
    @Value("${blackIP.limitTimes}")
    private Integer limitTimes;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("==== 当前的执行 IPLimitFilter ====");
//        if (1 == 1) {
//            //终止请求，返回错误信息
//            return renderErrorMsg(exchange, ResponseStatusEnum.SYSTEM_ERROR_BLACK_IP);
//        }
        log.info("continueCounts: {}", continueCounts);
        log.info("timeInterval: {}", timeInterval);
        log.info("limitTimes: {}", limitTimes);

        return doLimit(exchange, chain);
        //默认放行请求到后续的路由（服务）
//        return chain.filter(exchange);
    }

    /**
     * 限制ip请求次数的判断
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> doLimit(ServerWebExchange exchange,
                              GatewayFilterChain chain) {

        // 根据request获得请求ip
        ServerHttpRequest request = exchange.getRequest();
        String ip = IPUtil.getIP(request);

        // 正常的ip定义
        final String ipRedisKey = "gateway-ip:" + ip;
        // 被拦截的黑名单ip，如果在redis中存在，则表示目前被关小黑屋
        final String ipRedisLimitKey = "gateway-ip:limit:" + ip;

        // 获得当前的ip并且查询还剩下多少时间，如果时间存在（大于0），则表示当前仍然处在黑名单中
        long limitLeftTimes = redis.ttl(ipRedisLimitKey);
        if (limitLeftTimes > 0) {
            // 终止请求，返回错误
            return renderErrorMsg(exchange, ResponseStatusEnum.SYSTEM_ERROR_BLACK_IP);
        }

        // 在redis中获得ip的累加次数
        long requestCounts = redis.increment(ipRedisKey, 1);
        /**
         * 判断如果是第一次进来，也就是从0开始计数，则初期访问就是1，
         * 需要设置间隔的时间，也就是连续请求的次数的间隔时间
         */
        if (requestCounts == 1) {
            redis.expire(ipRedisKey, timeInterval);
        }

        /**
         * 如果还能获得请求的正常次数，说明用户的连续请求落在限定的[timeInterval]之内
         * 一旦请求次数超过限定的连续访问次数[continueCounts]，则需要限制当前的ip
         */
        if (requestCounts > continueCounts) {
            // 限制ip访问的时间[limitTimes]
            redis.set(ipRedisLimitKey, ipRedisLimitKey, limitTimes);
            // 终止请求，返回错误
            return renderErrorMsg(exchange, ResponseStatusEnum.SYSTEM_ERROR_BLACK_IP);
        }

        return chain.filter(exchange);
    }

    /**
     * 重新包装并且返回错误信息
     *
     * @param exchange
     * @param responseStatusEnum
     * @return
     */
    public Mono<Void> renderErrorMsg(ServerWebExchange exchange,
                                     ResponseStatusEnum responseStatusEnum) {
        //1.获得响应response
        ServerHttpResponse response = exchange.getResponse();
        //2.构建jsonResult
        GraceJSONResult jsonResult = GraceJSONResult.exception(responseStatusEnum);
        //3.设置header类型
        if (!response.getHeaders().containsKey("Content-Type")) {
            response.getHeaders().add("Content-Type",
                    MimeTypeUtils.APPLICATION_JSON_VALUE);
        }
        //4.修改response的状态码code为500
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        //5.转换json并且向response中写入数据
        String resultJson = new Gson().toJson(jsonResult);
        DataBuffer buffer = response
                .bufferFactory()
                .wrap(resultJson.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    //过滤器的顺序，数字越小则优先级越大
    @Override
    public int getOrder() {
        return 10;
    }

}
