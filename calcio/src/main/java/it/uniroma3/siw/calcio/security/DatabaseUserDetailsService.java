package it.uniroma3.siw.calcio.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.calcio.model.Utente;
import it.uniroma3.siw.calcio.repository.UtenteRepository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private UtenteRepository utenteRepository;

    public DatabaseUserDetailsService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Utente utente = this.utenteRepository.findFirstByUsername(username);

        if (utente == null) {
            throw new UsernameNotFoundException("Utente non trovato");
        }

        return new User(
                utente.getUsername(),
                "{noop}" + utente.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + utente.getRuolo()))
        );
    }
}