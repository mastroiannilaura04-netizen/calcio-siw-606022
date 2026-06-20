package it.uniroma3.siw.calcio.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Torneo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Il nome è obbligatorio")
    private String nome;

    @NotNull(message = "L'anno è obbligatorio")
    @Min(value = 1900, message = "L'anno deve essere valido")
    private Integer anno;

    @NotBlank(message = "La descrizione è obbligatoria")
    private String descrizione;

    @OneToMany(mappedBy = "torneo")
    private List<Partita> partite = new ArrayList<>();

    @ManyToMany
    private List<Squadra> squadre = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getAnno() { return anno; }
    public void setAnno(Integer anno) { this.anno = anno; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public List<Partita> getPartite() { return partite; }
    public void setPartite(List<Partita> partite) { this.partite = partite; }

    public List<Squadra> getSquadre() { return squadre; }
    public void setSquadre(List<Squadra> squadre) { this.squadre = squadre; }
}