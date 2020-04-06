package com.blacklth.utrs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blacklth.utrs.entity.Match;
import com.blacklth.utrs.entity.pojo.BaseEntity;
import com.blacklth.utrs.mapper.MatchMapper;
import com.blacklth.utrs.service.IMatchService;
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
public class MatchServiceImpl extends ServiceImpl<MatchMapper, Match> implements IMatchService {

    private final MatchMapper matchMapper;

    public MatchServiceImpl(MatchMapper matchMapper) {
        this.matchMapper = matchMapper;
    }

    @Override
    public Match selectById(Integer matchId) {
        Match match = matchMapper.selectById(matchId);
        if(match == null){
            throw ErrorUtil.wrap("没有该id对应的匹配项");
        }
        if(match.getIsDelete().equals(1)){
            throw ErrorUtil.wrap("该匹配项已删除");
        }
        return match;
    }

    @Override
    public IPage<Match> selectListByPage(Page<Match> matchPage) {
        QueryWrapper<Match> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BaseEntity::getIsDelete,0);
        return matchMapper.selectPage(matchPage,queryWrapper);
    }

    @Override
    public IPage<Match> selectListByUserId(Page<Match> matchPage, Integer userId) {
        QueryWrapper<Match> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BaseEntity::getIsDelete,0).eq(Match::getUserId,userId);
        return matchMapper.selectPage(matchPage,queryWrapper);
    }

    @Override
    public IPage<Match> selectListByShopId(Page<Match> matchPage, Integer shopId) {
        QueryWrapper<Match> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BaseEntity::getIsDelete,0).eq(Match::getShopId,shopId);
        return matchMapper.selectPage(matchPage,queryWrapper);
    }

    @Override
    public void deleteById(Integer matchId) {
        Match match = new Match();
        match.setMatchId(matchId);
        match.setIsDelete(1);
        matchMapper.updateById(match);
    }

    @Override
    public void deleteBatchById(List<Integer> matchIdList) {
        matchIdList.forEach(this::deleteById);
    }
}
