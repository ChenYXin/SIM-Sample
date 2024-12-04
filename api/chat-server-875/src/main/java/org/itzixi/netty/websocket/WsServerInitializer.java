package org.itzixi.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 初始化器，channel注册后，会执行里面的初始化方法
 */
public class WsServerInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        //通过SocketChannel获得对应的管道
        ChannelPipeline pipeline = channel.pipeline();

        /**
         * 通过管道，添加handler处理器
         */
        //HttpServerCodec 是由netty自己提供的助手类，此处可以理解为管道中的拦截器
        //当请求到服务端，我们需要进行做解码，相应到客户端做编码
        // websocket 基于http协议，所以需要有http的编解码器
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());

        //添加对大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());

        //对httpMessage进行聚合，聚合成为FullHttpRequest或FullHttpResponse
        //几乎在netty的编程中，都会使用到此handler
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        // ================== 以上是用于支持http协议相关的handler ==================
        // ================== 以下是用于支持websocket ==================

        /**
         * WebSocket 服务器处理的协议，用于指定给客户端连接的时候访问路由： /ws
         * 此handler会帮我们处理一些比较复杂的繁重的操作
         * 会处理一些握手操作：handShaking（close,ping,pong） ping+pong = 心跳
         * 对于WebSocket来说，数据都是以frames进行传输的，不同的数据类型所对应的frames也都不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //添加自定义的助手类
        pipeline.addLast(new ChatHandler());
    }
}
