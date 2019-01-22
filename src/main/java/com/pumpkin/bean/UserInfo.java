package com.pumpkin.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * @ClassName UserInfo
 * @Author 孙博
 * @Date 2019/1/18 下午6:07
 * @Version 1.0
 */
@Data
public class UserInfo {
    @Id
    private String id;

    // 用户名
    @Indexed(unique = true)
    private String username;

    // 昵称
    private String nickname;

    // 头像
    private String avatar;

    // 在线状态
    private Integer status;

    // 是否是好友
    private boolean friend;
}
