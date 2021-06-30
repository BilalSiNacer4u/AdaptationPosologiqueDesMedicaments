package com.fsciencesunivsetifdz.posologiemdicaments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Ordonnances_Activity extends AppCompatActivity {

    static List<Object> list = new ArrayList<>();

    static RecyclerView recyclerView;

    static AdapterOrdonnances  adapterOrdonnances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordonnances);

        recyclerView = findViewById(R.id.ordonnances);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshRecycleView(this);

    }

    static void refreshRecycleView(Context context){

        DataBase_SQLite dataBase_apm = new DataBase_SQLite(context);

        list.clear();

        list = dataBase_apm.getAllOrdonnances();

        adapterOrdonnances = new AdapterOrdonnances(list);
        recyclerView.setAdapter(adapterOrdonnances);

        adapterOrdonnances.setClickListener((view, position) -> {

            Contient_Activity.ordonnances = (Ordonnances_Patients_Medecines) list.get(position);

            Intent intent = new Intent(context,Contient_Activity.class);
            context.startActivity(intent);

        });

    }

}