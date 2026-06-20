package it.uniroma3.siw.calcio.model;

public class RigaClassifica {

    private Squadra squadra;
    private Integer punti;
    private Integer golFatti;
    private Integer golSubiti;

    public RigaClassifica(Squadra squadra) {
        this.squadra = squadra;
        this.punti = 0;
        this.golFatti = 0;
        this.golSubiti = 0;
    }

    public Squadra getSquadra() {
        return squadra;
    }

    public void setSquadra(Squadra squadra) {
        this.squadra = squadra;
    }

    public Integer getPunti() {
        return punti;
    }

    public void setPunti(Integer punti) {
        this.punti = punti;
    }

    public Integer getGolFatti() {
        return golFatti;
    }

    public void setGolFatti(Integer golFatti) {
        this.golFatti = golFatti;
    }

    public Integer getGolSubiti() {
        return golSubiti;
    }

    public void setGolSubiti(Integer golSubiti) {
        this.golSubiti = golSubiti;
    }
}