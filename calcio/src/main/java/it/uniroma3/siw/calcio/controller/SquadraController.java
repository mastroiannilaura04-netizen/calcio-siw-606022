package it.uniroma3.siw.calcio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.calcio.model.Squadra;
import it.uniroma3.siw.calcio.service.SquadraService;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
public class SquadraController {

    private SquadraService squadraService;

    public SquadraController(SquadraService squadraService) {
        this.squadraService = squadraService;
    }

    @GetMapping("/squadre")
    public String mostraSquadreReact(Model model) {
        String squadreJson = """
            [
                {"id":1,"nome":"Roma","citta":"Roma","annoFondazione":1927,"giocatori":[]},
                {"id":2,"nome":"Lazio","citta":"Roma","annoFondazione":1900,"giocatori":[]},
                {"id":3,"nome":"Milan","citta":"Milano","annoFondazione":1899,"giocatori":[]}
            ]
            """;

        model.addAttribute("squadreJson", squadreJson);

        return "reactSquadre";
    }

    @GetMapping("/squadre/new")
    public String mostraFormNuovaSquadra(Model model) {
        model.addAttribute("squadra", new Squadra());
        return "formSquadra";
    }

    @PostMapping("/squadre")
public String salvaSquadra(@Valid @ModelAttribute("squadra") Squadra squadra,
                           BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
        return "formSquadra";
    }

    this.squadraService.save(squadra);

    return "redirect:/squadre";
}

    @GetMapping("/squadre/{id}")
    public String mostraSquadra(@PathVariable("id") Long id, Model model) {
        model.addAttribute("squadra", this.squadraService.findById(id));
        return "showSquadra";
    }

    @GetMapping("/squadre/{id}/edit")
    public String mostraFormModificaSquadra(@PathVariable("id") Long id, Model model) {
        model.addAttribute("squadra", this.squadraService.findById(id));
        return "formSquadra";
    }

 @PostMapping("/squadre/{id}")
public String modificaSquadra(@PathVariable("id") Long id,
                              @Valid @ModelAttribute("squadra") Squadra squadra,
                              BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
        return "formSquadra";
    }

    squadra.setId(id);
    this.squadraService.save(squadra);

    return "redirect:/squadre/" + id;
}

    @PostMapping("/squadre/{id}/delete")
    public String eliminaSquadra(@PathVariable("id") Long id) {
        this.squadraService.deleteById(id);
        return "redirect:/squadre";
    }
}