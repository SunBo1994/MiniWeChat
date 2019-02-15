package com.pumpkin.config.sercurity;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AnyUserDetailsService anyUserDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.anyUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                /** /app/路径下的所有请求都要认证 */
                .antMatchers("/app/**").authenticated()
                .anyRequest().permitAll()
                /** 登陆页 */
                .and().formLogin().loginPage("/login").permitAll()
                /** 缓存用户登陆信息 */
                .and().rememberMe().alwaysRemember(true)
                /** 登出验证 */
                .and().logout().permitAll()
                /** 设置登陆失效时跳转页面 */
                .and().sessionManagement().invalidSessionUrl("/login")
                //TODO 不知道啥意思
                .and().csrf().disable();
    }

}