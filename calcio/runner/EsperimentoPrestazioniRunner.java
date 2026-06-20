package it.uniroma3.siw.calcio.runner;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.uniroma3.siw.calcio.model.Giocatore;
import it.uniroma3.siw.calcio.model.Squadra;
import it.uniroma3.siw.calcio.repository.GiocatoreRepository;
import it.uniroma3.siw.calcio.repository.SquadraRepository;

/**
 * Analisi sperimentale sulle strategie di fetch.
 * Confronto tra caricamento LAZY e JOIN FETCH.
 *
 * LAZY:
 * - carico prima tutte le squadre;
 * - poi, accedendo a squadra.getGiocatori(), Hibernate carica i giocatori dopo.
 *
 * JOIN FETCH:
 * - carico squadre e giocatori insieme con una sola query ottimizzata.
 */
@Component
public class EsperimentoPrestazioniRunner implements CommandLineRunner {

    private SquadraRepository squadraRepository;
    private GiocatoreRepository giocatoreRepository;

    public EsperimentoPrestazioniRunner(SquadraRepository squadraRepository,
                                        GiocatoreRepository giocatoreRepository) {
        this.squadraRepository = squadraRepository;
        this.giocatoreRepository = giocatoreRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println(">>> INIZIO ESPERIMENTO PRESTAZIONI FETCH <<<");

        setupDati();

        testLazy();

        testJoinFetch();

        System.out.println(">>> FINE ESPERIMENTO PRESTAZIONI FETCH <<<");
    }

    private void setupDati() {
        if (squadraRepository.count() < 20) {
            System.out.println("--- Creo dati di test ---");

            for (int i = 1; i <= 20; i++) {
                Squadra squadra = new Squadra();
                squadra.setNome("Squadra Test " + i);
                squadra.setCitta("Città Test " + i);
                squadra.setAnnoFondazione(1900 + i);
                squadraRepository.save(squadra);

                for (int j = 1; j <= 20; j++) {
                    Giocatore giocatore = new Giocatore();
                    giocatore.setNome("Giocatore " + j);
                    giocatore.setCognome("Cognome " + i);
                    giocatore.setRuolo("Ruolo Test");
                    giocatore.setSquadra(squadra);
                    giocatoreRepository.save(giocatore);
                }
            }
        }
    }

    private void testLazy() {
        System.out.println("--- Test LAZY FETCH ---");

        StopWatch watch = new StopWatch();
        watch.start("Lazy Fetch - squadraRepository.findAll()");

        List<Squadra> squadre = squadraRepository.findAll();

        int totaleGiocatori = 0;

        for (Squadra squadra : squadre) {
            if (squadra.getGiocatori() != null) {
                totaleGiocatori += squadra.getGiocatori().size();
            }
        }

        watch.stop();

        System.out.println("Risultato LAZY: trovati " + totaleGiocatori + " giocatori.");
        System.out.println(watch.prettyPrint());
    }

    private void testJoinFetch() {
        System.out.println("--- Test JOIN FETCH ---");

        StopWatch watch = new StopWatch();
        watch.start("Join Fetch - squadraRepository.findAllWithGiocatori()");

        List<Squadra> squadre = squadraRepository.findAllWithGiocatori();

        int totaleGiocatori = 0;

        for (Squadra squadra : squadre) {
            if (squadra.getGiocatori() != null) {
                totaleGiocatori += squadra.getGiocatori().size();
            }
        }

        watch.stop();

        System.out.println("Risultato JOIN FETCH: trovati " + totaleGiocatori + " giocatori.");
        System.out.println(watch.prettyPrint());
    }
}