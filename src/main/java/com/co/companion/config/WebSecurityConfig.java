package com.co.companion.config;

import com.co.companion.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http 시큐리티 빌더
        http.cors()
                .and()
                .csrf()					// csrf는 현재 사용하지 않으므로 disable
                .disable()
                .httpBasic()			// token을 사용하므로 basic 인증 disable
                .disable()
                .sessionManagement()	// session 기반이 아님을 선언
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        // filter 등록
        http.addFilterAfter(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );
    }
}