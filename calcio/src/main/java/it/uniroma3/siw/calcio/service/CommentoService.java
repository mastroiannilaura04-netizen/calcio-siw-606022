package it.uniroma3.siw.calcio.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.calcio.model.Commento;
import it.uniroma3.siw.calcio.repository.CommentoRepository;

@Service
public class CommentoService {

    private CommentoRepository commentoRepository;

    public CommentoService(CommentoRepository commentoRepository) {
        this.commentoRepository = commentoRepository;
    }

    @Transactional(readOnly = true)
    public Iterable<Commento> findAll() {
        return this.commentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Commento findById(Long id) {
        return this.commentoRepository.findById(id).get();
    }

    @Transactional
    public Commento save(Commento commento) {
        return this.commentoRepository.save(commento);
    }

    @Transactional
    public void deleteById(Long id) {
        this.commentoRepository.deleteById(id);
    }
}