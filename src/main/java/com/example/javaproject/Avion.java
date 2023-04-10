package com.example.javaproject;

public class Avion {
    public int ID_avion;
    public String type;
    public int capacite;

    public Avion(int ID_avion, String type, int capacite) {
        this.ID_avion = ID_avion;
        this.type = type;
        this.capacite = capacite;
    }

    public int getID_avion() {
        return ID_avion;
    }

    public void setID_avion(int ID_avion) {
        this.ID_avion = ID_avion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }
}
