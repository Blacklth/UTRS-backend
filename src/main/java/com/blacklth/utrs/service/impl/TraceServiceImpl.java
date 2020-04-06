package com.blacklth.utrs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blacklth.utrs.entity.Trace;
import com.blacklth.utrs.entity.pojo.BaseEntity;
import com.blacklth.utrs.mapper.TraceMapper;
import com.blacklth.utrs.service.ITraceService;
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
public class TraceServiceImpl extends ServiceImpl<TraceMapper, Trace> implements ITraceService {

    private final TraceMapper traceMapper;

    public TraceServiceImpl(TraceMapper traceMapper) {
        this.traceMapper = traceMapper;
    }


    @Override
    public List<Trace> selectListByUserId(Integer userId) {
        QueryWrapper<Trace> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Trace::getUserId,userId).eq(BaseEntity::getIsDelete,0);
        return traceMapper.selectList(queryWrapper);
    }

    @Override
    public List<Trace> selectList() {
        QueryWrapper<Trace> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BaseEntity::getIsDelete,0);
        return traceMapper.selectList(queryWrapper);
    }
}
