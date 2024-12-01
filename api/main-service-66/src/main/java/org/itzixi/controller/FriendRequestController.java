package org.itzixi.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.grace.result.GraceJSONResult;
import org.itzixi.pojo.bo.NewFriendRequestBO;
import org.itzixi.service.IFriendRequestService;
import org.itzixi.utils.PagedGridResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friendRequest")
public class FriendRequestController extends BaseInfoProperties {

    @Resource
    private IFriendRequestService friendRequestService;

    @PostMapping("/add")
    public GraceJSONResult add(@RequestBody @Valid NewFriendRequestBO friendRequestBO) {
        friendRequestService.addNewRequest(friendRequestBO);
        return GraceJSONResult.ok();
    }

    @PostMapping("/queryNew")
    public GraceJSONResult queryNew(HttpServletRequest request,
                                    @RequestParam(name = "page", defaultValue = "1") Integer page,
                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        String userId = request.getHeader(HEADER_USER_ID);

        PagedGridResult result = friendRequestService.queryNewFriendList(userId, page, pageSize);
        return GraceJSONResult.ok(result);
    }


}
