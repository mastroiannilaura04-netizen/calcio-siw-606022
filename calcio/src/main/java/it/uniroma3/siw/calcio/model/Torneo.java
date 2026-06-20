package it.uniroma3.siw.calcio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Torneo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer anno;
    private String descrizione;

    @OneToMany(mappedBy = "torneo")
private List<Partita> partite = new ArrayList<>();



    public List<Partita> getPartite() {
    return partite;
}

public void setPartite(List<Partita> partite) {
    this.partite = partite;
}
    @ManyToMany
private List<Squadra> squadre = new ArrayList<>();

    public List<Squadra> getSquadre() {
        return squadre;
    }

    public void setSquadre(List<Squadra> squadre) {
        this.squadre = squadre;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
    this.id = id;
    }   
   
    public String getNome() {
        return nome;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((anno == null) ? 0 : anno.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Torneo other = (Torneo) obj;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (anno == null) {
            if (other.anno != null)
                return false;
        } else if (!anno.equals(other.anno))
            return false;
        return true;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Integer getAnno() {
        return anno;
    }
    public void setAnno(Integer anno) {
        this.anno = anno;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    

}