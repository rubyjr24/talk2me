package com.serpies.talk2me.utilities.security.websocket;

import com.serpies.talk2me.utilities.security.auth.JwtUtil;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketEventListener {

    private static final Map<Long, String> sessionIds = new ConcurrentHashMap<>();

    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        Long userId = extractUserId(event.getMessage());
        if (userId != null) {
            sessionIds.put(userId, StompHeaderAccessor.wrap(event.getMessage()).getSessionId());
        }
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        Long userId = extractUserId(event.getMessage());
        if (userId != null) {
            sessionIds.remove(userId);
        }
    }

    private Long extractUserId(Message<?> message) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        Principal principal = accessor.getUser();

        if (principal instanceof UsernamePasswordAuthenticationToken token) {
            Object detailsObj = token.getDetails();

            if (detailsObj instanceof Map<?, ?> details) {
                Object id = details.get(JwtUtil.USER_ID_FIELD);
                if (id instanceof Long userId) {
                    return userId;
                }
            }
        }

        return null;
    }

    public static String getSessionIdForUser(Long userId) {
        return sessionIds.get(userId);
    }

}

