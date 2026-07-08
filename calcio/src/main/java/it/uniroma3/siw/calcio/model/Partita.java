package it.uniroma3.siw.calcio.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne; 
import java.util.List;
import java.util.ArrayList;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

import jakarta.persistence.OneToMany;
    

@Entity
public class Partita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

@NotNull(message = "La data e ora sono obbligatorie")
private LocalDateTime dataOra;

@NotBlank(message = "Il luogo è obbligatorio")
private String luogo;

@Min(value = 0, message = "I gol non possono essere negativi")
private Integer goalsHome;

@Min(value = 0, message = "I gol non possono essere negativi")
private Integer goalsAway;

@NotBlank(message = "Lo stato è obbligatorio")
private String stato;

@ManyToOne
@NotNull(message = "Il torneo è obbligatorio")
private Torneo torneo;

@ManyToOne
@NotNull(message = "L'arbitro è obbligatorio")
private Arbitro arbitro;

@ManyToOne
@NotNull(message = "La squadra di casa è obbligatoria")
private Squadra homeTeam;

@ManyToOne
@NotNull(message = "La squadra ospite è obbligatoria")
private Squadra awayTeam;

@OneToMany(mappedBy = "partita")
private List<Commento> commenti = new ArrayList<>();


public List<Commento> getCommenti() {
    return commenti;
}
public void setCommenti(List<Commento> commenti) {
    this.commenti = commenti;
}
public Squadra getHomeTeam() {
    return homeTeam;
}
public void setHomeTeam(Squadra homeTeam) {
    this.homeTeam = homeTeam;
}
public Squadra getAwayTeam() {
    return awayTeam;
}
public void setAwayTeam(Squadra awayTeam) {
    this.awayTeam = awayTeam;
}


    public Arbitro getArbitro() {
    return arbitro;
}
public void setArbitro(Arbitro arbitro) {
    this.arbitro = arbitro;
}
    public Torneo getTorneo() {
        return torneo;
    }
    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getDataOra() {
        return dataOra;
    }
    public void setDataOra(LocalDateTime dataOra) {
        this.dataOra = dataOra;
    }
    public String getLuogo() {
        return luogo;
    }
    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }
    public Integer getGoalsHome() {
        return goalsHome;
    }
    public void setGoalsHome(Integer goalsHome) {
        this.goalsHome = goalsHome;
    }
    public Integer getGoalsAway() {
        return goalsAway;
    }
    public void setGoalsAway(Integer goalsAway) {
        this.goalsAway = goalsAway;
    }
    public String getStato() {
        return stato;
    }
    public void setStato(String stato) {
        this.stato = stato;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataOra == null) ? 0 : dataOra.hashCode());
        result = prime * result + ((luogo == null) ? 0 : luogo.hashCode());
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
        Partita other = (Partita) obj;
        if (dataOra == null) {
            if (other.dataOra != null)
                return false;
        } else if (!dataOra.equals(other.dataOra))
            return false;
        if (luogo == null) {
            if (other.luogo != null)
                return false;
        } else if (!luogo.equals(other.luogo))
            return false;
        return true;
    }

}