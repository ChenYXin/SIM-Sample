package org.itzixi.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.grace.result.ResponseStatusEnum;
import org.itzixi.pojo.bo.FriendCircleBO;
import org.itzixi.service.IFriendCircleService;
import org.itzixi.utils.PagedGridResult;
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

    @PostMapping("/queryList")
    public GraceJSONResult queryList(@RequestParam String userId,
                                     @RequestParam(defaultValue = "1", name = "page") Integer page,
                                     @RequestParam(defaultValue = "10", name = "pageSize") Integer pageSize) {
        if (userId == null) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.USER_NOT_EXIST_ERROR);
        }
        PagedGridResult gridResult=friendCircleService.queryList(userId, page, pageSize);

        return GraceJSONResult.ok(gridResult);
    }
}
