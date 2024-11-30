package org.itzixi.controller;

import jakarta.annotation.Resource;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.pojo.bo.ModifyUserBO;
import org.itzixi.service.IUsersService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userinfo")
public class UserController {

    @Resource
    private IUsersService usersService;

    @PostMapping("/modify")
    public GraceJSONResult modify(@RequestBody ModifyUserBO modifyUserBO) {
        usersService.modifyUserInfo(modifyUserBO);

        return GraceJSONResult.ok();
    }
}
