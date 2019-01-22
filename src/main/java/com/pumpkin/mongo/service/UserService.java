package com.pumpkin.mongo.service;

import com.pumpkin.mongo.dao.UserDao;
import com.pumpkin.mongo.model.UserBean;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * @ClassName UserService
 * @Author 孙博
 * @Date 2019/1/18 下午7:52
 * @Version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    /** 验证用户密码是否符合规则的正则 */
    private static final String PASSWORD_RULE = "^(\\w){6,20}$";

    private UserDao userDao;

    /**
     * 保存新用户的信息
     * @param userBean 用户信息
     * @return 完成的用户信息
     */
    public UserBean save(UserBean userBean){
        /** 检查用户参数 */
        paramsCheck(userBean);
        /** 初始化用户信息  头像等 */
        userBean.init();
        log.info("the user to add: "+userBean.toString());
        return userDao.save(userBean);
    }

    /**
     * 注册用户参数校验
     * @param userBean 用户信息
     */
    private void paramsCheck(UserBean userBean) {
        log.info("paramsCheck:" + userBean.toString());
        String username = userBean.getUsername();
        UserBean result = getByUsername(username);
        if (result != null) {
            throw new RuntimeException("用户已存在");
        }
        String password = userBean.getPassword();
        if (!Pattern.compile(PASSWORD_RULE).matcher(password).matches()) {
            throw new RuntimeException("密码必须为 6~20 位字母、数字、下划线");
        }
        String nickname = userBean.getNickname();
        if (StringUtils.isEmpty(nickname)) {
            throw new RuntimeException("用户昵称不能为空");
        }
    }

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    public UserBean getByUsername(String username){
        return userDao.findUserByName(username);
    }

}
