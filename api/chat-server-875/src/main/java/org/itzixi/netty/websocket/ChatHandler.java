package org.itzixi.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 创建自定义助手类
 */
//SimpleChannelInboundHandler : 对应请求来说，相当于入站（入境）
//TextWebSocketFrame : 用于为WebSocket专门处理的文本数据对象，Frame是数据（消息）的载体
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //用于记录和管理所有客户端的channel组
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

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

    /**
     * 客户端连接到服务端之后（打开连接）
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel currentChannel = ctx.channel();
        String currentChannelId = currentChannel.id().asLongText();
        System.out.println("客户端建立连接，channel对应的长id为：" + currentChannelId);

        //获得客户端的channel，并且存入到ChannelGroup中进行管理（作为一个客户端群组）
        clients.add(currentChannel);
    }

    /**
     * 关闭连接，移除channel
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel currentChannel = ctx.channel();
        String currentChannelId = currentChannel.id().asLongText();
        System.out.println("客户端移除连接，channel对应的长id为：" + currentChannelId);

        clients.remove(currentChannel);
    }

    /**
     * 发生异常并捕获，移除channel
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel currentChannel = ctx.channel();
        String currentChannelId = currentChannel.id().asLongText();
        System.out.println("发生异常捕获，channel对应的长id为：" + currentChannelId);

        //发生异常之后关闭连接（关闭channel）
        ctx.channel().close();
        //随后从ChannelGroup中移除对应的channel
        clients.remove(currentChannel);
    }
}
