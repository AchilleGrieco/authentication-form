package com.example.AuthForm.config;

import com.example.AuthForm.repository.AuthFormRepository;

import com.example.AuthForm.security.AuthFormEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    AuthFormRepository authFormRepository;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        });
        http.authorizeHttpRequests(request -> {
            request.requestMatchers("/signUp", "/login", "/loginError", "/").permitAll();
            request.requestMatchers("/images/*", "/css/*", "/javascript/*").permitAll();
            request.anyRequest().authenticated();
        }).httpBasic(httpSecurityHttpBasicConfigurer -> {
            httpSecurityHttpBasicConfigurer.authenticationEntryPoint(new AuthFormEntryPoint());
        });
        http.logout(httpSecurityLogoutConfigurer -> {
            httpSecurityLogoutConfigurer.logoutSuccessUrl("/login");
        });
        return http.build();
    }
}
