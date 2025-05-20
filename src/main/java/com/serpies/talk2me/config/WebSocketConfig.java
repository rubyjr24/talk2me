package com.serpies.talk2me.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    public static final String ENDPOINT_HANDSHAKE = "/ws";
    public static final String APP_DESTINATION = "/api";
    public static final String USER_DESTINATION = "/user";
    public static final String BRODCAST = "/app";
    public static final String UNICAST = "/private";

    @Override
    public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry){
        messageBrokerRegistry.enableSimpleBroker(
                BRODCAST, // Para brodcast
                UNICAST // Para unicast
        );
        messageBrokerRegistry.setApplicationDestinationPrefixes(APP_DESTINATION); // Endpoint raíz para que los clientes envien información por el websocket
        messageBrokerRegistry.setUserDestinationPrefix(USER_DESTINATION); // // Mensajes privados hacia el cliente
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        // Endpoint para conectarse con el websocket -- handshake
        stompEndpointRegistry.addEndpoint(ENDPOINT_HANDSHAKE)
                .setAllowedOrigins("*"); // Cors
    }

}
