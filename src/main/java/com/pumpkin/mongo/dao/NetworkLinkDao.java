package com.pumpkin.mongo.dao;

import com.pumpkin.bean.NetworkLink;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName NetworkLinkDao
 * @Author 孙博
 * @Date 2019/1/18 下午8:31
 * @Version 1.0
 */
@Slf4j
@Repository
@AllArgsConstructor
public class NetworkLinkDao {
    private MongoTemplate template;

    public List<NetworkLink> findAll(){
        BasicQuery query = new BasicQuery("{}");
        query.setSortObject(Document.parse("{order:1}"));
        return template.find(query, NetworkLink.class, "network_link");
    }
}
