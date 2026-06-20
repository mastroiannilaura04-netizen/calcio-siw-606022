package it.uniroma3.siw.calcio.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne; 
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.OneToMany;
    

@Entity
public class Partita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataOra;
    private String luogo;
    private Integer goalsHome;
    private Integer goalsAway;
    private String stato;

  

    @ManyToOne
private Torneo torneo;

@ManyToOne
private Arbitro arbitro;

@ManyToOne
private Squadra homeTeam;

@OneToMany(mappedBy = "partita")
private List<Commento> commenti = new ArrayList<>();

@ManyToOne
private Squadra awayTeam;

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