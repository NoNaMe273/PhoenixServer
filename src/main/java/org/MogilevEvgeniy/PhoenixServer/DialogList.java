package org.MogilevEvgeniy.PhoenixServer;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.sql.SQLException;

@Component
public class DialogList extends TextWebSocketHandler {
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
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException, SQLException, ClassNotFoundException {
        //обработка сообщения
        String payload = message.getPayload();
        System.out.println(payload);

        payload = String.valueOf(Function.AllDialog(payload));
        System.out.println(payload);

        try {session.sendMessage(new TextMessage(payload));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
