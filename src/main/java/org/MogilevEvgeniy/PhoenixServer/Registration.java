package org.MogilevEvgeniy.PhoenixServer;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
public class Registration extends TextWebSocketHandler {

    private WebSocketSessionManager webSocketSessionManager = new WebSocketSessionManager();

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
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException{
        //обработка сообщения
        String payload = message.getPayload();
        System.out.println(payload);

        String[] parts = payload.split("//", 2);
        payload = String.valueOf(Function.NewUser(parts[0], parts[1]));
        System.out.println(payload+" - reg");

        try {session.sendMessage(new TextMessage(payload));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
