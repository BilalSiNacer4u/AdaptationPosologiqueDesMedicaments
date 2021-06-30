package com.fsciencesunivsetifdz.posologiemdicaments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce;
    private final Handler mHandler = new Handler();

    static Medecines medecine;

    static String date;

    CardView cv_patiens;
    CardView cv_medecines;
    CardView cv_medicaments;
    CardView cv_ordonnances;
    CardView cv_bilan;
    CardView cv_profil;
    LinearLayout lyt_profil;

    @SuppressLint("StaticFieldLeak")
    static TextView txt_name;
    @SuppressLint("StaticFieldLeak")
    static TextView txt_account;
    TextView txt_date;

    private boolean touch_patiens = false;
    private boolean touch_medecines = false;
    private boolean touch_medicaments = false;
    private boolean touch_ordonnances = false;
    private boolean touch_bilan = false;
    private boolean touch_profil = false;

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.items));

        txt_name = findViewById(R.id.txt_name);
        txt_account = findViewById(R.id.txt_account);

        txt_name.setText(medecine.getNom_Medecine().toUpperCase()+" "+medecine.getPrenom_Medecine().toLowerCase());
        txt_account.setText("#"+medecine.getCompte());

        cv_patiens = findViewById(R.id.cv_patiens);
        cv_medecines = findViewById(R.id.cv_medecines);
        cv_medicaments = findViewById(R.id.cv_medicaments);
        cv_ordonnances = findViewById(R.id.cv_ordonnances);
        cv_bilan = findViewById(R.id.cv_bilan);
        cv_profil = findViewById(R.id.cv_profil);

        lyt_profil = findViewById(R.id.lyt_profil);

        txt_date = findViewById(R.id.txt_date);

        cv_patiens.setCardElevation(10);
        cv_medecines.setCardElevation(10);
        cv_medicaments.setCardElevation(10);
        cv_ordonnances.setCardElevation(10);
        cv_bilan.setCardElevation(10);
        cv_profil.setCardElevation(10);

        mHandler.postDelayed(mRunnableDate, 0);

        cv_patiens.setOnTouchListener((v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            touch_patiens = true;
                            cv_patiens.setCardElevation(0);
                            cv_patiens.setCardBackgroundColor(Color.parseColor("#E4E5EB"));
                            break;
                        case MotionEvent.ACTION_UP:
                            touch_patiens = false;
                            cv_patiens.setCardElevation(10);
                            cv_patiens.setCardBackgroundColor(Color.parseColor("#D3D4D9"));
                            float x = event.getX();
                            float y = event.getY();
                            int width = cv_patiens.getWidth();
                            int height = cv_patiens.getHeight();
                            if ((!touch_medecines) && (!touch_medicaments) && (!touch_ordonnances) && (!touch_bilan) && (!touch_profil)) {
                                if (((x > 0) && (x < (width))) && ((y > 0) && (y < (height)))) {
                                    cv_patiens.callOnClick();
                                }
                            }
                            break;
                        default:
                            touch_patiens = false;
                            cv_patiens.setCardElevation(10);
                            cv_patiens.setCardBackgroundColor(Color.parseColor("#D3D4D9"));
                            break;
                    }
                    return true;
                }
        );

        cv_patiens.setOnClickListener(view -> {

            Intent intent = new Intent(this,Patient_Activity.class);
            startActivity(intent);

        });

        cv_medecines.setOnTouchListener((v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            touch_medecines = true;
                            cv_medecines.setCardElevation(0);
                            cv_medecines.setCardBackgroundColor(Color.parseColor("#E4E5EB"));
                            break;
                        case MotionEvent.ACTION_UP:
                            touch_medecines = false;
                            cv_medecines.setCardElevation(10);
                            cv_medecines.setCardBackgroundColor(Color.parseColor("#D3D4D9"));
                            float x = event.getX();
                            float y = event.getY();
                            int width = cv_medecines.getWidth();
                            int height = cv_medecines.getHeight();
                            if ((!touch_patiens) && (!touch_medicaments) && (!touch_ordonnances) && (!touch_bilan) && (!touch_profil)) {
                                if (((x > 0) && (x < (width))) && ((y > 0) && (y < (height)))) {
                                    cv_medecines.callOnClick();
                                }
                            }
                            break;
                        default:
                            touch_medecines = false;
                            cv_medecines.setCardElevation(10);
                            cv_medecines.setCardBackgroundColor(Color.parseColor("#D3D4D9"));
                            break;
                    }
                    return true;
                }
        );

        cv_medecines.setOnClickListener(view -> {

            Intent intent = new Intent(this,Medecines_Activity.class);
            startActivity(intent);

        });

        cv_medicaments.setOnTouchListener((v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            touch_medicaments = true;
                            cv_medicaments.setCardElevation(0);
                            cv_medicaments.setCardBackgroundColor(Color.parseColor("#E4E5EB"));
                            break;
                        case MotionEvent.ACTION_UP:
                            touch_medicaments = false;
                            cv_medicaments.setCardElevation(10);
                            cv_medicaments.setCardBackgroundColor(Color.parseColor("#D3D4D9"));
                            float x = event.getX();
                            float y = event.getY();
                            int width = cv_medicaments.getWidth();
                            int height = cv_medicaments.getHeight();
                            if ((!touch_patiens) && (!touch_medecines) && (!touch_ordonnances) && (!touch_bilan) && (!touch_profil)) {
                                if (((x > 0) && (x < (width))) && ((y > 0) && (y < (height)))) {
                                    cv_medicaments.callOnClick();
                                }
                            }
                            break;
                        default:
                            touch_medicaments = false;
                            cv_medicaments.setCardElevation(10);
                            cv_medicaments.setCardBackgroundColor(Color.parseColor("#D3D4D9"));
                            break;
                    }
                    return true;
                }
        );

        cv_medicaments.setOnClickListener(view -> {

            Intent intent = new Intent(this,Medicaments_Activity.class);
            startActivity(intent);

        });

        cv_bilan.setOnTouchListener((v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            touch_bilan = true;
                            cv_bilan.setCardElevation(0);
                            cv_bilan.setCardBackgroundColor(Color.parseColor("#E4E5EB"));
                            break;
                        case MotionEvent.ACTION_UP:
                            touch_bilan = false;
                            cv_bilan.setCardElevation(10);
                            cv_bilan.setCardBackgroundColor(Color.parseColor("#D3D4D9"));
                            float x = event.getX();
                            float y = event.getY();
                            int width = cv_bilan.getWidth();
                            int height = cv_bilan.getHeight();
                            if ((!touch_patiens) && (!touch_medecines) && (!touch_ordonnances) && (!touch_medicaments) && (!touch_profil)) {
                                if (((x > 0) && (x < (width))) && ((y > 0) && (y < (height)))) {
                                    cv_bilan.callOnClick();
                                }
                            }
                            break;
                        default:
                            touch_bilan = false;
                            cv_bilan.setCardElevation(10);
                            cv_bilan.setCardBackgroundColor(Color.parseColor("#D3D4D9"));
                            break;
                    }
                    return true;
                }
        );

        cv_bilan.setOnClickListener(view -> {

            Bilan_Activity.medecines = medecine;
            Intent intent = new Intent(this,Bilan_Activity.class);
            startActivity(intent);

        });

        cv_ordonnances.setOnTouchListener((v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            touch_ordonnances = true;
                            cv_ordonnances.setCardElevation(0);
                            cv_ordonnances.setCardBackgroundColor(Color.parseColor("#E4E5EB"));
                            break;
                        case MotionEvent.ACTION_UP:
                            touch_ordonnances = false;
                            cv_ordonnances.setCardElevation(10);
                            cv_ordonnances.setCardBackgroundColor(Color.parseColor("#D3D4D9"));
                            float x = event.getX();
                            float y = event.getY();
                            int width = cv_ordonnances.getWidth();
                            int height = cv_ordonnances.getHeight();
                            if ((!touch_patiens) && (!touch_medecines) && (!touch_bilan) && (!touch_medicaments) && (!touch_profil)) {
                                if (((x > 0) && (x < (width))) && ((y > 0) && (y < (height)))) {
                                    cv_ordonnances.callOnClick();
                                }
                            }
                            break;
                        default:
                            touch_ordonnances = false;
                            cv_ordonnances.setCardElevation(10);
                            cv_ordonnances.setCardBackgroundColor(Color.parseColor("#D3D4D9"));
                            break;
                    }
                    return true;
                }
        );

        cv_ordonnances.setOnClickListener(view -> {

            Intent intent = new Intent(this,Ordonnances_Activity.class);
            startActivity(intent);

        });

        cv_profil.setOnTouchListener((v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            touch_profil = true;
                            cv_profil.setCardElevation(0);
                            cv_profil.setCardBackgroundColor(Color.parseColor("#E4E5EB"));
                            break;
                        case MotionEvent.ACTION_UP:
                            touch_profil = false;
                            cv_profil.setCardElevation(10);
                            cv_profil.setCardBackgroundColor(Color.parseColor("#D3D4D9"));
                            float x = event.getX();
                            float y = event.getY();
                            int width = cv_profil.getWidth();
                            int height = cv_profil.getHeight();
                            if ((!touch_patiens) && (!touch_medecines) && (!touch_ordonnances) && (!touch_medicaments) && (!touch_bilan)) {
                                if (((x > 0) && (x < (width))) && ((y > 0) && (y < (height)))) {
                                    cv_profil.callOnClick();
                                }
                            }
                            break;
                        default:
                            touch_profil = false;
                            cv_profil.setCardElevation(10);
                            cv_profil.setCardBackgroundColor(Color.parseColor("#D3D4D9"));
                            break;
                    }
                    return true;
                }
        );

        cv_profil.setOnClickListener(view -> {

            Profil_Activity.medecines = medecine;
            Intent intent = new Intent(this,Profil_Activity.class);
            startActivity(intent);

        });

        lyt_profil.setOnClickListener(view -> cv_profil.callOnClick());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        if (Build.VERSION.SDK_INT >= 28) {
            menu.setGroupDividerEnabled(true);
        }else{
            MenuCompat.setGroupDividerEnabled(menu, true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    //Action items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.item_about) {

            Intent intent = new Intent(this,ContactActivity.class);
            startActivity(intent);

        }

        if(id == R.id.item_close) {

            close_app();

        }

        if(id == R.id.item_logout) {

            Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
            startActivity(intent);
            finish();

        }

        if(id == R.id.item_profil) {

            cv_profil.callOnClick();

        }


        return super.onOptionsItemSelected(item);
    }

    private void close_app() {

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this, R.style.AppCompatAlertDialogStyle).create();
        alertDialog.setMessage("Voulez-vous fermez l'application ?.");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Fermer", (dialog, which) -> {
            dialog.dismiss();
            finish();
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Annuler", (dialog, which) -> dialog.dismiss());
        alertDialog.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this,"Cliquez à nouveau pour fermer l'application",Toast.LENGTH_SHORT).show();
        mHandler.postDelayed(mRunnable, 2000);

    }


    //Timer => double click for close application
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    //Timer => double click for close application
    private final Runnable mRunnableDate = new Runnable() {
        @Override
        public void run() {

            mHandler.postDelayed(mRunnable, 1000);

            date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            txt_date.setText(date);

            mHandler.postDelayed(mRunnableDate, 1000);

        }
    };

    @SuppressLint("SetTextI18n")
    static void refreshInformationAccount(){

        txt_name.setText(medecine.getNom_Medecine().toUpperCase()+" "+medecine.getPrenom_Medecine().toLowerCase());
        txt_account.setText("#"+medecine.getCompte());

    }

}