package org.itzixi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.mapper.FriendCircleLikedMapper;
import org.itzixi.mapper.FriendCircleMapper;
import org.itzixi.mapper.FriendCircleMapperCustom;
import org.itzixi.pojo.FriendCircle;
import org.itzixi.pojo.FriendCircleLiked;
import org.itzixi.pojo.Users;
import org.itzixi.pojo.bo.FriendCircleBO;
import org.itzixi.pojo.vo.FriendCircleVO;
import org.itzixi.service.IFriendCircleService;
import org.itzixi.service.IUsersService;
import org.itzixi.utils.PagedGridResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FriendCircleServiceImpl extends BaseInfoProperties implements IFriendCircleService {

    @Resource
    FriendCircleMapper friendCircleMapper;
    @Resource
    FriendCircleMapperCustom friendCircleMapperCustom;
    @Resource
    FriendCircleLikedMapper friendCircleLikedMapper;
    @Resource
    IUsersService usersService;

    @Override
    public void publish(FriendCircleBO friendCircleBO) {

        FriendCircle pendingFriendCircle = new FriendCircle();

        BeanUtils.copyProperties(friendCircleBO, pendingFriendCircle);

        friendCircleMapper.insert(pendingFriendCircle);
    }

    @Override
    public PagedGridResult queryList(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        //设置分页参数
        Page<FriendCircleVO> pageInfo = new Page<>(page, pageSize);
        friendCircleMapperCustom.queryFriendCircleList(pageInfo, map);

        return setterPagedGridPlus(pageInfo);
    }

    @Transactional
    @Override
    public void like(String friendCircleId, String userId) {
        //根据朋友圈的主键ID查询归属人（发布人）
        FriendCircle friendCircle = this.selectFriendCircle(friendCircleId);
        //根据用户主键ID查询用户
        Users users = usersService.getById(userId);

        FriendCircleLiked circleLiked = new FriendCircleLiked();
        circleLiked.setFriendCircleId(friendCircleId);
        circleLiked.setBelongUserId(friendCircle.getUserId());
        circleLiked.setLikedUserId(userId);
        circleLiked.setLikedUserName(users.getNickname());
        circleLiked.setCreatedTime(LocalDateTime.now());

        friendCircleLikedMapper.insert(circleLiked);

        //点赞过后，朋友圈的对应点赞数累加1
        redis.increment(REDIS_FRIEND_CIRCLE_LIKED_COUNTS + ":" + friendCircleId, 1);
        //标记哪个用户点赞过该朋友圈
        redis.setnx(REDIS_DOES_USER_LIKE_FRIEND_CIRCLE + ":" + friendCircleId + ":" + userId, userId);
    }

    @Transactional
    @Override
    public void unLike(String friendCircleId, String userId) {
        ///从数据库中删除点赞关系
        QueryWrapper<FriendCircleLiked> deleteWrapper = new QueryWrapper<FriendCircleLiked>()
                .eq("friend_circle_id", friendCircleId)
                .eq("liked_user_id", userId);
        friendCircleLikedMapper.delete(deleteWrapper);

        //取消点赞过后，朋友圈的对应点赞数累减1
        redis.decrement(REDIS_FRIEND_CIRCLE_LIKED_COUNTS + ":" + friendCircleId, 1);
        //删除标记的那个用户点赞过的朋友圈
        redis.del(REDIS_DOES_USER_LIKE_FRIEND_CIRCLE + ":" + friendCircleId + ":" + userId);
    }

    @Override
    public List<FriendCircleLiked> queryLikedFriends(String friendCircleId) {
        QueryWrapper<FriendCircleLiked> queryWrapper=new QueryWrapper<FriendCircleLiked>()
                .eq("friend_circle_id", friendCircleId);

        return friendCircleLikedMapper.selectList(queryWrapper);
    }

    @Override
    public Boolean doILike(String friendCircleId, String userId) {
        String isExist=redis.get(REDIS_DOES_USER_LIKE_FRIEND_CIRCLE + ":" + friendCircleId + ":" + userId);
        return StringUtils.isNotBlank(isExist);
    }

    private FriendCircle selectFriendCircle(String friendCircleId) {
        return friendCircleMapper.selectById(friendCircleId);
    }
}
