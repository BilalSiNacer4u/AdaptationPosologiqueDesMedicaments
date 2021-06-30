package com.fsciencesunivsetifdz.posologiemdicaments;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Bilan_Activity extends AppCompatActivity {

    TextView txtv_msg_error;

    @SuppressLint("StaticFieldLeak")
    static EditText editText_patient;
    EditText editText_clairance;
    EditText editText_bilirubine;
    EditText editText_tgotgp;

    Button btn_select_patient;
    Button btn_suivant;

    static Patients patients;

    static Medecines medecines;

    DataBase_SQLite dataBase_apm = new DataBase_SQLite(this);

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilan);

        patients = null;

        txtv_msg_error = findViewById(R.id.txtv_msg_error);

        editText_patient = findViewById(R.id.editText_patient);
        editText_clairance = findViewById(R.id.editText_clairance);
        editText_bilirubine = findViewById(R.id.editText_bilirubine);
        editText_tgotgp = findViewById(R.id.editText_tgotgp);

        editText_patient.setEnabled(false);
        editText_patient.setText(null);

        btn_select_patient = findViewById(R.id.btn_select_patient);
        btn_suivant = findViewById(R.id.btn_suivant);

        txtv_msg_error.setText(null);

        btn_select_patient.setOnClickListener(v -> {

            Intent intent = new Intent(this,Liste_Patients_Activity.class);
            startActivity(intent);

        });

        btn_suivant.setOnClickListener(v -> {

            if(patients!=null){

                if(!editText_clairance.getText().toString().equals("")){

                    if(!editText_bilirubine.getText().toString().equals("")){

                        if(!editText_tgotgp.getText().toString().equals("")){

                            AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle).create();
                            alertDialog.setMessage("Êtes-vous sûr de ces informations, Il ne peut plus être modifié ?.");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui", (dialog, which) -> {
                                dialog.dismiss();

                                if(dataBase_apm.insertOrdonnance(MainActivity.date,patients.getID_Patient(),medecines.getCompte())){

                                    Toast.makeText(this, "l'ordonnance a été ajouté avec succès", Toast.LENGTH_SHORT).show();

                                    Medicaments_Ordonnance_Activity.ordonnances = dataBase_apm.getLastOrdonnance();

                                    Medicaments_Ordonnance_Activity.clairance = Double.parseDouble(editText_clairance.getText().toString());
                                    Medicaments_Ordonnance_Activity.bilirubine = Double.parseDouble(editText_bilirubine.getText().toString());
                                    Medicaments_Ordonnance_Activity.tgo_tgp = Double.parseDouble(editText_tgotgp.getText().toString());

                                    Intent intent = new Intent(this, Medicaments_Ordonnance_Activity.class);
                                    startActivity(intent);
                                    finish();
                                    
                                }else{
                                    
                                    
                                    
                                }
                                

                            });
                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Non", (dialog, which) -> dialog.dismiss());
                            alertDialog.show();

                        }else{

                            txtv_msg_error.setText("Entrez la valeur de Tgo/tgp S.V.P !.");
                            Toast.makeText(this, "Entrez la valeur de Tgo/tgp S.V.P !.", Toast.LENGTH_SHORT).show();
                            editText_tgotgp.requestFocus(editText_tgotgp.getText().toString().length());
                            show_keyboard(this);

                        }

                    }else{

                        txtv_msg_error.setText("Entrez la valeur de la bilirubine S.V.P !.");
                        Toast.makeText(this, "Entrez la valeur de la bilirubine S.V.P !.", Toast.LENGTH_SHORT).show();
                        editText_bilirubine.requestFocus(editText_bilirubine.getText().toString().length());
                        show_keyboard(this);

                    }

                }else{

                    txtv_msg_error.setText("Entrez la valeur de la clairance rénale S.V.P !.");
                    Toast.makeText(this, "Entrez la valeur de la clairance rénale S.V.P !.", Toast.LENGTH_SHORT).show();
                    editText_clairance.requestFocus(editText_clairance.getText().toString().length());
                    show_keyboard(this);

                }

            }else{

                txtv_msg_error.setText("Choisissez le patient avant de passer à l'étape suivante !.");
                Toast.makeText(this, "Choisissez le patient avant de passer à l'étape suivante !.", Toast.LENGTH_SHORT).show();

            }

        });

        editText_tgotgp.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                btn_suivant.callOnClick();

            }
            return false;
        });
    }

    public static void show_keyboard(Activity activity){

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);

    }

    @Override
    public void onBackPressed() {

        if(patients!=null || !editText_clairance.getText().toString().equals("") || !editText_bilirubine.getText().toString().equals("") ||!editText_tgotgp.getText().toString().equals("")){

            AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle).create();
            alertDialog.setMessage("Voulez-vous annuler cette opération ?.");
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