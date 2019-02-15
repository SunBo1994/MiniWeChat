package com.pumpkin.chat.controller;


import com.alibaba.fastjson.JSON;
import com.pumpkin.bean.ResponseBean;
import com.pumpkin.chat.model.BaseMessage;
import com.pumpkin.chat.model.ChatMessage;
import com.pumpkin.chat.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ChatController
 * @Author 孙博
 * @Date 2019/1/18 下午9:37
 * @Version 1.0
 */
@Slf4j
@Controller
@AllArgsConstructor
public class ChatController {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ChatService chatService;

    @MessageMapping("/chat")
    public void chat(Principal principal, String message) {
        BaseMessage baseMessage = JSON.parseObject(message, BaseMessage.class);
        baseMessage.setSender(principal.getName());
        Date createDate = new Date();
        baseMessage.setSendTime(sdf.format(createDate));
        baseMessage.setTimestamp(createDate.getTime());
        chatService.sendMessage(baseMessage);
    }

    @RequestMapping(value = "/app/chat")
    public String chat(){
        return "chat";
    }
    /**
     *
     * @param principal
     * @param friendId
     * @return
     */
    @PostMapping(value = "/app/getRecord/")
    @ResponseBody
    public ResponseBean getChatRecord(@AuthenticationPrincipal Principal principal, @RequestParam String friendId){
        String me = principal.getName();
        List<ChatMessage> chatRecord = chatService.getChatRecord(me, friendId);
        return ResponseBean.success(chatRecord);
    }
}
