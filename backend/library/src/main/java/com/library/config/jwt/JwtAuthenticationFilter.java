package com.library.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider provider;

    public JwtAuthenticationFilter(TokenProvider provider) {
        this.provider = provider;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws IOException, ServletException {
        final String requestTokenHeader = request.getHeader(AUTHORIZATION);
        String username = null;
        Claims claims = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith(provider.getTokenPrefix())) {
            var jwtToken = requestTokenHeader.replace(provider.getTokenPrefix(), "");
            try {
                if (isBlank(jwtToken)) {
                    throw new BadCredentialsException("Invalid credentials");
                }
                claims = provider.getAllClaimsFromToken(jwtToken);
                username = claims.getSubject();
            } catch (IllegalArgumentException ex) {
                log.error("Unable to get JWT Token", ex);
            } catch (ExpiredJwtException ex) {
                log.error("JWT Token has expired");
            }
        } else {
            log.warn("JWT Token does not begin with Bearer String");
        }
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (isNotBlank(username) && isNull(auth) && provider.validateToken(claims, request)) {
            var authentication = new UsernamePasswordAuthenticationToken(username,
                    "",
                    provider.getAuthorities(claims));
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            log.info("authenticated user " + username + ", setting security context");
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
