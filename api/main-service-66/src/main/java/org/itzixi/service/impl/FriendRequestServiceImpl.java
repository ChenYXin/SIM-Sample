package org.itzixi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.enums.FriendRequestVerifyStatus;
import org.itzixi.mapper.FriendRequestMapper;
import org.itzixi.pojo.FriendRequest;
import org.itzixi.pojo.bo.NewFriendRequestBO;
import org.itzixi.service.IFriendRequestService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class FriendRequestServiceImpl extends BaseInfoProperties implements IFriendRequestService {

    @Resource
    private FriendRequestMapper friendRequestMapper;

    @Transactional
    @Override
    public void addNewRequest(NewFriendRequestBO friendRequestBO) {
        //先删除以前的记录
        QueryWrapper deleteWrapper = new QueryWrapper<FriendRequest>()
                .eq("my_id",friendRequestBO.getMyId())
                .eq("friend_id",friendRequestBO.getFriendId());

        friendRequestMapper.delete(deleteWrapper);

        //再新增记录
        FriendRequest pendingFriendRequest = new FriendRequest();
        BeanUtils.copyProperties(friendRequestBO, pendingFriendRequest);

        pendingFriendRequest.setVerifyStatus(FriendRequestVerifyStatus.WAIT.type);
        pendingFriendRequest.setRequestTime(LocalDateTime.now());

        friendRequestMapper.insert(pendingFriendRequest);
    }
}
