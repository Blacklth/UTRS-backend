package com.blacklth.utrs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blacklth.utrs.entity.Trace;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liaotianhong
 * @since 2020-03-17
 */
public interface ITraceService extends IService<Trace> {


    List<Trace> selectListByUserId(Integer userId);

    List<Trace> selectList();
}
