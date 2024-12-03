package org.itzixi.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.pojo.bo.FriendCircleBO;
import org.itzixi.service.IFriendCircleService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/friendCircle")
public class FriendCircleController extends BaseInfoProperties {

    @Resource
    private IFriendCircleService friendCircleService;

    @PostMapping("/publish")
    public GraceJSONResult publish(@RequestBody FriendCircleBO friendCircleBO,
                                   HttpServletRequest request) {

        String userId = request.getHeader(HEADER_USER_ID);
        friendCircleBO.setUserId(userId);
        friendCircleBO.setPublishTime(LocalDateTime.now());

        friendCircleService.publish(friendCircleBO);

        return GraceJSONResult.ok();
    }
}
