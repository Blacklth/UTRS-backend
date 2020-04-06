package com.blacklth.utrs.error;


import com.blacklth.utrs.entity.pojo.ResultBean;
import com.blacklth.utrs.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : LiaoTianHong
 * @date : 2019/7/25 10:48
 */
@RestControllerAdvice
public class GlobalErrorHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     *  处理业务异常
     * @author     ：LiaoTianHong
     * @date       ：Created in 2019/8/15 14:32
     * @param       request
     * @param       e
     * @return     : com.crcloud.rbacs.entity.pojo.ResultBean<java.lang.Object>
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultBean<Object> serviceExceptionHandler(HttpServletRequest request, ServiceException e){
        return ResultUtil.makeFail(e.getCode(),e.getMsg());
    }

    /**
     *  处理http异常和系统异常
     * @author     ：TianHong Liao
     * @date       ：Created in 2019/7/19 22:37
     * @param       request
     * @param       e
     * @return     : com.blacklth.template.entity.pojo.ResultBean<java.lang.Object>
     */
    @ExceptionHandler(Exception.class)
    public ResultBean<Object> systemeExceptionHandler(HttpServletRequest request,Exception e){

        if(ClientError.isClientError(e)){
            ClientError clientError = new ClientError(e);
            logger.error(e.getMessage());
            return ResultUtil.makeFail(clientError.getCode(),clientError.getMsg());
        }
        if(e instanceof  RuntimeException){
            RuntimeException exception = (RuntimeException) e;
            e.printStackTrace();
            return ResultUtil.makeFail(exception.getClass().getSimpleName(),exception.getMessage());
        }
        e.printStackTrace();
        return ResultUtil.makeFail(e.getClass().getSimpleName(),e.getMessage());
    }

}
