package org.itzixi.controller;

import jakarta.annotation.Resource;
import org.apache.curator.framework.CuratorFramework;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.pojo.netty.NettyServerNode;
import org.itzixi.service.IChatMessagesService;
import org.itzixi.utils.JsonUtils;
import org.itzixi.utils.PagedGridResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Resource(name = "curatorClient")
    private CuratorFramework zkClient;

    @PostMapping("/getNettyOnlineInfo")
    public GraceJSONResult getNettyOnlineInfo() throws Exception {
        //从zookeeper中获得当前已注册的netty服务列表
        String path = "server-list";
        List<String> list = zkClient.getChildren().forPath(path);
        //System.out.println(list);

        List<NettyServerNode> serverNodeList = new ArrayList<>();
        for (String node : list) {
            String nodeValue = new String(zkClient.getData().forPath(path + "/" + node));
            //System.out.println(nodeValue);
            NettyServerNode serverNode = JsonUtils.jsonToPojo(nodeValue, NettyServerNode.class);
            serverNodeList.add(serverNode);
        }
        //计算当前哪个zk的node时最少连接数，获得【ip:port】并且返回给前端
        Optional<NettyServerNode> minNodeOptional = serverNodeList.stream().min(Comparator.comparing(nettyServerNode ->
                nettyServerNode.getOnlineCounts()
        ));

        NettyServerNode minNode = minNodeOptional.get();

        return GraceJSONResult.ok(minNode);
    }
}
