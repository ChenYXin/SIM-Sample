package org.itzixi.service;

import org.itzixi.pojo.netty.ChatMsg;
import org.itzixi.utils.PagedGridResult;
import org.springframework.web.bind.annotation.PathVariable;

public interface IChatMessagesService {

    public void saveMsg(ChatMsg chatMsg);

    public PagedGridResult queryChatMsgList(String senderId,
                                            String receiverId,
                                            Integer page,
                                            Integer pageSize);
}
