package org.itzixi.enums;

/**
 * @Description: 请求被好友审核的状态 枚举
 */
public enum FriendRequestVerifyStatus {

	// 请求被好友审核的状态，0-待审核；1-已添加，2-已过期

	WAIT(0, "待审核"),
	SUCCESS(1, "已添加"),
	EXPIRE(2, "已过期");

	public final Integer type;
	public final String value;

	FriendRequestVerifyStatus(Integer type, String value){
		this.type = type;
		this.value = value;
	}

}
