package com.app.JMS;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

/**
 * Created by ownlove on 2019/1/28.
 */
@Data
@Component
public class WebSocketClient {
    private Session session;
    private String clientIp;
    private String sessionId;
}
