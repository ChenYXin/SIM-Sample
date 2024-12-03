package org.itzixi.service.impl;

import jakarta.annotation.Resource;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.mapper.FriendCircleMapper;
import org.itzixi.pojo.FriendCircle;
import org.itzixi.pojo.bo.FriendCircleBO;
import org.itzixi.service.IFriendCircleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class FriendCircleServiceImpl extends BaseInfoProperties implements IFriendCircleService {

    @Resource
    FriendCircleMapper friendCircleMapper;

    @Override
    public void publish(FriendCircleBO friendCircleBO) {

        FriendCircle pendingFriendCircle = new FriendCircle();

        BeanUtils.copyProperties(friendCircleBO, pendingFriendCircle);

        friendCircleMapper.insert(pendingFriendCircle);
    }
}
