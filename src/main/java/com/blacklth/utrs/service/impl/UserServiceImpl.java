package com.blacklth.utrs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blacklth.utrs.entity.User;
import com.blacklth.utrs.entity.pojo.BaseEntity;
import com.blacklth.utrs.mapper.UserMapper;
import com.blacklth.utrs.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User selectById(Integer userId) {
        User user = userMapper.selectById(userId);
        if(user == null){
            throw ErrorUtil.wrap("没有该id对应的用户");
        }
        if(user.getIsDelete().equals(1)){
            throw ErrorUtil.wrap("该用户已删除");
        }
        return user;
    }

    @Override
    public User selectByWxOpenId(String wxOpenId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUserWxOpenId,wxOpenId);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            throw ErrorUtil.wrap("没有该id对应的用户");
        }
        if(user.getIsDelete().equals(1)){
            throw ErrorUtil.wrap("该用户已删除");
        }
        return user;
    }

    @Override
    public int countForUser() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BaseEntity::getIsDelete,0);
        return userMapper.selectCount(queryWrapper);
    }

    @Override
    public List<User> selectList() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BaseEntity::getIsDelete,0);
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<User> selectListByPage(Page<User> userPage) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BaseEntity::getIsDelete,0);
        return userMapper.selectPage(userPage,queryWrapper);
    }

    @Override
    public IPage<User> selectListByName(Page<User> userPage, String name) {
        //TODO 模糊搜索
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUserName,name).eq(BaseEntity::getIsDelete,0);;
        return userMapper.selectPage(userPage,queryWrapper);
    }

    @Override
    public void deleteById(Integer userId) {
        User user = new User();
        user.setUserId(userId);
        user.setIsDelete(1);
        userMapper.updateById(user);
    }

    @Override
    public void deleteBatchById(List<Integer> userIdList) {
        userIdList.forEach(this::deleteById);
    }
}
