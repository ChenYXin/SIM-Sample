package org.itzixi.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

/**
 * 创建自定义助手类
 */
//SimpleChannelInboundHandler : 对应请求来说，相当于入站（入境）
@Slf4j
public class HttpHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        //获取channel
        Channel channel = channelHandlerContext.channel();

        //打印客户端的远程地址
        log.info(channel.remoteAddress().toString());
//        System.out.println(channel.remoteAddress());

        //通过缓冲区定义发送的消息，读写数据都是通过缓冲区进行数据交换的
        ByteBuf content = Unpooled.copiedBuffer("Hello netty", CharsetUtil.UTF_8);

        //构建http的response
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                content);

        //为响应添加数据类型和数据长度
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

        //把响应数据写到缓冲区再刷到客户端
        channelHandlerContext.writeAndFlush(response);
    }
}
