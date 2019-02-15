package com.pumpkin.chat.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ChatMessage
 * @Author 孙博
 * @Date 2019/1/23 下午2:44
 * @Version 1.0
 */
@Data
public class ChatMessage implements Serializable {

    // 用户账号
    private String username;

    // 用户昵称
    private String nickname;

    // 用户头像
    private String avatar;

    // 消息内容
    private String content;

    // 发送时间
    private String sendTime;
}
