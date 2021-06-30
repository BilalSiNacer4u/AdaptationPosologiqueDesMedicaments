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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Medecines_Activity extends AppCompatActivity {

    static List<Object> list = new ArrayList<>();

    static RecyclerView recyclerView;

    static AdapterMedecines adapterMedecines;

    @SuppressLint("StaticFieldLeak")
    static SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medecines);

        Toolbar toolbar = findViewById(R.id.toolbar_medecines);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.medecines);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshRecycleView(this);

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

                adapterMedecines.getFilter().filter(newText);
                return false;

            }

        });

        return super.onCreateOptionsMenu(menu);
    }

    static void refreshRecycleView(Context context){

        DataBase_SQLite dataBase_apm = new DataBase_SQLite(context);

        list.clear();

        list = dataBase_apm.getAllMedecines();

        adapterMedecines = new AdapterMedecines(list);
        recyclerView.setAdapter(adapterMedecines);

        adapterMedecines.setClickListener((view, position) -> {

            Medecines medecines = (Medecines) list.get(position);

            if(medecines.getCompte().equals(MainActivity.medecine.getCompte())){

                Profil_Activity.medecines = medecines;
                Intent intent = new Intent(context,Profil_Activity.class);
                context.startActivity(intent);

            }else{

                Toast.makeText(context, "Vous ne pouvez pas accéder à ce profil !.", Toast.LENGTH_SHORT).show();

            }

        });

    }

}