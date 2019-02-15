package com.pumpkin.utils;

import org.bson.Document;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName RobotTools
 * @Author 孙博
 * @Date 2019/2/15 上午10:31
 * @Version 1.0
 */
public class RobotTools {
    private final static String KEY_1 = "5d2d5799befb4c46a62d8abb39f1d448";
    private final static String KEY_2 = "ad3fb0d1baee48ca9196cd69d793f0d7";

    private final static String SECRET = "288ef3d5df1634c2";

    private final static String API = "http://openapi.tuling123.com/openapi/api/v2";

    /**
     * 调用机器人的智能回复
     */
    public static String reply(String content,String username){
        try {
            Document params = packageParams(username, content);
            org.jsoup.nodes.Document document = Jsoup.connect(API)
                    .requestBody(params.toJson())
                    .postDataCharset("UTF-8")
                    .ignoreContentType(true)
                    .post();
            String text = document.text();
            return ((Document)(Document.parse(text).get("results", List.class).get(0))).get("values",Document.class).getString("text");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }

    private static Document packageParams(String username, String content){
        String userId = username.hashCode()+"";
        if (userId.length() < 32){
            for (int i = userId.length();userId.length()==32;i++){
                userId = "0"+userId;
            }
        }else {
            userId = userId.substring(0,32);
        }
        Document paramsDoc = org.bson.Document.parse("{\n" +
                "\t\"reqType\":0,\n" +
                "    \"perception\": {\n" +
                "        \"inputText\": {\n" +
                "            \"text\": \""+content+"\"\n" +
                "        },\n" +
                "    },\n" +
                "    \"userInfo\": {\n" +
                "        \"apiKey\": \""+KEY_1+"\",\n" +
                "        \"userId\": \""+userId+"\"\n" +
                "    }\n" +
                "}");

        return paramsDoc;

    }
}
