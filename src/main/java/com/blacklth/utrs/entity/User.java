package com.blacklth.utrs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.blacklth.utrs.entity.pojo.BaseEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author liaotianhong
 * @since 2020-03-17
 */
@TableName("sys_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    private String userWxOpenId;

    private String userName;

    private Integer userAge;

    private String userGender;

    private String userShoppingLabel;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUserWxOpenId() {
        return userWxOpenId;
    }

    public void setUserWxOpenId(String userWxOpenId) {
        this.userWxOpenId = userWxOpenId;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }
    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
    public String getUserShoppingLabel() {
        return userShoppingLabel;
    }

    public void setUserShoppingLabel(String userShoppingLabel) {
        this.userShoppingLabel = userShoppingLabel;
    }

    @Override
    public String toString() {
        return "User{" +
            "userId=" + userId +
            ", userWxOpenId=" + userWxOpenId +
            ", userName=" + userName +
            ", userAge=" + userAge +
            ", userGender=" + userGender +
            ", userShoppingLabel=" + userShoppingLabel +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
            ", updateTime=" + updateTime +
            ", updateBy=" + updateBy +
            ", isDelete=" + isDelete +
        "}";
    }
}
