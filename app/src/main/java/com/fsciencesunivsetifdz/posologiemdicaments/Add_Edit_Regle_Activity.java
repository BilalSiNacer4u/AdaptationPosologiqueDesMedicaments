package com.fsciencesunivsetifdz.posologiemdicaments;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Add_Edit_Regle_Activity extends AppCompatActivity {

    static boolean add;
    static boolean edit;

    static Medicaments medicaments;
    static Regles regles;

    String typeAnalyse;

    Spinner spinner_type;

    TextView txtvtitle_toolbar;

    EditText editText_id_regle;
    EditText editText_dose_regle;
    EditText editText_unite_regle;
    EditText editText_min;
    EditText editText_max;

    TextView txtv_sexe_enceinte;

    RadioButton radio_homme_non_enceinte;
    RadioButton radio_femme_enceinte;

    TextView txtv_msg_error;

    Button btn_edit_cancel;
    Button btn_delete_save;

    LinearLayout lyt_valeur_min_max;
    LinearLayout lyt_sexe_enceinte;
    LinearLayout lyt_btn;

    RelativeLayout.LayoutParams paramsAboveType;
    RelativeLayout.LayoutParams paramsAboveValue;
    RelativeLayout.LayoutParams paramsAboveRadio;


    TextView txtv_max;
    TextView txtv_min;

    DataBase_SQLite dataBase_apm = new DataBase_SQLite(this);

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_regle);

        txtvtitle_toolbar = findViewById(R.id.txtvtitle_toolbar);

        spinner_type = findViewById(R.id.type_regle_spinner);

        btn_edit_cancel = findViewById(R.id.btn_edit_cancel);
        btn_delete_save = findViewById(R.id.btn_delete_save);

        editText_id_regle = findViewById(R.id.editText_id_regle);
        editText_dose_regle = findViewById(R.id.editText_dose_regle);
        editText_unite_regle = findViewById(R.id.editText_unite_regle);
        editText_min = findViewById(R.id.editText_min);
        editText_max = findViewById(R.id.editText_max);

        txtv_sexe_enceinte = findViewById(R.id.txtv_sexe_enceinte);

        radio_homme_non_enceinte = findViewById(R.id.radio_homme_non_enceinte);
        radio_femme_enceinte = findViewById(R.id.radio_femme_enceinte);

        txtv_msg_error = findViewById(R.id.txtv_msg_error);

        lyt_valeur_min_max = findViewById(R.id.lyt_valeur_min_max);
        lyt_sexe_enceinte = findViewById(R.id.lyt_sexe_enceinte);
        lyt_btn = findViewById(R.id.lyt_btn);

        txtv_max = findViewById(R.id.txtv_max);
        txtv_min = findViewById(R.id.txtv_min);

        paramsAboveType = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        paramsAboveRadio = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        paramsAboveValue = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.type_analyse_suiv, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner_type.setAdapter(adapter);

        txtv_msg_error.setText(null);

        edit = false;

        if(add){

            txtvtitle_toolbar.setText("Ajouter un régle");
            btn_delete_save.setText("Sauvegarder");
            btn_delete_save.setBackgroundResource(R.drawable.bg_btn_add);
            btn_edit_cancel.setText("Annuler");
            btn_edit_cancel.setBackgroundResource(R.drawable.bg_btn_abt);

            editText_id_regle.setText(null);
            editText_dose_regle.setText(null);
            editText_unite_regle.setText(null);

            spinner_type.setSelection(adapter.getPosition("Clairance rénale"));
            typeAnalyse = "Clairance rénale";

            editText_min.setText(null);
            editText_max.setText(null);

            editText_id_regle.setEnabled(false);
            editText_dose_regle.setEnabled(true);
            editText_unite_regle.setEnabled(true);
            spinner_type.setEnabled(true);
            editText_min.setEnabled(true);
            editText_max.setEnabled(true);

            refreshUI();

            editText_dose_regle.requestFocus(editText_dose_regle.getText().toString().length());
            show_keyboard(this);

        }else{

            txtvtitle_toolbar.setText("Informations du régle");
            btn_delete_save.setText("Supprimer");
            btn_delete_save.setBackgroundResource(R.drawable.bg_btn_abt);
            btn_edit_cancel.setText("Modifier");
            btn_edit_cancel.setBackgroundResource(R.drawable.bg_btn_add);

            spinner_type.setSelection(adapter.getPosition(regles.getType_Analyse_Utiliser()));
            typeAnalyse = spinner_type.getSelectedItem().toString();
            refreshUI();

            editText_id_regle.setText(Integer.toString(regles.getID_Regle()));
            editText_dose_regle.setText(Double.toString(regles.getDose_Regle()));
            editText_unite_regle.setText(regles.getUnite());

            spinner_type.setSelection(adapter.getPosition(regles.getType_Analyse_Utiliser()));
            typeAnalyse = regles.getType_Analyse_Utiliser();

            editText_id_regle.setEnabled(false);
            editText_dose_regle.setEnabled(false);
            editText_unite_regle.setEnabled(false);
            spinner_type.setEnabled(false);
            editText_min.setEnabled(false);
            editText_max.setEnabled(false);
            radio_homme_non_enceinte.setEnabled(false);
            radio_femme_enceinte.setEnabled(false);

            if((regles.getType_Analyse_Utiliser().equals("Clairance rénale") || regles.getType_Analyse_Utiliser().equals("Bilirubine") ||
                    regles.getType_Analyse_Utiliser().equals("Tgo/tgp") || regles.getType_Analyse_Utiliser().equals("Age") ||
                    regles.getType_Analyse_Utiliser().equals("Poid"))){

                if(regles.getValeur_Minimum()!= null) {

                    editText_min.setText(Double.toString(regles.getValeur_Minimum()));

                }else{

                    editText_min.setText(null);

                }

                if(regles.getValeur_Maximum()!= null) {

                    editText_max.setText(Double.toString(regles.getValeur_Maximum()));

                }else{

                    editText_max.setText(null);

                }

            }else{

                if(regles.getValeur_Sexe().equals("Homme") || regles.getValeur_Sexe().equals("Femme")){

                    txtv_sexe_enceinte.setText("Sexe");

                    radio_homme_non_enceinte.setText("Homme");
                    radio_femme_enceinte.setText("Femme");

                    if(regles.getValeur_Sexe().equals("Homme")){

                        radio_homme_non_enceinte.setChecked(true);
                        radio_femme_enceinte.setChecked(false);


                    }else{

                        radio_homme_non_enceinte.setChecked(false);
                        radio_femme_enceinte.setChecked(true);

                    }

                }else{

                    if(regles.getValeur_Enceinte() != null){

                        txtv_sexe_enceinte.setText("Enceinte");

                        radio_homme_non_enceinte.setText("Non");
                        radio_femme_enceinte.setText("Oui");

                        if(regles.getValeur_Enceinte()==1){

                            radio_homme_non_enceinte.setChecked(false);
                            radio_femme_enceinte.setChecked(true);

                        }else{

                            radio_homme_non_enceinte.setChecked(true);
                            radio_femme_enceinte.setChecked(false);

                        }

                    }else{

                        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle).create();
                        alertDialog.setMessage("Cette règle ne contient aucune condition !.");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", (dialog, which) -> dialog.dismiss());

                    }

                }

            }

            refreshUI();

        }



        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 1:
                        typeAnalyse = "Bilirubine";
                        break;
                    case 2:
                        typeAnalyse = "Tgo/tgp";
                        break;
                    case 3:
                        typeAnalyse = "Age";
                        break;
                    case 4:
                        typeAnalyse = "Poid";
                        break;
                    case 5:
                        typeAnalyse = "Sexe";
                        break;
                    case 6:
                        typeAnalyse = "Enceinte";
                        break;
                    default:
                        typeAnalyse = "Clairance rénale";
                        break;
                }

                refreshUI();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                typeAnalyse = "Clairance rénale";

            }
        });

        radio_homme_non_enceinte.setOnCheckedChangeListener((compoundButton, b) -> {

            if (radio_homme_non_enceinte.isChecked()) {

                radio_femme_enceinte.setChecked(false);

            }

        });

        radio_femme_enceinte.setOnCheckedChangeListener((compoundButton, b) -> {

            if (radio_femme_enceinte.isChecked()) {

                radio_homme_non_enceinte.setChecked(false);

            }

        });

        btn_edit_cancel.setOnClickListener(v -> {

            if(add){//Cancel

                if(!edit) {

                    finish();

                }else{

                    txtvtitle_toolbar.setText("Informations du régle");
                    btn_delete_save.setText("Supprimer");
                    btn_delete_save.setBackgroundResource(R.drawable.bg_btn_abt);
                    btn_edit_cancel.setText("Modifier");
                    btn_edit_cancel.setBackgroundResource(R.drawable.bg_btn_add);

                    spinner_type.setSelection(adapter.getPosition(regles.getType_Analyse_Utiliser()));
                    typeAnalyse = spinner_type.getSelectedItem().toString();
                    refreshUI();

                    editText_id_regle.setText(regles.getID_Regle());
                    editText_dose_regle.setText(Double.toString(regles.getDose_Regle()));
                    editText_unite_regle.setText(regles.getUnite());

                    spinner_type.setSelection(adapter.getPosition(regles.getType_Analyse_Utiliser()));
                    typeAnalyse = regles.getType_Analyse_Utiliser();

                    editText_id_regle.setEnabled(false);
                    editText_dose_regle.setEnabled(false);
                    editText_unite_regle.setEnabled(false);
                    spinner_type.setEnabled(false);
                    editText_min.setEnabled(false);
                    editText_max.setEnabled(false);
                    radio_homme_non_enceinte.setEnabled(false);
                    radio_femme_enceinte.setEnabled(false);

                    if((regles.getType_Analyse_Utiliser().equals("Clairance rénale") || regles.getType_Analyse_Utiliser().equals("Bilirubine") ||
                            regles.getType_Analyse_Utiliser().equals("Tgo/tgp") || regles.getType_Analyse_Utiliser().equals("Age") ||
                            regles.getType_Analyse_Utiliser().equals("Poid"))){

                        editText_min.setText(Double.toString(regles.getValeur_Minimum()));
                        editText_max.setText(Double.toString(regles.getValeur_Maximum()));

                    }else{

                        if(regles.getValeur_Sexe().equals("Homme") || regles.getValeur_Sexe().equals("Femme")){

                            txtv_sexe_enceinte.setText("Sexe");

                            radio_homme_non_enceinte.setText("Homme");
                            radio_femme_enceinte.setText("Femme");

                            if(regles.getValeur_Sexe().equals("Homme")){

                                radio_homme_non_enceinte.setChecked(true);
                                radio_femme_enceinte.setChecked(false);


                            }else{

                                radio_homme_non_enceinte.setChecked(false);
                                radio_femme_enceinte.setChecked(true);

                            }

                        }else{

                            if(regles.getValeur_Enceinte() != null){

                                txtv_sexe_enceinte.setText("Enceinte");

                                radio_homme_non_enceinte.setText("Non");
                                radio_femme_enceinte.setText("Oui");

                                if(regles.getValeur_Enceinte()==1){

                                    radio_homme_non_enceinte.setChecked(false);
                                    radio_femme_enceinte.setChecked(true);

                                }else{

                                    radio_homme_non_enceinte.setChecked(true);
                                    radio_femme_enceinte.setChecked(false);

                                }

                            }else{

                                AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle).create();
                                alertDialog.setMessage("Cette règle ne contient aucune condition !.");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", (dialog, which) -> dialog.dismiss());

                            }

                        }

                    }

                }

            }else{//edit

                if(!edit) {

                    edit = true;

                    txtvtitle_toolbar.setText("Modification du régle");
                    btn_delete_save.setText("Sauvegarder");
                    btn_delete_save.setBackgroundResource(R.drawable.bg_btn_add);
                    btn_edit_cancel.setText("Annuler");
                    btn_edit_cancel.setBackgroundResource(R.drawable.bg_btn_abt);

                    editText_dose_regle.setEnabled(true);
                    editText_unite_regle.setEnabled(true);
                    spinner_type.setEnabled(true);
                    editText_min.setEnabled(true);
                    editText_max.setEnabled(true);
                    radio_femme_enceinte.setEnabled(true);
                    radio_homme_non_enceinte.setEnabled(true);

                    if((regles.getType_Analyse_Utiliser().equals("Clairance rénale") || regles.getType_Analyse_Utiliser().equals("Bilirubine") ||
                            regles.getType_Analyse_Utiliser().equals("Tgo/tgp") || regles.getType_Analyse_Utiliser().equals("Age") ||
                            regles.getType_Analyse_Utiliser().equals("Poid"))){

                        editText_min.setEnabled(true);
                        editText_max.setEnabled(true);

                    }else {

                        if (regles.getValeur_Sexe().equals("Homme") || regles.getValeur_Sexe().equals("Femme")) {

                            radio_homme_non_enceinte.setEnabled(true);
                            radio_femme_enceinte.setEnabled(true);

                        } else {

                            if (regles.getValeur_Enceinte() != null) {

                                radio_homme_non_enceinte.setEnabled(true);
                                radio_femme_enceinte.setEnabled(true);

                            } else {

                                AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle).create();
                                alertDialog.setMessage("Cette règle ne contient aucune condition !.");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", (dialog, which) -> dialog.dismiss());

                            }

                        }

                    }

                editText_dose_regle.requestFocus(editText_dose_regle.getText().toString().length());
                    show_keyboard(this);

                }else{

                    edit = false;

                    txtvtitle_toolbar.setText("Informations du régle");
                    btn_delete_save.setText("Supprimer");
                    btn_delete_save.setBackgroundResource(R.drawable.bg_btn_abt);
                    btn_edit_cancel.setText("Modifier");
                    btn_edit_cancel.setBackgroundResource(R.drawable.bg_btn_add);

                    spinner_type.setSelection(adapter.getPosition(regles.getType_Analyse_Utiliser()));
                    typeAnalyse = spinner_type.getSelectedItem().toString();
                    refreshUI();

                    editText_id_regle.setText(Integer.toString(regles.getID_Regle()));
                    editText_dose_regle.setText(Double.toString(regles.getDose_Regle()));
                    editText_unite_regle.setText(regles.getUnite());

                    spinner_type.setSelection(adapter.getPosition(regles.getType_Analyse_Utiliser()));
                    typeAnalyse = regles.getType_Analyse_Utiliser();

                    editText_id_regle.setEnabled(false);
                    editText_dose_regle.setEnabled(false);
                    editText_unite_regle.setEnabled(false);
                    spinner_type.setEnabled(false);
                    editText_min.setEnabled(false);
                    editText_max.setEnabled(false);
                    radio_homme_non_enceinte.setEnabled(false);
                    radio_femme_enceinte.setEnabled(false);

                    if((regles.getType_Analyse_Utiliser().equals("Clairance rénale") || regles.getType_Analyse_Utiliser().equals("Bilirubine") ||
                            regles.getType_Analyse_Utiliser().equals("Tgo/tgp") || regles.getType_Analyse_Utiliser().equals("Age") ||
                            regles.getType_Analyse_Utiliser().equals("Poid"))){

                        editText_min.setText(Double.toString(regles.getValeur_Minimum()));
                        editText_max.setText(Double.toString(regles.getValeur_Maximum()));

                    }else{

                        if(regles.getValeur_Sexe().equals("Homme") || regles.getValeur_Sexe().equals("Femme")){

                            txtv_sexe_enceinte.setText("Sexe");

                            radio_homme_non_enceinte.setText("Homme");
                            radio_femme_enceinte.setText("Femme");

                            if(regles.getValeur_Sexe().equals("Homme")){

                                radio_homme_non_enceinte.setChecked(true);
                                radio_femme_enceinte.setChecked(false);


                            }else{

                                radio_homme_non_enceinte.setChecked(false);
                                radio_femme_enceinte.setChecked(true);

                            }

                        }else{

                            if(regles.getValeur_Enceinte() != null){

                                txtv_sexe_enceinte.setText("Enceinte");

                                radio_homme_non_enceinte.setText("Non");
                                radio_femme_enceinte.setText("Oui");

                                if(regles.getValeur_Enceinte()==1){

                                    radio_homme_non_enceinte.setChecked(false);
                                    radio_femme_enceinte.setChecked(true);

                                }else{

                                    radio_homme_non_enceinte.setChecked(true);
                                    radio_femme_enceinte.setChecked(false);

                                }

                            }else{

                                AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle).create();
                                alertDialog.setMessage("Cette règle ne contient aucune condition !.");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", (dialog, which) -> dialog.dismiss());

                            }

                        }

                    }

                }

            }

        });

        btn_delete_save.setOnClickListener(v -> {

            hide_keyboard(this);

            if(add){//Save

                if(!editText_dose_regle.getText().toString().equals("")){

                    if(!editText_unite_regle.getText().toString().equals("")){

                        if(typeAnalyse.equals("Sexe")){

                            if(radio_femme_enceinte.isChecked()){

                                if (dataBase_apm.insertRegles(typeAnalyse, Double.parseDouble(editText_dose_regle.getText().toString()),
                                        editText_unite_regle.getText().toString(),null,
                                        null,"Femme",null,medicaments.getID_Medicament())) {

                                    Toast.makeText(this, "La régle a été ajouté avec succès", Toast.LENGTH_SHORT).show();
                                    Add_Edit_Medicament_Activity.refreshRecycleView(this);
                                    finish();

                                } else {

                                    Toast.makeText(this, "Il y a une erreur !.", Toast.LENGTH_SHORT).show();

                                }

                            }else{

                                if(radio_homme_non_enceinte.isChecked()) {

                                    if (dataBase_apm.insertRegles(typeAnalyse, Double.parseDouble(editText_dose_regle.getText().toString()),
                                            editText_unite_regle.getText().toString(), null,
                                            null, "Homme", null, medicaments.getID_Medicament())) {

                                        Toast.makeText(this, "La régle a été ajouté avec succès", Toast.LENGTH_SHORT).show();
                                        Add_Edit_Medicament_Activity.refreshRecycleView(this);
                                        finish();

                                    } else {

                                        Toast.makeText(this, "Il y a une erreur !.", Toast.LENGTH_SHORT).show();

                                    }

                                }else{

                                    txtv_msg_error.setText("Sélectionnez le sexe S.V.P !.");
                                    Toast.makeText(this, "Sélectionnez le sexe S.V.P !.", Toast.LENGTH_SHORT).show();

                                }

                            }

                        }else{

                            if(typeAnalyse.equals("Enceinte")){

                                if(radio_femme_enceinte.isChecked()){

                                    if (dataBase_apm.insertRegles(typeAnalyse, Double.parseDouble(editText_dose_regle.getText().toString()),
                                            editText_unite_regle.getText().toString(),null,
                                            null,null,1,medicaments.getID_Medicament())) {

                                        Toast.makeText(this, "La régle a été ajouté avec succès", Toast.LENGTH_SHORT).show();
                                        Add_Edit_Medicament_Activity.refreshRecycleView(this);
                                        finish();

                                    } else {

                                        Toast.makeText(this, "Il y a une erreur !.", Toast.LENGTH_SHORT).show();

                                    }

                                }else{

                                    if(radio_homme_non_enceinte.isChecked()) {

                                        if (dataBase_apm.insertRegles(typeAnalyse, Double.parseDouble(editText_dose_regle.getText().toString()),
                                                editText_unite_regle.getText().toString(), null,
                                                null, null, 0, medicaments.getID_Medicament())) {

                                            Toast.makeText(this, "La régle a été ajouté avec succès", Toast.LENGTH_SHORT).show();
                                            Add_Edit_Medicament_Activity.refreshRecycleView(this);
                                            finish();

                                        } else {

                                            Toast.makeText(this, "Il y a une erreur !.", Toast.LENGTH_SHORT).show();

                                        }

                                    }else{

                                        txtv_msg_error.setText("Déterminer si la femme est enceinte ou non S.V.P !.");
                                        Toast.makeText(this, "Déterminer si la femme est enceinte ou non S.V.P !.", Toast.LENGTH_SHORT).show();

                                    }

                                }

                            }else{

                                if(!editText_min.getText().toString().equals("") || !editText_max.getText().toString().equals("")){

                                    Double min = null;
                                    Double max = null;

                                    if(!editText_min.getText().toString().equals("")){

                                        min = Double.parseDouble(editText_min.getText().toString());

                                    }

                                    if(!editText_max.getText().toString().equals("")){

                                        max = Double.parseDouble(editText_max.getText().toString());

                                    }

                                    if (dataBase_apm.insertRegles(typeAnalyse, Double.parseDouble(editText_dose_regle.getText().toString()),
                                            editText_unite_regle.getText().toString(),
                                            min,
                                            max,
                                            null,null,medicaments.getID_Medicament())) {

                                        Toast.makeText(this, "La régle a été ajouté avec succès", Toast.LENGTH_SHORT).show();
                                        Add_Edit_Medicament_Activity.refreshRecycleView(this);
                                        finish();

                                    } else {

                                        Toast.makeText(this, "Il y a une erreur !.", Toast.LENGTH_SHORT).show();

                                    }

                                }else{

                                    txtv_msg_error.setText("Entrez au moins la valeur minimale ou maximale S.V.P !.");
                                    Toast.makeText(this, "Entrez au moins la valeur minimale ou maximale S.V.P !.", Toast.LENGTH_SHORT).show();
                                    editText_min.requestFocus(editText_min.getText().toString().length());
                                    show_keyboard(this);

                                }

                            }

                        }

                    }else{

                        txtv_msg_error.setText("Entrez l'unité S.V.P !.");
                        Toast.makeText(this, "Entrez l'unité S.V.P !.", Toast.LENGTH_SHORT).show();
                        editText_dose_regle.requestFocus(editText_dose_regle.getText().toString().length());
                        show_keyboard(this);

                    }

                }else{

                    txtv_msg_error.setText("Entrez la dose S.V.P !.");
                    Toast.makeText(this, "Entrez la dose S.V.P !.", Toast.LENGTH_SHORT).show();
                    editText_dose_regle.requestFocus(editText_dose_regle.getText().toString().length());
                    show_keyboard(this);

                }

            }else{//Save

                if(edit) {

                    if (!editText_dose_regle.getText().toString().equals("")) {

                        if (!editText_unite_regle.getText().toString().equals("")) {

                            if (typeAnalyse.equals("Sexe")) {

                                if (radio_femme_enceinte.isChecked()) {

                                    dataBase_apm.updateRegle(regles.getID_Regle(), typeAnalyse, Double.parseDouble(editText_dose_regle.getText().toString()),
                                            editText_unite_regle.getText().toString(), null,
                                            null, "Femme", null, medicaments.getID_Medicament());

                                    Toast.makeText(this, "La régle a été modifié avec succès", Toast.LENGTH_SHORT).show();
                                    Add_Edit_Medicament_Activity.refreshRecycleView(this);
                                    finish();

                                } else {

                                    if (radio_homme_non_enceinte.isChecked()) {

                                        dataBase_apm.updateRegle(regles.getID_Regle(), typeAnalyse, Double.parseDouble(editText_dose_regle.getText().toString()),
                                                editText_unite_regle.getText().toString(), null,
                                                null, "Homme", null, medicaments.getID_Medicament());

                                        Toast.makeText(this, "La régle a été modifié avec succès", Toast.LENGTH_SHORT).show();
                                        Add_Edit_Medicament_Activity.refreshRecycleView(this);
                                        finish();

                                    } else {

                                        txtv_msg_error.setText("Sélectionnez le sexe S.V.P !.");
                                        Toast.makeText(this, "Sélectionnez le sexe S.V.P !.", Toast.LENGTH_SHORT).show();

                                    }

                                }

                            } else {

                                if (typeAnalyse.equals("Enceinte")) {

                                    if (radio_femme_enceinte.isChecked()) {

                                        dataBase_apm.updateRegle(regles.getID_Regle(), typeAnalyse, Double.parseDouble(editText_dose_regle.getText().toString()),
                                                editText_unite_regle.getText().toString(), null,
                                                null, null, 1, medicaments.getID_Medicament());

                                        Toast.makeText(this, "La régle a été modifié avec succès", Toast.LENGTH_SHORT).show();
                                        Add_Edit_Medicament_Activity.refreshRecycleView(this);
                                        finish();

                                    } else {

                                        if (radio_homme_non_enceinte.isChecked()) {

                                            dataBase_apm.updateRegle(regles.getID_Regle(), typeAnalyse, Double.parseDouble(editText_dose_regle.getText().toString()),
                                                    editText_unite_regle.getText().toString(), null,
                                                    null, null, 0, medicaments.getID_Medicament());

                                            Toast.makeText(this, "La régle a été modifié avec succès", Toast.LENGTH_SHORT).show();
                                            Add_Edit_Medicament_Activity.refreshRecycleView(this);
                                            finish();

                                        } else {

                                            txtv_msg_error.setText("Déterminer si la femme est enceinte ou non S.V.P !.");
                                            Toast.makeText(this, "Déterminer si la femme est enceinte ou non S.V.P !.", Toast.LENGTH_SHORT).show();

                                        }

                                    }

                                } else {

                                    if (!editText_min.getText().toString().equals("") || !editText_max.getText().toString().equals("")) {

                                        Double min = null;
                                        Double max = null;

                                        if(!editText_min.getText().toString().equals("")){

                                            min = Double.parseDouble(editText_min.getText().toString());

                                        }

                                        if(!editText_max.getText().toString().equals("")){

                                            max = Double.parseDouble(editText_max.getText().toString());

                                        }

                                        dataBase_apm.updateRegle(regles.getID_Regle(), typeAnalyse, Double.parseDouble(editText_dose_regle.getText().toString()),
                                                editText_unite_regle.getText().toString(),
                                                min,
                                                max,
                                                null, null, medicaments.getID_Medicament());

                                        Toast.makeText(this, "La régle a été modifié avec succès", Toast.LENGTH_SHORT).show();
                                        Add_Edit_Medicament_Activity.refreshRecycleView(this);
                                        finish();

                                    }else{

                                        txtv_msg_error.setText("Entrez au moins la valeur minimale ou maximale S.V.P !.");
                                        Toast.makeText(this, "Entrez au moins la valeur minimale ou maximale S.V.P S.V.P !.", Toast.LENGTH_SHORT).show();
                                        editText_max.requestFocus(editText_max.getText().toString().length());
                                        show_keyboard(this);

                                    }

                                }

                            }

                        } else {

                            txtv_msg_error.setText("Entrez l'unité S.V.P !.");
                            Toast.makeText(this, "Entrez l'unité S.V.P !.", Toast.LENGTH_SHORT).show();
                            editText_unite_regle.requestFocus(editText_unite_regle.getText().toString().length());
                            show_keyboard(this);

                        }

                    } else {

                        txtv_msg_error.setText("Entrez la dose S.V.P !.");
                        Toast.makeText(this, "Entrez la dose S.V.P !.", Toast.LENGTH_SHORT).show();
                        editText_dose_regle.requestFocus(editText_dose_regle.getText().toString().length());
                        show_keyboard(this);

                    }

                }else{

                    AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle).create();
                    alertDialog.setMessage("Voulez-vous supprimer cette régle ?.");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui", (dialog, which) -> {
                        dialog.dismiss();
                        dataBase_apm.deleteRegle(regles.getID_Regle());
                        Add_Edit_Medicament_Activity.refreshRecycleView(this);
                        finish();
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Non", (dialog, which) -> dialog.dismiss());
                    alertDialog.show();

                }

            }

        });

        editText_unite_regle.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                if(typeAnalyse.equals("Sexe") || typeAnalyse.equals("Enceinte")) {

                    btn_delete_save.callOnClick();

                }

            }
            return false;
        });

        editText_max.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                btn_delete_save.callOnClick();

            }
            return false;
        });

    }

    void typeUIValue(){

        if(txtv_sexe_enceinte.getVisibility()==View.VISIBLE || radio_homme_non_enceinte.getVisibility()==View.VISIBLE || radio_femme_enceinte.getVisibility()==View.VISIBLE) {

            txtv_sexe_enceinte.setVisibility(View.INVISIBLE);
            radio_homme_non_enceinte.setVisibility(View.INVISIBLE);
            radio_femme_enceinte.setVisibility(View.INVISIBLE);

        }

        if(txtv_min.getVisibility()==View.INVISIBLE || editText_min.getVisibility()==View.INVISIBLE || txtv_max.getVisibility()==View.INVISIBLE || editText_max.getVisibility()==View.INVISIBLE ) {

            txtv_min.setVisibility(View.VISIBLE);
            editText_min.setVisibility(View.VISIBLE);
            txtv_max.setVisibility(View.VISIBLE);
            editText_max.setVisibility(View.VISIBLE);

        }

        paramsAboveType.addRule(RelativeLayout.BELOW, R.id.type_regle_spinner);
        lyt_valeur_min_max.setLayoutParams(paramsAboveType);

        paramsAboveRadio.addRule(RelativeLayout.BELOW, R.id.lyt_btn);
        lyt_sexe_enceinte.setLayoutParams(paramsAboveRadio);

        paramsAboveValue.addRule(RelativeLayout.BELOW, R.id.lyt_valeur_min_max);
        paramsAboveValue.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lyt_btn.setLayoutParams(paramsAboveValue);

    }

    @SuppressLint("SetTextI18n")
    void typeUIRadio(){

        if(txtv_sexe_enceinte.getVisibility()==View.INVISIBLE || radio_homme_non_enceinte.getVisibility()==View.INVISIBLE || radio_femme_enceinte.getVisibility()==View.INVISIBLE) {

            txtv_sexe_enceinte.setVisibility(View.VISIBLE);
            radio_homme_non_enceinte.setVisibility(View.VISIBLE);
            radio_femme_enceinte.setVisibility(View.VISIBLE);

        }

        if(txtv_min.getVisibility()==View.VISIBLE || editText_min.getVisibility()==View.VISIBLE || txtv_max.getVisibility()==View.VISIBLE || editText_max.getVisibility()==View.VISIBLE ) {

            txtv_min.setVisibility(View.INVISIBLE);
            editText_min.setVisibility(View.INVISIBLE);
            txtv_max.setVisibility(View.INVISIBLE);
            editText_max.setVisibility(View.INVISIBLE);

        }

        if(typeAnalyse.equals("Sexe")){

            txtv_sexe_enceinte.setText("Sexe");
            radio_homme_non_enceinte.setText("Homme");
            radio_femme_enceinte.setText("Femme");

        }else{

            txtv_sexe_enceinte.setText("Enceinte");
            radio_homme_non_enceinte.setText("Non");
            radio_femme_enceinte.setText("Oui");

        }

        paramsAboveRadio.addRule(RelativeLayout.BELOW, R.id.lyt_sexe_enceinte);
        lyt_btn.setLayoutParams(paramsAboveRadio);

        paramsAboveType.addRule(RelativeLayout.BELOW, R.id.type_regle_spinner);
        lyt_sexe_enceinte.setLayoutParams(paramsAboveType);

    }

    void refreshUI(){

        switch (typeAnalyse){

            case "Sexe":
            case "Enceinte":
                typeUIRadio();
                break;
            default:
                typeUIValue();
                break;

        }

    }

    public static void hide_keyboard(Activity activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();

        if (view == null) {

            view = new View(activity);

        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }


    public static void show_keyboard(Activity activity){

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);

    }

    @Override
    public void onBackPressed() {

        if(add){

            AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle).create();
            alertDialog.setMessage("Voulez-vous annuler l'insertion ?.");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui", (dialog, which) -> {
                dialog.dismiss();
                finish();
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Non", (dialog, which) -> dialog.dismiss());
            alertDialog.show();

        }else{

            if(edit){

                AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle).create();
                alertDialog.setMessage("Voulez-vous annuler la modification ?.");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui", (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Non", (dialog, which) -> dialog.dismiss());
                alertDialog.show();

            }else{

                super.onBackPressed();

            }

        }

    }

}