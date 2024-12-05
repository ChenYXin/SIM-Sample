package org.itzixi.controller;

import jakarta.annotation.Resource;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.service.IChatMessagesService;
import org.itzixi.utils.PagedGridResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController extends BaseInfoProperties {
    @Resource
    private IChatMessagesService chatMessagesService;

    @PostMapping("/getMyUnReadCounts")
    public GraceJSONResult getMyUnReadCounts(String myId) {
        Map<Object, Object> map = redis.hgetall(CHAT_MSG_LIST + ":" + myId);
        return GraceJSONResult.ok(map);
    }

    @PostMapping("/clearMyUnReadCounts")
    public GraceJSONResult clearMyUnReadCounts(String myId, String oppositeId) {
        redis.setHashValue(CHAT_MSG_LIST + ":" + myId, oppositeId, "0");
        return GraceJSONResult.ok();
    }

    @PostMapping("/list/{senderId}/{receiverId}")
    public GraceJSONResult list(@PathVariable("senderId") String senderId,
                                @PathVariable("receiverId") String receiverId,
                                Integer page,
                                Integer pageSize) {

        if (page == null) page = 1;
        if (pageSize == null) pageSize = 20;

        PagedGridResult gridResult = chatMessagesService.queryChatMsgList(senderId, receiverId, page, pageSize);

        return GraceJSONResult.ok(gridResult);
    }

    @PostMapping("/signRead/{msgId}")
    public GraceJSONResult clearMyUnReadCounts(@PathVariable("msgId") String msgId) {
        chatMessagesService.updateMsgSignRead(msgId);
        return GraceJSONResult.ok();
    }

}
