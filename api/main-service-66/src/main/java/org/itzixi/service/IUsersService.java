package org.itzixi.service;

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
}
