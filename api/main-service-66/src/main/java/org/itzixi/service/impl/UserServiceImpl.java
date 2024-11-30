package org.itzixi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.exceptions.GraceException;
import org.itzixi.grace.result.ResponseStatusEnum;
import org.itzixi.mapper.UsersMapper;
import org.itzixi.pojo.Users;
import org.itzixi.pojo.bo.ModifyUserBO;
import org.itzixi.service.IUsersService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends BaseInfoProperties implements IUsersService {

    @Resource
    private UsersMapper usersMapper;

    @Transactional
    @Override
    public void modifyUserInfo(ModifyUserBO userBO) {
        Users pendingUser = new Users();
        String userId = userBO.getUserId();
        if (StringUtils.isBlank(userId)) {
            GraceException.display(ResponseStatusEnum.USER_INFO_UPDATED_ERROR);
        }
        Users users = usersMapper.selectById(userId);
//        Users users = usersMapper.selectOne(new QueryWrapper<Users>().eq("id",userId));
        if (users == null) {
            GraceException.display(ResponseStatusEnum.USER_NOT_EXIST_ERROR);
        }
        pendingUser.setId(userId);
        pendingUser.setUpdatedTime(LocalDateTime.now());

        BeanUtils.copyProperties(userBO, pendingUser);
        usersMapper.updateById(pendingUser);
    }

    @Override
    public Users getById(String userId) {
         return usersMapper.selectById(userId);
    }
}
