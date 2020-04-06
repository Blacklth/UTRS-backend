package com.blacklth.utrs.error;

/**
 * 业务异常
 * @author : LiaoTianHong
 * @date : 2019/7/25 10:47
 */
public class ServiceException extends RuntimeException{
    private String code ;

    private String msg;

   public ServiceException(Throwable cause, String code, String msg){
        super(cause);
        this.code = code;
        this.msg = msg;
    }


    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}


