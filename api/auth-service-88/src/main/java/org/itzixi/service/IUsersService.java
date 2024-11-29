package org.itzixi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.itzixi.pojo.Users;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Donkor
 * @since 2024-11-29
 */
public interface IUsersService{

    public Users queryMobileIfExists(String mobile);

    public Users createUsers(String mobile);
}
