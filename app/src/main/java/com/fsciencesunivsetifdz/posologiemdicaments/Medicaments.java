package com.fsciencesunivsetifdz.posologiemdicaments;

public class Medicaments {

    private String ID_Medicament;
    private String Designation;
    private final String Full_Informations;

    public Medicaments(String ID_Medicament, String Designation, String Full_Informations) {

        this.ID_Medicament = ID_Medicament;
        this.Designation = Designation;
        this.Full_Informations = Full_Informations;

    }

    public String getFull_Informations() {
        return Full_Informations;
    }

    public String getID_Medicament() {

        return ID_Medicament;

    }

    public void setID_Medicament(String ID_Medicament) {

        this.ID_Medicament = ID_Medicament;

    }

    public String getDesignation() {

        return Designation;

    }

    public void setDesignation(String designation) {

        Designation = designation;

    }

}
