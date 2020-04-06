package com.blacklth.utrs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blacklth.utrs.entity.User;
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
public interface IUserService extends IService<User> {

    void deleteById(Integer userId);

    void deleteBatchById(List<Integer> userIdList);

    IPage<User> selectListByPage(Page<User> userPage);

    IPage<User> selectListByName(Page<User> userPage,String name);

    User selectById(Integer userId);

    User selectByWxOpenId(String wxOpenId);

    int countForUser();

    List<User> selectList();
}
