package org.MogilevEvgeniy.PhoenixServer;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.sql.SQLException;


@Component
public class Login extends TextWebSocketHandler {

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    System.out.println(session.getId() + " - connected");
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    System.out.println(session.getId() + " - disconnected");
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
      throws SQLException, ClassNotFoundException {
    String payload = message.getPayload();
    String[] parts = payload.split("//", 2);
    payload = Function.login(parts[0], parts[1]);
    try {
      session.sendMessage(new TextMessage(payload));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
