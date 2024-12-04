package org.itzixi.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.itzixi.enums.MsgTypeEnum;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.pojo.netty.ChatMsg;
import org.itzixi.pojo.netty.DataContent;
import org.itzixi.utils.JsonUtils;
import org.itzixi.utils.LocalDateUtils;
import org.itzixi.utils.OkHttpUtil;

import java.time.LocalDateTime;
import java.util.List;

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

        //1.获取客户端发来的消息并且解析
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        ChatMsg chatMsg = dataContent.getChatMsg();

        String msgText = chatMsg.getMsg();
        String receiverId = chatMsg.getReceiverId();
        String senderId = chatMsg.getSenderId();

        //判断是否黑名单 start
        //如果双方只要有一方是黑名单，则终止发送
        GraceJSONResult result = OkHttpUtil.get("http://127.0.0.1:1000/friendship/isBlack?friendId1st=" + receiverId
                + "&friendId2nd=" + senderId);
        boolean isBlack = (Boolean) result.getData();
        System.out.println("当前的黑名单关系为：" + isBlack);
        if (isBlack) {
            return;
        }
        //判断是否黑名单 end

        //时间校准，以服务器时间为准
        chatMsg.setChatTime(LocalDateTime.now());

        Integer msgType = chatMsg.getMsgType();

        //获取channel
        Channel currentChannel = ctx.channel();
        String currentChannelId = currentChannel.id().asLongText();
        String currentChannelIdShort = currentChannel.id().asShortText();

        //System.out.println("客户端currentChannelId：" + currentChannelId);
        //System.out.println("客户端currentChannelIdShort：" + currentChannelIdShort);

        //2.判断消息类型，根据不同的类型来处理不同的业务
        if (msgType == MsgTypeEnum.CONNECT_INIT.type) {
            //当WebSocket初次open的时候，初始化channel，把channel和用户userId关联起来
            UserChannelSession.putMultiChannels(senderId, currentChannel);
            UserChannelSession.putUserChannelIdRelation(currentChannelId, senderId);
        } else if (msgType == MsgTypeEnum.WORDS.type ||
                msgType == MsgTypeEnum.IMAGE.type ||
                msgType == MsgTypeEnum.VIDEO.type ||
                msgType == MsgTypeEnum.VOICE.type) {
            //发送消息
            List<Channel> receiverChannels = UserChannelSession.getMultiChannels(receiverId);
            if (receiverChannels == null || receiverChannels.size() == 0 || receiverChannels.isEmpty()) {
                //multiChannels 为空，表示用户离线/断线状态，消息不需要发送，后续可以存储到数据库
                chatMsg.setIsReceiverOnLine(false);
            } else {
                chatMsg.setIsReceiverOnLine(true);
                //当multiChannels不为空的时候，同账户多端设备接受消息
                for (Channel c : receiverChannels) {
                    Channel findChannel = clients.find(c.id());
                    if (findChannel != null) {
                        if (msgType == MsgTypeEnum.VOICE.type) {
                            chatMsg.setIsRead(false);
                        }
                        dataContent.setChatMsg(chatMsg);
                        String dataTimeFormat = LocalDateUtils.format(
                                chatMsg.getChatTime(),
                                LocalDateUtils.DATETIME_PATTERN_2);
                        dataContent.setChatTime(dataTimeFormat);
                        //发送消息给在线的用户
                        findChannel.writeAndFlush(
                                new TextWebSocketFrame(
                                        JsonUtils.objectToJson(dataContent)));

                    }
                }
            }
        }

        List<Channel> myOtherChannels = UserChannelSession.getMyOtherChannels(senderId, currentChannelId);
        for (Channel c : myOtherChannels) {
            Channel findChannel = clients.find(c.id());
            if (findChannel != null) {
                dataContent.setChatMsg(chatMsg);
                String dataTimeFormat = LocalDateUtils.format(
                        chatMsg.getChatTime(),
                        LocalDateUtils.DATETIME_PATTERN_2);
                dataContent.setChatTime(dataTimeFormat);
                //发送消息给在线的用户
                findChannel.writeAndFlush(
                        new TextWebSocketFrame(
                                JsonUtils.objectToJson(dataContent)));
                //同步消息给在线的其他设备端
                findChannel.writeAndFlush(
                        new TextWebSocketFrame(
                                JsonUtils.objectToJson(dataContent)));
            }
        }

        //currentChannel.writeAndFlush(new TextWebSocketFrame(currentChannelId));

        //群发
        //clients.writeAndFlush(new TextWebSocketFrame(currentChannelId));

        UserChannelSession.outputMulti();
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
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel currentChannel = ctx.channel();
        String currentChannelId = currentChannel.id().asLongText();
        System.out.println("客户端移除连接，channel对应的长id为：" + currentChannelId);

        //移除多余的会话
        String userId = UserChannelSession.getUserIdByChannelId(currentChannelId);
        UserChannelSession.removeUnlessChannels(userId, currentChannelId);

        clients.remove(currentChannel);
    }

    /**
     * 发生异常并捕获，移除channel
     *
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

        //移除多余的会话
        String userId = UserChannelSession.getUserIdByChannelId(currentChannelId);
        UserChannelSession.removeUnlessChannels(userId, currentChannelId);
    }
}
