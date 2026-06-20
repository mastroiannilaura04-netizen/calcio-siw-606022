package it.uniroma3.siw.calcio.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.calcio.model.Squadra;
import it.uniroma3.siw.calcio.repository.SquadraRepository;

@Service
public class SquadraService {

    private SquadraRepository squadraRepository;

    public SquadraService(SquadraRepository squadraRepository) {
        this.squadraRepository = squadraRepository;
    }

    @Transactional(readOnly = true)
    public Iterable<Squadra> findAll() {
        return this.squadraRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Squadra findById(Long id) {
        return this.squadraRepository.findById(id).get();
    }

    @Transactional
    public Squadra save(Squadra squadra) {
        return this.squadraRepository.save(squadra);
    }

    @Transactional
    public void deleteById(Long id) {
        this.squadraRepository.deleteById(id);
    }
}