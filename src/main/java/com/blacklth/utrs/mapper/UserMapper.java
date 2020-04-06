package com.blacklth.utrs.mapper;

import com.blacklth.utrs.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liaotianhong
 * @since 2020-03-17
 */
@Component
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
