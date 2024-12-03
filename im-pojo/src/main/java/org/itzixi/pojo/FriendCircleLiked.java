package org.itzixi.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 点赞朋友圈的朋友
 * </p>
 *
 * @author Donkor
 * @since 2024-11-29
 */
@TableName("friend_circle_liked")
public class FriendCircleLiked implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 朋友圈归属用户的id
     */
    private String belongUserId;

    /**
     * 点赞的那个朋友圈id
     */
    private String friendCircleId;

    /**
     * 点赞的那个用户id
     */
    private String likedUserId;

    /**
     * 点赞用户的昵称
     */
    private String likedUserName;

    /**
     * 点赞时间
     */
    private LocalDateTime createdTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBelongUserId() {
        return belongUserId;
    }

    public void setBelongUserId(String belongUserId) {
        this.belongUserId = belongUserId;
    }

    public String getFriendCircleId() {
        return friendCircleId;
    }

    public void setFriendCircleId(String friendCircleId) {
        this.friendCircleId = friendCircleId;
    }

    public String getLikedUserId() {
        return likedUserId;
    }

    public void setLikedUserId(String likedUserId) {
        this.likedUserId = likedUserId;
    }

    public String getLikedUserName() {
        return likedUserName;
    }

    public void setLikedUserName(String likedUserName) {
        this.likedUserName = likedUserName;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "FriendCircleLiked{" +
        "id = " + id +
        ", belongUserId = " + belongUserId +
        ", friendCircleId = " + friendCircleId +
        ", likedUserId = " + likedUserId +
        ", likedUserName = " + likedUserName +
        ", createdTime = " + createdTime +
        "}";
    }
}
