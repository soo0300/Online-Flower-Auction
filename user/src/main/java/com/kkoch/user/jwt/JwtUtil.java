package com.kkoch.user.jwt;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    public String getEmailByJWT(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
