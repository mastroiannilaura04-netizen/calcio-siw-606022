package it.uniroma3.siw.calcio.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.calcio.model.Arbitro;
import it.uniroma3.siw.calcio.repository.ArbitroRepository;

@Service
public class ArbitroService {

    private ArbitroRepository arbitroRepository;

    public ArbitroService(ArbitroRepository arbitroRepository) {
        this.arbitroRepository = arbitroRepository;
    }

    @Transactional(readOnly = true)
    public Iterable<Arbitro> findAll() {
        return this.arbitroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Arbitro findById(Long id) {
        return this.arbitroRepository.findById(id).get();
    }

    @Transactional
    public Arbitro save(Arbitro arbitro) {
        return this.arbitroRepository.save(arbitro);
    }

    @Transactional
    public void deleteById(Long id) {
        this.arbitroRepository.deleteById(id);
    }
}