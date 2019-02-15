package com.pumpkin.mongo.dao;

import com.pumpkin.mongo.model.RelationBean;
import com.pumpkin.mongo.model.UserBean;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @ClassName RelationDao
 * @Author 孙博
 * @Date 2019/1/23 下午8:08
 * @Version 1.0
 */
@Repository
@AllArgsConstructor
public class RelationDao {

    private MongoTemplate template;

    public RelationBean save(RelationBean relationBean){
        template.save(relationBean,"user_relation");
        return relationBean;
    }

    /**
     * 删除好友
     * @param userId 用户id
     * @param friendId 好友id
     */
    public void removeFriends(String userId, String friendId){
        BasicQuery query = new BasicQuery("{$or:[{me:'"+userId+"',friend:'"+friendId+"'},{me:'"+friendId+"',friend:'"+userId+"'}]}");
        template.findAllAndRemove(query,"user_relation");
    }

    /**
     * 根据自己和好友的id查询数据
     * @param userId 用户自己的id
     * @param friendId 好友id
     * @return RelationBean   不为空说明是好友  为null 说明不是好友
     */
    public RelationBean findFriendByMeAndFriendId(String userId, String friendId){
        BasicQuery query = new BasicQuery("{$or:[{me:'"+userId+"',friend:'"+friendId+"'},{me:'"+friendId+"',friend:'"+userId+"'}]}");
        return template.findOne(query, RelationBean.class, "user_relation");
    }

    /**
     * 好友列表
     * @param userId 用户id
     * @return 好友列表
     */
    public List<RelationBean> findAllFriendByMe(String userId){
        BasicQuery query = new BasicQuery("{$or:[{me:'"+userId+"'},{friend:'"+userId+"'}]}");
        return template.find(query,RelationBean.class,"user_relation");
    }
}
