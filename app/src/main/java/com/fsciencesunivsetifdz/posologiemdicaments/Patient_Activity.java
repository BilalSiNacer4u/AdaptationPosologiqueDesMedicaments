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

public class Patient_Activity extends AppCompatActivity {

    static List<Object> list = new ArrayList<>();

    static RecyclerView recyclerView;

    static AdapterPatients  adapterPatients;

    @SuppressLint("StaticFieldLeak")
    static SearchView searchView;

    Button btn_add_patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        Toolbar toolbar = findViewById(R.id.toolbar_patients);
        setSupportActionBar(toolbar);

        btn_add_patient = findViewById(R.id.btn_add_patient);

        recyclerView = findViewById(R.id.patients);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshRecycleView(this);

        btn_add_patient.setOnClickListener(view -> {

            Add_Edit_Patient_Activity.add = true;
            Add_Edit_Patient_Activity.edit = false;
            Intent intent = new Intent(this,Add_Edit_Patient_Activity.class);
            startActivity(intent);

        });

    }

    static void refreshRecycleView(Context context){

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

            Add_Edit_Patient_Activity.patients = (Patients) list.get(position);

            Add_Edit_Patient_Activity.add = false;
            Add_Edit_Patient_Activity.edit = false;
            Intent intent = new Intent(context,Add_Edit_Patient_Activity.class);
            context.startActivity(intent);

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