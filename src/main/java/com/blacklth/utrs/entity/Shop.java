package com.blacklth.utrs.entity;

import java.math.BigDecimal;
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
@TableName("sys_shop")
public class Shop extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "shop_id", type = IdType.AUTO)
    private Integer shopId;

    private String shopName;

    private String shopLabel;

    private BigDecimal shopLongitude;

    private BigDecimal shopLatitude;

    private BigDecimal shopAltitude;


    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getShopLabel() {
        return shopLabel;
    }

    public void setShopLabel(String shopLabel) {
        this.shopLabel = shopLabel;
    }
    public BigDecimal getShopLongitude() {
        return shopLongitude;
    }

    public void setShopLongitude(BigDecimal shopLongitude) {
        this.shopLongitude = shopLongitude;
    }
    public BigDecimal getShopLatitude() {
        return shopLatitude;
    }

    public void setShopLatitude(BigDecimal shopLatitude) {
        this.shopLatitude = shopLatitude;
    }
    public BigDecimal getShopAltitude() {
        return shopAltitude;
    }

    public void setShopAltitude(BigDecimal shopAltitude) {
        this.shopAltitude = shopAltitude;
    }


    @Override
    public String toString() {
        return "Shop{" +
            "shopId=" + shopId +
            ", shopName=" + shopName +
            ", shopLabel=" + shopLabel +
            ", shopLongitude=" + shopLongitude +
            ", shopLatitude=" + shopLatitude +
            ", shopAltitude=" + shopAltitude +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
            ", updateTime=" + updateTime +
            ", updateBy=" + updateBy +
            ", isDelete=" + isDelete +
        "}";
    }
}
