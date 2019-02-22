package com.canghuang.logincenter.core.ExceptionHandler;

import com.canghuang.logincenter.core.Response.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cs
 * @date 2019/1/21
 * @description
 */
@Slf4j
@ControllerAdvice
public class MyExceptionHandler {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {}

    /**
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public AjaxResult errorHandler(BindException ex) {
        StringBuilder failureMsg = new StringBuilder();
        ex.getAllErrors().forEach(e -> failureMsg.append(e.getDefaultMessage()).append(" "));
        log.error("error => {}", failureMsg);
        return AjaxResult.failure(failureMsg.toString());
    }

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Map errorHandler(Exception ex) {
        Map map = new HashMap();
        map.put("code", 100);
        map.put("msg", ex.getMessage());
        log.error("error => {}", ex.getMessage());
        return map;
    }

}
