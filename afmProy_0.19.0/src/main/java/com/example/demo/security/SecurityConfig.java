package com.example.demo.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.headers(
                                headersConfigurer -> headersConfigurer
                                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
                http.authorizeHttpRequests(auth -> auth

                                .requestMatchers("/residuo/listComplete", "/incidencia/list", "/categoria/**", "/user/list", "/user/new", "/userEditOther/**").hasRole("ADMIN")
                                .requestMatchers("/ruta/userLogin").hasAnyRole("NEGOCIANTE", "ADMIN")
                                .requestMatchers("/incidencia/new", "/incidencia/listByUser", "/incidencia/list").hasAnyRole("USER","NEGOCIANTE","ADMIN")
                                .requestMatchers("/user/editUserLogin", "/user/editUserLogued").hasAnyRole("USER","NEGOCIANTE","ADMIN")
                                .requestMatchers("/residuo/edit/**", "/residuo/new", "/residuo/listByUser", "/residuo/listByUserSolicitados", "/residuo/listByUserAndSolicitadosAndReservados").hasAnyRole("USER","NEGOCIANTE","ADMIN")
                                .requestMatchers("/", "/public/**", "/residuo/list", "/residuo/categoria/**", "/api/residuos").permitAll()
                                // .requestMatchers("...").hasAnyRole(...) //configurarpermisosreales
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                .permitAll() // para rutas: /css, /js /images
                                .anyRequest().authenticated())
                                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                                                .loginPage("/public/signin") // mapping par mostrar formulario de login
                                                .loginProcessingUrl("/login") // ruta post de /signin
                                                .failureUrl("/public/signin?error")
                                                .defaultSuccessUrl("/residuo/list", true)
                                                .permitAll())
                                .logout((logout) -> logout
                                                .logoutSuccessUrl("/public").permitAll())
                                // .csrf(csrf -> csrf.disable())
                                .httpBasic(Customizer.withDefaults());
                http.exceptionHandling(exceptions -> exceptions.accessDeniedPage("/public"));
                return http.build();
        }
}