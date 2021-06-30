package com.fsciencesunivsetifdz.posologiemdicaments;

public class Ordonnances_Patients_Medecines {

    private int ID_Ordonnance;
    private String Date_Ordonnance;
    private String Nom_Patient;
    private String Prenom_Patient;
    private String Nom_Medecine;
    private String Prenom_Medecine;

    public Ordonnances_Patients_Medecines(int ID_Ordonnance, String Date_Ordonnance, String Nom_Patient, String Prenom_Patient, String Nom_Medecine, String Prenom_Medecine) {

        this.ID_Ordonnance = ID_Ordonnance;
        this.Date_Ordonnance = Date_Ordonnance;
        this.Nom_Patient = Nom_Patient;
        this.Prenom_Patient = Prenom_Patient;
        this.Nom_Medecine = Nom_Medecine;
        this.Prenom_Medecine = Prenom_Medecine;

    }

    public int getID_Ordonnance() {
        return ID_Ordonnance;
    }

    public String getDate_Ordonnance() {
        return Date_Ordonnance;
    }

    public String getNom_Patient() {
        return Nom_Patient;
    }

    public String getPrenom_Patient() {
        return Prenom_Patient;
    }

    public String getNom_Medecine() {
        return Nom_Medecine;
    }

    public String getPrenom_Medecine() {
        return Prenom_Medecine;
    }
}
