package com.fsciencesunivsetifdz.posologiemdicaments;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Add_Edit_Patient_Activity extends AppCompatActivity {

    static boolean add;
    static boolean edit;

    TextView txtvtitle_toolbar;
    TextView txtv_msg_error;

    Button btn_edit_cancel;
    Button btn_delete_save;

    EditText editText_id;
    EditText editText_first_name;
    EditText editText_last_name;
    EditText editText_age;
    EditText editText_poid;

    RadioButton radio_homme;
    RadioButton radio_femme;
    RadioButton radio_enceint_oui;
    RadioButton radio_enceint_non;

    static Patients patients;

    DataBase_SQLite dataBase_apm = new DataBase_SQLite(this);

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_patient);

        txtvtitle_toolbar = findViewById(R.id.txtvtitle_toolbar);
        txtv_msg_error = findViewById(R.id.txtv_msg_error);

        btn_edit_cancel = findViewById(R.id.btn_edit_cancel);
        btn_delete_save = findViewById(R.id.btn_delete_save);

        txtv_msg_error.setText(null);

        editText_id = findViewById(R.id.editText_id);
        editText_first_name = findViewById(R.id.editText_first_name);
        editText_last_name = findViewById(R.id.editText_last_name);
        editText_age = findViewById(R.id.editText_age);
        editText_poid = findViewById(R.id.editText_poid);

        radio_homme = findViewById(R.id.radio_homme);
        radio_femme = findViewById(R.id.radio_femme);
        radio_enceint_oui = findViewById(R.id.radio_enceint_oui);
        radio_enceint_non = findViewById(R.id.radio_enceint_non);

        if(add) {

            txtvtitle_toolbar.setText("Ajouter un patient");
            btn_delete_save.setText("Sauvegarder");
            btn_delete_save.setBackgroundResource(R.drawable.bg_btn_add);
            btn_edit_cancel.setText("Annuler");
            btn_edit_cancel.setBackgroundResource(R.drawable.bg_btn_abt);

            radio_homme.setChecked(true);
            radio_femme.setChecked(false);
            radio_homme.setEnabled(true);
            radio_femme.setEnabled(true);
            radio_enceint_oui.setEnabled(false);
            radio_enceint_non.setEnabled(false);

            editText_first_name.setEnabled(true);
            editText_last_name.setEnabled(true);
            editText_age.setEnabled(true);
            editText_poid.setEnabled(true);

            editText_first_name.requestFocus(editText_first_name.getText().toString().length());
            show_keyboard(this);

        }else{

            txtvtitle_toolbar.setText("Informations patient");
            btn_delete_save.setText("Supprimer");
            btn_delete_save.setBackgroundResource(R.drawable.bg_btn_abt);
            btn_edit_cancel.setText("Modifier");
            btn_edit_cancel.setBackgroundResource(R.drawable.bg_btn_add);

            if(patients.getSexe().equals("Homme")){

                radio_homme.setChecked(true);
                radio_femme.setChecked(false);

            }else{

                radio_homme.setChecked(false);
                radio_femme.setChecked(true);

                if(patients.getEnceinte()==0){

                    radio_enceint_non.setChecked(true);
                    radio_enceint_oui.setChecked(false);

                }else{

                    radio_enceint_non.setChecked(false);
                    radio_enceint_oui.setChecked(true);

                }

            }

            radio_homme.setEnabled(false);
            radio_femme.setEnabled(false);

            radio_enceint_oui.setEnabled(false);
            radio_enceint_non.setEnabled(false);

            editText_id.setText(Integer.toString(patients.getID_Patient()));
            editText_first_name.setText(patients.getNom_Patient());
            editText_last_name.setText(patients.getPrenom_Patient());
            editText_age.setText(Integer.toString(patients.getAge()));
            editText_poid.setText(Double.toString(patients.getPoid()));

            editText_first_name.setEnabled(false);
            editText_last_name.setEnabled(false);
            editText_age.setEnabled(false);
            editText_poid.setEnabled(false);

        }

        btn_edit_cancel.setOnClickListener(view -> {

            if(add){//Cancel

                if(!edit) {

                    editText_first_name.setText(null);
                    editText_last_name.setText(null);
                    editText_age.setText(null);
                    editText_poid.setText(null);

                    finish();

                }else{

                    txtvtitle_toolbar.setText("Informations patient");
                    btn_delete_save.setText("Supprimer");
                    btn_delete_save.setBackgroundResource(R.drawable.bg_btn_abt);
                    btn_edit_cancel.setText("Modifier");
                    btn_edit_cancel.setBackgroundResource(R.drawable.bg_btn_add);

                    if(patients.getSexe().equals("Homme")){

                        radio_homme.setChecked(true);
                        radio_femme.setChecked(false);

                    }else{

                        radio_homme.setChecked(false);
                        radio_femme.setChecked(true);

                        if(patients.getEnceinte()==0){

                            radio_enceint_non.setChecked(true);
                            radio_enceint_oui.setChecked(false);

                        }else{

                            radio_enceint_non.setChecked(false);
                            radio_enceint_oui.setChecked(true);

                        }

                    }

                    radio_homme.setEnabled(false);
                    radio_femme.setEnabled(false);

                    radio_enceint_oui.setEnabled(false);
                    radio_enceint_non.setEnabled(false);

                    editText_id.setText(Integer.toString(patients.getID_Patient()));
                    editText_first_name.setText(patients.getNom_Patient());
                    editText_last_name.setText(patients.getPrenom_Patient());
                    editText_age.setText(Integer.toString(patients.getAge()));
                    editText_poid.setText(Double.toString(patients.getPoid()));

                    editText_first_name.setEnabled(false);
                    editText_last_name.setEnabled(false);
                    editText_age.setEnabled(false);
                    editText_poid.setEnabled(false);

                }

            }else{//edit

                if(!edit) {

                    edit = true;

                    editText_first_name.setEnabled(true);
                    editText_last_name.setEnabled(true);
                    editText_age.setEnabled(true);
                    editText_poid.setEnabled(true);

                    radio_homme.setEnabled(true);
                    radio_femme.setEnabled(true);

                    if(radio_femme.isChecked()) {

                        radio_enceint_oui.setEnabled(true);
                        radio_enceint_non.setEnabled(true);

                    }else {

                        radio_enceint_oui.setEnabled(false);
                        radio_enceint_non.setEnabled(false);

                    }


                    txtvtitle_toolbar.setText("Modification du patient");
                    btn_delete_save.setText("Sauvegarder");
                    btn_delete_save.setBackgroundResource(R.drawable.bg_btn_add);
                    btn_edit_cancel.setText("Annuler");
                    btn_edit_cancel.setBackgroundResource(R.drawable.bg_btn_abt);

                    editText_first_name.requestFocus(editText_first_name.getText().toString().length());
                    show_keyboard(this);

                }else{

                    edit = false;

                    txtvtitle_toolbar.setText("Informations patient");
                    btn_delete_save.setText("Supprimer");
                    btn_delete_save.setBackgroundResource(R.drawable.bg_btn_abt);
                    btn_edit_cancel.setText("Modifier");
                    btn_edit_cancel.setBackgroundResource(R.drawable.bg_btn_add);

                    if(patients.getSexe().equals("Homme")){

                        radio_homme.setChecked(true);
                        radio_femme.setChecked(false);

                    }else{

                        radio_homme.setChecked(false);
                        radio_femme.setChecked(true);

                        if(patients.getEnceinte()==0){

                            radio_enceint_non.setChecked(true);
                            radio_enceint_oui.setChecked(false);

                        }else{

                            radio_enceint_non.setChecked(false);
                            radio_enceint_oui.setChecked(true);

                        }

                    }

                    radio_homme.setEnabled(false);
                    radio_femme.setEnabled(false);

                    radio_enceint_oui.setEnabled(false);
                    radio_enceint_non.setEnabled(false);

                    editText_id.setText(Integer.toString(patients.getID_Patient()));
                    editText_first_name.setText(patients.getNom_Patient());
                    editText_last_name.setText(patients.getPrenom_Patient());
                    editText_age.setText(Integer.toString(patients.getAge()));
                    editText_poid.setText(Double.toString(patients.getPoid()));

                    editText_first_name.setEnabled(false);
                    editText_last_name.setEnabled(false);
                    editText_age.setEnabled(false);
                    editText_poid.setEnabled(false);

                }

            }

        });

        btn_delete_save.setOnClickListener(view -> {

            hide_keyboard(this);

            int age;
            double poid;

            if(add){//Save

                if(!editText_first_name.getText().toString().equals("")){

                    if(!editText_last_name.getText().toString().equals("")){

                        if(!editText_age.getText().toString().equals("")){

                            age = Integer.parseInt(editText_age.getText().toString());

                            if(age>=0 && age<=120){

                                if(!editText_poid.getText().toString().equals("")){

                                    poid = Double.parseDouble(editText_poid.getText().toString());

                                    if(poid>=0 && poid<=500){

                                        String sexe;
                                        int enceinte;

                                        if(radio_homme.isChecked()){

                                            sexe = "Homme";
                                            enceinte = 0;

                                        }else{

                                            sexe = "Femme";
                                            if(radio_enceint_oui.isChecked()){

                                                enceinte = 1;

                                            }else{

                                                enceinte = 0;

                                            }

                                        }

                                        if(dataBase_apm.insertPatients(editText_first_name.getText().toString(),editText_last_name.getText().toString(),sexe,enceinte,age,poid)){

                                            Toast.makeText(this, "Le patient a été ajouté avec succès", Toast.LENGTH_SHORT).show();
                                            Patient_Activity.refreshRecycleView(this);
                                            finish();

                                        }else{

                                            Toast.makeText(this, "Il y a une erreur !.", Toast.LENGTH_SHORT).show();

                                        }

                                    }else{

                                        txtv_msg_error.setText("Le poids que vous avez entré n'est pas logique !.");
                                        Toast.makeText(this, "Le poids que vous avez entré n'est pas logique !.", Toast.LENGTH_SHORT).show();
                                        editText_poid.requestFocus(editText_poid.getText().toString().length());
                                        show_keyboard(this);

                                    }

                                }else {

                                    txtv_msg_error.setText("Entrez le poids S.V.P !.");
                                    Toast.makeText(this, "Entrez le poids S.V.P !.", Toast.LENGTH_SHORT).show();
                                    editText_poid.requestFocus(editText_poid.getText().toString().length());
                                    show_keyboard(this);

                                }

                            }else{

                                txtv_msg_error.setText("L'âge que vous avez entré n'est pas logique !.");
                                Toast.makeText(this, "L'âge que vous avez entré n'est pas logique !.", Toast.LENGTH_SHORT).show();
                                editText_age.requestFocus(editText_age.getText().toString().length());
                                show_keyboard(this);

                            }

                        }else {

                            txtv_msg_error.setText("Entrez l'âge S.V.P !.");
                            Toast.makeText(this, "Entrez l'âge S.V.P !.", Toast.LENGTH_SHORT).show();
                            editText_age.requestFocus(editText_age.getText().toString().length());
                            show_keyboard(this);

                        }

                    }else {

                        txtv_msg_error.setText("Entrez le prénom S.V.P !.");
                        Toast.makeText(this, "Entrez le prénom S.V.P !.", Toast.LENGTH_SHORT).show();
                        editText_last_name.requestFocus(editText_last_name.getText().toString().length());
                        show_keyboard(this);

                    }

                }else {

                    txtv_msg_error.setText("Entrez le nom S.V.P !.");
                    Toast.makeText(this, "Entrez le nom S.V.P !.", Toast.LENGTH_SHORT).show();
                    editText_first_name.requestFocus(editText_first_name.getText().toString().length());
                    show_keyboard(this);

                }

            }else{

                if(edit){//edit

                    if(!editText_first_name.getText().toString().equals("")){

                        if(!editText_last_name.getText().toString().equals("")){

                            if(!editText_age.getText().toString().equals("")){

                                age = Integer.parseInt(editText_age.getText().toString());

                                if(age>=0 && age<=120){

                                    if(!editText_poid.getText().toString().equals("")){

                                        poid = Double.parseDouble(editText_poid.getText().toString());

                                        if(poid>=0 && poid<=500){

                                            String sexe;
                                            int enceinte;

                                            if(radio_homme.isChecked()){

                                                sexe = "Homme";
                                                enceinte = 0;

                                            }else{

                                                sexe = "Femme";
                                                if(radio_enceint_oui.isChecked()){

                                                    enceinte = 1;

                                                }else{

                                                    enceinte = 0;

                                                }

                                            }

                                            dataBase_apm.updatePatient(Integer.parseInt(editText_id.getText().toString()),editText_first_name.getText().toString(),editText_last_name.getText().toString(),sexe,enceinte,age,poid);

                                            Toast.makeText(this, "Le patient a été modifié avec succès", Toast.LENGTH_SHORT).show();
                                            Patient_Activity.refreshRecycleView(this);
                                            finish();



                                        }else{

                                            txtv_msg_error.setText("Le poids que vous avez entré n'est pas logique !.");
                                            Toast.makeText(this, "Le poids que vous avez entré n'est pas logique !.", Toast.LENGTH_SHORT).show();
                                            editText_poid.requestFocus(editText_poid.getText().toString().length());
                                            show_keyboard(this);

                                        }

                                    }else {

                                        txtv_msg_error.setText("Entrez le poids S.V.P !.");
                                        Toast.makeText(this, "Entrez le poids S.V.P !.", Toast.LENGTH_SHORT).show();
                                        editText_poid.requestFocus(editText_poid.getText().toString().length());
                                        show_keyboard(this);

                                    }

                                }else{

                                    txtv_msg_error.setText("L'âge que vous avez entré n'est pas logique !.");
                                    Toast.makeText(this, "L'âge que vous avez entré n'est pas logique !.", Toast.LENGTH_SHORT).show();
                                    editText_age.requestFocus(editText_age.getText().toString().length());
                                    show_keyboard(this);

                                }

                            }else {

                                txtv_msg_error.setText("Entrez l'âge S.V.P !.");
                                Toast.makeText(this, "Entrez l'âge S.V.P !.", Toast.LENGTH_SHORT).show();
                                editText_age.requestFocus(editText_age.getText().toString().length());
                                show_keyboard(this);

                            }

                        }else {

                            txtv_msg_error.setText("Entrez le prénom S.V.P !.");
                            Toast.makeText(this, "Entrez le prénom S.V.P !.", Toast.LENGTH_SHORT).show();
                            editText_last_name.requestFocus(editText_last_name.getText().toString().length());
                            show_keyboard(this);

                        }

                    }else {

                        txtv_msg_error.setText("Entrez le nom S.V.P !.");
                        Toast.makeText(this, "Entrez le nom S.V.P !.", Toast.LENGTH_SHORT).show();
                        editText_first_name.requestFocus(editText_first_name.getText().toString().length());
                        show_keyboard(this);

                    }

                }else{//delete

                        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle).create();
                        alertDialog.setMessage("Voulez-vous supprimer ce patient ?.\nAttention : Cette action supprimera toutes les ordonnances de ce patient !.");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui", (dialog, which) -> {
                            dialog.dismiss();
                            dataBase_apm.deleteOrdonnancePatient(patients.getID_Patient());
                            dataBase_apm.deletePatient(patients.getID_Patient());
                            Patient_Activity.refreshRecycleView(this);
                            finish();
                        });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Non", (dialog, which) -> dialog.dismiss());
                        alertDialog.show();

                }

            }

        });

        editText_poid.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                btn_delete_save.callOnClick();

            }
            return false;
        });

        radio_homme.setOnCheckedChangeListener((compoundButton, b) -> {

            if (radio_homme.isChecked()) {

                radio_femme.setChecked(false);

                radio_enceint_oui.setEnabled(false);
                radio_enceint_non.setEnabled(false);

                radio_enceint_oui.setChecked(false);
                radio_enceint_non.setChecked(false);

            }

        });

        radio_femme.setOnCheckedChangeListener((compoundButton, b) -> {

            if (radio_femme.isChecked()) {

                radio_homme.setChecked(false);

                if(add || edit){

                    radio_enceint_oui.setEnabled(true);
                    radio_enceint_non.setEnabled(true);

                    radio_enceint_non.setChecked(true);
                    radio_enceint_oui.setChecked(false);

                }

            }

        });

        radio_enceint_oui.setOnCheckedChangeListener((compoundButton, b) -> {

            if (radio_enceint_oui.isChecked()) {

                radio_enceint_non.setChecked(false);

            }

        });

        radio_enceint_non.setOnCheckedChangeListener((compoundButton, b) -> {

            if (radio_enceint_non.isChecked()) {

                radio_enceint_oui.setChecked(false);

            }

        });

        editText_last_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(getCurrentFocus().getId() != editText_last_name.getId()){

                    try {

                        editText_age.requestFocus(editText_age.getText().toString().length());

                    }catch(Exception e){

                        e.printStackTrace();

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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