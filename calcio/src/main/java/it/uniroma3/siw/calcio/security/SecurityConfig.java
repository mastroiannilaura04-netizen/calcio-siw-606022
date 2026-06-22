package it.uniroma3.siw.calcio.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import it.uniroma3.siw.calcio.model.Utente;
import it.uniroma3.siw.calcio.service.UtenteService;

@Configuration
public class SecurityConfig {

    private UtenteService utenteService;

    public SecurityConfig(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

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

                .requestMatchers("/partite/*/commenti").authenticated()
                .requestMatchers("/commenti/*/edit", "/commenti/*").authenticated()

                .requestMatchers(
                    "/",
                    "/error",
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
                .successHandler((request, response, authentication) -> {

                    OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

                    String email = oauthUser.getAttribute("email");

                    Utente utenteEsistente = this.utenteService.findByUsername(email);

                    if (utenteEsistente == null) {
                        Utente nuovoUtente = new Utente();
                        nuovoUtente.setUsername(email);
                        nuovoUtente.setPassword("GOOGLE_LOGIN");
                        nuovoUtente.setRuolo("USER");

                        this.utenteService.save(nuovoUtente);
                    }

                    response.sendRedirect("/");
                })
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }
}