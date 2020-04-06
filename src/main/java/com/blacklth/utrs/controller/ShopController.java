package com.blacklth.utrs.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blacklth.utrs.entity.Shop;
import com.blacklth.utrs.entity.pojo.ResultBean;
import com.blacklth.utrs.service.IShopService;
import com.blacklth.utrs.utils.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liaotianhong
 * @since 2020-03-17
 */
@RestController
public class ShopController {
    private final IShopService shopService;


    public ShopController(IShopService shopService) {
        this.shopService = shopService;
    }

    @ApiOperation(value = "通过id获取用户",httpMethod = "GET")
    @ApiImplicitParam(name = "shopId",value = "用户Id",required = true,paramType = "path")
    @GetMapping("/shop/{shopId}")
    public ResultBean<Object> selectShopById(@PathVariable Integer shopId){
        Shop shop = shopService.selectById(shopId);
        return ResultUtil.makeSuccess(shop);
    }


    @ApiOperation(value = "分页获取用户列表",httpMethod = "GET")
    @GetMapping("/shops/page")
    public ResultBean<Object> selectShopListByPage(@RequestParam Integer pageNo, @RequestParam Integer pageSize){
        Page<Shop> shopPage = new Page<>(pageNo,pageSize);
        IPage<Shop> shopipage = shopService.selectListByPage(shopPage);
        return ResultUtil.makeSuccess(shopipage);
    }

    @ApiOperation(value = "新增用户",httpMethod = "POST")
    @PostMapping("/shop")
    public ResultBean<Object> insertShop(@RequestBody Shop shop){

        shopService.save(shop);
        return ResultUtil.makeSuccess();
    }

    @ApiOperation(value = "通过id删除用户",httpMethod = "DELETE")
    @ApiImplicitParam(name = "shopId",value = "用户Id",required = true,paramType = "path")
    @DeleteMapping("/shop/{shopId}")
    public ResultBean<Object> deleteShopById(@PathVariable Integer shopId){
        shopService.deleteById(shopId);
        return ResultUtil.makeSuccess();
    }

    @ApiOperation(value = "批量删除用户",httpMethod = "DELETE")
    @DeleteMapping("/shops")
    public ResultBean<Object> deleteShopBatchById(@RequestBody List<Integer> shopIdList){
        shopService.deleteBatchById(shopIdList);
        return ResultUtil.makeSuccess();
    }

    @ApiOperation(value = "通过id修改用户",httpMethod = "PUT")
    @ApiImplicitParam(name = "shopId",value = "用户Id",required = true,paramType = "path")
    @PutMapping("/shop/{shopId}")
    public ResultBean<Object> updateShopById(@RequestBody Shop shop,@PathVariable Integer shopId){
        shop.setShopId(shopId);
        shopService.updateById(shop);
        return ResultUtil.makeSuccess();
    }

}
