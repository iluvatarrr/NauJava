package com.dmitry.NauJava.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * SecurityConfiguration - конфигурация спринг секьюрити.
 * Использует BCryptPasswordEncoder.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/perform_login",
                                "/logout",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/api/v1/users/**",
                                "/api/v1/report/**"
                        )
                )
                .cors(AbstractHttpConfigurer::disable)
                .exceptionHandling(configurer -> configurer
                        .authenticationEntryPoint(
                                (req, resp, authException) -> resp.sendRedirect("/api/v1/auth/login"))
                )
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers(
                                        "/api/v1/auth/**",
                                        "/api/v1/report/**"
                                ).permitAll()
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui.html"
                                ).hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(configurer ->
                        configurer
                                .loginPage("/api/v1/auth/login")
                                .loginProcessingUrl("/perform_login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/api/v1/auth/me", true)
                                .failureUrl("/api/v1/auth/login?error=true")
                                .permitAll()
                )
                .logout(configurer ->
                        configurer
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/api/v1/auth/login")
                                .permitAll()
                )
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}