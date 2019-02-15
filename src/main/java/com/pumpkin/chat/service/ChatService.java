package com.pumpkin.chat.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pumpkin.chat.model.BaseMessage;
import com.pumpkin.chat.model.ChatMessage;
import com.pumpkin.mongo.dao.BaseMessageDao;
import com.pumpkin.mongo.model.UserBean;
import com.pumpkin.mongo.service.UserService;
import com.pumpkin.utils.RobotTools;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName ChatService
 * @Author 孙博
 * @Date 2019/1/18 下午9:38
 * @Version 1.0
 */
@Data
@Slf4j
@Service
public class ChatService {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");

    private static final String ROBOT = "IM_ROBOT";

    private static final String ALL = "IM_ALL";

    private final SimpMessagingTemplate template;

    private final UserService userService;

    private final BaseMessageDao baseMessageDao;

    /**
     * 发送消息
     * @param baseMessage 消息内容  包括
     *                    sender  发送者
     *                    receiver 接收者
     *                    receiverType 接受者类型
     *                    content 消息内容
     *                    sendTime 发送时间
     *                    timestamp 时间戳
     */
    public void sendMessage(BaseMessage baseMessage){
        String receiver = baseMessage.getReceiver();
        switch (receiver) {
            case ROBOT:
                robotReply(baseMessage);
                break;
            case ALL:
                sendToAll(JSON.toJSONString(baseMessage));
                saveBaseMessage(baseMessage);
                break;
            default:
                sendToUser(JSON.toJSONString(baseMessage));
                saveBaseMessage(baseMessage);
                break;
        }
    }


    private void sendToUser(String baseMessage){
        JSONObject json = JSONObject.parseObject(baseMessage);
        ChatMessage chatMessage = createChatMessage(json.getString("sender"), json.getString("content"),json.getLong("timestamp"));
        log.info("[发送][个人消息][{} -> {}] <{}>", chatMessage.getUsername(), json.getString("receiver"), chatMessage.getContent());
        template.convertAndSendToUser(json.getString("receiver"), "/topic/chat", JSON.toJSONString(chatMessage));
    }

    /**
     * 发送给所有人
     */
    private void sendToAll(String baseMessage) {
        JSONObject json = JSONObject.parseObject(baseMessage);
        ChatMessage chatMessage = createChatMessage(json.getString("sender"), json.getString("content"),json.getLong("timestamp"));
        log.info("[发送][全服消息][{}] <{}>", json.getString("receiver"), chatMessage.getContent());
        template.convertAndSend("/topic/notice", JSON.toJSONString(chatMessage));
    }

    /**
     * 机器人回复
     */
    private void robotReply(BaseMessage baseMessage) {
//        String replyContent = RobotTools.reply(baseMessage.getContent());
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setAvatar("http://img.hb.aicdn.com/79cf2c86f7c626ae5d135786b912803275fcd0c82423-LJyhZR_fw658");
        chatMessage.setNickname("AnyIM 小沫");
        chatMessage.setContent(RobotTools.reply(baseMessage.getContent(),baseMessage.getSender()));
        chatMessage.setSendTime(packageChatMessage(new Date().getTime()));
        chatMessage.setUsername(ROBOT);
        log.info("[发送][机器人]" + baseMessage.getSender());
        template.convertAndSendToUser(baseMessage.getSender(), "/topic/chat", JSON.toJSONString(chatMessage));
    }



    private static String packageChatMessage(Long timestamp){
        Date sendDate = new Date(timestamp);
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.add(Calendar.DATE,-1);
        Date yesterday = ca.getTime();
        boolean isYesterday = sdf.format(yesterday).equals(sdf.format(sendDate));
        if (isYesterday) {
            return "昨天 "+sendDate.getHours()+":"+sendDate.getMinutes();
        }else if (sendDate.before(yesterday)){
            return sendDate.getMonth()+1+"-"+sendDate.getDate()+" "+sendDate.getHours()+":"+sendDate.getMinutes();
        }else {
            return sendDate.getHours()+":"+sendDate.getMinutes();
        }
    }

    private ChatMessage createChatMessage(String sender, String content,Long timestamp){
        ChatMessage chatMessage = new ChatMessage();
        UserBean userBean = userService.getByUsername(sender);
        chatMessage.setAvatar(userBean.getAvatar());
        chatMessage.setNickname(userBean.getNickname());
        chatMessage.setContent(content);
        chatMessage.setSendTime(packageChatMessage(timestamp));
        chatMessage.setUsername(userBean.getUsername());
        return chatMessage;
    }

    private void saveBaseMessage(BaseMessage baseMessage){
        baseMessage.setContent(baseMessage.getContent().trim());
        baseMessageDao.save(baseMessage);
    }

    public List<ChatMessage> getChatRecord(String me,String friendId){
        UserBean user = userService.getByUsername(me);
        UserBean friend = userService.getByUsername(friendId);
        List<BaseMessage> baseMessageList = baseMessageDao.search(me, friendId);
        Collections.sort(baseMessageList);
        List<ChatMessage> chatMessageList = new ArrayList<>();
        for (BaseMessage baseMessage : baseMessageList) {
            ChatMessage chatMessage = new ChatMessage();
            //消息发送人
            chatMessage.setUsername(baseMessage.getSender());
            packageChatMessage(chatMessage,user,friend);
            //消息内容
            chatMessage.setContent(baseMessage.getContent());
            chatMessage.setSendTime(packageChatMessage(baseMessage.getTimestamp()));
            chatMessageList.add(chatMessage);
        }
        return chatMessageList;
    }

    private void packageChatMessage(ChatMessage chatMessage,UserBean user,UserBean friend){
        UserBean userBean;
        if (chatMessage.getUsername().equals(user.getUsername())){
            userBean = user;
        }else {
            userBean = friend;
        }
        chatMessage.setAvatar(userBean.getAvatar());
        chatMessage.setNickname(userBean.getNickname());
    }
}
