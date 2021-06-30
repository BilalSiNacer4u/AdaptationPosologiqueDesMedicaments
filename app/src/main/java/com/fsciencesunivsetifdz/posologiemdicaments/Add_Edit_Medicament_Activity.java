package com.fsciencesunivsetifdz.posologiemdicaments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Add_Edit_Medicament_Activity extends AppCompatActivity {

    static boolean add;
    static boolean edit;

    static Medicaments medicaments;

    TextView txtvtitle_toolbar;
    TextView txtv_msg_error;

    EditText editText_id_medicament;
    EditText editText_designation;
    Button btn_edit_cancel;
    Button btn_delete_save;

    FloatingActionButton btn_add_regle;

    static List<Object> list = new ArrayList<>();

    static RecyclerView recyclerView;

    static AdapterRegles adapterRegles;

    DataBase_SQLite dataBase_apm = new DataBase_SQLite(this);

    LinearLayout lyt_title;

    TextView title;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_medicament);

        editText_id_medicament = findViewById(R.id.editText_id_medicament);
        editText_designation = findViewById(R.id.editText_designation);

        txtvtitle_toolbar = findViewById(R.id.txtvtitle_toolbar);

        btn_edit_cancel = findViewById(R.id.btn_edit_cancel);
        btn_delete_save = findViewById(R.id.btn_delete_save);
        btn_add_regle = findViewById(R.id.btn_add_regle);
        txtv_msg_error = findViewById(R.id.txtv_msg_error);

        lyt_title = findViewById(R.id.lyt_title);

        title = findViewById(R.id.title);
        recyclerView = findViewById(R.id.regles);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshRecycleView(this);

        txtv_msg_error.setText(null);

        edit = false;

        if(add){

            txtvtitle_toolbar.setText("Ajouter un médicament");
            btn_delete_save.setText("Sauvegarder");
            btn_delete_save.setBackgroundResource(R.drawable.bg_btn_add);
            btn_edit_cancel.setText("Annuler");
            btn_edit_cancel.setBackgroundResource(R.drawable.bg_btn_abt);

            lyt_title.setVisibility(View.INVISIBLE);

            btn_add_regle.setVisibility(View.INVISIBLE);

            title.setVisibility(View.INVISIBLE);

            editText_id_medicament.setText(null);
            editText_designation.setText(null);

            editText_id_medicament.setEnabled(true);
            editText_designation.setEnabled(true);

            editText_id_medicament.requestFocus(editText_id_medicament.getText().toString().length());
            show_keyboard(this);

        }else{

            txtvtitle_toolbar.setText("Informations médicament");
            btn_delete_save.setText("Supprimer");
            btn_delete_save.setBackgroundResource(R.drawable.bg_btn_abt);
            btn_edit_cancel.setText("Modifier");
            btn_edit_cancel.setBackgroundResource(R.drawable.bg_btn_add);

            lyt_title.setVisibility(View.VISIBLE);

            btn_add_regle.setVisibility(View.VISIBLE);

            title.setVisibility(View.VISIBLE);

            editText_id_medicament.setText(medicaments.getID_Medicament());
            editText_designation.setText(medicaments.getDesignation());

            editText_id_medicament.setEnabled(false);
            editText_designation.setEnabled(false);

        }

        btn_add_regle.setOnClickListener(v -> {

            if(add || edit){

                AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle).create();
                alertDialog.setMessage("Vous ne pouvez pas ajouter de règles tant que vous n'avez pas enregistré le médicament ?.");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", (dialog, which) -> dialog.dismiss());

                alertDialog.show();

            }else{

                Add_Edit_Regle_Activity.medicaments = medicaments;

                Add_Edit_Regle_Activity.add = true;
                Add_Edit_Regle_Activity.edit = false;

                Intent intent = new Intent(this,Add_Edit_Regle_Activity.class);
                startActivity(intent);

            }

        });

        btn_delete_save.setOnClickListener(v -> {

            hide_keyboard(this);

            if(add){//Save

                if(!editText_id_medicament.getText().toString().equals("")){

                    if(!dataBase_apm.checkMedicament(editText_id_medicament.getText().toString())) {

                        if(!editText_designation.getText().toString().equals("")){

                            if (dataBase_apm.insertMedicaments(editText_id_medicament.getText().toString(), editText_designation.getText().toString())) {

                                Toast.makeText(this, "Le médicament a été ajouté avec succès", Toast.LENGTH_SHORT).show();
                                Medicaments_Activity.refreshRecycleView(this);
                                finish();

                            } else {

                                Toast.makeText(this, "Il y a une erreur !.", Toast.LENGTH_SHORT).show();

                            }

                        }else{

                            txtv_msg_error.setText("Entrez désignation du médicament S.V.P !.");
                            Toast.makeText(this, "Entrez désignation du médicament S.V.P !.", Toast.LENGTH_SHORT).show();
                            editText_designation.requestFocus(editText_designation.getText().toString().length());
                            show_keyboard(this);

                        }

                    }else {

                        txtv_msg_error.setText("Cet identifiant existe déjà, veuillez en saisir un autre !.");
                        Toast.makeText(this, "Cet identifiant existe déjà, veuillez en saisir un autre !.", Toast.LENGTH_SHORT).show();
                        editText_id_medicament.requestFocus(editText_id_medicament.getText().toString().length());
                        show_keyboard(this);

                    }

                }else {

                    txtv_msg_error.setText("Entrez l'ID du médicament S.V.P !.");
                    Toast.makeText(this, "Entrez l'ID du médicament S.V.P !.", Toast.LENGTH_SHORT).show();
                    editText_id_medicament.requestFocus(editText_id_medicament.getText().toString().length());
                    show_keyboard(this);

                }

            }else{

                if(edit){//edit

                    if(!editText_id_medicament.getText().toString().equals("")){

                        if(!editText_designation.getText().toString().equals("")){

                            dataBase_apm.updateMedicament(editText_id_medicament.getText().toString(),editText_designation.getText().toString());

                            Toast.makeText(this, "Le médicament a été modifié avec succès", Toast.LENGTH_SHORT).show();
                            Medicaments_Activity.refreshRecycleView(this);
                            finish();

                        }else {

                            txtv_msg_error.setText("Entrez désignation du médicament S.V.P !.");
                            Toast.makeText(this, "Entrez désignation du médicament S.V.P !.", Toast.LENGTH_SHORT).show();
                            editText_designation.requestFocus(editText_designation.getText().toString().length());
                            show_keyboard(this);

                        }

                    }else {

                        txtv_msg_error.setText("Entrez l'ID du médicament S.V.P !.");
                        Toast.makeText(this, "Entrez l'ID du médicament S.V.P !.", Toast.LENGTH_SHORT).show();
                        editText_id_medicament.requestFocus(editText_id_medicament.getText().toString().length());
                        show_keyboard(this);

                    }

                }else{//delete

                    AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle).create();
                    alertDialog.setMessage("Voulez-vous supprimer cette médicament ?.\nAttention : toutes les règles sont supprimées avec ce médicament, ce médicament sera supprimé aussi de toutes les ordonnances !.");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui", (dialog, which) -> {
                        dialog.dismiss();
                        dataBase_apm.deleteRegles(medicaments.getID_Medicament());
                        dataBase_apm.deleteMedicamentOrdonnance(medicaments.getID_Medicament());
                        dataBase_apm.deleteMedicament(medicaments.getID_Medicament());
                        Medicaments_Activity.refreshRecycleView(this);
                        finish();
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Non", (dialog, which) -> dialog.dismiss());
                    alertDialog.show();

                }

            }

        });

        btn_edit_cancel.setOnClickListener(v -> {

            if(add){//Cancel

                if(!edit) {

                    editText_id_medicament.setText(null);
                    editText_designation.setText(null);

                    finish();

                }else{

                    txtvtitle_toolbar.setText("Informations médicament");
                    btn_delete_save.setText("Supprimer");
                    btn_delete_save.setBackgroundResource(R.drawable.bg_btn_abt);
                    btn_edit_cancel.setText("Modifier");
                    btn_edit_cancel.setBackgroundResource(R.drawable.bg_btn_add);

                    editText_id_medicament.setText(medicaments.getID_Medicament());
                    editText_designation.setText(medicaments.getDesignation());

                    editText_id_medicament.setEnabled(false);
                    editText_designation.setEnabled(false);

                }

            }else{//edit

                if(!edit) {

                    edit = true;

                    editText_id_medicament.setEnabled(false);
                    editText_designation.setEnabled(true);

                    txtvtitle_toolbar.setText("Modification du médicament");
                    btn_delete_save.setText("Sauvegarder");
                    btn_delete_save.setBackgroundResource(R.drawable.bg_btn_add);
                    btn_edit_cancel.setText("Annuler");
                    btn_edit_cancel.setBackgroundResource(R.drawable.bg_btn_abt);

                    editText_designation.requestFocus(editText_designation.getText().toString().length());
                    show_keyboard(this);

                }else{

                    edit = false;

                    txtvtitle_toolbar.setText("Informations médicament");
                    btn_delete_save.setText("Supprimer");
                    btn_delete_save.setBackgroundResource(R.drawable.bg_btn_abt);
                    btn_edit_cancel.setText("Modifier");
                    btn_edit_cancel.setBackgroundResource(R.drawable.bg_btn_add);


                    editText_id_medicament.setText(medicaments.getID_Medicament());
                    editText_designation.setText(medicaments.getDesignation());

                    editText_id_medicament.setEnabled(false);
                    editText_designation.setEnabled(false);

                }

            }

        });


        editText_designation.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                btn_delete_save.callOnClick();

            }
            return false;
        });

    }

    static void refreshRecycleView(Context context){

        DataBase_SQLite dataBase_apm = new DataBase_SQLite(context);

        list.clear();

        if(dataBase_apm.getCountMedicament()>0) {

            list = dataBase_apm.getAllRegles(medicaments.getID_Medicament());

        }else{

            list = dataBase_apm.getAllRegles("");

        }

        adapterRegles = new AdapterRegles(list);
        recyclerView.setAdapter(adapterRegles);

        adapterRegles.setClickListener((view, position) -> {

            Add_Edit_Regle_Activity.regles = (Regles) list.get(position);

            Add_Edit_Regle_Activity.medicaments = medicaments;

            Add_Edit_Regle_Activity.add = false;
            Add_Edit_Regle_Activity.edit = false;
            Intent intent = new Intent(context,Add_Edit_Regle_Activity.class);
            context.startActivity(intent);

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