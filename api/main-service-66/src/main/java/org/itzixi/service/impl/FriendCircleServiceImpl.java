package org.itzixi.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.mapper.FriendCircleMapper;
import org.itzixi.mapper.FriendCircleMapperCustom;
import org.itzixi.pojo.FriendCircle;
import org.itzixi.pojo.bo.FriendCircleBO;
import org.itzixi.pojo.vo.FriendCircleVO;
import org.itzixi.service.IFriendCircleService;
import org.itzixi.utils.PagedGridResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FriendCircleServiceImpl extends BaseInfoProperties implements IFriendCircleService {

    @Resource
    FriendCircleMapper friendCircleMapper;
    @Resource
    FriendCircleMapperCustom friendCircleMapperCustom;

    @Override
    public void publish(FriendCircleBO friendCircleBO) {

        FriendCircle pendingFriendCircle = new FriendCircle();

        BeanUtils.copyProperties(friendCircleBO, pendingFriendCircle);

        friendCircleMapper.insert(pendingFriendCircle);
    }

    @Override
    public PagedGridResult queryList(String userId, Integer page, Integer pageSize) {
        Map<String,Object> map =new HashMap<>();
        map.put("userId",userId);

        //设置分页参数
        Page<FriendCircleVO> pageInfo=new Page<>(page,pageSize);
        friendCircleMapperCustom.queryFriendCircleList(pageInfo,map);

        return setterPagedGridPlus(pageInfo);
    }
}
