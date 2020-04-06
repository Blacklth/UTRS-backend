package com.blacklth.utrs.utils;

import com.blacklth.utrs.entity.Shop;
import com.blacklth.utrs.entity.Trace;
import com.blacklth.utrs.entity.User;
import com.blacklth.utrs.service.IMatchService;
import com.blacklth.utrs.service.IShopService;
import com.blacklth.utrs.service.ITraceService;
import com.blacklth.utrs.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author : TianHong Liao
 * @date : 2020/3/23 21:20
 * @description:
 * @modified :
 */
@Component
public class RecommandUtil {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private final IUserService userService;

    @Autowired
    private final IShopService shopService;

    @Autowired
    private final IMatchService matchService;

    @Autowired
    private final ITraceService traceService;

    public RecommandUtil(IUserService userService, IShopService shopService, IMatchService matchService, ITraceService traceService) {
        this.userService = userService;
        this.shopService = shopService;
        this.matchService = matchService;
        this.traceService = traceService;
    }

    public  void recommand() {
        /**
         * 输入用户-->物品条目 一个用户对应多个物品
         * 用户ID 物品ID集合
         * A  a b d
         * B  a c
         * C  b e
         * D  c d e
         */
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the total users number:");
        //输入用户总量
        int N = userService.countForUser();
        int[][] sparseMatrix = new int[N][N];
        //建立用户稀疏矩阵，用于用户相似度计算【相似度矩阵】
        Map<String, Integer> userItemLength = new HashMap<>();
        //存储每一个用户对应的不同物品总数 eg: A 3
        Map<String, Set<String>> itemUserCollection = new HashMap<>();
        //建立物品到用户的倒排表 eg: a A B
        Set<String> items = new HashSet<>();
        //辅助存储物品集合
        Map<String, Integer> userID = new HashMap<>();
        //辅助存储每一个用户的用户ID映射
        Map<Integer, String> idUser = new HashMap<>();
        //辅助存储每一个ID对应的用户映射
        System.out.println("Input user--items maping infermation:<eg:A a b d>");
        scanner.nextLine();
        for (int i = 0; i < N ; i++){
            //依次处理N个用户 输入数据 以空格间隔
            String[] user_item = scanner.nextLine().split(" ");
            int length = user_item.length;
            userItemLength.put(user_item[0], length-1);
            //eg: A 3
            userID.put(user_item[0], i);
            //用户ID与稀疏矩阵建立对应关系
            idUser.put(i, user_item[0]);
            //建立物品--用户倒排表
            for (int j = 1; j < length; j ++){
                if(items.contains(user_item[j])){
                    //如果已经包含对应的物品--用户映射，直接添加对应的用户
                    itemUserCollection.get(user_item[j]).add(user_item[0]);
                } else{
                    //否则创建对应物品--用户集合映射
                    items.add(user_item[j]);
                    itemUserCollection.put(user_item[j], new HashSet<String>());
                    //创建物品--用户倒排关系
                    itemUserCollection.get(user_item[j]).add(user_item[0]);
                }
            }
        }
        System.out.println(itemUserCollection.toString());
        //计算相似度矩阵【稀疏】
        Set<Map.Entry<String, Set<String>>> entrySet = itemUserCollection.entrySet();
        Iterator<Map.Entry<String, Set<String>>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Set<String> commonUsers = iterator.next().getValue();
            for (String user_u : commonUsers) {
                for (String user_v : commonUsers) {
                    if(user_u.equals(user_v)){
                        continue;
                    }
                    sparseMatrix[userID.get(user_u)][userID.get(user_v)] += 1;
                    //计算用户u与用户v都有正反馈的物品总数
                }
            }
        }
        System.out.println(userItemLength.toString());
        System.out.println("Input the user for recommendation:<eg:A>");
        String recommendUser = scanner.nextLine();
        System.out.println(userID.get(recommendUser));
        //计算用户之间的相似度【余弦相似性】
        int recommendUserId = userID.get(recommendUser);
        for (int j = 0;j < sparseMatrix.length; j++) {
            if(j != recommendUserId){
                System.out.println(idUser.get(recommendUserId)+"--"+idUser.get(j)+"相似度:"+sparseMatrix[recommendUserId][j]/Math.sqrt(userItemLength.get(idUser.get(recommendUserId))*userItemLength.get(idUser.get(j))));
            }
        }
        //计算指定用户recommendUser的物品推荐度
        for (String item: items){
            //遍历每一件物品
            Set<String> users = itemUserCollection.get(item);
            //得到购买当前物品的所有用户集合
            if(!users.contains(recommendUser)){
                //如果被推荐用户没有购买当前物品，则进行推荐度计算
                double itemRecommendDegree = 0.0;
                for (String user: users){
                    itemRecommendDegree += sparseMatrix[userID.get(recommendUser)][userID.get(user)]/Math.sqrt(userItemLength.get(recommendUser)*userItemLength.get(user));
                    //推荐度计算
                }
                System.out.println("The item "+item+" for "+recommendUser +"'s recommended degree:"+itemRecommendDegree);
            }
        }
        scanner.close();
    }

    public  void recommand1(int recommendUser) {
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
        Map<Integer, Set<Integer>> itemUserTable= itemUserTable(traceList,shopUserLength,userItemLength);

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
        Set<Entry<Integer, Set<Integer>>> entrySet = itemUserTable.entrySet();
        Iterator<Entry<Integer, Set<Integer>>> iterator = entrySet.iterator();
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


        //计算指定用户recommendUser的物品推荐度
        for(Shop item: shopList){//遍历每一件物品
            Set<Integer> users = itemUserTable.get(item.getShopId());//得到购买当前物品的所有用户集合
            if(!users.contains(recommendUser)){//如果被推荐用户没有购买当前物品，则进行推荐度计算
                double itemRecommendDegree = 0.0;
                for(Integer user: users){
                    itemRecommendDegree += sparseMatrix[userID.get(recommendUser)][userID.get(user)]/Math.sqrt(userItemLength.get(recommendUser)*userItemLength.get(user));//推荐度计算
                }
                logger.info("The item "+item+" for "+recommendUser +"'s recommended degree:"+itemRecommendDegree);
            }
        }
    }

    public void userBasedCF(Map<Integer, Set<Integer>> itemUserTable,int recommendUser){


    }
    //商户-用户倒排表
    public static Map<Integer, Set<Integer>> itemUserTable(List<Trace> traceList,Map<Integer,Integer> shopUserLength,Map<Integer,Integer> userShopLength){
        Map<Integer, Set<Integer>> itemUserTable = new HashMap<>();
        for(Trace trace: traceList){
            Integer shopId = trace.getShopId();
            Integer userId = trace.getUserId();
            if(itemUserTable.containsKey(shopId)){
                itemUserTable.get(shopId).add(userId);
                //记录每个商户被访问的数量
                shopUserLength.put(shopId,shopUserLength.get(shopId)+1);
            }else {
                itemUserTable.put(shopId,new HashSet<>());
                itemUserTable.get(shopId).add(userId);
                shopUserLength.put(shopId,1);
            }
            if(userShopLength.containsKey(userId)){
                userShopLength.put(userId,userShopLength.get(userId)+1);
            }else {
                userShopLength.put(userId,1);
            }
        }
        return itemUserTable;
    }

    //用户-商品倒排表
    public static Map<Integer, Set<Integer>> userItemTable(List<Trace> traceList,Map<Integer,Integer> shopUserLength,Map<Integer,Integer> userShopLength){
        Map<Integer, Set<Integer>> userItemTable = new HashMap<>();
        for(Trace trace: traceList){
            Integer shopId = trace.getShopId();
            Integer userId = trace.getUserId();
            if(userItemTable.containsKey(userId)){
                userItemTable.get(userId).add(shopId);
                userShopLength.put(userId,userShopLength.get(userId)+1);
            }else {
                userItemTable.put(userId,new HashSet<>());
                userItemTable.get(userId).add(shopId);
                userShopLength.put(userId,1);
            }
            if(shopUserLength.containsKey(shopId)){
                shopUserLength.put(shopId,shopUserLength.get(shopId)+1);
            }else {
                shopUserLength.put(shopId,1);
            }
        }
        return userItemTable;
    }


}
