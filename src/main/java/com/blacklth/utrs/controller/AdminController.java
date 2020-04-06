package com.blacklth.utrs.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blacklth.utrs.entity.Admin;
import com.blacklth.utrs.entity.pojo.ResultBean;
import com.blacklth.utrs.service.IAdminService;
import com.blacklth.utrs.utils.ErrorUtil;
import com.blacklth.utrs.utils.ResultUtil;
import com.blacklth.utrs.utils.StringUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
public class AdminController {
    private final IAdminService adminService;


    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }
    
    @ApiOperation(value = "通过id获取管理员",httpMethod = "GET")
    @ApiImplicitParam(name = "adminId",value = "管理员Id",required = true,paramType = "path")
    @GetMapping("/admin/{adminId}")
    public ResultBean<Object> selectAdminById(@PathVariable Integer adminId){
        Admin admin = adminService.selectById(adminId);
        return ResultUtil.makeSuccess(admin);
    }


    @ApiOperation(value = "分页获取管理员列表",httpMethod = "GET")
    @GetMapping("/admins/page")
    public ResultBean<Object> selectAdminListByPage(@RequestParam Integer pageNo,@RequestParam Integer pageSize){
        Page<Admin> adminPage = new Page<>(pageNo,pageSize);
        IPage<Admin> adminipage = adminService.selectListByPage(adminPage);
        return ResultUtil.makeSuccess(adminipage);
    }

    @ApiOperation(value = "新增管理员",httpMethod = "POST")
    @PostMapping("/admin")
    public ResultBean<Object> insertAdmin(@RequestBody Admin admin){
        if(StringUtil.isEmpty(admin.getAdminPwd())){
            throw ErrorUtil.wrap("管理员密码不能为空");
        }
        adminService.save(admin);
        return ResultUtil.makeSuccess();
    }
    
    @ApiOperation(value = "通过id删除管理员",httpMethod = "DELETE")
    @ApiImplicitParam(name = "adminId",value = "管理员Id",required = true,paramType = "path")
    @DeleteMapping("/admin/{adminId}")
    public ResultBean<Object> deleteAdminById(@PathVariable Integer adminId){
        adminService.deleteById(adminId);
        return ResultUtil.makeSuccess();
    }
    
    @ApiOperation(value = "批量删除管理员",httpMethod = "DELETE")
    @DeleteMapping("/admins")
    public ResultBean<Object> deleteAdminBatchById(@RequestBody List<Integer> adminIdList){
        adminService.deleteBatchById(adminIdList);
        return ResultUtil.makeSuccess();
    }
    
    @ApiOperation(value = "通过id修改管理员",httpMethod = "PUT")
    @ApiImplicitParam(name = "adminId",value = "管理员Id",required = true,paramType = "path")
    @PutMapping("/admin/{adminId}")
    public ResultBean<Object> updateAdminById(@RequestBody Admin admin,@PathVariable Integer adminId){
        admin.setAdminId(adminId);
        adminService.updateById(admin);
        return ResultUtil.makeSuccess();
    }

    @ApiOperation(value = "登录",httpMethod = "POST")
    @PostMapping("/login")
    public ResultBean<Object> login(@RequestBody JSONObject jsonObject, HttpServletResponse httpServletResponse){
        String adminId = jsonObject.getString("adminId");
        String pwd = jsonObject.getString("pwd");
        if(StringUtil.isEmpty(adminId) || StringUtil.isEmpty(pwd)){
            throw ErrorUtil.wrap("管理员账号或者密码为空");
        }

        String realPassword = adminService.selectById(Integer.parseInt(adminId)).getAdminPwd();
        if (!pwd.equals(realPassword)) {
            throw ErrorUtil.wrap("密码错误");
        } else {
            return ResultUtil.makeSuccess();
        }
    }

}
