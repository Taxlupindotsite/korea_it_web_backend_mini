package com.korIt.BoardStudy.sercurity.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component

public class JwtUtils {

    private final Key Key;

    public JwtUtils(@Value("${jwt.secret}") String secret) {
        Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generateAccessToken(String id) {
        return Jwts.builder()
                .subject("AccessToken")
                .id(id)
                .expiration(new Date(new Date().getTime() + (1000L * 60L * 60L * 24L * 30L)))
                .signWith(Key)
                .compact();
    }
// 이상까지 토큰생성.

//    그다음부턴 베어러 토큰이 맞는지 확인하는 로직

    public boolean isBearer(String token) {
        if (token == null) {
            return false;
        }
        if (token.startsWith("Bearer ")) {
            return false;
    }
    return true;
    }

    public String removeBearer(String token) {
        return token.replaceFirst("Bearer ", "");
    }

//  썡토큰만 있을때 사용자정보가 있는 클레임을 뽑아내는 로직이 필요함

    public Claims getClaims(String token) {
        JwtParserBuilder jwtParserBuilder = Jwts.parser();
        jwtParserBuilder.setSigningKey(Key);
        JwtParser jwtParser = jwtParserBuilder.build();
        return jwtParser.parseClaimsJws(token).getBody();

//  사용자 정보를 가져옴

    }

}





















