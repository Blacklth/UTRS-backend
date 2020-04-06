package com.blacklth.utrs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blacklth.utrs.entity.pojo.BaseEntity;

/**
 * @author : TianHong Liao
 * @date : 2020/3/24 12:36
 * @description:
 * @modified :
 */
@TableName("sys_trace")
public class Trace extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "match_id", type = IdType.AUTO)
    private Integer matchId;

    private Integer userId;

    private Integer shopId;

    private Integer stayTime;


    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
    public Integer getStayTime() {
        return stayTime;
    }

    public void setStayTime(Integer stayTime) {
        this.stayTime = stayTime;
    }

    @Override
    public String toString() {
        return "Match{" +
                "matchId=" + matchId +
                ", userId=" + userId +
                ", shopId=" + shopId +
                ", stayTime=" + stayTime +
                ", createTime=" + createTime +
                ", createBy=" + createBy +
                ", updateTime=" + updateTime +
                ", updateBy=" + updateBy +
                ", isDelete=" + isDelete +
                "}";
    }


}
