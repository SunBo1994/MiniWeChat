package com.pumpkin.api;

import com.pumpkin.bean.ResponseBean;
import com.pumpkin.mongo.model.RelationBean;
import com.pumpkin.mongo.model.UserBean;
import com.pumpkin.mongo.service.RelationService;
import com.pumpkin.mongo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private RelationService relationService;

    /**
     * 用户注册
     */
    @PostMapping(value = "/register")
    public ResponseBean register(@RequestParam String username, @RequestParam String password, @RequestParam String nickname) {
        UserBean userBean = new UserBean(username, password, nickname);
        userService.save(userBean);
        return ResponseBean.success(userBean);
    }

    @GetMapping("/userInfo")
    public ResponseBean userInfo(@AuthenticationPrincipal Principal principal){
        try {
            //TODO ?????
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UserBean userBean = userService.getByUsername(principal.getName());
        return ResponseBean.success(userBean);
    }

    /**
     * 添加好友
     */
    @PostMapping("/friend")
    public ResponseBean add(@AuthenticationPrincipal Principal principal, @RequestParam String friendUsername) {
        RelationBean relationBean = new RelationBean(principal.getName(), friendUsername);
        relationService.add(relationBean);
        return ResponseBean.success(null);
    }

    /**
     * 好友列表
     * @param principal 用户信息
     * @return
     */
    @GetMapping(value = "/friend")
    public ResponseBean friends(@AuthenticationPrincipal Principal principal) {
        String username = principal.getName();
        List<String> friendUsernameList = relationService.friends(username);
        List<UserBean> friendList = userService.getAllByUsernameList(friendUsernameList);
        Collections.sort(friendList);
        return ResponseBean.success(friendList);
    }

    @PostMapping(value = "/common/user")
    public ResponseBean searchUser(@RequestParam String keyword){
        if (StringUtils.isEmpty(keyword)){
            return ResponseBean.success(new ArrayList<>());
        }
        List<UserBean> userBeans = userService.searchUser(keyword);
        return ResponseBean.success(userBeans);
    }

    @GetMapping(value = "/common/online")
    public ResponseBean onlineUser(@AuthenticationPrincipal Principal principal){
        String usernane = principal.getName();
        List<UserBean> onlineUsers = userService.findOnlineUsers(usernane);
        return ResponseBean.success(onlineUsers);
    }
}
