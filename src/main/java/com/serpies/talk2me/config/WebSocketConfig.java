package com.serpies.talk2me.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry){
        messageBrokerRegistry.enableSimpleBroker(
                "/app", // Para brodcast
                "/private" // Para unicast
        );
        messageBrokerRegistry.setApplicationDestinationPrefixes("/api"); // Endpoint raíz para que los clientes envien información por el websocket
        messageBrokerRegistry.setUserDestinationPrefix("/user"); // // Mensajes privados hacia el cliente
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        // Endpoint para conectarse con el websocket -- handshake
        stompEndpointRegistry.addEndpoint("/ws")
                .setAllowedOrigins("*"); // Cors
    }

//    @Override
//    public void configureClientOutboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                System.out.println("→ OUTBOUND: " + message);
//                return message;
//            }
//        });
//    }
//
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                System.out.println("← INBOUND: " + message);
//                return message;
//            }
//        });
//    }

}
