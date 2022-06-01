package com.library.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static java.util.Objects.isNull;

@Configuration
@EnableJpaAuditing
@Order(1)
public class AuditorConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (isNull(authentication) || !authentication.isAuthenticated()) {
                return Optional.of("Unauthenticated user");
            }
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                return Optional.ofNullable((String) authentication.getPrincipal());
            }
            return Optional.of(
                    Optional.ofNullable((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                            .filter(StringUtils::isNotBlank)
                            .orElse("System")
            );
        };
    }
}

