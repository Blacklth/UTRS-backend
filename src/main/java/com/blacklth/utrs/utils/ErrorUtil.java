package com.blacklth.utrs.utils;


import com.blacklth.utrs.error.ServiceException;

/**
 * @author : LiaoTianHong
 * @date : 2019/7/25 11:14
 */
public class ErrorUtil {
    /**
     *  异常抛出方式
     * @author     ：LiaoTianHong
     * @date       ：Created in 2019/7/25 11:13
     * @param       code
     * @param       msg
     * @return     : com.crcloud.rbacs.error.ServiceException
     */
    public static ServiceException wrap(String code, String msg){
        return new ServiceException(null,code,msg);
    }

    /**
     *  异常抛出方式(默认设置code为500)
     * @author     ：LiaoTianHong
     * @date       ：Created in 2019/7/25 11:13
     * @param       msg
     * @return     : com.crcloud.rbacs.error.ServiceException
     */
    public static ServiceException wrap(String msg){
        return new ServiceException(null, "500 ServiceException",msg);
    }
}
