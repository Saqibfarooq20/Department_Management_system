package com.example.dept_mgmt.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Public Endpoints
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api/login",
                                "/api/addUser"
                        ).permitAll()

                        // Department: Authenticated GET, restricted POST/PUT/DELETE
                        .requestMatchers(HttpMethod.GET, "/api/department/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/department/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/updateDepartment/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/department/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")

                        // User Management
                        .requestMatchers("/api/delete/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                        .requestMatchers("/api/users").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                        // 👇 THE CRITICAL FIX IS HERE
                        .requestMatchers(HttpMethod.PUT, "/api/updateUser/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")

                        // Activation Endpoints
                        .requestMatchers(HttpMethod.POST, "/api/department/save").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/department/activate/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/department/deactivate/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/user/activate/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/user/deactivate/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")

                        // All other requests
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173")); // Your frontend origin
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}