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

    private WebSocketSessionManager webSocketSessionManager = new WebSocketSessionManager();

    private Map<String, WebSocketSession> dictionary = new HashMap<String,WebSocketSession>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        //обработка подключения
        webSocketSessionManager.addWebSocketSession(session);
        System.out.println(session.getId()+" - connected");
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        //обработка разрыва соеденения
        webSocketSessionManager.removeWebSocketSession(session);
        System.out.println(session.getId()+" - disconnected");
    }
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        //обработка сообщения
        String payload = message.getPayload();
        System.out.println(payload);

        String[] parts = payload.split("//", 3);
        if (parts.length == 1) dictionary.put(parts[0], session);
        else if(parts.length == 3){
            if (dictionary.containsKey(parts[1])) {
                session = dictionary.get(parts[1]);
                try {
                    session.sendMessage(new TextMessage(payload));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                System.out.println("NOT IN MAP");
                System.out.println(dictionary.toString());
            }
        }
        System.out.println(payload+" - reg");
    }
}
