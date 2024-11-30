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


}