package com.co.companion.security;

import com.co.companion.dto.UserDTO;
import com.co.companion.exception.AuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 요청에서 토큰 가져오기
            String token = parseBearerToken(request);
            // 토큰 검사하기. JWT이므로 인가 서버에 요청하지 않고도 검증 가능
            if(token != null && !token.equalsIgnoreCase(null)) {
                // userId 가져오기. 위조된 경우 예외 처리된다.
                Claims claims = tokenProvider.getAllClaims(token);
                CustomUserDetails userDetails = CustomUserDetails.builder()
                        .username((String) claims.get("id"))
                        .nickname((String) claims.get("nickname"))
                        .user_name((String) claims.get("user_name"))
                        .password((String) claims.get("password"))
                        .phone((String) claims.get("phone"))
                        .authorities(Arrays.asList(new SimpleGrantedAuthority((String) claims.get("role"))))
                        .isAccountNonExpired(true)
                        .isAccountNonLocked(true)
                        .isCredentialsNonExpired(true)
                        .build();
                //인증 완료. SecurityContextHolder에 등록해야 인증된 사용자라고 생가한다.
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, // 인증된 사용자의 정보. 문자열이 아니어도 아무것이나 넣을 수 있다. 보통 UserDetails라는 오브젝트를 넣는데 우리는 넣지 않았다.
                        null,
                        AuthorityUtils.NO_AUTHORITIES
                );

                log.info("오류 333333333333333333333333333");
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication); 	// 인증 정보 넣기
                SecurityContextHolder.setContext(securityContext);	// 다시 등록
            }
        } catch (Exception ex) {
            logger.error("시큐리티 필터 오류", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        // Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴한다.
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
        {
            return bearerToken.substring(7);
        }
        return null;
    }
}