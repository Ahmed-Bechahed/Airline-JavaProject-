package com.example.javaproject;

public class Passenger {
    public int ID_passenger;
    public String nom;
    public String prenom;
    public String num_passeport;
    public String ID_vol;

    public int getID_passenger() {
        return ID_passenger;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNum_passeport() {
        return num_passeport;
    }

    public String getID_vol() {
        return ID_vol;
    }

    public void setID_passenger(int ID_passenger) {
        this.ID_passenger = ID_passenger;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNum_passeport(String num_passeport) {
        this.num_passeport = num_passeport;
    }

    public void setID_vol(String ID_vol) {
        this.ID_vol = ID_vol;
    }
}
