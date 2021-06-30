package com.fsciencesunivsetifdz.posologiemdicaments;

public class Ordonnances {

    private int ID_Ordonnance;
    private String Date_Ordonnance;
    private int ID_Patient;
    private String Compte;

    public Ordonnances(int ID_Ordonnance, String Date_Ordonnance, int ID_Patient, String Compte) {

        this.ID_Ordonnance = ID_Ordonnance;
        this.Date_Ordonnance = Date_Ordonnance;
        this.ID_Patient = ID_Patient;
        this.Compte = Compte;

    }

    public int getID_Ordonnance() {

        return ID_Ordonnance;

    }

    public void setID_Ordonnance(int ID_Ordonnance) {

        this.ID_Ordonnance = ID_Ordonnance;

    }

    public String getDate_Ordonnance() {

        return Date_Ordonnance;

    }

    public void setDate_Ordonnance(String date_Ordonnance) {

        Date_Ordonnance = date_Ordonnance;

    }

    public int getID_Patient() {

        return ID_Patient;

    }

    public void setID_Patient(int ID_Patient) {

        this.ID_Patient = ID_Patient;

    }

    public String getCompte() {

        return Compte;

    }

    public void setCompte(String compte) {

        Compte = compte;

    }

}
