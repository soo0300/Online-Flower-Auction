package com.kkoch.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // h2-console/ 하위 모든 요청과 파비콘은 모두 무시하는것으로 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/h2-console/**"
                ,"/favicon.ico"
                ,"/error"
                ,"swagger-resources/**"
                ,"/swagger-ui.html"
                ,"/swagger-ui/index.html"
                ,"/swagger/**"
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
                .antMatchers("/*").permitAll();// 인증
        return httpSecurity.build();
    }

}