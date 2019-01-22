package com.pumpkin.mongo.dao;

import com.pumpkin.mongo.model.UserBean;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;


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

//    public List<Document> findTop10ByUsernameLikeOrNicknameLike
}
