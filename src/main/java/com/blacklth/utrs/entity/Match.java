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
@TableName("sys_match")
public class Match extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "match_id", type = IdType.AUTO)
    private Integer matchId;

    private Integer userId;

    private Integer shopId;

    private Integer mark;

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
    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }


    @Override
    public String toString() {
        return "Match{" +
            "matchId=" + matchId +
            ", userId=" + userId +
            ", shopId=" + shopId +
            ", mark=" + mark +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
            ", updateTime=" + updateTime +
            ", updateBy=" + updateBy +
            ", isDelete=" + isDelete +
        "}";
    }
}
