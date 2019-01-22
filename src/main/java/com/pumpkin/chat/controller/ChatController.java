package com.pumpkin.chat.controller;


import com.pumpkin.chat.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

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
    private ChatService chatService;
}
