package com.thungcam.chacalang.security;

import com.thungcam.chacalang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
                        .requestMatchers("/order/**", "/cart/**", "/api/**").permitAll()
                        .requestMatchers("/checkout/**").permitAll()
                        .requestMatchers("/user/**","/user/profile/**","/user/profile-manager", "/user/change-password", "/user/user-address").permitAll()
                        .requestMatchers("/api/location/**").permitAll()
                        .requestMatchers("/admin/**").permitAll() // Các trang admin yêu cầu login (sẽ config sau)
                        .requestMatchers("/branch-manager/**").permitAll() // Chỉ cho phép Branch Manager và Admin truy cập
                        .requestMatchers("/", "/lien-he", "/dat-ban", "/menu/**", "/error/**")
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/error/404") // xử lý lỗi 403
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

    // ✅ Spring dùng userService để load user từ DB
//    @Bean
//    public org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {
//        return userService;
//    }
}
