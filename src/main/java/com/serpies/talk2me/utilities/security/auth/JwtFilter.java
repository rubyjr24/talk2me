package com.serpies.talk2me.utilities.security.auth;

import com.serpies.talk2me.utilities.Properties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTH_HEADER = "Authorization";

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private Properties properties;

    @Override
    public void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader(AUTH_HEADER);

        if (authHeader == null){
            filterChain.doFilter(request, response);
            return;
        }

        String authTypeWithSpace = this.properties.getAuthType().concat(" ");

        if (!authHeader.startsWith(authTypeWithSpace)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(authTypeWithSpace.length());
        String email = this.jwtUtil.getEmail(token);

        if (email == null || !this.jwtUtil.isTokenValid(token)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: Invalid Token");
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, List.of());

            Map<String, Object> details = new HashMap<>();
            details.put(JwtUtil.USER_ID_FIELD, this.jwtUtil.getUserId(token));
            authentication.setDetails(details);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
