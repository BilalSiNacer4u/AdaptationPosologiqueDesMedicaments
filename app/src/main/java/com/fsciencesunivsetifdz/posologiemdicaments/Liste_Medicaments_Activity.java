package com.fsciencesunivsetifdz.posologiemdicaments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Liste_Medicaments_Activity extends AppCompatActivity {

    static List<Object> list = new ArrayList<>();

    static RecyclerView recyclerView;

    static AdapterMedicaments adapterMedicaments;

    static double bilirubine;
    static double tgo_tgp;
    static double clairance;

    static Regles regles;

    static Ordonnances ordonnances;

    @SuppressLint("StaticFieldLeak")
    static SearchView searchView;

    Button btn_cancel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_medicaments);

        btn_cancel = findViewById(R.id.btn_cancel);

        Toolbar toolbar = findViewById(R.id.toolbar_liste_medicaments);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.liste_medicaments);
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

        list = dataBase_apm.getAllMedicaments();

        adapterMedicaments = new AdapterMedicaments(list);
        recyclerView.setAdapter(adapterMedicaments);

        adapterMedicaments.setClickListener((view, position) -> {
            
            Medicaments medicaments = (Medicaments) list.get(position); 
            
            if(dataBase_apm.getCountRegles(medicaments.getID_Medicament()) != 0) {

                if(dataBase_apm.getCountMedicamentsOrdonnance(medicaments.getID_Medicament(),ordonnances.getID_Ordonnance()) == 0) {

                    dataBase_apm.insertContient(medicaments.getID_Medicament(), ordonnances.getID_Ordonnance(), dataBase_apm.getDose(medicaments.getID_Medicament(),
                            ordonnances.getID_Patient(),bilirubine,tgo_tgp,clairance));

                    Medicaments_Ordonnance_Activity.refreshRecycleView(this);

                    finish();

                }else{

                    AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle).create();
                    alertDialog.setMessage("Ce médicament est déjà dans l'ordonnance, choisissez un autre médicament S.V.P !.");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", (dialog, which) -> {

                        dialog.dismiss();

                    });

                    alertDialog.show();

                }

            }else{

                AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle).create();
                alertDialog.setMessage("Vous ne pouvez pas choisir ce médicament, parce qu'il ne contient aucune règle !.");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", (dialog, which) -> {

                    dialog.dismiss();

                });

                alertDialog.show();
                
            }

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

                adapterMedicaments.getFilter().filter(newText);
                return false;

            }

        });

        return super.onCreateOptionsMenu(menu);
    }

}