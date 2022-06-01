package com.library.config;

import com.library.config.jwt.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import javax.servlet.Filter;
import java.util.concurrent.Executor;

import static com.library.model.entities.UserTypes.ADMIN;
import static com.library.model.entities.UserTypes.LIBRARIAN;
import static com.library.model.entities.UserTypes.USER;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Order(1)
@Configuration
@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final static String[] SWAGGER_URLS = {
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
    };
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public Filter corsFilter() {
        return new SpringCorsFilter();
    }

    // For IntelliJ IDEA
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(corsFilter(), ConcurrentSessionFilter.class)
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/login", "/password/reset",
                        "/validate","/contacts/**",
                        "/registration", "/books/**")
                .permitAll()
                .antMatchers(SWAGGER_URLS)
                .permitAll()
                .antMatchers("/librarians/**")
                .hasAnyRole(ADMIN.name())
                .antMatchers("/loans/**")
                .hasAnyRole(LIBRARIAN.name(), USER.name())
                .antMatchers("/users/**")
                .hasAnyRole(LIBRARIAN.name())
                .antMatchers("/profiles/**")
                .hasAnyRole(USER.name())
                .antMatchers("/profiles/password/change")
                .hasAnyRole(ADMIN.name(), LIBRARIAN.name(), USER.name())
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(STATELESS);
    }

    @Bean(name = "threadPoolTaskExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(100);
        executor.initialize();
        return executor;
    }

}

