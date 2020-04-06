package com.blacklth.utrs.entity.pojo;

import java.io.Serializable;

/**
 * @author : LiaoTianHong
 * @date : 2019/7/15 14:54
 * @description:统一返回结果类
 */
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = -6248238306422472597L;

    private String code;

    private int state ;

    private String msg ;

    private T data ;


    public ResultBean(String code, int state, String msg, T data){
        this.code = code;
        this.state = state;
        this.msg = msg;
        this.data = data;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", state=" + state +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }


}
