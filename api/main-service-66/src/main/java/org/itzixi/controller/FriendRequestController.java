package org.itzixi.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.pojo.bo.NewFriendRequestBO;
import org.itzixi.service.IFriendRequestService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friendRequest")
public class FriendRequestController {

    @Resource
    private IFriendRequestService friendRequestService;

    @PostMapping("/add")
    public GraceJSONResult add(@RequestBody @Valid NewFriendRequestBO friendRequestBO) {
        friendRequestService.addNewRequest(friendRequestBO);
        return GraceJSONResult.ok();
    }
}
