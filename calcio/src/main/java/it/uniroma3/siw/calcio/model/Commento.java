package it.uniroma3.siw.calcio.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Commento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testo;

    private LocalDateTime dataCreazione;
    
    @ManyToOne
private Utente autore;

@ManyToOne
private Partita partita;

    public Long getId() {
        return id;
    }

    public Partita getPartita() {
        return partita;
    }

    public void setPartita(Partita partita) {
        this.partita = partita;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((testo == null) ? 0 : testo.hashCode());
        result = prime * result + ((dataCreazione == null) ? 0 : dataCreazione.hashCode());
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
        Commento other = (Commento) obj;
        if (testo == null) {
            if (other.testo != null)
                return false;
        } else if (!testo.equals(other.testo))
            return false;
        if (dataCreazione == null) {
            if (other.dataCreazione != null)
                return false;
        } else if (!dataCreazione.equals(other.dataCreazione))
            return false;
        return true;
    }


    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public LocalDateTime getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(LocalDateTime dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public Utente getAutore() {
        return autore;
    }

    public void setAutore(Utente autore) {
        this.autore = autore;
    }

}