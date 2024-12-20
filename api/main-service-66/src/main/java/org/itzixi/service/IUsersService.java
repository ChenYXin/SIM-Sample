package org.itzixi.service;

import org.itzixi.pojo.Users;
import org.itzixi.pojo.bo.ModifyUserBO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Donkor
 * @since 2024-11-29
 */
public interface IUsersService {

    public void modifyUserInfo(ModifyUserBO userBO);

    public Users getById(String userId);

    public Users getByWechatNumberOrMobile(String queryString);
}
