package com.blacklth.utrs.error;


import com.blacklth.utrs.entity.pojo.ResultBean;
import com.blacklth.utrs.utils.ResultUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 拦截404异常并抛出信息
 * @author : LiaoTianHong
 * @date : 2019/7/25 13:52
 */
@Controller
public class NoHandlerFoundErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    private static final String ERROR_PATH = "/error";

    @GetMapping(ERROR_PATH)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResultBean<Object> error(HttpServletRequest request) {

        Enumeration<String> attributes = request.getAttributeNames();
        Map<String, Object> map = new HashMap<>(16);
        while (attributes.hasMoreElements()) {
            String name = attributes.nextElement();
            if (name.startsWith("java")) {
                Object value = request.getAttribute(name);
                map.put(name, value);
            }
        }
        String msg = "请求路径 "+ map.get("javax.servlet.error.request_uri")+ "不存在";
        return ResultUtil.makeFail("404 Not Found",msg);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
