package it.uniroma3.siw.calcio.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import it.uniroma3.siw.calcio.model.Partita;
import it.uniroma3.siw.calcio.model.RigaClassifica;
import it.uniroma3.siw.calcio.model.Squadra;
import it.uniroma3.siw.calcio.model.Torneo;
import it.uniroma3.siw.calcio.repository.TorneoRepository;

@Service
public class TorneoService {

    private TorneoRepository torneoRepository;

    public TorneoService(TorneoRepository torneoRepository) {
        this.torneoRepository = torneoRepository;
    }

    @Transactional(readOnly = true)
    public Iterable<Torneo> findAll() {
        return this.torneoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Torneo findById(Long id) {
        return this.torneoRepository.findById(id).get();
    }

    @Transactional
    public Torneo save(Torneo torneo) {
        return this.torneoRepository.save(torneo);
    }

    @Transactional
    public void deleteById(Long id) {
        this.torneoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<RigaClassifica> getClassifica(Torneo torneo) {
        List<RigaClassifica> classifica = new ArrayList<>();

        for (Squadra squadra : torneo.getSquadre()) {
            classifica.add(new RigaClassifica(squadra));
        }

        for (Partita partita : torneo.getPartite()) {
            if (partita.getGoalsHome() == null || partita.getGoalsAway() == null) {
                continue;
            }

            RigaClassifica rigaHome = null;
            RigaClassifica rigaAway = null;

            for (RigaClassifica riga : classifica) {
                if (riga.getSquadra().equals(partita.getHomeTeam())) {
                    rigaHome = riga;
                }
                if (riga.getSquadra().equals(partita.getAwayTeam())) {
                    rigaAway = riga;
                }
            }

            if (rigaHome != null && rigaAway != null) {
                rigaHome.setGolFatti(rigaHome.getGolFatti() + partita.getGoalsHome());
                rigaHome.setGolSubiti(rigaHome.getGolSubiti() + partita.getGoalsAway());

                rigaAway.setGolFatti(rigaAway.getGolFatti() + partita.getGoalsAway());
                rigaAway.setGolSubiti(rigaAway.getGolSubiti() + partita.getGoalsHome());

                if (partita.getGoalsHome() > partita.getGoalsAway()) {
                    rigaHome.setPunti(rigaHome.getPunti() + 3);
                } else if (partita.getGoalsHome() < partita.getGoalsAway()) {
                    rigaAway.setPunti(rigaAway.getPunti() + 3);
                } else {
                    rigaHome.setPunti(rigaHome.getPunti() + 1);
                    rigaAway.setPunti(rigaAway.getPunti() + 1);
                }
            }
        }

        return classifica;
    }
}