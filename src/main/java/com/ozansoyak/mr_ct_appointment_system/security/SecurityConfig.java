package com.ozansoyak.mr_ct_appointment_system.security;

import com.ozansoyak.mr_ct_appointment_system.model.User;
import com.ozansoyak.mr_ct_appointment_system.repository.UserRepository;
import com.ozansoyak.mr_ct_appointment_system.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserRepository userRepository) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/register", "/login", "/verify", "/images/**", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureHandler((request, response, exception) -> {
                    Optional<User> user = userRepository.findByUsername(request.getParameter("username"));
                    if (user.isPresent() && !user.get().isEnabled()) {
                        // Kullanıcı aktif değilse özel hata mesajı
                        String errorMessage = "Hesabınız aktifleştirilmedi. Lütfen e-posta adresinizi(Spama düşmüş olabilir) kontrol edin.";
                        // Kullanıcıyı verify sayfasına yönlendir ve hata mesajını parametre olarak gönder
                        response.sendRedirect("/verify?error=" + URLEncoder.encode(errorMessage, StandardCharsets.UTF_8) + "&email=" + request.getParameter("username"));
                        return; // Dışarı çık
                    } else if (exception.getMessage().equalsIgnoreCase("Bad credentials")) {
                        String errorMessage = "Kullanıcı adı veya şifre yanlış.";
                        String encodedErrorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
                        response.sendRedirect("/login?error=" + encodedErrorMessage);  // URL'e encode edilmiş hata mesajı ekleyerek yönlendirme
                    } else {
                        String errorMessage = "Giriş sırasında bir hata oluştu.";
                        String encodedErrorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
                        response.sendRedirect("/login?error=" + encodedErrorMessage);
                    }
                })
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

}
