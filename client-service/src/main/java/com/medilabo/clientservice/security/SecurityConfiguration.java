package com.medilabo.clientservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/patients/new").hasRole("ORGANIZER")
                        .requestMatchers("patients/*/update").hasRole("ORGANIZER")
                        .requestMatchers("/patients/*/notes/new").hasRole("PRACTITIONER")
                        .anyRequest().authenticated()  // Toutes les autres requêtes nécessitent une authentification
                )
                .formLogin(form -> form
                        .loginPage("/")  // Page de connexion personnalisée
                        .defaultSuccessUrl("/patients", true)  // Redirection après succès
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                        .invalidateHttpSession(true) // Supprime la session HTTP
                        .deleteCookies("JSESSIONID") // Supprime les cookies de session
                        .clearAuthentication(true) // Supprime les informations d'authentification côté serveur
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied") // Redirection vers la page 403 pour accès refusé
                );
        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails practitionerUser = User.builder()
                .username("praticien")
                .password(passwordEncoder().encode("ok"))
                .roles("PRACTITIONER")
                .build();

        UserDetails organizerUser = User.builder()
                .username("organisateur")
                .password(passwordEncoder().encode("ok"))
                .roles("ORGANIZER")
                .build();

        return new InMemoryUserDetailsManager(practitionerUser, organizerUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

