package it.uniroma3.siw.calcio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import it.uniroma3.siw.calcio.service.ArbitroService;
import it.uniroma3.siw.calcio.model.Arbitro;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ArbitroController {

    private ArbitroService arbitroService;

    public ArbitroController(ArbitroService arbitroService) {
        this.arbitroService = arbitroService;
    }
    @GetMapping("/arbitri")
    public String mostraArbitri(Model model) {
        model.addAttribute("arbitri", this.arbitroService.findAll());
        return "listArbitro";
    }
    @GetMapping("/arbitri/new")
    public String mostraFormNuovoArbitro(Model model) {
        model.addAttribute("arbitro", new Arbitro());
        return "formArbitro";
    }
    @GetMapping("/arbitri/{id}/edit")
    public String mostraFormModificaArbitro(@PathVariable("id") Long id, Model model) {
        model.addAttribute("arbitro", this.arbitroService.findById(id));
        return "formArbitro";
    }
    @PostMapping("/arbitri")
    public String salvaArbitro(@ModelAttribute("arbitro") Arbitro arbitro) {
        this.arbitroService.save(arbitro);
        return "redirect:/arbitri";
    }
}