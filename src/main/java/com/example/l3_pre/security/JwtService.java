package com.example.l3_pre.security;

import com.example.l3_pre.service.impl.UserServiceImpl;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.l3_pre.consts.MessageErrors.INVALID_OR_EXPIRED_TOKEN;
import static com.example.l3_pre.consts.ValueConst.VALID_JWT_TIME_SECOND;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    private final UserServiceImpl userService;

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String createToken(String username) {
        return Jwts.builder()
                .setClaims(Jwts.claims().setSubject(username))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + VALID_JWT_TIME_SECOND))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException exception) {
            throw new JwtException(INVALID_OR_EXPIRED_TOKEN);
        }
    }
}
