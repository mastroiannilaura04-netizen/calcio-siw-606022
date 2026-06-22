package it.uniroma3.siw.calcio.controller;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.calcio.model.Commento;
import it.uniroma3.siw.calcio.model.Partita;
import it.uniroma3.siw.calcio.model.Utente;
import it.uniroma3.siw.calcio.service.ArbitroService;
import it.uniroma3.siw.calcio.service.CommentoService;
import it.uniroma3.siw.calcio.service.PartitaService;
import it.uniroma3.siw.calcio.service.SquadraService;
import it.uniroma3.siw.calcio.service.TorneoService;
import it.uniroma3.siw.calcio.service.UtenteService;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Controller
public class PartitaController {

    private PartitaService partitaService;
    private SquadraService squadraService;
    private ArbitroService arbitroService;
    private TorneoService torneoService;
    private CommentoService commentoService;
    private UtenteService utenteService;

    public PartitaController(PartitaService partitaService,
                             SquadraService squadraService,
                             ArbitroService arbitroService,
                             TorneoService torneoService,
                             CommentoService commentoService,
                             UtenteService utenteService) {
        this.partitaService = partitaService;
        this.squadraService = squadraService;
        this.arbitroService = arbitroService;
        this.torneoService = torneoService;
        this.commentoService = commentoService;
        this.utenteService = utenteService;
    }

    @GetMapping("/partite")
    public String mostraPartite(Model model) {
        model.addAttribute("partite", this.partitaService.findAll());
        return "listPartita";
    }

    @GetMapping("/partite/new")
    public String mostraFormNuovaPartita(Model model) {
        model.addAttribute("partita", new Partita());
        model.addAttribute("squadre", this.squadraService.findAll());
        model.addAttribute("arbitri", this.arbitroService.findAll());
        model.addAttribute("tornei", this.torneoService.findAll());
        return "formPartita";
    }

    @PostMapping("/partite")
    public String salvaPartita(@ModelAttribute("partita") Partita partita) {
        this.partitaService.save(partita);
        return "redirect:/partite";
    }

    @GetMapping("/partite/{id}")
    public String mostraPartita(@PathVariable("id") Long id, Model model, Principal principal) {
        model.addAttribute("partita", this.partitaService.findById(id));
        model.addAttribute("commento", new Commento());

        if (principal != null) {
            model.addAttribute("usernameLoggato", principal.getName());
        }

        return "showPartita";
    }

    @PostMapping("/partite/{id}/commenti")
public String aggiungiCommento(@PathVariable("id") Long id,
                               @ModelAttribute("commento") Commento commento,
                               Authentication authentication) {

    Partita partita = this.partitaService.findById(id);

    String username;

    if (authentication.getPrincipal() instanceof OAuth2User oauthUser) {
        username = oauthUser.getAttribute("email");
    } else {
        username = authentication.getName();
    }

    Utente autore = this.utenteService.findByUsername(username);

    commento.setPartita(partita);
    commento.setAutore(autore);
    commento.setDataCreazione(LocalDateTime.now());

    this.commentoService.save(commento);

    return "redirect:/partite/" + id;
}

   @PostMapping("/partite/{id}/delete")
public String eliminaPartita(@PathVariable("id") Long id) {

    Partita partita = this.partitaService.findById(id);

    for (Commento commento : partita.getCommenti()) {
        this.commentoService.deleteById(commento.getId());
    }

    this.partitaService.deleteById(id);

    return "redirect:/partite";
}

    @GetMapping("/partite/{id}/risultato")
    public String mostraFormRisultato(@PathVariable("id") Long id, Model model) {
        model.addAttribute("partita", this.partitaService.findById(id));
        return "formRisultato";
    }

    @PostMapping("/partite/{id}/risultato")
    public String salvaRisultato(@PathVariable("id") Long id,
                                 @ModelAttribute("partita") Partita partitaForm) {

        Partita partita = this.partitaService.findById(id);

        partita.setGoalsHome(partitaForm.getGoalsHome());
        partita.setGoalsAway(partitaForm.getGoalsAway());
        partita.setStato("PLAYED");

        this.partitaService.save(partita);

        return "redirect:/partite/" + id;
    }
}