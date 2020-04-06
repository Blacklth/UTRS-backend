package com.blacklth.utrs.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blacklth.utrs.entity.User;
import com.blacklth.utrs.entity.pojo.ResultBean;
import com.blacklth.utrs.service.IUserService;
import com.blacklth.utrs.utils.ErrorUtil;
import com.blacklth.utrs.utils.ResultUtil;
import com.blacklth.utrs.utils.StringUtil;
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
public class UserController {
    private final IUserService userService;


    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "通过id获取用户",httpMethod = "GET")
    @ApiImplicitParam(name = "userId",value = "用户Id",required = true,paramType = "path")
    @GetMapping("/user/{userId}")
    public ResultBean<Object> selectUserById(@PathVariable Integer userId){
        User user = userService.selectById(userId);
        return ResultUtil.makeSuccess(user);
    }


    @ApiOperation(value = "分页获取用户列表",httpMethod = "GET")
    @GetMapping("/users/page")
    public ResultBean<Object> selectUserListByPage(@RequestParam Integer pageNo, @RequestParam Integer pageSize){
        Page<User> userPage = new Page<>(pageNo,pageSize);
        IPage<User> useripage = userService.selectListByPage(userPage);
        return ResultUtil.makeSuccess(useripage);
    }

    @ApiOperation(value = "新增用户",httpMethod = "POST")
    @PostMapping("/user")
    public ResultBean<Object> insertUser(@RequestBody User user){

        userService.save(user);
        return ResultUtil.makeSuccess();
    }

    @ApiOperation(value = "通过id删除用户",httpMethod = "DELETE")
    @ApiImplicitParam(name = "userId",value = "用户Id",required = true,paramType = "path")
    @DeleteMapping("/user/{userId}")
    public ResultBean<Object> deleteUserById(@PathVariable Integer userId){
        userService.deleteById(userId);
        return ResultUtil.makeSuccess();
    }

    @ApiOperation(value = "批量删除用户",httpMethod = "DELETE")
    @DeleteMapping("/users")
    public ResultBean<Object> deleteUserBatchById(@RequestBody List<Integer> userIdList){
        userService.deleteBatchById(userIdList);
        return ResultUtil.makeSuccess();
    }

    @ApiOperation(value = "通过id修改用户",httpMethod = "PUT")
    @ApiImplicitParam(name = "userId",value = "用户Id",required = true,paramType = "path")
    @PutMapping("/user/{userId}")
    public ResultBean<Object> updateUserById(@RequestBody User user,@PathVariable Integer userId){
        user.setUserId(userId);
        userService.updateById(user);
        return ResultUtil.makeSuccess();
    }

}
