package com.serpies.talk2me.utilities.security;

import com.serpies.talk2me.config.Config;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private Config config;

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

        String authTypeWithSpace = this.config.getAuthType().concat(" ");

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
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
