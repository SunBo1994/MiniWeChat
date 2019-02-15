package com.pumpkin.config;

import com.pumpkin.bean.ResponseBean;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName GlobalExceptionHandler
 * @Author 孙博
 * @Date 2019/1/18 下午7:36
 * @Version 1.0
 */
@ControllerAdvice
@Log4j2
@AllArgsConstructor
public class GlobalExceptionHandler {

    /**
     * 抛出错误前，打印错误日志
     **/
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseBean handleException(Exception exception){
        log.error(exception.getMessage(), exception);
        return ResponseBean.error(exception);
    }

}
