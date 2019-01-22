package com.pumpkin.config.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.security.Principal;

/**
 * 监听 WebSocket 连接建立的事件
 */
@Component
@AllArgsConstructor
@Data
@Log4j2
public class WebSocketConnectHandler implements ApplicationListener<SessionConnectedEvent> {

    private final SimpMessagingTemplate template;

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        MessageHeaders headers = event.getMessage().getHeaders();
        Principal principal = SimpMessageHeaderAccessor.getUser(headers);
        Assert.notNull(principal, "principal is null");
        String username = principal.getName();
        log.info("[建立连接] {} ", username);
        // TODO 处理连接后的逻辑
    }
}
