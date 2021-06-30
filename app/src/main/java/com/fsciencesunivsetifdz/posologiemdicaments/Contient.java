package com.fsciencesunivsetifdz.posologiemdicaments;

public class Contient {

    private String Dose;
    private String Designation;
    private String ID_Medicament;
    private int ID_Ordonnance;

    public Contient(String Dose, String Designation, String ID_Medicament, int ID_Ordonnance) {

        this.Dose = Dose;
        this.Designation = Designation;
        this.ID_Medicament = ID_Medicament;
        this.ID_Ordonnance = ID_Ordonnance;

    }

    public String getDose() {
        return Dose;
    }

    public void setDose(String dose) {
        Dose = dose;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getID_Medicament() {
        return ID_Medicament;
    }

    public void setID_Medicament(String ID_Medicament) {
        this.ID_Medicament = ID_Medicament;
    }

    public int getID_Ordonnance() {
        return ID_Ordonnance;
    }

    public void setID_Ordonnance(int ID_Ordonnance) {
        this.ID_Ordonnance = ID_Ordonnance;
    }
}
