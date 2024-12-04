package org.itzixi.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 创建自定义助手类
 */
//SimpleChannelInboundHandler : 对应请求来说，相当于入站（入境）
//TextWebSocketFrame : 用于为WebSocket专门处理的文本数据对象，Frame是数据（消息）的载体
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获得客户端传输过来的消息
        String content = msg.text();
        System.out.println("接收到的数据：" + content);

        //获取channel
        Channel currentChannel = ctx.channel();
        String currentChannelId = currentChannel.id().asLongText();
        String currentChannelIdShort = currentChannel.id().asShortText();

        System.out.println("客户端currentChannelId：" + currentChannelId);
        System.out.println("客户端currentChannelIdShort：" + currentChannelIdShort);

        currentChannel.writeAndFlush(new TextWebSocketFrame(currentChannelId));
    }
}
