package com.blacklth.utrs.error;

import java.util.ResourceBundle;

/**
 * 客户端异常
 * @author : LiaoTianHong
 * @date : 2019/7/25 10:47
 */
public class ClientError {
    private String code;
    private String msg;

    /**
     * 通过异常类名构建错误码和错误信息
     * 如果属性文件中不存在后面的msg信息，则填充为exception自带的message
     * @param e
     */
    ClientError(Exception e){
        String key = e.getClass().getSimpleName();
        ResourceBundle rb = ResourceBundle.getBundle("i18n");
        String errorMessage = rb.getString(key);
        String []  str = errorMessage.split(",");
        this.code = str[0];
        this.msg = e.getMessage();
        if(str.length > 1){
            this.msg = str[1];
        }

    }


    /**
     *  判断异常类名是否在配置文件中
     * @author     ：TianHong Liao
     * @date       ：Created in 2019/7/19 22:36
     * @param       e
     * @return     : java.lang.Boolean
     */
    static public Boolean isClientError(Exception e){
        String key = e.getClass().getSimpleName();
        ResourceBundle rb = ResourceBundle.getBundle("i18n");
        if(rb.containsKey(key)){
            return true;
        }
        return false;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
