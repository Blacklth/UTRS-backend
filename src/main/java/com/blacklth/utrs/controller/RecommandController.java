package com.blacklth.utrs.controller;

import com.blacklth.utrs.entity.RecommendDto;
import com.blacklth.utrs.entity.Shop;
import com.blacklth.utrs.entity.Trace;
import com.blacklth.utrs.entity.User;
import com.blacklth.utrs.entity.pojo.ResultBean;
import com.blacklth.utrs.service.IMatchService;
import com.blacklth.utrs.service.IShopService;
import com.blacklth.utrs.service.ITraceService;
import com.blacklth.utrs.service.IUserService;
import com.blacklth.utrs.utils.RecommandUtil;
import com.blacklth.utrs.utils.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.DoubleBinaryOperator;

/**
 * @author : TianHong Liao
 * @date : 2020/3/24 15:38
 * @description:
 * @modified :
 */
@RestController
public class RecommandController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private final IUserService userService;

    @Autowired
    private final IShopService shopService;

    @Autowired
    private final IMatchService matchService;

    @Autowired
    private final ITraceService traceService;

    public RecommandController(IUserService userService, IShopService shopService, IMatchService matchService, ITraceService traceService) {
        this.userService = userService;
        this.shopService = shopService;
        this.matchService = matchService;
        this.traceService = traceService;
    }


    @ApiOperation(value = "基于用户推荐",httpMethod = "GET")
    @ApiImplicitParam(name = "userId",value = "用户Id",required = true,paramType = "path")
    @GetMapping("/userBased/{userId}")
    public ResultBean<Object> userBaseddRecommend(@PathVariable Integer userId,@RequestParam(name = "k") Integer k){
        List<RecommendDto> recommendList = userBasedRecommend(userId,k);
        return ResultUtil.makeSuccess(recommendList);
    }

    @ApiOperation(value = "基于物品推荐",httpMethod = "GET")
    @ApiImplicitParam(name = "userId",value = "用户Id",required = true,paramType = "path")
    @GetMapping("/itemBased/{userId}")
    public ResultBean<Object> itemBaseddRecommend(@PathVariable Integer userId,@RequestParam(name = "k") Integer k){
        List<RecommendDto> recommendList =itemBasedRecommend(userId,k);
        return ResultUtil.makeSuccess(recommendList);
    }
    public List<RecommendDto> userBasedRecommend(int recommendUser, int k) {
        //用户集合
        List<User> userList = userService.selectList();
        //商户集合
        List<Shop> shopList = shopService.selectList();
        //轨迹集合
        List<Trace> traceList = traceService.selectList();

        //记录每个商户被访问的数量
        Map<Integer,Integer> shopUserLength = new HashMap<>();

        //存储每一个用户对应的不同物品总数 eg: A 3
        Map<Integer, Integer> userItemLength = new HashMap<>();

        //建立物品到用户的倒排表 eg: a A B
        Map<Integer, Set<Integer>> itemUserTable= RecommandUtil.itemUserTable(traceList,shopUserLength,userItemLength);

        //建立用户稀疏矩阵，用于用户相似度计算【相似度矩阵】
        int N = userService.countForUser();
        int[][] sparseMatrix = new int[N][N];
        //辅助存储每一个用户的用户ID映射
        Map<Integer, Integer> userID = new HashMap<>();
        //辅助存储每一个ID对应的用户映射
        Map<Integer, Integer> idUser = new HashMap<>();

        for(int i=0;i<N;i++){
            Integer userId = userList.get(i).getUserId();
            userID.put(userId,i);
            idUser.put(i,userId);
        }
        //计算相似度矩阵【稀疏】
        Set<Map.Entry<Integer, Set<Integer>>> entrySet = itemUserTable.entrySet();
        Iterator<Map.Entry<Integer, Set<Integer>>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Set<Integer> commonUsers = iterator.next().getValue();
            for (Integer user_u : commonUsers) {
                for (Integer user_v : commonUsers) {
                    if(user_u.equals(user_v)){
                        continue;
                    }
                    sparseMatrix[userID.get(user_u)][userID.get(user_v)] += 1;//计算用户u与用户v都有正反馈的物品总数
                }
            }
        }
        //商户访问量最大堆
        Queue<Map.Entry<Integer,Integer>> priorityQueue=new PriorityQueue<>((a,b)->(b.getValue().compareTo(a.getValue())));
        priorityQueue.addAll(shopUserLength.entrySet());

        Queue<Map.Entry<Shop,Double>> recommendQueue=new PriorityQueue<>((a,b)->(b.getValue().compareTo(a.getValue())));
        Map<Shop,Double> degreeMap = new HashMap<>();
        //计算指定用户recommendUser的物品推荐度
        for(Shop item: shopList){//遍历每一件物品
            Set<Integer> users = itemUserTable.get(item.getShopId());//得到购买当前物品的所有用户集合
            if(users != null && !users.contains(recommendUser)){//如果被推荐用户没有购买当前物品，则进行推荐度计算
                double itemRecommendDegree = 0.0;
                for(Integer user: users){
                    double fz = sparseMatrix[userID.get(recommendUser)][userID.get(user)];
                    if(fz == 0){
                        itemRecommendDegree += 0.0;
                    }else {
                        double fm = Math.sqrt(userItemLength.get(recommendUser)*userItemLength.get(user));//推荐度计算
                        itemRecommendDegree += (fz/fm);
                    }
                }
                logger.info("商户"+item+"对用户"+recommendUser+"的推荐度是"+itemRecommendDegree);
                degreeMap.put(item,itemRecommendDegree);
            }
        }
        recommendQueue.addAll(degreeMap.entrySet());
        int count = 0;
        List<RecommendDto> recommendList = new ArrayList<>();
        while (!recommendQueue.isEmpty() && count<k){
            RecommendDto recommendDto = new RecommendDto();
            Map.Entry<Shop, Double> template = recommendQueue.poll();
            Shop shop = template.getKey();
            recommendDto.setShopId(shop.getShopId());
            recommendDto.setShopName(shop.getShopName());
            recommendDto.setRate(template.getValue());
            recommendList.add(recommendDto);
            count++;
        }
        return recommendList;
    }

    public List<RecommendDto> itemBasedRecommend(int recommendUser, int k) {
        //用户集合
        List<User> userList = userService.selectList();
        //商户集合
        List<Shop> shopList = shopService.selectList();
        //轨迹集合
        List<Trace> traceList = traceService.selectList();

        //记录每个商户被访问的数量
        Map<Integer,Integer> shopUserLength = new HashMap<>();

        //存储每一个用户对应的不同物品总数 eg: A 3
        Map<Integer, Integer> userItemLength = new HashMap<>();

        //建立用户到商户的倒排表 eg: a A B
        Map<Integer, Set<Integer>> userItemTable= RecommandUtil.userItemTable(traceList,shopUserLength,userItemLength);

        //建立商户稀疏矩阵，用于用户相似度计算【相似度矩阵】
        int N = shopService.countForShop();
        int[][] sparseMatrix = new int[N][N];
        //辅助存储每一个用户的用户ID映射
        Map<Integer, Integer> shopID = new HashMap<>();
        //辅助存储每一个ID对应的用户映射
        Map<Integer, Integer> idShop = new HashMap<>();

        for(int i=0;i<N;i++){
            Integer shopId= shopList.get(i).getShopId();
            shopID.put(shopId,i);
            idShop.put(i,shopId);
        }
        //计算相似度矩阵【稀疏】
        Set<Map.Entry<Integer, Set<Integer>>> entrySet = userItemTable.entrySet();
        Iterator<Map.Entry<Integer, Set<Integer>>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Set<Integer> commonShops = iterator.next().getValue();
            for (Integer shop_u : commonShops) {
                for (Integer shop_v : commonShops) {
                    if(shop_u.equals(shop_v)){
                        continue;
                    }
                    sparseMatrix[shopID.get(shop_u)][shopID.get(shop_v)] += 1;//计算用户u与用户v都有正反馈的物品总数
                }
            }
        }
        //商户访问量最大堆
        Queue<Map.Entry<Integer,Integer>> priorityQueue=new PriorityQueue<>((a,b)->(b.getValue().compareTo(a.getValue())));
        priorityQueue.addAll(shopUserLength.entrySet());

        Queue<Map.Entry<Shop,Double>> recommendQueue=new PriorityQueue<>((a,b)->(b.getValue().compareTo(a.getValue())));
        Map<Shop,Double> degreeMap = new HashMap<>();
        //计算指定用户recommendUser的物品推荐度
        for(Shop item: shopList){
            Set<Integer> shops = userItemTable.get(recommendUser);//得到推荐用户访问过的商户集合
            if(shops != null && !shops.contains(item.getShopId())){//如果没有被推荐用户访问过，则进行推荐度计算
                double itemRecommendDegree = 0.0;
                for(Integer shop: shops){
                    double fz = sparseMatrix[shopID.get(shop)][shopID.get(item.getShopId())];
                    if(fz == 0){
                        itemRecommendDegree += 0.0;
                    }else {
                        double fm = Math.sqrt(shopUserLength.get(shop)*shopUserLength.get(item.getShopId()));
                        itemRecommendDegree += (fz/fm);
                    }
                }
                logger.info("商户"+item+"对用户"+recommendUser+"的推荐度是"+itemRecommendDegree);
                degreeMap.put(item,itemRecommendDegree);
            }
        }
        recommendQueue.addAll(degreeMap.entrySet());
        int count = 0;
        List<RecommendDto> recommendList = new ArrayList<>();
        while (!recommendQueue.isEmpty() && count<k){
            RecommendDto recommendDto = new RecommendDto();
            Map.Entry<Shop, Double> template = recommendQueue.poll();
            Shop shop = template.getKey();
            recommendDto.setShopId(shop.getShopId());
            recommendDto.setShopName(shop.getShopName());
            recommendDto.setRate(template.getValue());
            recommendList.add(recommendDto);
            count++;
        }
        return recommendList;
    }
}
