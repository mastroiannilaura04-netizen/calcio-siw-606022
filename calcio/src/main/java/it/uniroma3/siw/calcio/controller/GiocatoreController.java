package it.uniroma3.siw.calcio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.calcio.model.Giocatore;
import it.uniroma3.siw.calcio.service.GiocatoreService;
import it.uniroma3.siw.calcio.service.SquadraService;
import jakarta.validation.Valid;

@Controller
public class GiocatoreController {

    private GiocatoreService giocatoreService;
    private SquadraService squadraService;

    public GiocatoreController(GiocatoreService giocatoreService, SquadraService squadraService) {
        this.giocatoreService = giocatoreService;
        this.squadraService = squadraService;
    }

    @GetMapping("/giocatori")
    public String mostraGiocatori(Model model) {
        model.addAttribute("giocatori", this.giocatoreService.findAll());
        return "listGiocatore";
    }

    @GetMapping("/giocatori/new")
    public String mostraFormNuovoGiocatore(Model model) {
        model.addAttribute("giocatore", new Giocatore());
        model.addAttribute("squadre", this.squadraService.findAll());
        return "formGiocatore";
    }

    @PostMapping("/giocatori")
    public String salvaGiocatore(@Valid @ModelAttribute("giocatore") Giocatore giocatore,
                                 BindingResult bindingResult,
                                 Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("squadre", this.squadraService.findAll());
            return "formGiocatore";
        }

        this.giocatoreService.save(giocatore);
        return "redirect:/giocatori";
    }

    @GetMapping("/giocatori/{id}")
    public String mostraGiocatore(@PathVariable("id") Long id, Model model) {
        model.addAttribute("giocatore", this.giocatoreService.findById(id));
        return "showGiocatore";
    }

    @GetMapping("/giocatori/{id}/edit")
    public String mostraFormModificaGiocatore(@PathVariable("id") Long id, Model model) {
        model.addAttribute("giocatore", this.giocatoreService.findById(id));
        model.addAttribute("squadre", this.squadraService.findAll());
        return "formGiocatore";
    }

    @PostMapping("/giocatori/{id}")
    public String modificaGiocatore(@PathVariable("id") Long id,
                                    @Valid @ModelAttribute("giocatore") Giocatore giocatore,
                                    BindingResult bindingResult,
                                    Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("squadre", this.squadraService.findAll());
            return "formGiocatore";
        }

        giocatore.setId(id);
        this.giocatoreService.save(giocatore);

        return "redirect:/giocatori/" + id;
    }
}