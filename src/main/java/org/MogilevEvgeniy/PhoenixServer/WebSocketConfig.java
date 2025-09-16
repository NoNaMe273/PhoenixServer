package org.MogilevEvgeniy.PhoenixServer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(new Registration(), "/Registration").setAllowedOriginPatterns("*");
    registry.addHandler(new Login(), "/Login").setAllowedOriginPatterns("*");
    registry.addHandler(new NewDialog(), "/NewDialog").setAllowedOriginPatterns("*");
    registry.addHandler(new OpenDialog(), "/OpenDialog").setAllowedOriginPatterns("*");
    registry.addHandler(new GettingDialogNames(), "/DialogList").setAllowedOriginPatterns("*");
    registry.addHandler(new Notification(), "/Notification").setAllowedOriginPatterns("*");
    registry.addHandler(new Profile(), "/OpenProfile").setAllowedOriginPatterns("*");
  }
}
