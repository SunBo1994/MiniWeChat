package com.pumpkin.mongo.dao;

import com.pumpkin.chat.model.BaseMessage;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName BaseMessageDao
 * @Author 孙博
 * @Date 2019/1/25 上午11:52
 * @Version 1.0
 */
@Repository
@AllArgsConstructor
public class BaseMessageDao {

    private MongoTemplate template;

    public void save(BaseMessage baseMessage){
        template.save(baseMessage,"base_message");
    }

    public List<BaseMessage> search(String sender,String receiver){
        BasicQuery query = new BasicQuery("{$or:[{sender:'" + sender + "',receiver:'" + receiver + "'},{sender:'" + receiver + "',receiver:'" + sender + "'}]}");
        query.setSortObject(Document.parse("{timestamp:-1}"));
        return template.find(query,BaseMessage.class,"base_message");
    }

    public List<BaseMessage> search(String me){
        BasicQuery query = new BasicQuery("{receiver:'IM_ALL'}");
        query.setSortObject(Document.parse("{timestamp:-1}"));
        return template.find(query,BaseMessage.class,"base_message");
    }
}
