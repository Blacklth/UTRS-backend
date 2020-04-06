package com.blacklth.utrs.utils;


import com.blacklth.utrs.entity.pojo.ResultBean;
import org.springframework.http.HttpStatus;

/**
 * 关于统一返回结果的工具类
 * @author : LiaoTianHong
 * @date : 2019/7/23 16:03
 */
public class ResultUtil {
    /**
     * 表示接口调用成功
     */
    public static final int SUCCESS = 1;
    /**
     * 表示接口调用失败
     */
    public static final int FAIL = 0;

    public static final String SUCC_MSG = "成功";
    public static final String FAIL_MSG = "失败";

    public static <T> ResultBean<T> makeSuccess(){
        return new ResultBean<>(HttpStatus.OK.toString(),SUCCESS,SUCC_MSG,null);
    }

    public static <T> ResultBean<T> makeSuccess(String msg){
        return new ResultBean<>(HttpStatus.OK.toString(),SUCCESS,msg,null);
    }

    public static <T> ResultBean<T> makeSuccess(T data){
        return new ResultBean<>(HttpStatus.OK.toString(),SUCCESS,SUCC_MSG,data);
    }

    public static <T> ResultBean<T> makeFail(String code){
        return new ResultBean<>(code,FAIL,FAIL_MSG,null);
    }

    public static <T> ResultBean<T> makeFail(String code,String msg){
        return new ResultBean<>(code,FAIL,msg,null);
    }

    public static <T> ResultBean<T> makeFail(String code,String msg,T data){
        return new ResultBean<>(code,FAIL,msg,data);
    }
}
