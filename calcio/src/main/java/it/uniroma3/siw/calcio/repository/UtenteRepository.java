package it.uniroma3.siw.calcio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.calcio.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    public Utente findFirstByUsername(String username);
}