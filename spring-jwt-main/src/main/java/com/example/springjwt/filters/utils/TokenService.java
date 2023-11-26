package com.example.springjwt.filters.utils;

import com.example.springjwt.models.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private String secretKey = "cnskjdfsd7687";

    public TokenService() {}

    public String generate(User user) {
        return String.format("%s&%s", user.getUsername(), user.getPassword());
    }

    public String getTokenHeader(HttpServletRequest req) {
        String accessToken = req.getHeader(TOKEN_HEADER);
        if (accessToken != null && accessToken.startsWith(TOKEN_PREFIX)) {
            return accessToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public String[] getClaims(String token) {
        return token.split("&");
    }

    public boolean validateToken() {
        return false;
    }

}
