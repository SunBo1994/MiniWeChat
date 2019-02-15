package com.pumpkin.mongo.service;

import com.pumpkin.mongo.dao.RelationDao;
import com.pumpkin.mongo.dao.UserDao;
import com.pumpkin.mongo.model.RelationBean;
import com.pumpkin.mongo.model.UserBean;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName RelationService
 * @Author 孙博
 * @Date 2019/1/23 下午8:42
 * @Version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class RelationService {

    private RelationDao relationDao;
    private UserDao userDao;

    public RelationBean add(RelationBean relationBean){
        paramsCheck(relationBean);
        log.info("addFriend:" + relationBean.toString());
        return relationDao.save(relationBean);
    }


    /**
     * 删除好友
     * @param userId 用户id
     * @param friendId 朋友id
     */
    public void removeFriend(String userId, String friendId){
        relationDao.removeFriends(userId, friendId);
    }

    /**
     * 好友集合
     * @param userId
     * @return
     */
    public List<String> friends(String userId){
        List<String> friendUsernameList = new ArrayList<>();
        List<RelationBean> relations = relationDao.findAllFriendByMe(userId);
        for (RelationBean relation : relations) {
            String friendUsername = userId.equals(relation.getMe()) ? relation.getFriend() : relation.getMe();
            friendUsernameList.add(friendUsername);
        }
        return friendUsernameList;
    }


    /**
     * 参数校验
     */
    private void paramsCheck(RelationBean relationBean) {
        if (relationBean.getMe().equals(relationBean.getFriend())){
            throw new RuntimeException("不能添加自己为好友");
        }
        UserBean result = userDao.findUserByName(relationBean.getFriend());
        if (result == null) {
            throw new RuntimeException("用户不存在");
        }
        RelationBean hasRelationBean = relationDao.findFriendByMeAndFriendId(relationBean.getMe(), relationBean.getFriend());
        if (hasRelationBean != null){
            throw new RuntimeException("你们已经是好友关系啦");
        }
    }
}
