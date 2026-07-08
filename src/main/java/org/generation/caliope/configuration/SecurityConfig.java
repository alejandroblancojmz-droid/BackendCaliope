package org.generation.caliope.configuration;


import lombok.RequiredArgsConstructor;
import org.generation.caliope.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.Customizer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

   private final JwtAuthenticationFilter jwtAuthenticationFilter;

   @Bean
   public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();
   }
   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

      http
              .csrf(csrf -> csrf.disable())
              .cors(Customizer.withDefaults())

              .sessionManagement(session ->
                      session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

              .authorizeHttpRequests(auth -> auth

                      // Rutas públicas
                      .requestMatchers(
                              "/api/users/login",
                              "/api/users",
                              "/api/users/me",
                              "/api/contacts"
                      ).permitAll()

                      // Todo lo demás requiere JWT
                      .anyRequest().authenticated()
              )

              .addFilterBefore(
                      jwtAuthenticationFilter,
                      UsernamePasswordAuthenticationFilter.class
              )

              .formLogin(form -> form.disable())
              .httpBasic(httpBasic -> httpBasic.disable());

      return http.build();
   }
}
