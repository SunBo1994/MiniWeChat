package com.pumpkin.controller;

import com.pumpkin.chat.service.ChatService;
import com.pumpkin.mongo.dao.NetworkLinkDao;
import com.pumpkin.mongo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


/**
 * @ClassName PageController
 * @Author 孙博
 * @Date 2019/1/18 下午8:28
 * @Version 1.0
 */
@Controller
@CrossOrigin
@AllArgsConstructor
public class PageController {
    private final NetworkLinkDao networkLinkDao;
    private final ChatService chatService;
    private final UserService userService;

//    @GetMapping("/")
//    public String index(Model model) {
////        List<NetworkLink> links = networkLinkDao.findAll();
////        model.addAttribute("links", links);
//
//        return "chat";
//    }
    @GetMapping("/")
    public String index(@AuthenticationPrincipal Principal principal) {
        return "redirect:/app/chat";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
