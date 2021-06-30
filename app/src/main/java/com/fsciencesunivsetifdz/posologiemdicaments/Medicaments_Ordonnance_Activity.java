package com.fsciencesunivsetifdz.posologiemdicaments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Medicaments_Ordonnance_Activity extends AppCompatActivity {

    static Ordonnances ordonnances;

    static double bilirubine;
    static double tgo_tgp;
    static double clairance;

    Button btn_confirme;
    Button btn_add;

    TextView txt_id_date;

    static List<Object> list = new ArrayList<>();

    static RecyclerView recyclerView;

    static AdapterContient adapterContient;

    static Context activity;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicaments_ordonnance);

        activity = this;

        txt_id_date = findViewById(R.id.txt_id_date);

        btn_confirme = findViewById(R.id.btn_confirme);
        btn_add = findViewById(R.id.btn_add);

        recyclerView = findViewById(R.id.liste_medicaments_ordonnance);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshRecycleView(this);

        txt_id_date.setText("ID Ordonnance : "+ordonnances.getID_Ordonnance()+" | Date : "+ordonnances.getDate_Ordonnance());

        btn_add.setOnClickListener(v -> {

            Liste_Medicaments_Activity.bilirubine = bilirubine;
            Liste_Medicaments_Activity.tgo_tgp = tgo_tgp;
            Liste_Medicaments_Activity.clairance = clairance;

            Liste_Medicaments_Activity.ordonnances = ordonnances;

            Intent intent = new Intent(this,Liste_Medicaments_Activity.class);
            startActivity(intent);

        });

        btn_confirme.setOnClickListener(v -> {

            AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle).create();
            alertDialog.setMessage("Voulez-vous confirmer cette ordonnance ?.");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui", (dialog, which) -> {
                dialog.dismiss();
                finish();
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Non", (dialog, which) -> dialog.dismiss());
            alertDialog.show();

        });

    }

    static void refreshRecycleView(Context context){

        DataBase_SQLite dataBase_apm = new DataBase_SQLite(context);

        list.clear();

        list = dataBase_apm.getContientOrdonnance(ordonnances.getID_Ordonnance());

        adapterContient = new AdapterContient(list);
        recyclerView.setAdapter(adapterContient);

        adapterContient.setClickListener((view, position) -> {

            Contient contient = (Contient) list.get(position);

            AlertDialog alertDialog = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle).create();
            alertDialog.setMessage("Voulez-vous supprimer cette médicament ?.");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui", (dialog, which) -> {

                dialog.dismiss();
                dataBase_apm.deleteContient(contient.getID_Medicament(),contient.getID_Ordonnance());
                refreshRecycleView(context);

            });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Non", (dialog, which) -> dialog.dismiss());
            alertDialog.show();

        });

        }

    @Override
    public void onBackPressed() {

        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle).create();
        alertDialog.setMessage("Vous ne pouvez pas revenir tant que vous n'avez pas confirmé cette ordonnance !.");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Confirmer", (dialog, which) -> {
            dialog.dismiss();
            super.onBackPressed();
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Annuler", (dialog, which) -> dialog.dismiss());
        alertDialog.show();

    }

}