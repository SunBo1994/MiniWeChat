package com.pumpkin.mongo.model;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @ClassName UserBean
 * @Author 孙博
 * @Date 2019/1/18 下午7:59
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class UserBean implements Comparable<UserBean>{
    @Id
    private String id;

    // 用户名
    @Indexed(unique = true)
    private String username;

    // 密码
    @JsonIgnore
    private String password;

    // 昵称
    private String nickname;

    // 头像
    private String avatar;

    // 加入时间
    private Date joinTime;

    public UserBean(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    /**
     * 初始化：头像 + 注册时间
     */
    public void init() {
        try {
            Document document = Jsoup.connect("http://www.woyaogexing.com/touxiang/new/").get();
            Elements elements = document.select("div.txList");
            int size = elements.size();
            List<String> avatars = new ArrayList<>(size);
            elements.forEach(element -> avatars.add(element.select("img.lazy").attr("src")));
            Random random = new Random();
            this.avatar = avatars.get(random.nextInt(size));
            this.joinTime = new Date();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }

    @Override
    public int compareTo(UserBean userBean) {
        return this.username.compareTo((userBean.username));
    }
}
