package com.kkoch.user.jwt;

import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil {
    public String getEmailByJWT(){
//        return SecurityContextHolder.getContext().getAuthentication().getName();
        return null;
    }
}
