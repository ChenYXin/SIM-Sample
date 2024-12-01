package org.itzixi.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 朋友关系表
 * </p>
 *
 * @author Donkor
 * @since 2024-11-29
 */
public class Friendship implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 自己的用户id
     */
    private String myId;

    /**
     * 我朋友的id
     */
    private String friendId;

    /**
     * 好友的备注名
     */
    private String friendRemark;

    /**
     * 聊天背景，局部
     */
    private String chatBg;

    /**
     * 是否消息免打扰，0-打扰，不忽略消息(默认)；1-免打扰，忽略消息
     */
    private Integer isMsgIgnore;

    /**
     * 是否拉黑，0-好友(默认)；1-已拉黑
     */
    private Integer isBlack;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendRemark() {
        return friendRemark;
    }

    public void setFriendRemark(String friendRemark) {
        this.friendRemark = friendRemark;
    }

    public String getChatBg() {
        return chatBg;
    }

    public void setChatBg(String chatBg) {
        this.chatBg = chatBg;
    }

    public Integer getIsMsgIgnore() {
        return isMsgIgnore;
    }

    public void setIsMsgIgnore(Integer isMsgIgnore) {
        this.isMsgIgnore = isMsgIgnore;
    }

    public Integer getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(Integer isBlack) {
        this.isBlack = isBlack;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "Friendship{" +
        "id = " + id +
        ", myId = " + myId +
        ", friendId = " + friendId +
        ", friendRemark = " + friendRemark +
        ", chatBg = " + chatBg +
        ", isMsgIgnore = " + isMsgIgnore +
        ", isBlack = " + isBlack +
        ", createdTime = " + createdTime +
        ", updatedTime = " + updatedTime +
        "}";
    }
}
