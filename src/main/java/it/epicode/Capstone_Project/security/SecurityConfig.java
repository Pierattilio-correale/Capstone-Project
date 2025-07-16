package it.epicode.Capstone_Project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.formLogin(http -> http.disable());

        httpSecurity.csrf(http -> http.disable());

        httpSecurity.sessionManagement(http -> http.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.cors(Customizer.withDefaults());

        httpSecurity.authorizeHttpRequests(http -> http
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/capitoli/**").permitAll()
                .requestMatchers("/storie/**").permitAll()
                .requestMatchers("/commenti/**").permitAll()
                .requestMatchers("/api/statistiche/**").permitAll()
                .requestMatchers("/users/**").permitAll()
                .requestMatchers("/users/*/descrizione").permitAll()
                .requestMatchers(HttpMethod.GET).permitAll()
                .anyRequest().denyAll()
        );

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE","PATCH", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}