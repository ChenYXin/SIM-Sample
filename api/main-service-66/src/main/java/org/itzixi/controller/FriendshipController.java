package org.itzixi.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.enums.YesOrNo;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.grace.result.ResponseStatusEnum;
import org.itzixi.pojo.Friendship;
import org.itzixi.pojo.vo.ContactsVO;
import org.itzixi.service.IFriendshipService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    /**
     * 通过好友关系表查询我的好友，即通讯录列表
     */
    @PostMapping("/queryMyFriends")
    public GraceJSONResult queryMyFriends(HttpServletRequest request) {
        String myId = request.getHeader(HEADER_USER_ID);

        List<ContactsVO> list = friendshipService.getFriendship(myId, false);
        return GraceJSONResult.ok(list);
    }

    @PostMapping("/updateFriendRemark")
    public GraceJSONResult updateFriendRemark(HttpServletRequest request,
                                              String friendId,
                                              String friendRemark) {
        if (StringUtils.isBlank(friendId) || StringUtils.isBlank(friendRemark)) {
            return GraceJSONResult.error();
        }
        String myId = request.getHeader(HEADER_USER_ID);
        friendshipService.updateFriendRemark(myId, friendId, friendRemark);
        return GraceJSONResult.ok();
    }

    @PostMapping("/tobeBlack")
    public GraceJSONResult tobeBlack(HttpServletRequest request,
                                     String friendId) {
        if (StringUtils.isBlank(friendId)) {
            return GraceJSONResult.error();
        }
        String myId = request.getHeader(HEADER_USER_ID);
        friendshipService.updateBlackList(myId, friendId, YesOrNo.YES);
        return GraceJSONResult.ok();
    }

    @PostMapping("/moveOutBlack")
    public GraceJSONResult moveOutBlack(HttpServletRequest request,
                                        String friendId) {
        if (StringUtils.isBlank(friendId)) {
            return GraceJSONResult.error();
        }
        String myId = request.getHeader(HEADER_USER_ID);
        friendshipService.updateBlackList(myId, friendId, YesOrNo.NO);
        return GraceJSONResult.ok();
    }

    @PostMapping("/queryMyBlackList")
    public GraceJSONResult queryMyBlackList(HttpServletRequest request) {
        String myId = request.getHeader(HEADER_USER_ID);
        List<ContactsVO> list = friendshipService.getFriendship(myId, true);
        return GraceJSONResult.ok(list);
    }

    @PostMapping("/delete")
    public GraceJSONResult delete(HttpServletRequest request,
                                  String friendId) {
        if (StringUtils.isBlank(friendId)) {
            return GraceJSONResult.error();
        }
        String myId = request.getHeader(HEADER_USER_ID);
        friendshipService.delete(myId, friendId);
        return GraceJSONResult.ok();
    }
}
