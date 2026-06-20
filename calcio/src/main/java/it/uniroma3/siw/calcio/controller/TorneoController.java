package it.uniroma3.siw.calcio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.calcio.model.Torneo;
import it.uniroma3.siw.calcio.service.SquadraService;
import it.uniroma3.siw.calcio.service.TorneoService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TorneoController {

    private TorneoService torneoService;
    private SquadraService squadraService;

    public TorneoController(TorneoService torneoService, SquadraService squadraService) {
        this.torneoService = torneoService;
        this.squadraService = squadraService;
    }

    @GetMapping("/tornei")
    public String mostraTornei(Model model) {
        model.addAttribute("tornei", this.torneoService.findAll());
        return "listTorneo";
    }

    @GetMapping("/tornei/new")
    public String mostraFormNuovoTorneo(Model model) {
        model.addAttribute("torneo", new Torneo());
        model.addAttribute("squadre", this.squadraService.findAll());
        return "formTorneo";
    }

    @PostMapping("/tornei")
    public String salvaTorneo(@ModelAttribute("torneo") Torneo torneo) {
        this.torneoService.save(torneo);
        return "redirect:/tornei";
    }

    @GetMapping("/tornei/{id}")
    public String mostraTorneo(@PathVariable("id") Long id, Model model) {
        Torneo torneo = this.torneoService.findById(id);

        model.addAttribute("torneo", torneo);
        model.addAttribute("classifica", this.torneoService.getClassifica(torneo));

        return "showTorneo";
    }

    @GetMapping("/tornei/{id}/edit")
    public String mostraFormModificaTorneo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("torneo", this.torneoService.findById(id));
        model.addAttribute("squadre", this.squadraService.findAll());
        return "formTorneo";
    }

    @PostMapping("/tornei/{id}")
    public String modificaTorneo(@PathVariable("id") Long id,
                                 @ModelAttribute("torneo") Torneo torneo) {

        torneo.setId(id);

        this.torneoService.save(torneo);

        return "redirect:/tornei/" + id;
    }

}