package org.itzixi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.enums.YesOrNo;
import org.itzixi.mapper.FriendshipMapper;
import org.itzixi.mapper.FriendshipMapperCustom;
import org.itzixi.pojo.FriendRequest;
import org.itzixi.pojo.Friendship;
import org.itzixi.pojo.vo.ContactsVO;
import org.itzixi.service.IFriendshipService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<ContactsVO> getFriendship(String myId, boolean needBlack) {
        Map<String, Object> map = new HashMap<>();
        map.put("myId", myId);
        map.put("needBlack", needBlack);
        return friendshipMapperCustom.queryMyFriends(map);
    }

    @Override
    public void updateFriendRemark(String myId, String friendId, String remark) {

        QueryWrapper<Friendship> updateWrapper = new QueryWrapper<Friendship>()
                .eq("my_id", myId)
                .eq("friend_id", friendId);

        Friendship friendship = new Friendship();
        friendship.setFriendRemark(remark);
        friendship.setUpdatedTime(LocalDateTime.now());

        friendshipMapper.update(friendship, updateWrapper);
    }

    @Override
    public void updateBlackList(String myId, String friendId, YesOrNo yesOrNo) {
        QueryWrapper<Friendship> updateWrapper = new QueryWrapper<Friendship>()
                .eq("my_id", myId)
                .eq("friend_id", friendId);

        Friendship friendship = new Friendship();
        friendship.setIsBlack(yesOrNo.type);
        friendship.setUpdatedTime(LocalDateTime.now());

        friendshipMapper.update(friendship, updateWrapper);
    }

    @Override
    public void delete(String myId, String friendId) {
        QueryWrapper<Friendship> deleteWrapper1 = new QueryWrapper<Friendship>()
                .eq("my_id", myId)
                .eq("friend_id", friendId);

        friendshipMapper.delete(deleteWrapper1);

        QueryWrapper<Friendship> deleteWrapper2 = new QueryWrapper<Friendship>()
                .eq("my_id", friendId)
                .eq("friend_id", myId);

        friendshipMapper.delete(deleteWrapper2);
    }

    @Override
    public boolean isBlackEachOther(String friendId1st, String friendId2nd) {
        QueryWrapper<Friendship> queryWrapper1 = new QueryWrapper<Friendship>()
                .eq("my_id", friendId1st)
                .eq("friend_id", friendId2nd)
                .eq("is_black", YesOrNo.YES.type);
        Friendship friendship1st = friendshipMapper.selectOne(queryWrapper1);

        QueryWrapper<Friendship> queryWrapper2 = new QueryWrapper<Friendship>()
                .eq("my_id", friendId2nd)
                .eq("friend_id", friendId1st)
                .eq("is_black", YesOrNo.YES.type);
        Friendship friendship2nd = friendshipMapper.selectOne(queryWrapper2);

        return friendship1st != null || friendship2nd != null;
    }
}
