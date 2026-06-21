package it.uniroma3.siw.calcio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.calcio.model.Partita;

public interface PartitaRepository extends JpaRepository<Partita, Long> {

}