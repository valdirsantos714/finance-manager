package com.valdirsantos714.backend.configuration;

import com.valdirsantos714.backend.adapters.out.repository.UserRepositoryAdapter;
import com.valdirsantos714.backend.infrastructure.security.SecurityFilter;
import com.valdirsantos714.backend.infrastructure.security.TokenService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                        .anyRequest().authenticated() // ainda exige autenticação para outras rotas
                )
                .addFilterBefore(mock(SecurityFilter.class), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public TokenService tokenService() {
        return mock(TokenService.class);
    }

    @Bean
    public SecurityFilter securityFilter() {
        return mock(SecurityFilter.class);
    }

    @Bean
    public UserRepositoryAdapter userRepositoryAdapter() {
        return mock(UserRepositoryAdapter.class);
    }
}