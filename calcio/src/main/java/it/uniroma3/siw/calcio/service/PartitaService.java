package it.uniroma3.siw.calcio.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.calcio.model.Partita;
import it.uniroma3.siw.calcio.repository.PartitaRepository;

@Service
public class PartitaService {

    private PartitaRepository partitaRepository;

    public PartitaService(PartitaRepository partitaRepository) {
        this.partitaRepository = partitaRepository;
    }

    @Transactional(readOnly = true)
    public Iterable<Partita> findAll() {
        return this.partitaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Partita findById(Long id) {
        return this.partitaRepository.findById(id).get();
    }

    @Transactional
    public Partita save(Partita partita) {
        return this.partitaRepository.save(partita);
    }

    @Transactional
    public void deleteById(Long id) {
        this.partitaRepository.deleteById(id);
    }
}