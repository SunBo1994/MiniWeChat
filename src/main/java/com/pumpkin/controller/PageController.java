package com.pumpkin.controller;

import com.pumpkin.bean.NetworkLink;
import com.pumpkin.mongo.dao.NetworkLinkDao;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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

    @GetMapping("/")
    public String index(Model model) {
        List<NetworkLink> links = networkLinkDao.findAll();
        model.addAttribute("links", links);
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
