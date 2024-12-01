package org.itzixi.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.grace.result.ResponseStatusEnum;
import org.itzixi.pojo.Friendship;
import org.itzixi.service.IFriendshipService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friendship")
@Slf4j
public class FriendshipController extends BaseInfoProperties {

    @Resource
    private IFriendshipService friendshipService;

    @PostMapping("/getFriendship")
    public GraceJSONResult getFriendship(String friendId, HttpServletRequest request) {
        String myId = request.getHeader(HEADER_USER_ID);
        if (myId.equals(friendId)) {
            //不能查询自己
        }

        Friendship friendship = friendshipService.getFriendship(myId, friendId);
        return GraceJSONResult.ok(friendship);
    }
}
