package org.itzixi.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 初始化器，channel注册后，会执行里面的初始化方法
 */
public class HttpServerInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        //通过SocketChannel获得对应的管道
        ChannelPipeline pipeline = channel.pipeline();

        /**
         * 通过管道，添加handler处理器
         */
        //HttpServerCodec 是由netty自己提供的助手类，此处可以理解为管道中的拦截器
        //当请求到服务端，我们需要进行做解码，相应到客户端做编码
        pipeline.addLast("", new HttpServerCodec());
        //添加自定义的助手类，当请求访问，返回“hello netty”
        pipeline.addLast("", null);
    }
}
