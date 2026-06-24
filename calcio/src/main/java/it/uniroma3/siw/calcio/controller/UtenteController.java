package it.uniroma3.siw.calcio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.calcio.service.UtenteService;

@Controller
public class UtenteController {

    private UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping("/utenti")
    public String mostraUtenti(Model model) {
        model.addAttribute("utenti", this.utenteService.findAll());
        return "listUtente";
    }
}