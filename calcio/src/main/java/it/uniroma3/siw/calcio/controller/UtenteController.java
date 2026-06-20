package it.uniroma3.siw.calcio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import it.uniroma3.siw.calcio.model.Utente;
import it.uniroma3.siw.calcio.service.UtenteService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UtenteController {

    private UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping("/registrazione")
    public String mostraFormRegistrazione(Model model) {
        model.addAttribute("utente", new Utente());
        return "formRegistrazione";
    }

    @PostMapping("/registrazione")
    public String registraUtente(@ModelAttribute("utente") Utente utente) {

        utente.setRuolo("ADMIN");

        this.utenteService.save(utente);

        return "redirect:/tornei";
    }

    @GetMapping("/utenti")
    public String mostraUtenti(Model model) {
        model.addAttribute("utenti", this.utenteService.findAll());
        return "listUtente";
    }

    @GetMapping("/login")
    public String mostraFormLogin(Model model) {
        return "formLogin";
    }

}