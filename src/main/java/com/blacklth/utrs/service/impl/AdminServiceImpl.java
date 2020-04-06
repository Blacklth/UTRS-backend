package com.blacklth.utrs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blacklth.utrs.entity.Admin;
import com.blacklth.utrs.entity.pojo.BaseEntity;
import com.blacklth.utrs.mapper.AdminMapper;
import com.blacklth.utrs.service.IAdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    private final AdminMapper adminMapper;

    public AdminServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Override
    public Admin selectById(Integer adminId) {
        Admin admin = adminMapper.selectById(adminId);
        if(admin == null){
            throw ErrorUtil.wrap("没有该id对应的管理员");
        }
        if(admin.getIsDelete().equals(1)){
            throw ErrorUtil.wrap("该应用已删除");
        }
        return admin;
    }

    @Override
    public IPage<Admin> selectListByPage(Page<Admin> adminPage) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BaseEntity::getIsDelete,0);
        return adminMapper.selectPage(adminPage,queryWrapper);
    }

    @Override
    public void deleteById(Integer adminId) {
        Admin admin = new Admin();
        admin.setAdminId(adminId);
        admin.setIsDelete(1);
        adminMapper.updateById(admin);
    }

    @Override
    public void deleteBatchById(List<Integer> adminIdList) {
        adminIdList.forEach(this::deleteById);
    }
}
