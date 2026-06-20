package it.uniroma3.siw.calcio.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import it.uniroma3.siw.calcio.model.Commento;
import it.uniroma3.siw.calcio.service.CommentoService;

@Controller
public class CommentoController {

    private CommentoService commentoService;

    public CommentoController(CommentoService commentoService) {
        this.commentoService = commentoService;
    }

    @GetMapping("/commenti/{id}/edit")
    public String mostraFormModificaCommento(@PathVariable("id") Long id, Model model, Principal principal) {
        Commento commento = this.commentoService.findById(id);

        if (principal == null || !commento.getAutore().getUsername().equals(principal.getName())) {
            return "redirect:/partite/" + commento.getPartita().getId();
        }

        model.addAttribute("commento", commento);

        return "commentoForm";
    }

    @PostMapping("/commenti/{id}")
public String salvaModificaCommento(@PathVariable("id") Long id,
                                    @ModelAttribute("commento") Commento commentoModificato,
                                    Principal principal) {

    Commento commento = this.commentoService.findById(id);

    if (principal == null || !commento.getAutore().getUsername().equals(principal.getName())) {
        return "redirect:/partite/" + commento.getPartita().getId();
    }

    commento.setTesto(commentoModificato.getTesto());

    this.commentoService.save(commento);

    return "redirect:/partite/" + commento.getPartita().getId();
}
    @GetMapping("/commenti/new")
    public String mostraFormNuovoCommento(Model model) {
        model.addAttribute("commento", new Commento());
        return "formCommento";
    }

    @PostMapping("/commenti")
    public String salvaCommento(@ModelAttribute("commento") Commento commento) {
        this.commentoService.save(commento);
        return "redirect:/"; // mantieni il redirect voluto (modifica se necessario)
    }
}
