package com.blacklth.utrs.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blacklth.utrs.entity.Match;
import com.blacklth.utrs.entity.pojo.ResultBean;
import com.blacklth.utrs.service.IMatchService;
import com.blacklth.utrs.utils.ErrorUtil;
import com.blacklth.utrs.utils.ResultUtil;
import com.blacklth.utrs.utils.StringUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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
public class MatchController {
    private final IMatchService matchService;


    public MatchController(IMatchService matchService) {
        this.matchService = matchService;
    }

    @ApiOperation(value = "通过id获取配对项",httpMethod = "GET")
    @ApiImplicitParam(name = "matchId",value = "配对项Id",required = true,paramType = "path")
    @GetMapping("/match/{matchId}")
    public ResultBean<Object> selectMatchById(@PathVariable Integer matchId){
        Match match = matchService.selectById(matchId);
        return ResultUtil.makeSuccess(match);
    }


    @ApiOperation(value = "分页获取配对项列表",httpMethod = "GET")
    @GetMapping("/matchs/page")
    public ResultBean<Object> selectMatchListByPage(@RequestParam Integer pageNo, @RequestParam Integer pageSize){
        Page<Match> matchPage = new Page<>(pageNo,pageSize);
        IPage<Match> matchipage = matchService.selectListByPage(matchPage);
        return ResultUtil.makeSuccess(matchipage);
    }

    @ApiOperation(value = "新增配对项",httpMethod = "POST")
    @PostMapping("/match")
    public ResultBean<Object> insertMatch(@RequestBody Match match){
        matchService.save(match);
        return ResultUtil.makeSuccess();
    }

    @ApiOperation(value = "通过id删除配对项",httpMethod = "DELETE")
    @ApiImplicitParam(name = "matchId",value = "配对项Id",required = true,paramType = "path")
    @DeleteMapping("/match/{matchId}")
    public ResultBean<Object> deleteMatchById(@PathVariable Integer matchId){
        matchService.deleteById(matchId);
        return ResultUtil.makeSuccess();
    }

    @ApiOperation(value = "批量删除配对项",httpMethod = "DELETE")
    @DeleteMapping("/matchs")
    public ResultBean<Object> deleteMatchBatchById(@RequestBody List<Integer> matchIdList){
        matchService.deleteBatchById(matchIdList);
        return ResultUtil.makeSuccess();
    }

    @ApiOperation(value = "通过id修改配对项",httpMethod = "PUT")
    @ApiImplicitParam(name = "matchId",value = "配对项Id",required = true,paramType = "path")
    @PutMapping("/match/{matchId}")
    public ResultBean<Object> updateMatchById(@RequestBody Match match,@PathVariable Integer matchId){
        match.setMatchId(matchId);
        matchService.updateById(match);
        return ResultUtil.makeSuccess();
    }

}
