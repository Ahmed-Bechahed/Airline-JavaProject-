package com.example.javaproject;

public class Vol {
    public int ID_vol;
    public String depart;
    public String arrive;
    public int ID_avion;
    public int ID_pilote;
    public String jdep;
    public String jarr;

    public Vol(int ID_vol, String depart, String arrive, int ID_avion, int ID_pilote, String jdep, String jarr) {
        this.ID_vol = ID_vol;
        this.depart = depart;
        this.arrive = arrive;
        this.ID_avion = ID_avion;
        this.ID_pilote = ID_pilote;
        this.jdep = jdep;
        this.jarr = jarr;
    }

    public int getID_vol() {
        return ID_vol;
    }

    public String getDepart() {
        return depart;
    }

    public String getArrive() {
        return arrive;
    }

    public int getID_avion() {
        return ID_avion;
    }

    public int getID_pilote() {
        return ID_pilote;
    }

    public String getJdep() {
        return jdep;
    }

    public String getJarr() {
        return jarr;
    }

    public void setID_vol(int ID_vol) {
        this.ID_vol = ID_vol;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public void setID_avion(int ID_avion) {
        this.ID_avion = ID_avion;
    }

    public void setID_pilote(int ID_pilote) {
        this.ID_pilote = ID_pilote;
    }

    public void setJdep(String jdep) {
        this.jdep = jdep;
    }

    public void setJarr(String jarr) {
        this.jarr = jarr;
    }
}
