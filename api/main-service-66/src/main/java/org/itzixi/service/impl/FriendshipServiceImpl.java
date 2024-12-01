package org.itzixi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.mapper.FriendshipMapper;
import org.itzixi.mapper.FriendshipMapperCustom;
import org.itzixi.pojo.FriendRequest;
import org.itzixi.pojo.Friendship;
import org.itzixi.pojo.vo.ContactsVO;
import org.itzixi.service.IFriendshipService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FriendshipServiceImpl extends BaseInfoProperties implements IFriendshipService {

    @Resource
    private FriendshipMapper friendshipMapper;
    @Resource
    private FriendshipMapperCustom friendshipMapperCustom;

    @Override
    public Friendship getFriendship(String myId, String friendId) {
        QueryWrapper queryWrapper = new QueryWrapper<FriendRequest>()
                .eq("my_id", myId)
                .eq("friend_id", friendId);

        return friendshipMapper.selectOne(queryWrapper);
    }

    @Override
    public List<ContactsVO> getFriendship(String myId) {
        Map<String,Object> map=new HashMap<>();
        map.put("myId",myId);
        return friendshipMapperCustom.queryMyFriends(map);
    }
}
