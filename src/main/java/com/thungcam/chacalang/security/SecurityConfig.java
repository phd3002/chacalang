package com.thungcam.chacalang.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomLoginSuccessHandler customLoginSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // ❗ Tắt CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/img/**", "/js/**", "/fonts/**").permitAll()
                        .requestMatchers("/auth/register", "/auth/gui-otp", "/auth/verify-otp", "/auth/verify-otp-success",
                                "/auth/login", "/auth/forgot-password", "/auth/reset-password/**").permitAll()
                        .requestMatchers("/order/**", "/cart/**", "/api/**").hasRole( "CUSTOMER" )
                        .requestMatchers("/checkout/**").hasRole( "CUSTOMER" )
                        .requestMatchers("/user/**","/user/profile/**","/user/profile-manager", "/user/change-password", "/user/user-address").hasRole( "CUSTOMER" )
                        .requestMatchers("/admin/**").hasRole( "ADMIN" )
                        .requestMatchers("/branch-manager/**").hasRole( "BRANCH_MANAGER" )
                        .requestMatchers("/staff/**").hasRole("STAFF")
                        .requestMatchers("/shipper/**").hasRole("SHIPPER")
                        .requestMatchers("/", "/lien-he", "/dat-ban", "/menu/**", "/error/**") //Home page, contact, reservation, menu
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/error/404")
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .successHandler(customLoginSuccessHandler) // Chuyển hướng sau khi login thành công
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
