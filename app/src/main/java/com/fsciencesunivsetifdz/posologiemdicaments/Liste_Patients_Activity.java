package com.fsciencesunivsetifdz.posologiemdicaments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Liste_Patients_Activity extends AppCompatActivity {

    static List<Object> list = new ArrayList<>();

    static RecyclerView recyclerView;

    static AdapterPatients  adapterPatients;

    @SuppressLint("StaticFieldLeak")
    static SearchView searchView;

    Button btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_patients);

        btn_cancel = findViewById(R.id.btn_cancel);

        Toolbar toolbar = findViewById(R.id.toolbar_liste_patients);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.liste_patients);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshRecycleView(this);

        btn_cancel.setOnClickListener(v -> {

            finish();

        });

    }

    @SuppressLint("SetTextI18n")
    void refreshRecycleView(Context context){

        try {

            searchView.setQuery("", false);

        }catch (Exception e){

            e.printStackTrace();

        }
        DataBase_SQLite dataBase_apm = new DataBase_SQLite(context);

        list.clear();

        list = dataBase_apm.getAllPatients();

        adapterPatients = new AdapterPatients(list);
        recyclerView.setAdapter(adapterPatients);

        adapterPatients.setClickListener((view, position) -> {

            Bilan_Activity.patients = (Patients) list.get(position);

            Bilan_Activity.editText_patient.setText(Integer.toString(Bilan_Activity.patients.getID_Patient())+" | "+Bilan_Activity.patients.getNom_Patient().toUpperCase()+" "+
                    Bilan_Activity.patients.getPrenom_Patient().toLowerCase());

            finish();

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_patients,menu);
        MenuItem item = menu.findItem(R.id.search_patients);
        searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterPatients.getFilter().filter(newText);
                return false;

            }

        });

        return super.onCreateOptionsMenu(menu);
    }

}