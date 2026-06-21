package it.uniroma3.siw.calcio.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import it.uniroma3.siw.calcio.service.ArbitroService;
import it.uniroma3.siw.calcio.model.Arbitro;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/arbitri/{id}")
    public String mostraArbitro(@PathVariable("id") Long id, Model model) {
        model.addAttribute("arbitro", this.arbitroService.findById(id));
        return "showArbitro";
    }

    @GetMapping("/arbitri/{id}/edit")
    public String mostraFormModificaArbitro(@PathVariable("id") Long id, Model model) {
        model.addAttribute("arbitro", this.arbitroService.findById(id));
        return "formArbitro";
    }

    @PostMapping("/arbitri")
    public String salvaArbitro(@ModelAttribute("arbitro") Arbitro arbitro,
                               @RequestParam("fileFoto") MultipartFile fileFoto)
            throws IOException {

        if (!fileFoto.isEmpty()) {
            String nomeFile = System.currentTimeMillis() + "_" + fileFoto.getOriginalFilename();
            Path percorso = Paths.get("src/main/resources/static/images/arbitri/" + nomeFile);
            Files.write(percorso, fileFoto.getBytes());
            arbitro.setFoto("/images/arbitri/" + nomeFile);
        }

        this.arbitroService.save(arbitro);

        return "redirect:/arbitri";
    }

    @PostMapping("/arbitri/{id}")
    public String modificaArbitro(@PathVariable("id") Long id,
                                  @ModelAttribute("arbitro") Arbitro arbitro,
                                  @RequestParam("fileFoto") MultipartFile fileFoto)
            throws IOException {

        Arbitro vecchioArbitro = this.arbitroService.findById(id);

        if (!fileFoto.isEmpty()) {
            String nomeFile = System.currentTimeMillis() + "_" + fileFoto.getOriginalFilename();
            Path percorso = Paths.get("src/main/resources/static/images/arbitri/" + nomeFile);
            Files.write(percorso, fileFoto.getBytes());
            arbitro.setFoto("/images/arbitri/" + nomeFile);
        } else {
            arbitro.setFoto(vecchioArbitro.getFoto());
        }

        arbitro.setId(id);
        this.arbitroService.save(arbitro);

        return "redirect:/arbitri/" + id;
    }
}