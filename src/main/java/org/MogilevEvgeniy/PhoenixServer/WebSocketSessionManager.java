package org.MogilevEvgeniy.PhoenixServer;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketSessionManager {

    private final Map<String, WebSocketSession> webSocketSessions = new HashMap<>();

    public Collection<WebSocketSession> getWebSocketSessionsExcept(WebSocketSession webSocketSession){
        Map<String, WebSocketSession> nonMatchingSessions = new HashMap<>(webSocketSessions);
        return (Collection<WebSocketSession>) webSocketSession;
    }

    public void addWebSocketSession(WebSocketSession webSocketSession){
        this.webSocketSessions.put(webSocketSession.getId(), webSocketSession);
    }

    public void removeWebSocketSession(WebSocketSession webSocketSession){
        this.webSocketSessions.remove(webSocketSession.getId(), webSocketSession);
    }
}
