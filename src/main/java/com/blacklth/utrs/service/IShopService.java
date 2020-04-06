package com.blacklth.utrs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blacklth.utrs.entity.Shop;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liaotianhong
 * @since 2020-03-17
 */
public interface IShopService extends IService<Shop> {

    void deleteById(Integer shopId);

    void deleteBatchById(List<Integer> shopIdList);

    IPage<Shop> selectListByPage(Page<Shop> shopPage);

    IPage<Shop> selectListByName(Page<Shop> shopPage,String name);

    IPage<Shop> selectListByLabel(Page<Shop> shopPage,String label);

    Shop selectById(Integer shopId);

    List<Shop> selectList();

    int countForShop();
}
