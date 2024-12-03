package org.itzixi.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.grace.result.ResponseStatusEnum;
import org.itzixi.pojo.FriendCircleLiked;
import org.itzixi.pojo.bo.FriendCircleBO;
import org.itzixi.pojo.vo.FriendCircleVO;
import org.itzixi.service.IFriendCircleService;
import org.itzixi.utils.PagedGridResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

        PagedGridResult gridResult = friendCircleService.queryList(userId, page, pageSize);

        List<FriendCircleVO> list = (List<FriendCircleVO>) gridResult.getRows();
        for (FriendCircleVO f : list) {
            String fiendCircleId = f.getFriendCircleId();
            List<FriendCircleLiked> likedList = friendCircleService.queryLikedFriends(fiendCircleId);
            f.setLikedFriends(likedList);

            boolean res=friendCircleService.doILike(fiendCircleId,userId);
            f.setDoILike(res);
        }

        return GraceJSONResult.ok(gridResult);
    }


    @PostMapping("/unlike")
    public GraceJSONResult unlike(@RequestParam String friendCircleId,
                                  HttpServletRequest request) {
        String userId = request.getHeader(HEADER_USER_ID);
        if (userId == null) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.USER_NOT_EXIST_ERROR);
        }
        friendCircleService.unLike(friendCircleId, userId);
        return GraceJSONResult.ok();
    }

    @PostMapping("/like")
    public GraceJSONResult like(@RequestParam String friendCircleId,
                                HttpServletRequest request) {
        String userId = request.getHeader(HEADER_USER_ID);
        if (userId == null) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.USER_NOT_EXIST_ERROR);
        }

        friendCircleService.like(friendCircleId, userId);

        return GraceJSONResult.ok();
    }
}
