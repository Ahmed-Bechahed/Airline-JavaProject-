package com.example.javaproject;

public class Passenger {
    public int ID_passenger;
    public String nom;
    public String prenom;
    public int num_passeport;

    public Passenger(int ID_passenger, String nom, String prenom, int num_passeport) {
        this.ID_passenger = ID_passenger;
        this.nom = nom;
        this.prenom = prenom;
        this.num_passeport = num_passeport;
    }

    public int getID_passenger() {
        return ID_passenger;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getNum_passeport() {
        return num_passeport;
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

    public void setNum_passeport(int num_passeport) {
        this.num_passeport = num_passeport;
    }


}
