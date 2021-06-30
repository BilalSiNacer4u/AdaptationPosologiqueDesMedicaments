package com.fsciencesunivsetifdz.posologiemdicaments;

public class Regles {

    private int ID_Regle;
    private double Dose_Regle;
    private String Unite;
    private Double Valeur_Minimum;
    private Double Valeur_Maximum;
    private String Valeur_Sexe;
    private Integer Valeur_Enceinte;
    private String ID_Medicament;
    private String Type_Analyse_Utiliser;

    public Regles(int ID_Regle, String Type_Analyse_Utiliser, double Dose_Regle, String Unite, Double Valeur_Minimum, Double Valeur_Maximum, String Valeur_Sexe, Integer Valeur_Enceinte, String ID_Medicament) {

        this.ID_Regle = ID_Regle;
        this.Type_Analyse_Utiliser = Type_Analyse_Utiliser;
        this.Dose_Regle = Dose_Regle;
        this.Unite = Unite;
        this.Valeur_Minimum = Valeur_Minimum;
        this.Valeur_Maximum = Valeur_Maximum;
        this.Valeur_Sexe = Valeur_Sexe;
        this.Valeur_Enceinte = Valeur_Enceinte;
        this.ID_Medicament = ID_Medicament;

    }

    public int getID_Regle() {
        return ID_Regle;
    }

    public void setID_Regle(int ID_Regle) {
        this.ID_Regle = ID_Regle;
    }

    public double getDose_Regle() {
        return Dose_Regle;
    }

    public void setDose_Regle(double dose_Regle) {
        Dose_Regle = dose_Regle;
    }

    public String getUnite() {
        return Unite;
    }

    public void setUnite(String unite) {
        Unite = unite;
    }

    public Double getValeur_Minimum() {
        return Valeur_Minimum;
    }

    public void setValeur_Minimum(Double valeur_Minimum) {
        Valeur_Minimum = valeur_Minimum;
    }

    public Double getValeur_Maximum() {
        return Valeur_Maximum;
    }

    public void setValeur_Maximum(Double valeur_Maximum) {
        Valeur_Maximum = valeur_Maximum;
    }

    public String getValeur_Sexe() {
        return Valeur_Sexe;
    }

    public void setValeur_Sexe(String valeur_Sexe) {
        Valeur_Sexe = valeur_Sexe;
    }

    public Integer getValeur_Enceinte() {
        return Valeur_Enceinte;
    }

    public void setValeur_Enceinte(Integer valeur_Enceinte) {
        Valeur_Enceinte = valeur_Enceinte;
    }

    public String getID_Medicament() {
        return ID_Medicament;
    }

    public void setID_Medicament(String ID_Medicament) {
        this.ID_Medicament = ID_Medicament;
    }

    public String getType_Analyse_Utiliser() {
        return Type_Analyse_Utiliser;
    }

    public void setType_Analyse_Utiliser(String type_Analyse_Utiliser) {
        Type_Analyse_Utiliser = type_Analyse_Utiliser;
    }

}
