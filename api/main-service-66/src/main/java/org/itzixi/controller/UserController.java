package org.itzixi.controller;

import jakarta.annotation.Resource;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.pojo.Users;
import org.itzixi.pojo.bo.ModifyUserBO;
import org.itzixi.pojo.vo.UsersVO;
import org.itzixi.service.IUsersService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/userinfo")
public class UserController extends BaseInfoProperties {

    @Resource
    private IUsersService usersService;

    @PostMapping("/modify")
    public GraceJSONResult modify(@RequestBody ModifyUserBO userBO) {
        //修改用户信息
        usersService.modifyUserInfo(userBO);
        //返回最新用户信息
        UsersVO usersVO = getUserInfo(userBO.getUserId(), true);
        return GraceJSONResult.ok(usersVO);
    }

    private UsersVO getUserInfo(String userId, boolean needToken) {
        Users latestUsers = usersService.getById(userId);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(latestUsers, usersVO);

        if (needToken) {
            String uToken = TOKEN_USER_PREFIX + SYMBOL_DOT + UUID.randomUUID();
            redis.set(REDIS_USER_TOKEN + ":" + userId, uToken);//设置分布式会话
            usersVO.setUserToken(uToken);
        }
        return usersVO;
    }

    @PostMapping("/updateFace")
    public GraceJSONResult updateFace(@RequestParam("userId") String userId,
                                      @RequestParam("face") String face) {
        ModifyUserBO userBO = new ModifyUserBO();
        userBO.setUserId(userId);
        userBO.setFace(face);

        //修改用户信息
        usersService.modifyUserInfo(userBO);
        //返回最新用户信息
        UsersVO usersVO = getUserInfo(userBO.getUserId(), true);

        return GraceJSONResult.ok(usersVO);
    }

    @PostMapping("/updateFriendCircleBg")
    public GraceJSONResult updateFriendCircleBg(@RequestParam("userId") String userId,
                                                @RequestParam("friendCircleBg") String friendCircleBg) {
        ModifyUserBO userBO = new ModifyUserBO();
        userBO.setUserId(userId);
        userBO.setFriendCircleBg(friendCircleBg);

        //修改用户信息
        usersService.modifyUserInfo(userBO);
        //返回最新用户信息
        UsersVO usersVO = getUserInfo(userBO.getUserId(), true);

        return GraceJSONResult.ok(usersVO);
    }

    @PostMapping("/updateChatBg")
    public GraceJSONResult updateChatBg(@RequestParam("userId") String userId,
                                                @RequestParam("chatBg") String chatBg) {
        ModifyUserBO userBO = new ModifyUserBO();
        userBO.setUserId(userId);
        userBO.setChatBg(chatBg);

        //修改用户信息
        usersService.modifyUserInfo(userBO);
        //返回最新用户信息
        UsersVO usersVO = getUserInfo(userBO.getUserId(), true);

        return GraceJSONResult.ok(usersVO);
    }
}
