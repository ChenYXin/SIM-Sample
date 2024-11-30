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

public class RenderErrorUtils extends BaseInfoProperties {

    /**
     * 重新包装并且返回错误信息
     *
     * @param exchange
     * @param responseStatusEnum
     * @return
     */
    public static Mono<Void> display(ServerWebExchange exchange,
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


}
