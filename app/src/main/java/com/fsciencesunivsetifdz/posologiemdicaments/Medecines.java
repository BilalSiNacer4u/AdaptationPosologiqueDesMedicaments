package com.fsciencesunivsetifdz.posologiemdicaments;

public class Medecines {

    private final String Compte;
    private final String Mot_De_Passe;
    private final String Nom_Medecine;
    private String Prenom_Medecine;
    private final String FullName_Account_Medecine;

    public Medecines(String Compte, String Mot_De_Passe, String Nom_Medecine, String Prenom_Medecine) {

        this.Compte = Compte;
        this.Mot_De_Passe = Mot_De_Passe;
        this.Nom_Medecine = Nom_Medecine;
        this.Prenom_Medecine = Prenom_Medecine;
        FullName_Account_Medecine = Nom_Medecine.toUpperCase()+" "+Prenom_Medecine.toLowerCase()+" "+Compte;

    }

    public String getMot_De_Passe() {
        return Mot_De_Passe;
    }

    public String getFullName_Account_Medecine() {

        return FullName_Account_Medecine;

    }

    public String getCompte() {

        return Compte;

    }

    public String getNom_Medecine() {

        return Nom_Medecine;

    }

    public String getPrenom_Medecine() {

        return Prenom_Medecine;

    }

    public void setPrenom_Medecine(String prenom_Medecine) {

        Prenom_Medecine = prenom_Medecine;

    }

}
