package com.client_jwt.security.utils;

import com.client_jwt.security.request.UserJwtRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Date gerarDataExpiracao() {
        return new Date(System.currentTimeMillis() + expiration * 60000);
    }

    public String obterToken(UserJwtRequest userJwtRequest) {
        return Jwts.builder()
                .setSubject(userJwtRequest.getEmail())
                .setExpiration(gerarDataExpiracao())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsuario(String token) throws ExpiredJwtException {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public Date getDataExpiracao(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration();
    }

    private boolean isTokenExpirado(String token) {
        final Date dataExpiracao = getDataExpiracao(token);
        return dataExpiracao.before(new Date());
    }

    public boolean isTokenValido(String token) {
        return !isTokenExpirado(token);
    }
}
