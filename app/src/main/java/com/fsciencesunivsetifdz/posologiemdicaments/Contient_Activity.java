package com.fsciencesunivsetifdz.posologiemdicaments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Contient_Activity extends AppCompatActivity {

    static Ordonnances_Patients_Medecines ordonnances;

    static List<Object> list = new ArrayList<>();

    static RecyclerView recyclerView;

    static AdapterContient adapterContient;

    Button btn_delete;

    DataBase_SQLite dataBase_apm = new DataBase_SQLite(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contient);

        btn_delete = findViewById(R.id.btn_delete);

        btn_delete.setOnClickListener(v -> {

            AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle).create();
            alertDialog.setMessage("Voulez-vous supprimer cette ordonnance ?.");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui", (dialog, which) -> {

                dialog.dismiss();
                dataBase_apm.deleteOrdonnance(ordonnances.getID_Ordonnance());
                Ordonnances_Activity.refreshRecycleView(this);
                finish();

            });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Non", (dialog, which) -> dialog.dismiss());
            alertDialog.show();

        });

        recyclerView = findViewById(R.id.contient);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshRecycleView(this);

    }

    void refreshRecycleView(Context context){

        DataBase_SQLite dataBase_apm = new DataBase_SQLite(context);

        list.clear();

        list = dataBase_apm.getContientOrdonnance(ordonnances.getID_Ordonnance());

        adapterContient = new AdapterContient(list);
        recyclerView.setAdapter(adapterContient);

    }

}