package com.example.javaproject;

public class Pilote {
    public int ID_pilote;
    public String nom;
    public String prenom;

    public Pilote(int ID_pilote, String nom, String prenom) {
        this.ID_pilote = ID_pilote;
        this.nom = nom;
        this.prenom = prenom;
    }

    public int getID_pilote() {
        return ID_pilote;
    }

    public void setID_pilote(int ID_pilote) {
        this.ID_pilote = ID_pilote;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
