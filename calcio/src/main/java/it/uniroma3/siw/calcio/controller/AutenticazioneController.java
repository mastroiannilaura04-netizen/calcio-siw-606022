package it.uniroma3.siw.calcio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.calcio.model.Utente;
import it.uniroma3.siw.calcio.service.UtenteService;

@Controller
public class AutenticazioneController {

    private UtenteService utenteService;

    public AutenticazioneController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping("/login")
    public String mostraFormLogin() {
        return "formLogin";
    }

    @GetMapping("/registrazione")
    public String mostraFormRegistrazione(Model model) {
        model.addAttribute("utente", new Utente());
        return "formRegistrazione";
    }

    @PostMapping("/registrazione")
    public String registraUtente(@ModelAttribute("utente") Utente utente) {
        utente.setRuolo("USER");
        this.utenteService.save(utente);
        return "redirect:/login";
    }
}