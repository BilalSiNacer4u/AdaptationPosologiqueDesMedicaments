package com.fsciencesunivsetifdz.posologiemdicaments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBase_SQLite extends SQLiteOpenHelper {

    public static String databaseName = "apm.db";
    Context activity;

    public DataBase_SQLite(@Nullable Context context) {
        super(context, databaseName, null, 1);
        activity = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE Medecines (Compte text PRIMARY KEY NOT NULL,Mot_De_Passe text NOT NULL,Nom_Medecine text NOT NULL,Prenom_Medecine text NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE Patients (ID_Patient INTEGER PRIMARY KEY AUTOINCREMENT,Nom_Patient text NOT NULL,Prenom_Patient text NOT NULL," +
                "Sexe text NOT NULL, Enceinte INTEGER NOT NULL, Age INTEGER NOT NULL, Poid REAL NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE Ordonnances (ID_Ordonnance INTEGER PRIMARY KEY AUTOINCREMENT,Date_Ordonnance text NOT NULL," +
                "ID_Patient INTEGER NOT NULL,Compte text NOT NULL, FOREIGN KEY (ID_Patient) REFERENCES Patients(ID_Patient),FOREIGN KEY (Compte) REFERENCES Medecines(Compte))");

        sqLiteDatabase.execSQL("CREATE TABLE Contient (ID_Medicament text NOT NULL,ID_Ordonnance INTEGER NOT NULL,Dose_Medicament_Ordonnance text NOT NULL,PRIMARY KEY (ID_Medicament,ID_Ordonnance))");

        sqLiteDatabase.execSQL("CREATE TABLE Medicaments (ID_Medicament text PRIMARY KEY NOT NULL,Designation text NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE Regles (ID_Regle INTEGER PRIMARY KEY AUTOINCREMENT,Type_Analyse_Utiliser text NOT NULL,Dose_Regle REAL NOT NULL,Unite text NOT NULL," +
                "Valeur_Minimum REAL,Valeur_Maximum REAL,Valeur_Sexe text,Valeur_Enceinte INTEGER,ID_Medicament text NOT NULL,FOREIGN KEY (ID_Medicament) REFERENCES Medicaments(ID_Medicament))");

        onOpen(sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists Contient");
        sqLiteDatabase.execSQL("drop table if exists Regles");
        sqLiteDatabase.execSQL("drop table if exists Ordonnances");
        sqLiteDatabase.execSQL("drop table if exists Medecines");
        sqLiteDatabase.execSQL("drop table if exists Patients");
        sqLiteDatabase.execSQL("drop table if exists Medicaments");

        onCreate(sqLiteDatabase);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {

            db.execSQL("PRAGMA foreign_keys = ON;");

        }
    }

    public boolean insertMedecine(String Nom_Medecine, String Prenom_Medecine, String Compte, String Mot_De_Passe){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Compte",String.valueOf(Compte));
        contentValues.put("Mot_De_Passe", String.valueOf(Mot_De_Passe));
        contentValues.put("Nom_Medecine",String.valueOf(Nom_Medecine));
        contentValues.put("Prenom_Medecine",String.valueOf(Prenom_Medecine));

        long result = database.insert("Medecines",null,contentValues);

        return result != -1;

    }

    public void upDateMedecine(String Compte, String Nom_Medecine, String Prenom_Medecine, String Mot_De_Passe){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE Medecines SET Nom_Medecine = '"+Nom_Medecine+"',Prenom_Medecine = '"+Prenom_Medecine+"'," +
                "Mot_De_Passe = '"+Mot_De_Passe+"' WHERE Compte = '"+Compte+"'");
        db.close();

    }

    public boolean insertPatients(String Nom_Patient, String Prenom_Patient, String Sexe, int Enceinte, int Age, double Poid){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Nom_Patient",String.valueOf(Nom_Patient));
        contentValues.put("Prenom_Patient", String.valueOf(Prenom_Patient));
        contentValues.put("Sexe",String.valueOf(Sexe));
        contentValues.put("Enceinte", Enceinte);
        contentValues.put("Age", Age);
        contentValues.put("Poid", Poid);

        long result = database.insert("Patients",null,contentValues);

        return result != -1;

    }

    public boolean insertOrdonnance(String Date_Ordonnance, int ID_Patient, String Compte){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date_Ordonnance",String.valueOf(Date_Ordonnance));
        contentValues.put("ID_Patient", String.valueOf(ID_Patient));
        contentValues.put("Compte",String.valueOf(Compte));

        long result = database.insert("Ordonnances",null,contentValues);

        return result != -1;

    }

    public void insertContient(String ID_Medicament, int ID_Ordonnance, String Dose_Medicament_Ordonnance){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_Medicament",String.valueOf(ID_Medicament));
        contentValues.put("ID_Ordonnance", ID_Ordonnance);
        contentValues.put("Dose_Medicament_Ordonnance", Dose_Medicament_Ordonnance);

        database.insert("Contient",null,contentValues);

    }

    public boolean checkAccount(String Compte){

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor;
        String sql ="SELECT Medecines.Compte from Medecines where Compte = '"+Compte+"'";
        cursor= database.rawQuery(sql,null);
        int count = cursor.getCount();
        cursor.close();
        database.close();

        return count > 0;

    }

    public boolean checkAccount(String Compte,String Mot_De_Passe){

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor;
        String sql ="SELECT Medecines.Compte from Medecines where Compte = '"+Compte+"' and Mot_De_Passe = '"+Mot_De_Passe+"'";
        cursor= database.rawQuery(sql,null);
        int count = cursor.getCount();
        cursor.close();
        database.close();

        return count > 0;

    }

    public int getCountAccounts(){

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor;
        String sql ="SELECT Medecines.Compte from Medecines";
        cursor= database.rawQuery(sql,null);
        int count = cursor.getCount();
        cursor.close();
        database.close();

        return count;

    }

    public Medecines  getMedecine(String Compte){

        SQLiteDatabase database = this.getReadableDatabase();

        Medecines medecine;

        Cursor cursor = database.rawQuery("SELECT Medecines.Compte,Medecines.Mot_De_Passe,Medecines.Nom_Medecine,Medecines.Prenom_Medecine from Medecines where Compte = '"+Compte+"'", null);

        if (cursor.moveToFirst()) {

            String[] columnNames = cursor.getColumnNames();

            medecine = new Medecines(cursor.getString(cursor.getColumnIndex(columnNames[0])),
                    cursor.getString(cursor.getColumnIndex(columnNames[1])),
                    cursor.getString(cursor.getColumnIndex(columnNames[2])),
                    cursor.getString(cursor.getColumnIndex(columnNames[3])));

        }else{

            medecine = new Medecines(null,null,"","");

        }

        cursor.close();
        database.close();

        return medecine;

    }

    public List<Object> getAllMedecines(){

        List<Object> list = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT Medecines.Compte,Medecines.Mot_De_Passe,Medecines.Nom_Medecine,Medecines.Prenom_Medecine from Medecines", null);

        if (cursor.moveToFirst()) {

            String[] columnNames = cursor.getColumnNames();

            while (!cursor.isAfterLast()) {

                list.add(new Medecines(cursor.getString(cursor.getColumnIndex(columnNames[0])),
                        cursor.getString(cursor.getColumnIndex(columnNames[1])),
                        cursor.getString(cursor.getColumnIndex(columnNames[2])),
                        cursor.getString(cursor.getColumnIndex(columnNames[3]))));
                cursor.moveToNext();
            }

        }

        cursor.close();
        database.close();

        return list;

    }

    public List<Object> getAllPatients(){

        List<Object> list = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT Patients.ID_Patient,Patients.Nom_Patient," +
                "Patients.Prenom_Patient,Patients.Sexe,Patients.Enceinte,Patients.Age,Patients.Poid from Patients order by Patients.ID_Patient DESC", null);

        if (cursor.moveToFirst()) {

            String[] columnNames = cursor.getColumnNames();

            while (!cursor.isAfterLast()) {

                list.add(new Patients(cursor.getInt(cursor.getColumnIndex(columnNames[0])),
                        cursor.getString(cursor.getColumnIndex(columnNames[1])),
                        cursor.getString(cursor.getColumnIndex(columnNames[2])),
                        cursor.getString(cursor.getColumnIndex(columnNames[3])),
                        cursor.getInt(cursor.getColumnIndex(columnNames[4])),
                        cursor.getInt(cursor.getColumnIndex(columnNames[5])),
                        cursor.getDouble(cursor.getColumnIndex(columnNames[6])),
                        cursor.getInt(cursor.getColumnIndex(columnNames[0]))+" "+
                                cursor.getString(cursor.getColumnIndex(columnNames[1])).toLowerCase()+" "+
                                cursor.getString(cursor.getColumnIndex(columnNames[2])).toUpperCase()));
                cursor.moveToNext();
            }

        }

        cursor.close();
        database.close();

        return list;

    }

    public Patients getPatient(int ID_Patient){

        SQLiteDatabase database = this.getReadableDatabase();

        Patients patients = new Patients(0,null,null,null,-1,-1,-1.0,null);

        Cursor cursor = database.rawQuery("SELECT Patients.ID_Patient,Patients.Nom_Patient," +
                "Patients.Prenom_Patient,Patients.Sexe,Patients.Enceinte,Patients.Age,Patients.Poid from Patients where Patients.ID_Patient = '"+ID_Patient+"'", null);

        if (cursor.moveToFirst()) {

            String[] columnNames = cursor.getColumnNames();

            patients = new Patients(cursor.getInt(cursor.getColumnIndex(columnNames[0])),
                    cursor.getString(cursor.getColumnIndex(columnNames[1])),
                    cursor.getString(cursor.getColumnIndex(columnNames[2])),
                    cursor.getString(cursor.getColumnIndex(columnNames[3])),
                    cursor.getInt(cursor.getColumnIndex(columnNames[4])),
                    cursor.getInt(cursor.getColumnIndex(columnNames[5])),
                    cursor.getDouble(cursor.getColumnIndex(columnNames[6])),
                    cursor.getInt(cursor.getColumnIndex(columnNames[0]))+" "+
                            cursor.getString(cursor.getColumnIndex(columnNames[1])).toLowerCase()+" "+
                            cursor.getString(cursor.getColumnIndex(columnNames[2])).toUpperCase());

        }

        cursor.close();
        database.close();

        return patients;

    }

    public void deletePatient(int ID_Patient){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Patients WHERE Patients.ID_Patient='"+ID_Patient+"'");
        db.close();

    }

    public void updatePatient(int ID_Patient,String Nom_Patient, String Prenom_Patient, String Sexe, int Enceinte, int Age, double Poid){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE Patients SET Nom_Patient = '"+Nom_Patient+"',Prenom_Patient = '"+Prenom_Patient+"'," +
                "Sexe = '"+Sexe+"',Enceinte = '"+Enceinte+"',Age = '"+Age+"',Poid = '"+Poid+"' WHERE ID_Patient = '"+ID_Patient+"'");
        db.close();

    }

    public List<Object> getAllMedicaments(){

        List<Object> list = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT Medicaments.ID_Medicament,Medicaments.Designation" +
                " from Medicaments", null);

        if (cursor.moveToFirst()) {

            String[] columnNames = cursor.getColumnNames();

            while (!cursor.isAfterLast()) {

                list.add(new Medicaments(cursor.getString(cursor.getColumnIndex(columnNames[0])),
                        cursor.getString(cursor.getColumnIndex(columnNames[1])),
                        cursor.getString(cursor.getColumnIndex(columnNames[0]))+" "+
                        cursor.getString(cursor.getColumnIndex(columnNames[1]))));
                cursor.moveToNext();
            }

        }

        cursor.close();
        database.close();

        return list;

    }

    public boolean insertMedicaments(String ID_Medicament, String Designation){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_Medicament",String.valueOf(ID_Medicament));
        contentValues.put("Designation", String.valueOf(Designation));

        long result = database.insert("Medicaments",null,contentValues);

        return result != -1;

    }

    public void updateMedicament(String ID_Medicament, String Designation){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE Medicaments SET Designation = '"+Designation+"' WHERE ID_Medicament = '"+ID_Medicament+"'");
        db.close();

    }

    public void deleteMedicament(String ID_Medicament){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Medicaments WHERE Medicaments.ID_Medicament='"+ID_Medicament+"'");
        db.close();

    }

    public void deleteMedicamentOrdonnance(String ID_Medicament){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM Contient WHERE Contient.ID_Medicament='"+ID_Medicament+"'");
        db.close();

        db.close();

    }

    public boolean checkMedicament(String ID_Medicament){

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor;
        String sql ="SELECT Medicaments.ID_Medicament from Medicaments where ID_Medicament = '"+ID_Medicament+"'";
        cursor= database.rawQuery(sql,null);
        int count = cursor.getCount();
        cursor.close();
        database.close();

        return count > 0;

    }

    public List<Object> getAllRegles(String ID_Medicament){

        List<Object> list = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT Regles.ID_Regle,Regles.Type_Analyse_Utiliser," +
                "Regles.Dose_Regle,Regles.Unite,Regles.Valeur_Minimum,Regles.Valeur_Maximum,Regles.Valeur_Sexe,Regles.Valeur_Enceinte,Regles.ID_Medicament" +
                " from Regles Where Regles.ID_Medicament = '"+ID_Medicament+"' order by Regles.ID_Regle DESC", null);

        if (cursor.moveToFirst()) {

            String[] columnNames = cursor.getColumnNames();

            while (!cursor.isAfterLast()) {

                list.add(new Regles(cursor.getInt(cursor.getColumnIndex(columnNames[0])),
                        cursor.getString(cursor.getColumnIndex(columnNames[1])),
                        cursor.getDouble(cursor.getColumnIndex(columnNames[2])),
                        cursor.getString(cursor.getColumnIndex(columnNames[3])),
                        getDouble(cursor.getString(cursor.getColumnIndex(columnNames[4]))),
                        getDouble(cursor.getString(cursor.getColumnIndex(columnNames[5]))),
                        cursor.getString(cursor.getColumnIndex(columnNames[6])),
                        cursor.getInt(cursor.getColumnIndex(columnNames[7])),
                        cursor.getString(cursor.getColumnIndex(columnNames[8]))));
                cursor.moveToNext();

            }

        }

        cursor.close();
        database.close();

        return list;

    }

    public void updateRegle(int ID_Regle,String Type_Analyse_Utiliser, double Dose_Regle, String Unite, Double Valeur_Minimum, Double Valeur_Maximum, String Valeur_Sexe, Integer Valeur_Enceinte, String ID_Medicament){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("Type_Analyse_Utiliser",String.valueOf(Type_Analyse_Utiliser));
        contentValues.put("Dose_Regle", Dose_Regle);
        contentValues.put("Unite",Unite);
        contentValues.put("Valeur_Minimum", Valeur_Minimum);
        contentValues.put("Valeur_Maximum",Valeur_Maximum);
        contentValues.put("Valeur_Sexe",String.valueOf(Valeur_Sexe));
        contentValues.put("Valeur_Enceinte",Valeur_Enceinte);

        contentValues.put("ID_Medicament",String.valueOf(ID_Medicament));

        db.update("Regles", contentValues, "ID_Regle = ?", new String[]{Integer.toString(ID_Regle)});
        db.close();

    }

    public boolean insertRegles(String Type_Analyse_Utiliser, double Dose_Regle, String Unite, Double Valeur_Minimum, Double Valeur_Maximum, String Valeur_Sexe, Integer Valeur_Enceinte, String ID_Medicament){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Type_Analyse_Utiliser",String.valueOf(Type_Analyse_Utiliser));
        contentValues.put("Dose_Regle", Dose_Regle);
        contentValues.put("Unite",Unite);
        contentValues.put("Valeur_Minimum", Valeur_Minimum);
        contentValues.put("Valeur_Maximum",Valeur_Maximum);
        contentValues.put("Valeur_Sexe",String.valueOf(Valeur_Sexe));
        contentValues.put("Valeur_Enceinte",Valeur_Enceinte);

        contentValues.put("ID_Medicament",String.valueOf(ID_Medicament));

        long result = database.insert("Regles",null,contentValues);

        return result != -1;

    }

    public int getCountMedicament(){

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor;
        String sql ="SELECT Medicaments.ID_Medicament from Medicaments";
        cursor= database.rawQuery(sql,null);
        int count = cursor.getCount();
        cursor.close();
        database.close();

        return count;

    }

    public void deleteRegles(String ID_Medicament){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Regles WHERE Regles.ID_Medicament='"+ID_Medicament+"'");
        db.close();

    }



    public void deleteRegle(int ID_Regle){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Regles WHERE Regles.ID_Regle='"+ID_Regle+"'");
        db.close();

    }

    public Ordonnances getLastOrdonnance(){

        SQLiteDatabase database = this.getReadableDatabase();

        Ordonnances ordonnances;

        Cursor cursor = database.rawQuery("SELECT Ordonnances.ID_Ordonnance,Ordonnances.Date_Ordonnance,Ordonnances.ID_Patient,Ordonnances.Compte from Ordonnances where ID_Ordonnance = (SELECT MAX(ID_Ordonnance)  FROM Ordonnances)", null);

        if (cursor.moveToFirst()) {

            String[] columnNames = cursor.getColumnNames();

            ordonnances = new Ordonnances(cursor.getInt(cursor.getColumnIndex(columnNames[0])),
                    cursor.getString(cursor.getColumnIndex(columnNames[1])),
                    cursor.getInt(cursor.getColumnIndex(columnNames[2])),
                    cursor.getString(cursor.getColumnIndex(columnNames[3])));

        }else{

            ordonnances = new Ordonnances(0,null,0,null);

        }

        cursor.close();
        database.close();
        return ordonnances;
    }

    public List<Object> getAllOrdonnances(){

        List<Object> list = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT Ordonnances.ID_Ordonnance,Ordonnances.Date_Ordonnance," +
                "Patients.Nom_Patient,Patients.Prenom_Patient,Medecines.Nom_Medecine,Medecines.Prenom_Medecine " +
                " from Ordonnances left join Patients on Patients.ID_Patient = Ordonnances.ID_Patient left join Medecines on Medecines.Compte = Ordonnances.Compte order by Ordonnances.ID_Ordonnance DESC", null);

        if (cursor.moveToFirst()) {

            String[] columnNames = cursor.getColumnNames();

            while (!cursor.isAfterLast()) {

                list.add(new Ordonnances_Patients_Medecines(cursor.getInt(cursor.getColumnIndex(columnNames[0])),
                        cursor.getString(cursor.getColumnIndex(columnNames[1])),
                        cursor.getString(cursor.getColumnIndex(columnNames[2])),
                        cursor.getString(cursor.getColumnIndex(columnNames[3])),
                        cursor.getString(cursor.getColumnIndex(columnNames[4])),
                        cursor.getString(cursor.getColumnIndex(columnNames[5]))));
                cursor.moveToNext();

            }

        }

        cursor.close();
        database.close();

        return list;

    }

    public String getDose(String ID_Medicament,int ID_Patient,double bilirubine,double tgo_tgp,double clairance){

        SQLiteDatabase database = this.getReadableDatabase();

        Patients patients = getPatient(ID_Patient);

        Regles reglesFinal = null;
        Regles reglesClairance;
        Regles reglesBilirubine;
        Regles reglesTgoTgp;
        Regles reglesAge;
        Regles reglesPoid;
        Regles reglesSexe;
        Regles reglesEnceinte;

        int indexClairance = -1;
        int indexBilirubine = -1;
        int indexTgoTgp = -1;
        int indexAge = -1;
        int indexPoid = -1;
        int indexSexe = -1;
        int indexEnceinte = -1;

        String dose;

        List<Object> list;
        List<Object> listValue = new ArrayList<>();

        list = getAllRegles(ID_Medicament);

        boolean typeClairance = false;
        boolean typeBilirubine = false;
        boolean typeTgoTgp = false;
        boolean typeAge = false;
        boolean typePoid = false;
        boolean typeSexe = false;
        boolean typeEnceinte = false;

        int index = 0;

        while(index != list.size()){

            if(((Regles) list.get(index)).getType_Analyse_Utiliser().equals("Bilirubine")){

                typeBilirubine = true;

            }else{

                if(((Regles) list.get(index)).getType_Analyse_Utiliser().equals("Tgo/tgp")){

                    typeTgoTgp = true;

                }else{

                    if(((Regles) list.get(index)).getType_Analyse_Utiliser().equals("Clairance rénale")){

                        typeClairance = true;

                    }else{

                        if(((Regles) list.get(index)).getType_Analyse_Utiliser().equals("Age")){

                            typeAge = true;

                        }else{

                            if(((Regles) list.get(index)).getType_Analyse_Utiliser().equals("Poid")){

                                typePoid = true;

                            }else{

                                if(((Regles) list.get(index)).getType_Analyse_Utiliser().equals("Sexe")){

                                    typeSexe = true;

                                }else{

                                    typeEnceinte = true;

                                }

                            }

                        }

                    }

                }

            }

            index++;

        }

        if(typeClairance){

            index = 0;

            while(index < list.size()){

                Regles regles = (Regles) list.get(index);

                if(regles.getType_Analyse_Utiliser().equals("Clairance rénale")){

                    if(regles.getValeur_Minimum() != null && regles.getValeur_Maximum() != null){

                        if(regles.getValeur_Minimum()<=clairance && clairance<=regles.getValeur_Maximum()) {

                            if (indexClairance == -1) {

                                indexClairance = index;

                            } else {

                                if (regles.getDose_Regle() < ((Regles) list.get(indexClairance)).getDose_Regle()) {

                                    indexClairance = index;

                                }

                            }

                        }

                    }else{

                        if(regles.getValeur_Minimum() != null){

                            if(regles.getValeur_Minimum()<=clairance) {

                                if (indexClairance == -1) {

                                    indexClairance = index;

                                } else {

                                    if (regles.getDose_Regle() < ((Regles) list.get(indexClairance)).getDose_Regle()) {

                                        indexClairance = index;

                                    }

                                }

                            }

                        }else{

                            if(clairance<=regles.getValeur_Maximum()) {

                                if (indexClairance == -1) {

                                    indexClairance = index;

                                } else {

                                    if (regles.getDose_Regle() < ((Regles) list.get(indexClairance)).getDose_Regle()) {

                                        indexClairance = index;

                                    }

                                }

                            }

                        }

                    }

                }

                index++;

            }

        }

        if(typeBilirubine){

            index = 0;

            while(index < list.size()){

                Regles regles = (Regles) list.get(index);

                if(regles.getType_Analyse_Utiliser().equals("Bilirubine")){

                    if(regles.getValeur_Minimum() != null && regles.getValeur_Maximum() != null){

                        if(regles.getValeur_Minimum()<=bilirubine && bilirubine<=regles.getValeur_Maximum()) {

                            if (indexBilirubine == -1) {

                                indexBilirubine = index;

                            } else {

                                if (regles.getDose_Regle() < ((Regles) list.get(indexBilirubine)).getDose_Regle()) {

                                    indexBilirubine = index;

                                }

                            }

                        }

                    }else{

                        if(regles.getValeur_Minimum() != null){

                            if(regles.getValeur_Minimum()<=bilirubine) {

                                if (indexBilirubine == -1) {

                                    indexBilirubine = index;

                                } else {

                                    if (regles.getDose_Regle() < ((Regles) list.get(indexBilirubine)).getDose_Regle()) {

                                        indexBilirubine = index;

                                    }

                                }

                            }

                        }else{

                            if(bilirubine<=regles.getValeur_Maximum()) {

                                if (indexBilirubine == -1) {

                                    indexBilirubine = index;

                                } else {

                                    if (regles.getDose_Regle() < ((Regles) list.get(indexBilirubine)).getDose_Regle()) {

                                        indexBilirubine = index;

                                    }

                                }

                            }

                        }

                    }

                }

                index++;

            }

        }

        if(typeTgoTgp){

            index = 0;

            while(index < list.size()){

                Regles regles = (Regles) list.get(index);

                if(regles.getType_Analyse_Utiliser().equals("Tgo/tgp")){

                    if(regles.getValeur_Minimum() != null && regles.getValeur_Maximum() != null){

                        if(regles.getValeur_Minimum()<=tgo_tgp && tgo_tgp<=regles.getValeur_Maximum()) {

                            if (indexTgoTgp == -1) {

                                indexTgoTgp = index;

                            } else {

                                if (regles.getDose_Regle() < ((Regles) list.get(indexTgoTgp)).getDose_Regle()) {

                                    indexTgoTgp = index;

                                }

                            }

                        }

                    }else{

                        if(regles.getValeur_Minimum() != null){

                            if(regles.getValeur_Minimum()<=tgo_tgp) {

                                if (indexTgoTgp == -1) {

                                    indexTgoTgp = index;

                                } else {

                                    if (regles.getDose_Regle() < ((Regles) list.get(indexTgoTgp)).getDose_Regle()) {

                                        indexTgoTgp = index;

                                    }

                                }

                            }

                        }else{

                            if(tgo_tgp<=regles.getValeur_Maximum()) {

                                if (indexTgoTgp == -1) {

                                    indexTgoTgp = index;

                                } else {

                                    if (regles.getDose_Regle() < ((Regles) list.get(indexTgoTgp)).getDose_Regle()) {

                                        indexTgoTgp = index;

                                    }

                                }

                            }

                        }

                    }

                }

                index++;

            }

        }

        if(typeAge){

            index = 0;

            while(index < list.size()){

                Regles regles = (Regles) list.get(index);

                if(regles.getType_Analyse_Utiliser().equals("Age")){

                    if(regles.getValeur_Minimum() != null && regles.getValeur_Maximum() != null){

                        if(regles.getValeur_Minimum()<=patients.getAge() && patients.getAge()<=regles.getValeur_Maximum()) {

                            if (indexAge == -1) {

                                indexAge = index;

                            } else {

                                if (regles.getDose_Regle() < ((Regles) list.get(indexAge)).getDose_Regle()) {

                                    indexAge = index;

                                }

                            }

                        }

                    }else{

                        if(regles.getValeur_Minimum() != null){

                            if(regles.getValeur_Minimum()<=patients.getAge()) {

                                if (indexAge == -1) {

                                    indexAge = index;

                                } else {

                                    if (regles.getDose_Regle() < ((Regles) list.get(indexAge)).getDose_Regle()) {

                                        indexAge = index;

                                    }

                                }

                            }

                        }else{

                            if(patients.getAge()<=regles.getValeur_Maximum()) {

                                if (indexAge == -1) {

                                    indexAge = index;

                                } else {

                                    if (regles.getDose_Regle() < ((Regles) list.get(indexAge)).getDose_Regle()) {

                                        indexAge = index;

                                    }

                                }

                            }

                        }

                    }

                }

                index++;

            }

        }

        if(typePoid){

            index = 0;

            while(index < list.size()){

                Regles regles = (Regles) list.get(index);

                if(regles.getType_Analyse_Utiliser().equals("Poid")){

                    if(regles.getValeur_Minimum() != null && regles.getValeur_Maximum() != null){

                        if(regles.getValeur_Minimum()<=patients.getPoid() && patients.getPoid()<=regles.getValeur_Maximum()) {

                            if (indexPoid == -1) {

                                indexPoid = index;

                            } else {

                                if (regles.getDose_Regle() < ((Regles) list.get(indexPoid)).getDose_Regle()) {

                                    indexPoid = index;

                                }

                            }

                        }

                    }else{

                        if(regles.getValeur_Minimum() != null){

                            if(regles.getValeur_Minimum()<=patients.getPoid()) {

                                if (indexPoid == -1) {

                                    indexPoid = index;

                                } else {

                                    if (regles.getDose_Regle() < ((Regles) list.get(indexPoid)).getDose_Regle()) {

                                        indexPoid = index;

                                    }

                                }

                            }

                        }else{

                            if(patients.getPoid()<=regles.getValeur_Maximum()) {

                                if (indexPoid == -1) {

                                    indexPoid = index;

                                } else {

                                    if (regles.getDose_Regle() < ((Regles) list.get(indexPoid)).getDose_Regle()) {

                                        indexPoid = index;

                                    }

                                }

                            }

                        }

                    }

                }

                index++;

            }

        }

        if(typeSexe){

            index = 0;

            while(index < list.size()){

                Regles regles = (Regles) list.get(index);

                if(regles.getType_Analyse_Utiliser().equals("Sexe")){

                    if(regles.getValeur_Sexe().equals(patients.getSexe())) {

                        if (indexSexe == -1) {

                            indexSexe = index;

                        } else {

                            if (regles.getDose_Regle() < ((Regles) list.get(indexSexe)).getDose_Regle()) {

                                indexSexe = index;

                            }

                        }

                    }

                }

                index++;

            }

        }

        if(typeEnceinte){

            index = 0;

            while(index < list.size()){

                Regles regles = (Regles) list.get(index);

                if(regles.getType_Analyse_Utiliser().equals("Enceinte")){

                    if(regles.getValeur_Enceinte().equals(patients.getEnceinte())) {

                        if (indexEnceinte == -1) {

                            indexEnceinte = index;

                        } else {

                            if (regles.getDose_Regle() < ((Regles) list.get(indexEnceinte)).getDose_Regle()) {

                                indexEnceinte = index;

                            }

                        }

                    }

                }

                index++;

            }

        }

        if(indexClairance != -1) {

            reglesClairance = (Regles) list.get(indexClairance);
            listValue.add(reglesClairance);

        }

        if(indexBilirubine != -1) {

            reglesBilirubine = (Regles) list.get(indexBilirubine);
            listValue.add(reglesBilirubine);

        }

        if(indexTgoTgp != -1) {

            reglesTgoTgp = (Regles) list.get(indexTgoTgp);
            listValue.add(reglesTgoTgp);

        }

        if(indexAge != -1) {

            reglesAge = (Regles) list.get(indexAge);
            listValue.add(reglesAge);

        }

        if(indexPoid != -1) {

            reglesPoid = (Regles) list.get(indexPoid);
            listValue.add(reglesPoid);

        }

        if(indexSexe != -1) {

            reglesSexe = (Regles) list.get(indexSexe);
            listValue.add(reglesSexe);

        }

        if(indexEnceinte != -1) {

            reglesEnceinte = (Regles) list.get(indexEnceinte);
            listValue.add(reglesEnceinte);

        }

        if(!listValue.isEmpty()){

            int i = 1;

            reglesFinal = (Regles) listValue.get(0);
            
            while(i < listValue.size()){

                if(((Regles) listValue.get(i)).getDose_Regle() < reglesFinal.getDose_Regle()) {

                    reglesFinal = (Regles) listValue.get(i);

                }

                i++;

            }

        }

        if(reglesFinal != null) {
            
            if (reglesFinal.getDose_Regle() != 0.0) {

                dose = reglesFinal.getDose_Regle() + " " + reglesFinal.getUnite();

            } else {

                dose = reglesFinal.getUnite();

            }
            
        }else{

            dose = "Aucune dose trouvée";

        }
        
        database.close();
        return dose;

    }

    public int getCountRegles(String ID_Medicament){

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor;
        String sql ="SELECT Regles.ID_Regle from Regles where Regles.ID_Medicament='"+ID_Medicament+"'";
        cursor= database.rawQuery(sql,null);
        int count = cursor.getCount();
        cursor.close();
        database.close();

        return count;

    }

    public List<Object> getContientOrdonnance(int ID_Ordonnance){

        List<Object> list = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT Medicaments.Designation,Contient.Dose_Medicament_Ordonnance,Contient.ID_Medicament,Contient.ID_Ordonnance " +
                " from Contient left join Medicaments on Medicaments.ID_Medicament = Contient.ID_Medicament where Contient.ID_Ordonnance = '"+ID_Ordonnance+"'", null);

        if (cursor.moveToFirst()) {

            String[] columnNames = cursor.getColumnNames();

            while (!cursor.isAfterLast()) {

                list.add(new Contient(cursor.getString(cursor.getColumnIndex(columnNames[1])),
                        cursor.getString(cursor.getColumnIndex(columnNames[0])),
                        cursor.getString(cursor.getColumnIndex(columnNames[2])),
                        cursor.getInt(cursor.getColumnIndex(columnNames[3]))));
                cursor.moveToNext();

            }

        }

        cursor.close();
        database.close();

        return list;

    }

    Double getDouble(String value){

        Double aDouble = null;

        if(value != null){

            aDouble = Double.valueOf(value);

        }

        return aDouble;

    }

    public void deleteOrdonnance(int ID_Ordonnance){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Contient WHERE Contient.ID_Ordonnance='"+ID_Ordonnance+"'");
        db.execSQL("DELETE FROM Ordonnances WHERE Ordonnances.ID_Ordonnance='"+ID_Ordonnance+"'");
        db.close();

    }

    public List<Object> getAllOrdonnances(int ID_Patient){

        List<Object> list = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT Ordonnances.ID_Ordonnance,Ordonnances.Date_Ordonnance," +
                "Patients.Nom_Patient,Patients.Prenom_Patient,Medecines.Nom_Medecine,Medecines.Prenom_Medecine " +
                " from Ordonnances left join Patients on Patients.ID_Patient = Ordonnances.ID_Patient left join Medecines on Medecines.Compte = Ordonnances.Compte where Ordonnances.ID_Patient = '"+ID_Patient+"'", null);

        if (cursor.moveToFirst()) {

            String[] columnNames = cursor.getColumnNames();

            while (!cursor.isAfterLast()) {

                list.add(new Ordonnances_Patients_Medecines(cursor.getInt(cursor.getColumnIndex(columnNames[0])),
                        cursor.getString(cursor.getColumnIndex(columnNames[1])),
                        cursor.getString(cursor.getColumnIndex(columnNames[2])),
                        cursor.getString(cursor.getColumnIndex(columnNames[3])),
                        cursor.getString(cursor.getColumnIndex(columnNames[4])),
                        cursor.getString(cursor.getColumnIndex(columnNames[5]))));
                cursor.moveToNext();

            }

        }

        cursor.close();
        database.close();

        return list;

    }

    public void deleteContient(int ID_Ordonnance){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Contient WHERE Contient.ID_Ordonnance = '"+ID_Ordonnance+"'");
        db.close();

    }

    public void deleteOrdonnancePatient(int ID_Patient){

        SQLiteDatabase db = this.getWritableDatabase();

        List<Object> list;

        list = getAllOrdonnances(ID_Patient);

        int i = 0;
        while(i < list.size()){

            deleteContient(((Ordonnances_Patients_Medecines) list.get(i)).getID_Ordonnance());
            deleteOrdonnance(((Ordonnances_Patients_Medecines) list.get(i)).getID_Ordonnance());
            i++;

        }

        db.close();

    }

    public int getCountMedicamentsOrdonnance(String ID_Medicament,int ID_Ordonnance){

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor;
        String sql ="SELECT Contient.ID_Ordonnance from Contient where Contient.ID_Medicament = '"+ID_Medicament+"' and Contient.ID_Ordonnance = '"+ID_Ordonnance+"'";
        cursor= database.rawQuery(sql,null);
        int count = cursor.getCount();
        cursor.close();
        database.close();

        return count;

    }

    public void deleteContient(String ID_Medicament,int ID_Ordonnance){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Contient WHERE Contient.ID_Ordonnance = '"+ID_Ordonnance+"' and Contient.ID_Medicament = '"+ID_Medicament+"'");
        db.close();

    }

}
