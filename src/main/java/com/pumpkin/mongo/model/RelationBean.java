package com.pumpkin.mongo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @ClassName RelationBean
 * @Author 孙博
 * @Date 2019/1/23 下午8:11
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class RelationBean implements Serializable {

    @Id
    private String id;

    // 自身 ID
    private String me;

    // 好友 ID
    private String friend;

    public RelationBean(String me, String friend) {
        this.me = me;
        this.friend = friend;
    }
}
