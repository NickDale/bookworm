package com.library.config.jwt;

import com.library.controller.dto.LoginResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.lang.Boolean.FALSE;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpHeaders.FROM;
import static org.springframework.http.HttpHeaders.USER_AGENT;

@Slf4j
@Component
public class TokenProvider implements Serializable {

    public static final String ROLE_PREFIX = "ROLE_";
    public static final String USER_NAME = "USER";

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.token.prefix}")
    private String tokenPrefix;
    @Value("${jwt.token.expiration.min:90}")
    private int expirationMin;

    public String getTokenPrefix() {
        return this.tokenPrefix;
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    }

    public String generateToken(@NonNull LoginResponse loginResponse, HttpServletRequest request) {
        return generateToken(loginResponse.getUsername(), loginResponse.getUserType().name(), request);
    }

    public String generateToken(@NonNull String username, @NonNull String userType, HttpServletRequest request) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ROLE_PREFIX, userType);
        claims.put(USER_NAME, username);

        Map<String, Object> headers = new HashMap<>();
        claims.put(USER_AGENT, request.getHeader(USER_AGENT));
        claims.put(FROM, request.getRemoteAddr());
        return doGenerateToken(claims, headers, username);
    }

    private String doGenerateToken(@NonNull final Map<String, Object> claims,
                                   @NonNull final Map<String, Object> headers,
                                   @NonNull final String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setHeader(headers)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(java.sql.Timestamp.valueOf(LocalDateTime.now().plusMinutes(expirationMin)))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(@NonNull Claims claims, HttpServletRequest request) {
        boolean notExpired = !claims.getExpiration().before(new Date());
        boolean sameClient = ofNullable(request.getHeader(USER_AGENT))
                .map(h -> h.equalsIgnoreCase(claims.get(USER_AGENT).toString()))
                .orElse(FALSE);
        boolean sameIp = request.getRemoteAddr().equalsIgnoreCase(claims.get(FROM).toString());
        return notExpired && sameClient && sameIp;
    }

    public boolean validateToken(@NonNull String jwtToken, HttpServletRequest request) {
        return validateToken(getAllClaimsFromToken(jwtToken.replace(tokenPrefix, "")), request);
    }

    public Collection<? extends GrantedAuthority> getAuthorities(final Claims claims) {
        return Collections.singleton(new SimpleGrantedAuthority(ROLE_PREFIX + claims.get(ROLE_PREFIX).toString()));
    }

}
