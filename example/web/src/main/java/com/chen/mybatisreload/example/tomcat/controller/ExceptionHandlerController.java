package com.chen.mybatisreload.example.tomcat.controller;

import com.chen.mybatisreload.core.bean.ReloadContext;
import com.chen.mybatisreload.core.exceptions.ReloadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器。
 *
 * @author wrote-code
 */
@ControllerAdvice
public class ExceptionHandlerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    /**
     * 处理捕捉到的业务异常。
     *
     * @param e 异常。
     *
     * @return 通用返回结果。
     */
    @ExceptionHandler(ReloadException.class)
    @ResponseBody
    public ReloadContext handleReloadException(ReloadException e) {
        LOGGER.error("出现业务异常", e);
        String message = e.toString();
        ReloadContext reloadContext = new ReloadContext();
        reloadContext.setMessage(message);
        return reloadContext;
    }

    /**
     * 解析捕捉到的非业务异常（未知异常）。
     *
     * @param e 异常。
     *
     * @return 通用返回结果。
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ReloadContext handleException(Exception e) {
        String message = e.toString();
        LOGGER.error("出现未知异常", e);
        ReloadContext reloadContext = new ReloadContext();
        reloadContext.setMessage(message);
        return reloadContext;
    }

}
