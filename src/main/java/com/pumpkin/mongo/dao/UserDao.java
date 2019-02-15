package com.pumpkin.mongo.dao;

import com.pumpkin.mongo.model.UserBean;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @ClassName UserDao
 * @Author 孙博
 * @Date 2019/1/18 下午7:44
 * @Version 1.0
 */
@Repository
@AllArgsConstructor
public class UserDao {
    private MongoTemplate template;

    public UserBean save(UserBean userBean){
        template.save(userBean,"user_detail");
        return userBean;
    }

    /**
     * 根据用户名查询用户信息用于权限验证
     * @param username 用户名
     * @return 用户信息
     */
    public UserBean findUserByName(String username){
        BasicQuery query = new BasicQuery("{username:'" + username + "'}");
        return template.findOne(query, UserBean.class, "user_detail");
    }

    public List<UserBean> findFriends(List<String> friendNameList){
        Query query = new Query(Criteria.where("username").in(friendNameList));
        return template.find(query,UserBean.class,"user_detail");
    }

    public List<UserBean> findTop10ByUsernameLikeOrNicknameLike(String keywords){
        BasicQuery query = new BasicQuery("{$or:[{username:{$regex:'^" + keywords + "'}},{nickname:{$regex:'^" + keywords + "'}}]}");
        query.setSortObject(Document.parse("{username:1}"));
        query.limit(10);
        return template.find(query,UserBean.class,"user_detail");
    }

    public List<UserBean> findUserByUsername(List<String> usernameList){
        Query query = new Query(Criteria.where("username").in(usernameList));
        return template.find(query,UserBean.class,"user_detail");
    }

    public List<UserBean> findOnlineUser(String username){
        BasicQuery query = new BasicQuery("{username:{$ne:'" + username + "'}}");
        return template.find(query ,UserBean.class,"user_online");
    }

    public void addOnline(UserBean userBean){
        template.save(userBean,"user_online");
    }

    public void removeOnline(UserBean userBean){
        template.remove(userBean,"user_online");
    }
}
