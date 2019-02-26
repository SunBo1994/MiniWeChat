package com.pumpkin.config.sercurity;

import com.pumpkin.mongo.model.UserBean;
import com.pumpkin.mongo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户登录及授权配置
 */
@Slf4j
@Service
@AllArgsConstructor
public class AnyUserDetailsService implements UserDetailsService {

    /** spring security角色 */
    private final static String ROLE_TAG = "USER";

    private final UserService userService;

    /**
     * 权限验证
     * @param username 用户名(账号)
     * @return 用户详情
     * @throws UsernameNotFoundException 查询没有此用户异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBean userBean = userService.getByUsername(username);
        if (userBean == null) {
            throw new UsernameNotFoundException("username:" + username);
        }
        return createUserPrincipal(userBean);
    }

    /**
     * 包装用户信息
     * @param userBean 用户数据
     * @return 用户详情 用户权限验证
     */
    private UserDetails createUserPrincipal(UserBean userBean){
        /** 用户认证（用户名，密码，权限）*/
        log.info("登陆用户的信息: "+userBean.toString());
//        return org.springframework.security.core.userdetails.User.withUsername(userBean.getUsername())
//                .password(userBean.getPassword())
//                .roles(ROLE_TAG).build();
//        return org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
//                .username(userBean.getUsername())
//                .password(userBean.getPassword())
//                .roles(ROLE_TAG).build();
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return User.withUsername(userBean.getUsername())
                .passwordEncoder(encoder::encode)
                .password(userBean.getPassword())
                .roles(ROLE_TAG)
                .build();
    }

}