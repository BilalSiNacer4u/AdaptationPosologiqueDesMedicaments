package com.fsciencesunivsetifdz.posologiemdicaments;

public class Patients {

    private final int ID_Patient;
    private final String Nom_Patient;
    private final String Prenom_Patient;
    private final String Full_Information_Patient;
    private final String Sexe;
    private final int Enceinte;
    private final int Age;
    private final double Poid;

    public Patients(int ID_Patient, String Nom_Patient, String Prenom_Patient, String Sexe, int Enceinte, int Age, double Poid,String Full_Information_Patient) {

        this.ID_Patient = ID_Patient;
        this.Nom_Patient = Nom_Patient;
        this.Prenom_Patient = Prenom_Patient;
        this.Sexe = Sexe;
        this.Enceinte = Enceinte;
        this.Age = Age;
        this.Poid = Poid;
        this.Full_Information_Patient = Full_Information_Patient;

    }

    public String getFull_Information_Patient() {
        return Full_Information_Patient;
    }

    public int getID_Patient() {

        return ID_Patient;

    }

    public String getNom_Patient() {

        return Nom_Patient;

    }

    public String getPrenom_Patient() {

        return Prenom_Patient;

    }

    public String getSexe() {

        return Sexe;

    }

    public int getEnceinte() {

        return Enceinte;

    }

    public int getAge() {

        return Age;

    }

    public double getPoid() {

        return Poid;

    }

}
