package org.itzixi.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 好友请求记录表
 * </p>
 *
 * @author Donkor
 * @since 2024-11-29
 */
@TableName("friend_request")
public class FriendRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 添加好友，发起请求的用户id
     */
    private String myId;

    /**
     * 要添加的朋友的id
     */
    private String friendId;

    /**
     * 好友的备注名
     */
    private String friendRemark;

    /**
     * 请求的留言，验证消息
     */
    private String verifyMessage;

    /**
     * 请求被好友审核的状态，0-待审核；1-已添加，2-已过期
     */
    private Integer verifyStatus;

    /**
     * 发起请求的时间
     */
    private LocalDateTime requestTime;

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

    public String getVerifyMessage() {
        return verifyMessage;
    }

    public void setVerifyMessage(String verifyMessage) {
        this.verifyMessage = verifyMessage;
    }

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
        "id = " + id +
        ", myId = " + myId +
        ", friendId = " + friendId +
        ", friendRemark = " + friendRemark +
        ", verifyMessage = " + verifyMessage +
        ", verifyStatus = " + verifyStatus +
        ", requestTime = " + requestTime +
        "}";
    }
}
