package com.blacklth.utrs.entity;

/**
 * @author : TianHong Liao
 * @date : 2020/3/30 17:27
 * @description:
 * @modified :
 */
public class RecommendDto {

    private Integer shopId;

    private String shopName;

    private Double rate;

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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
