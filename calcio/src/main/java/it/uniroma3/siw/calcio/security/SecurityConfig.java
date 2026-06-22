package it.uniroma3.siw.calcio.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                    "/tornei/new",
                    "/tornei/*/edit",
                    "/squadre/new",
                    "/squadre/*/edit",
                    "/giocatori/new",
                    "/giocatori/*/edit",
                    "/partite/new",
                    "/partite/*/risultato",
                    "/partite/*/delete",
                    "/squadre/*/delete",
                    "/arbitri/new",
                    "/arbitri/*/edit"
                ).authenticated()

                .requestMatchers("/partite/*/commenti").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/commenti/*/edit", "/commenti/*").hasAnyRole("USER", "ADMIN")

                .requestMatchers(
                    "/",
                    "/login",
                    "/registrazione",
                    "/oauth2/**",
                    "/login/oauth2/**",
                    "/tornei",
                    "/tornei/*",
                    "/squadre",
                    "/squadre/*",
                    "/giocatori",
                    "/giocatori/*",
                    "/partite",
                    "/partite/*",
                    "/arbitri",
                    "/arbitri/*",
                    "/utenti",
                    "/css/**",
                    "/images/**",
                    "/js/**"
                ).permitAll()

                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )

            .oauth2Login(oauth -> oauth
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }
}