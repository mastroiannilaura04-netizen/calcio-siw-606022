package it.uniroma3.siw.calcio.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.calcio.model.Giocatore;
import it.uniroma3.siw.calcio.repository.GiocatoreRepository;

@Service
public class GiocatoreService {

    private GiocatoreRepository giocatoreRepository;

    public GiocatoreService(GiocatoreRepository giocatoreRepository) {
        this.giocatoreRepository = giocatoreRepository;
    }

    @Transactional(readOnly = true)
    public Iterable<Giocatore> findAll() {
        return this.giocatoreRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Giocatore findById(Long id) {
        return this.giocatoreRepository.findById(id).get();
    }

    @Transactional
    public Giocatore save(Giocatore giocatore) {
        return this.giocatoreRepository.save(giocatore);
    }

    @Transactional
    public void deleteById(Long id) {
        this.giocatoreRepository.deleteById(id);
    }
}