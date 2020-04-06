package com.blacklth.utrs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blacklth.utrs.entity.Admin;
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
public interface IAdminService extends IService<Admin> {

    /**
     *  通过adminId查询管理员
     * @author     ：TianHong Liao
     * @date       ：Created in 2020/3/20 19:04
     * @param       adminId
     * @return     : com.blacklth.utrs.entity.Admin
     */
    Admin selectById(Integer adminId);

    IPage<Admin> selectListByPage(Page<Admin> adminPage);

    void deleteById(Integer adminId);

    void deleteBatchById(List<Integer> adminIdList);
}
