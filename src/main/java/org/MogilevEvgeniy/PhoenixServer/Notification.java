package org.MogilevEvgeniy.PhoenixServer;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

@Component
public class Notification extends TextWebSocketHandler {

    private Map<String, WebSocketSession> dictionary = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println(session.getId() + " - connected");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println(session.getId() + " - disconnected");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        String[] parts = payload.split("//", 3);
        if (parts.length == 1) dictionary.put(parts[0], session);
        else if (parts.length == 3) {
            if (dictionary.containsKey(parts[1])) {
                session = dictionary.get(parts[1]);
                try {
                    session.sendMessage(new TextMessage(payload));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
