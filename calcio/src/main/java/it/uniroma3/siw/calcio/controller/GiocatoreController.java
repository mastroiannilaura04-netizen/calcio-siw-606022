package it.uniroma3.siw.calcio.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.calcio.model.Giocatore;
import it.uniroma3.siw.calcio.service.GiocatoreService;
import it.uniroma3.siw.calcio.service.SquadraService;
import jakarta.validation.Valid;

@Controller
public class GiocatoreController {

    private GiocatoreService giocatoreService;
    private SquadraService squadraService;

    public GiocatoreController(GiocatoreService giocatoreService,
                               SquadraService squadraService) {
        this.giocatoreService = giocatoreService;
        this.squadraService = squadraService;
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication != null &&
                authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping("/giocatori")
    public String mostraGiocatori(Model model, Authentication authentication) {
        model.addAttribute("giocatori", this.giocatoreService.findAll());
        model.addAttribute("isAdmin", isAdmin(authentication));
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
                                 Model model,
                                 @RequestParam("fileFoto") MultipartFile fileFoto)
            throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("squadre", this.squadraService.findAll());
            return "formGiocatore";
        }

        if (!fileFoto.isEmpty()) {
            String nomeFile = System.currentTimeMillis() + "_" + fileFoto.getOriginalFilename();
            Path percorso = Paths.get("src/main/resources/static/images/" + nomeFile);
            Files.write(percorso, fileFoto.getBytes());
            giocatore.setFoto("/images/" + nomeFile);
        }

        this.giocatoreService.save(giocatore);

        return "redirect:/giocatori";
    }

    @GetMapping("/giocatori/{id}")
    public String mostraGiocatore(@PathVariable("id") Long id,
                                  Model model,
                                  Authentication authentication) {
        model.addAttribute("giocatore", this.giocatoreService.findById(id));
        model.addAttribute("isAdmin", isAdmin(authentication));
        return "showGiocatore";
    }

    @GetMapping("/giocatori/{id}/edit")
    public String mostraFormModificaGiocatore(@PathVariable("id") Long id,
                                              Model model) {

        model.addAttribute("giocatore", this.giocatoreService.findById(id));
        model.addAttribute("squadre", this.squadraService.findAll());

        return "formGiocatore";
    }

    @PostMapping("/giocatori/{id}")
    public String modificaGiocatore(@PathVariable("id") Long id,
                                    @Valid @ModelAttribute("giocatore") Giocatore giocatore,
                                    BindingResult bindingResult,
                                    Model model,
                                    @RequestParam("fileFoto") MultipartFile fileFoto)
            throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("squadre", this.squadraService.findAll());
            return "formGiocatore";
        }

        Giocatore vecchioGiocatore = this.giocatoreService.findById(id);

        if (!fileFoto.isEmpty()) {
            String nomeFile = System.currentTimeMillis() + "_" + fileFoto.getOriginalFilename();
            Path percorso = Paths.get("src/main/resources/static/images/" + nomeFile);
            Files.write(percorso, fileFoto.getBytes());
            giocatore.setFoto("/images/" + nomeFile);
        } else {
            giocatore.setFoto(vecchioGiocatore.getFoto());
        }

        giocatore.setId(id);

        this.giocatoreService.save(giocatore);

        return "redirect:/giocatori/" + id;
    }
}