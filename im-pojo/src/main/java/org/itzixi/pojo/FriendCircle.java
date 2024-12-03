package org.itzixi.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 朋友圈表
 * </p>
 *
 * @author Donkor
 * @since 2024-11-29
 */
@TableName("friend_circle")
public class FriendCircle implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 哪个用户发的朋友圈，用户id
     */
    private String userId;

    /**
     * 文字内容
     */
    private String words;

    /**
     * 图片内容，url用逗号分割
     */
    private String images;

    /**
     * 单个视频的url
     */
    private String video;

    /**
     * 发布朋友圈的时间
     */
    private LocalDateTime publishTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public String toString() {
        return "FriendCircle{" +
        "id = " + id +
        ", userId = " + userId +
        ", words = " + words +
        ", images = " + images +
        ", video = " + video +
        ", publishTime = " + publishTime +
        "}";
    }
}
