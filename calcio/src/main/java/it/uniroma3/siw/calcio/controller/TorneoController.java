package it.uniroma3.siw.calcio.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.calcio.model.Torneo;
import it.uniroma3.siw.calcio.service.SquadraService;
import it.uniroma3.siw.calcio.service.TorneoService;
import jakarta.validation.Valid;

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

    private boolean isAdmin(Authentication authentication) {
        return authentication != null &&
                authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping("/tornei")
    public String mostraTornei(Model model, Authentication authentication) {
        model.addAttribute("tornei", this.torneoService.findAll());
        model.addAttribute("isAdmin", isAdmin(authentication));
        return "listTorneo";
    }

    @GetMapping("/tornei/new")
    public String mostraFormNuovoTorneo(Model model) {
        model.addAttribute("torneo", new Torneo());
        model.addAttribute("squadre", this.squadraService.findAll());
        return "formTorneo";
    }

    @PostMapping("/tornei")
    public String salvaTorneo(@Valid @ModelAttribute("torneo") Torneo torneo,
                              BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("squadre", this.squadraService.findAll());
            return "formTorneo";
        }

        this.torneoService.save(torneo);
        return "redirect:/tornei";
    }

    @GetMapping("/tornei/{id}")
    public String mostraTorneo(@PathVariable("id") Long id,
                               Model model,
                               Authentication authentication) {
        Torneo torneo = this.torneoService.findById(id);

        model.addAttribute("torneo", torneo);
        model.addAttribute("classifica", this.torneoService.getClassifica(torneo));
        model.addAttribute("isAdmin", isAdmin(authentication));

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
                                 @Valid @ModelAttribute("torneo") Torneo torneo,
                                 BindingResult bindingResult,
                                 Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("squadre", this.squadraService.findAll());
            return "formTorneo";
        }

        torneo.setId(id);
        this.torneoService.save(torneo);

        return "redirect:/tornei/" + id;
    }
}