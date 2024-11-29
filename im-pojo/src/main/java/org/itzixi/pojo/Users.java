package org.itzixi.pojo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Donkor
 * @since 2024-11-29
 */
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 微信号
     */
    private String wechatNum;

    /**
     * 微信号二维码
     */
    private String wechatNumImg;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别，1:男 0:女 2:保密
     */
    private Integer sex;

    /**
     * 用户头像
     */
    private String face;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区县
     */
    private String district;

    /**
     * 聊天背景
     */
    private String chatBg;

    /**
     * 朋友圈背景图
     */
    private String friendCircleBg;

    /**
     * 我的一句话签名
     */
    private String signature;

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

    public String getWechatNum() {
        return wechatNum;
    }

    public void setWechatNum(String wechatNum) {
        this.wechatNum = wechatNum;
    }

    public String getWechatNumImg() {
        return wechatNumImg;
    }

    public void setWechatNumImg(String wechatNumImg) {
        this.wechatNumImg = wechatNumImg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getChatBg() {
        return chatBg;
    }

    public void setChatBg(String chatBg) {
        this.chatBg = chatBg;
    }

    public String getFriendCircleBg() {
        return friendCircleBg;
    }

    public void setFriendCircleBg(String friendCircleBg) {
        this.friendCircleBg = friendCircleBg;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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
        return "Users{" +
        "id = " + id +
        ", wechatNum = " + wechatNum +
        ", wechatNumImg = " + wechatNumImg +
        ", mobile = " + mobile +
        ", nickname = " + nickname +
        ", realName = " + realName +
        ", sex = " + sex +
        ", face = " + face +
        ", email = " + email +
        ", birthday = " + birthday +
        ", country = " + country +
        ", province = " + province +
        ", city = " + city +
        ", district = " + district +
        ", chatBg = " + chatBg +
        ", friendCircleBg = " + friendCircleBg +
        ", signature = " + signature +
        ", createdTime = " + createdTime +
        ", updatedTime = " + updatedTime +
        "}";
    }
}
