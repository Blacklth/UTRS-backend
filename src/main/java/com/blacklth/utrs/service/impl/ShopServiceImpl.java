package com.blacklth.utrs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blacklth.utrs.entity.Shop;
import com.blacklth.utrs.entity.pojo.BaseEntity;
import com.blacklth.utrs.mapper.ShopMapper;
import com.blacklth.utrs.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blacklth.utrs.utils.ErrorUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liaotianhong
 * @since 2020-03-17
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    private final ShopMapper shopMapper;

    public ShopServiceImpl(ShopMapper shopMapper) {
        this.shopMapper = shopMapper;
    }

    @Override
    public Shop selectById(Integer shopId) {
        Shop shop = shopMapper.selectById(shopId);
        if(shop == null){
            throw ErrorUtil.wrap("没有该id对应的商户");
        }
        if(shop.getIsDelete().equals(1)){
            throw ErrorUtil.wrap("该商户已删除");
        }
        return shop;
    }

    @Override
    public List<Shop> selectList() {
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BaseEntity::getIsDelete,0);
        return shopMapper.selectList(queryWrapper);
    }

    @Override
    public int countForShop() {
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BaseEntity::getIsDelete,0);
        return shopMapper.selectCount(queryWrapper);
    }

    @Override
    public IPage<Shop> selectListByPage(Page<Shop> shopPage) {
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BaseEntity::getIsDelete,0);
        return shopMapper.selectPage(shopPage,queryWrapper);
    }

    @Override
    public IPage<Shop> selectListByName(Page<Shop> shopPage, String name) {
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BaseEntity::getIsDelete,0).eq(Shop::getShopName,name);
        return shopMapper.selectPage(shopPage,queryWrapper);
    }

    @Override
    public IPage<Shop> selectListByLabel(Page<Shop> shopPage, String label) {
        //TODO
        return null;
    }

    @Override
    public void deleteById(Integer shopId) {
        Shop shop = new Shop();
        shop.setShopId(shopId);
        shop.setIsDelete(1);
        shopMapper.updateById(shop);
    }

    @Override
    public void deleteBatchById(List<Integer> shopIdList) {
        shopIdList.forEach(this::deleteById);
    }
}
