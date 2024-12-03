package org.itzixi.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author Donkor
 * @since 2024-11-29
 */
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 评论的朋友圈是哪个用户的关联id
     */
    private String belongUserId;

    /**
     * 如果是回复留言，则本条为子留言，需要关联查询
     */
    private String fatherId;

    /**
     * 评论的那个朋友圈的主键id
     */
    private String friendCircleId;

    /**
     * 发布留言的用户id
     */
    private String commentUserId;

    /**
     * 留言内容
     */
    private String commentContent;

    /**
     * 留言时间
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

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getFriendCircleId() {
        return friendCircleId;
    }

    public void setFriendCircleId(String friendCircleId) {
        this.friendCircleId = friendCircleId;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
        "id = " + id +
        ", belongUserId = " + belongUserId +
        ", fatherId = " + fatherId +
        ", friendCircleId = " + friendCircleId +
        ", commentUserId = " + commentUserId +
        ", commentContent = " + commentContent +
        ", createdTime = " + createdTime +
        "}";
    }
}
