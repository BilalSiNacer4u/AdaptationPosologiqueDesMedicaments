package com.fsciencesunivsetifdz.posologiemdicaments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Profil_Activity extends AppCompatActivity {

    static Medecines medecines;

    boolean edit;

    EditText editText_compte;
    EditText editText_first_name;
    EditText editText_last_name;
    EditText editText_password;
    EditText editText_confirm_password_profil;

    CheckBox checkbox_show_password;

    TextView txtv_msg_error;
    TextView txtv_password;
    TextView txtv_confirm_password;

    Button btn_edit_cancel;
    Button btn_back_save;

    LinearLayout lyt_btn;
    RelativeLayout.LayoutParams params;

    DataBase_SQLite dataBase_apm = new DataBase_SQLite(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        editText_compte  = findViewById(R.id.editText_compte);
        editText_first_name = findViewById(R.id.editText_first_name);
        editText_last_name = findViewById(R.id.editText_last_name);
        editText_password = findViewById(R.id.editText_password);
        editText_confirm_password_profil = findViewById(R.id.editText_confirm_password_profil);

        checkbox_show_password = findViewById(R.id.checkbox_show_password);

        txtv_msg_error = findViewById(R.id.txtv_msg_error);
        txtv_password = findViewById(R.id.txtv_password);
        txtv_confirm_password = findViewById(R.id.txtv_confirm_password);

        btn_edit_cancel = findViewById(R.id.btn_edit_cancel);
        btn_back_save = findViewById(R.id.btn_back_save);

        lyt_btn = findViewById(R.id.lyt_btn);
        params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        editText_compte.setEnabled(false);
        editText_first_name.setEnabled(false);
        editText_last_name.setEnabled(false);
        editText_password.setEnabled(false);
        editText_confirm_password_profil.setEnabled(false);

        edit = false;

        btn_edit_cancel.setText("Modifier");

        btn_back_save.setText("Retour");

        checkbox_show_password.setChecked(false);

        params.addRule(RelativeLayout.BELOW, R.id.editText_last_name);
        lyt_btn.setLayoutParams(params);

        editText_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editText_password.setTypeface(Typeface.DEFAULT_BOLD);
        editText_confirm_password_profil.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editText_confirm_password_profil.setTypeface(Typeface.DEFAULT_BOLD);

        editText_compte.setText(medecines.getCompte());
        editText_first_name.setText(medecines.getNom_Medecine());
        editText_last_name.setText(medecines.getPrenom_Medecine());
        editText_password.setText(medecines.getMot_De_Passe());
        editText_confirm_password_profil.setText(medecines.getMot_De_Passe());

        txtv_msg_error.setText(null);

        editText_password.setVisibility(View.INVISIBLE);
        editText_confirm_password_profil.setVisibility(View.INVISIBLE);

        txtv_password.setVisibility(View.INVISIBLE);
        txtv_confirm_password.setVisibility(View.INVISIBLE);
        checkbox_show_password.setVisibility(View.INVISIBLE);

        checkbox_show_password.setOnCheckedChangeListener((compoundButton, b) -> {

            int charFocus = -1;

            try {

                if (this.getCurrentFocus().getId() == editText_password.getId()) {

                    charFocus = editText_password.getSelectionEnd();

                } else {

                    if (this.getCurrentFocus().getId() == editText_confirm_password_profil.getId()) {

                        charFocus = editText_confirm_password_profil.getSelectionEnd();

                    }

                }

            }catch(Exception e){

                e.printStackTrace();

            }

            if (checkbox_show_password.isChecked()) {

                editText_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                editText_password.setTypeface(Typeface.DEFAULT_BOLD);
                editText_confirm_password_profil.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

            }else{

                editText_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText_password.setTypeface(Typeface.DEFAULT_BOLD);
                editText_confirm_password_profil.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            }
            editText_confirm_password_profil.setTypeface(Typeface.DEFAULT_BOLD);

            if(charFocus!=-1){

                try {

                    if (this.getCurrentFocus().getId() == editText_password.getId()) {

                        editText_password.setSelection(charFocus);

                    } else {

                        if (this.getCurrentFocus().getId() == editText_confirm_password_profil.getId()) {

                            editText_confirm_password_profil.setSelection(charFocus);

                        }

                    }

                }catch(Exception e){

                    e.printStackTrace();

                }

            }
        });

        btn_edit_cancel.setOnClickListener(view -> {

            if(edit){//Modifier -> Annuler

                params.addRule(RelativeLayout.BELOW, R.id.editText_last_name);
                lyt_btn.setLayoutParams(params);

                editText_compte.setEnabled(false);
                editText_first_name.setEnabled(false);
                editText_last_name.setEnabled(false);
                editText_password.setEnabled(false);
                editText_confirm_password_profil.setEnabled(false);

                edit = false;

                btn_edit_cancel.setText("Modifier");

                btn_back_save.setText("Retour");

                editText_compte.setText(medecines.getCompte());
                editText_first_name.setText(medecines.getNom_Medecine());
                editText_last_name.setText(medecines.getPrenom_Medecine());
                editText_password.setText(medecines.getMot_De_Passe());
                editText_confirm_password_profil.setText(medecines.getMot_De_Passe());

                txtv_msg_error.setText(null);

                editText_password.setVisibility(View.INVISIBLE);
                editText_confirm_password_profil.setVisibility(View.INVISIBLE);

                txtv_password.setVisibility(View.INVISIBLE);
                txtv_confirm_password.setVisibility(View.INVISIBLE);
                checkbox_show_password.setVisibility(View.INVISIBLE);

                checkbox_show_password.setChecked(false);

                editText_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText_password.setTypeface(Typeface.DEFAULT_BOLD);
                editText_confirm_password_profil.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText_confirm_password_profil.setTypeface(Typeface.DEFAULT_BOLD);

                editText_first_name.requestFocus(editText_first_name.getText().toString().length());


            }else{//Annuler -> Modifier

                params.addRule(RelativeLayout.BELOW, R.id.txtv_msg_error);
                lyt_btn.setLayoutParams(params);

                editText_first_name.setEnabled(true);
                editText_last_name.setEnabled(true);
                editText_password.setEnabled(true);
                editText_confirm_password_profil.setEnabled(true);

                edit = true;

                btn_edit_cancel.setText("Annuler");

                btn_back_save.setText("Sauvegarder");

                txtv_msg_error.setText(null);

                editText_password.setVisibility(View.VISIBLE);
                editText_confirm_password_profil.setVisibility(View.VISIBLE);

                txtv_password.setVisibility(View.VISIBLE);
                txtv_confirm_password.setVisibility(View.VISIBLE);
                checkbox_show_password.setVisibility(View.VISIBLE);

                editText_compte.setText(medecines.getCompte());
                editText_first_name.setText(medecines.getNom_Medecine());
                editText_last_name.setText(medecines.getPrenom_Medecine());
                editText_password.setText(medecines.getMot_De_Passe());
                editText_confirm_password_profil.setText(medecines.getMot_De_Passe());

                checkbox_show_password.setChecked(false);

                editText_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText_password.setTypeface(Typeface.DEFAULT_BOLD);
                editText_confirm_password_profil.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText_confirm_password_profil.setTypeface(Typeface.DEFAULT_BOLD);

                editText_first_name.requestFocus(editText_first_name.getText().toString().length());

            }

        });

        btn_back_save.setOnClickListener(view -> {

            if(edit){

                hide_keyboard(this);

                if(!editText_first_name.getText().toString().equals("")) {

                    if (!editText_last_name.getText().toString().equals("")) {

                        if (!editText_password.getText().toString().equals("")) {

                            if (editText_password.getText().length()>=8) {

                                if (!editText_confirm_password_profil.getText().toString().equals("")) {

                                    if (editText_confirm_password_profil.getText().length()>=8) {

                                        if (editText_password.getText().toString().equals(editText_confirm_password_profil.getText().toString())) {

                                            try{

                                            dataBase_apm.upDateMedecine(medecines.getCompte(),editText_first_name.getText().toString(),editText_last_name.getText().toString(),editText_password.getText().toString());

                                                MainActivity.medecine = dataBase_apm.getMedecine(medecines.getCompte());

                                                MainActivity.refreshInformationAccount();

                                                txtv_msg_error.setText(null);
                                                checkbox_show_password.setChecked(false);

                                                editText_compte.setEnabled(false);
                                                editText_first_name.setEnabled(false);
                                                editText_last_name.setEnabled(false);
                                                editText_password.setEnabled(false);
                                                editText_confirm_password_profil.setEnabled(false);

                                                edit = false;

                                                btn_edit_cancel.setText("Modifier");

                                                btn_back_save.setText("Retour");

                                                checkbox_show_password.setChecked(false);

                                                editText_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                                editText_password.setTypeface(Typeface.DEFAULT_BOLD);
                                                editText_confirm_password_profil.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                                editText_confirm_password_profil.setTypeface(Typeface.DEFAULT_BOLD);

                                                editText_compte.setText(null);
                                                editText_first_name.setText(null);
                                                editText_last_name.setText(null);
                                                editText_password.setText(null);
                                                editText_confirm_password_profil.setText(null);

                                                Toast.makeText(this, "Votre compte a été modifié avec succès", Toast.LENGTH_SHORT).show();

                                                finish();

                                                try{

                                                    Login_Activity.activity.finish();

                                                }catch (Exception e){

                                                    e.printStackTrace();

                                                }

                                            }catch (Exception e){

                                                txtv_msg_error.setText("Erreur : Le compte n'a pas été modifié !.");

                                            }

                                        }else{

                                            txtv_msg_error.setText("Le mot de passe ne correspond pas !.");
                                            Toast.makeText(this, "Le mot de passe ne correspond pas !.", Toast.LENGTH_SHORT).show();
                                            editText_password.requestFocus(editText_password.getText().toString().length());
                                            show_keyboard(this);

                                        }

                                    }else{

                                        txtv_msg_error.setText("Entrez au moins 8 caractères pour vérifier votre mot de passe !.");
                                        Toast.makeText(this, "Entrez au moins 8 caractères pour vérifier votre mot de passe !.", Toast.LENGTH_SHORT).show();
                                        editText_confirm_password_profil.requestFocus(editText_confirm_password_profil.getText().toString().length());
                                        show_keyboard(this);

                                    }

                                }else{

                                    txtv_msg_error.setText("Entrez le mot de passe pour le vérifier S.V.P !.");
                                    Toast.makeText(this, "Entrez le mot de passe pour le vérifier S.V.P !.", Toast.LENGTH_SHORT).show();
                                    editText_confirm_password_profil.requestFocus(editText_confirm_password_profil.getText().toString().length());
                                    show_keyboard(this);

                                }

                            }else{

                                txtv_msg_error.setText("Entrez au moins 8 caractères pour le mot de passe !.");
                                Toast.makeText(this, "Entrez au moins 8 caractères pour le mot de passe !.", Toast.LENGTH_SHORT).show();
                                editText_password.requestFocus(editText_password.getText().toString().length());
                                show_keyboard(this);

                            }

                        }else{

                            txtv_msg_error.setText("Entrer le mot de passe S.V.P !.");
                            Toast.makeText(this, "Entrer le mot de passe S.V.P !.", Toast.LENGTH_SHORT).show();
                            editText_password.requestFocus(editText_password.getText().toString().length());
                            show_keyboard(this);

                        }

                    } else {

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

                params.addRule(RelativeLayout.BELOW, R.id.editText_last_name);
                lyt_btn.setLayoutParams(params);

                finish();

            }

        });

        editText_confirm_password_profil.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                btn_back_save.callOnClick();

            }
            return false;
        });

        editText_confirm_password_profil.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                try {

                    if(getCurrentFocus().getId() != editText_confirm_password_profil.getId()) {

                        editText_confirm_password_profil.requestFocus(editText_confirm_password_profil.getText().toString().length());

                    }

                }catch(Exception e){

                    e.printStackTrace();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText_last_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                try {

                    if(getCurrentFocus().getId() != editText_password.getId()) {

                        editText_password.requestFocus(editText_password.getText().toString().length());

                    }

                }catch(Exception e){

                    e.printStackTrace();

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