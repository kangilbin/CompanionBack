package com.co.companion.security;

import com.co.companion.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {

    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

    public String create(UserEntity userEntity) {
        // 기한은 지금부터 1일로 설정
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS));


        // JWT Token 생성
            return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .claim("id", userEntity.getId())
                .claim("user_name", userEntity.getUser_name())
                .claim("nickname", userEntity.getNickname())
                .claim("phone", userEntity.getPhone())
                .claim("role", userEntity.getRole())
                .setSubject(userEntity.getId()) // sub
                .setIssuedAt(new Date())		// iat
                .setExpiration(expiryDate)		// exp
                .compact();
    }

    /**
     * 토큰의 Claim 디코딩
     */
    public Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}