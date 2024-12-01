package org.itzixi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.mapper.FriendshipMapper;
import org.itzixi.pojo.FriendRequest;
import org.itzixi.pojo.Friendship;
import org.itzixi.service.IFriendshipService;
import org.springframework.stereotype.Service;

@Service
public class FriendshipServiceImpl extends BaseInfoProperties implements IFriendshipService {

    @Resource
    private FriendshipMapper friendshipMapper;

    @Override
    public Friendship getFriendship(String myId, String friendId) {
        QueryWrapper queryWrapper = new QueryWrapper<FriendRequest>()
                .eq("my_id", myId)
                .eq("friend_id", friendId);

        return friendshipMapper.selectOne(queryWrapper);
    }
}
