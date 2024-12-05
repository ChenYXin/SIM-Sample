package org.itzixi.service.impl;

import jakarta.annotation.Resource;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.mapper.ChatMessageMapper;
import org.itzixi.pojo.ChatMessage;
import org.itzixi.pojo.netty.ChatMsg;
import org.itzixi.service.IChatMessagesService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatMessagesServiceImpl extends BaseInfoProperties implements IChatMessagesService {

    @Resource
    private ChatMessageMapper chatMessageMapper;

    @Transactional
    @Override
    public void saveMsg(ChatMsg chatMsg) {
        ChatMessage message = new ChatMessage();
        BeanUtils.copyProperties(chatMsg, message);

        //手动设置聊天信息的主键ID
        message.setId(chatMsg.getMsgId());

        chatMessageMapper.insert(message);

        String receiverId = chatMsg.getReceiverId();
        String senderId = chatMsg.getSenderId();

        //通过redis累加信息接受者的对应记录
        redis.incrementHash(CHAT_MSG_LIST + ":" + receiverId, senderId, 1);
    }
}
