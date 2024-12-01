package org.itzixi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tencentcloudapi.ciam.v20220331.models.User;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.itzixi.api.feign.FileMicroServiceFeign;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.enums.Sex;
import org.itzixi.mapper.UsersMapper;
import org.itzixi.pojo.Users;
import org.itzixi.service.IUsersService;
import org.itzixi.utils.DesensitizationUtil;
import org.itzixi.utils.LocalDateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Donkor
 * @since 2024-11-29
 */
@Service
public class UsersServiceImpl extends BaseInfoProperties implements IUsersService {

    @Resource
    private UsersMapper usersMapper;

    @Override
    public Users queryMobileIfExists(String mobile) {
        return usersMapper.selectOne(
                new QueryWrapper<Users>()
                        .eq("mobile", mobile));
    }

    @Transactional
    @Override
    public Users createUsers(String mobile, String nickName) {
        Users users = new Users();
        users.setMobile(mobile);
        String uuid = UUID.randomUUID().toString();
        String uuidStr[] = uuid.split("-");
        String wechatNum = "wx" + uuidStr[0] + uuidStr[1];
        users.setWechatNum(wechatNum);

        //二维码
        String wechatNumberUrl = getQrCodeUrl(wechatNum, TEMP_STRING);
        users.setWechatNumImg(wechatNumberUrl);

        //用户139******1234
        //DesensitizationUtil
        if (StringUtils.isBlank(nickName)) {
            users.setNickname("用户" + DesensitizationUtil.commonDisplay(mobile));
        }else{
            users.setNickname(nickName);
        }

        users.setRealName(nickName);

        users.setSex(Sex.secret.type);
        users.setFace("");
        users.setFriendCircleBg("");
        users.setEmail("");
        users.setBirthday(LocalDateUtils
                .parseLocalDate("1980-01-01",
                        LocalDateUtils.DATE_PATTERN));

        users.setCountry("中国");
        users.setProvince("");
        users.setCity("");
        users.setDistrict("");

        users.setCreatedTime(LocalDateTime.now());
        users.setUpdatedTime(LocalDateTime.now());

        usersMapper.insert(users);
        return users;
    }

    @Resource
    private FileMicroServiceFeign fileMicroServiceFeign;

    private String getQrCodeUrl(String wechatNumber,
                                String userId) {
        try {
            return fileMicroServiceFeign.generatorQrCode(wechatNumber, userId);
        } catch (Exception e) {
            return null;
        }
    }

}
