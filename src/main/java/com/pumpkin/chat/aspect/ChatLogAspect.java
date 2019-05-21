package com.pumpkin.chat.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * @ClassName ChatLogAspect
 * @Author 孙博
 * @Date 2019/4/12 4:25 PM
 * @Version 1.0
 */
@Aspect
@Component
public class ChatLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatLogAspect.class);

    private static final String CHART_RECORD = "execution(public * com.pumpkin.chat.controller.ChatController.getChatRecord(..))";

    @Pointcut(value = CHART_RECORD)
    public void chatRecordAspect(){

    }

    @Before(value = "chatRecordAspect()")
    public void chatBefore(JoinPoint joinPoint){
        Document logDoc = new Document();
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            logDoc.put(headerName, request.getHeader(headerName));
        }


        Object[] args = joinPoint.getArgs();
        Arrays.stream(args).forEach(arg -> {
            if ("HttpHeader".equals(arg.getClass().getSimpleName())) {

            }
        });
        logDoc.put("request_url", request.getRequestURL().toString());
        logDoc.put("log_time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss")));
        LOGGER.info(logDoc.toJson());
    }
}
