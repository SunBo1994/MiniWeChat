package com.pumpkin.api;

import com.pumpkin.bean.ResponseBean;
import com.pumpkin.mongo.model.UserBean;
import com.pumpkin.mongo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserApi
 * @Author 孙博
 * @Date 2019/1/18 下午5:38
 * @Version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserApi {

    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping(value = "/register")
    public ResponseBean register(@RequestParam String username, @RequestParam String password, @RequestParam String nickname) {
        UserBean userBean = new UserBean(username, password, nickname);
        userService.save(userBean);
        return ResponseBean.success(userBean);
    }}
