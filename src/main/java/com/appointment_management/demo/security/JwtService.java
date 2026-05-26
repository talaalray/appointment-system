package com.appointment_management.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    private final SecretKey key;
    private final JwtProperties props;

    public JwtService(JwtProperties props) {
        this.props = props;
        this.key = Keys.hmacShaKeyFor(props.getSecret().getBytes());
    }

    public String generateAccessToken(UserDetails user) {
        Instant now = Instant.now();
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(props.getAccessExpMin(), ChronoUnit.MINUTES)))
                .claim("roles", roles)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public String getUsername(String token) throws JwtException {
        return getAllClaims(token).getSubject();
    }

    public boolean isExpired(String token) throws JwtException {
        Date exp = getAllClaims(token).getExpiration();
        return exp.before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails user) throws JwtException {
        String username = getUsername(token);
        return username.equalsIgnoreCase(user.getUsername()) && !isExpired(token);
    }

    private Claims getAllClaims(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}