package com.blacklth.utrs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blacklth.utrs.entity.Match;
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
public interface IMatchService extends IService<Match> {

    Match selectById(Integer matchId);

    IPage<Match> selectListByPage(Page<Match> matchPage);

    IPage<Match> selectListByUserId(Page<Match> matchPage,Integer userId);

    IPage<Match> selectListByShopId(Page<Match> matchPage,Integer shopId);

    void deleteById(Integer matchId);

    void deleteBatchById(List<Integer> matchIdList);
}
