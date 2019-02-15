package com.pumpkin.chat.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @ClassName ChatMessage
 * @Author 孙博
 * @Date 2019/1/23 下午2:23
 * @Version 1.0
 */
@Data
public class BaseMessage implements Serializable,Comparable<BaseMessage> {
    @Id
    private String id;

    // 发送者
    private String sender;

    // 接受者
    private String receiver;

    // 接受者类型
    private String receiverType;

    // 消息内容
    private String content;

    // 发送时间
    private String sendTime;

    //时间戳
    private Long timestamp;

    @Override
    public int compareTo(BaseMessage o) {
        return this.timestamp.compareTo(o.timestamp);
    }
}
