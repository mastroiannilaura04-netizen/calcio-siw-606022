package it.uniroma3.siw.calcio.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.calcio.model.Utente;
import it.uniroma3.siw.calcio.repository.UtenteRepository;

@Service
public class UtenteService {

    private UtenteRepository utenteRepository;

    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    @Transactional(readOnly = true)
    public Iterable<Utente> findAll() {
        return this.utenteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Utente findById(Long id) {
        return this.utenteRepository.findById(id).get();
    }

    @Transactional
    public Utente save(Utente utente) {
        return this.utenteRepository.save(utente);
    }

    @Transactional
    public void deleteById(Long id) {
        this.utenteRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Utente findByUsername(String username) {
        return this.utenteRepository.findByUsername(username);
    }
}