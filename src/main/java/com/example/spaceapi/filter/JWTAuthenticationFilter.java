package com.example.spaceapi.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.spaceapi.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.example.spaceapi.filter.SecurityConstants.JWT_SECRET;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authManager;

    private ObjectMapper mapper;

    // 15 minutes
    private final int JWT_TIMEOUT = 900000;

    public JWTAuthenticationFilter(AuthenticationManager authManager) {
        this.authManager = authManager;
        this.mapper = new ObjectMapper();

        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User credentials = new ObjectMapper().readValue(request.getInputStream(), User.class);

            return authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    credentials.getEmail(),
                    credentials.getPassword(),
                    new ArrayList<>()
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String token = JWT.create()
                .withSubject(authResult.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TIMEOUT))
                .sign(Algorithm.HMAC512(JWT_SECRET));

        response.setContentType("application/json");
        response.getWriter().write(mapResponseBody(new SuccessDto(authResult.getName(), token)));
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        // Logging?

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(mapResponseBody(new FailDto(failed.getMessage())));
        response.getWriter().flush();
    }

    private String mapResponseBody(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    @Data
    @AllArgsConstructor
    private static class SuccessDto {

        private String email;

        private String token;

    }

    @Data
    @AllArgsConstructor
    private static class FailDto {

        private String message;

    }
}
