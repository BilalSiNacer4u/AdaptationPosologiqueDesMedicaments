package com.fsciencesunivsetifdz.posologiemdicaments;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ContactActivity extends AppCompatActivity {

    boolean touch_bilal = false,touch_mahdi = false,touch_keroum = false,touch_email = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity);

        CardView cv_email;
        CardView cv_facebook_mahdi;
        CardView cv_facebook_abd_elkarim;
        CardView cv_facebook_bilal;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            final Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(getColor(R.color.light_grey));

        }

        cv_email = findViewById(R.id.cv_email);
        cv_facebook_mahdi = findViewById(R.id.cv_facebook_mahdi);
        cv_facebook_abd_elkarim = findViewById(R.id.cv_facebook_abd_elkarim);
        cv_facebook_bilal = findViewById(R.id.cv_facebook_bilal);

        cv_email.setCardElevation(10);
        cv_facebook_mahdi.setCardElevation(10);
        cv_facebook_abd_elkarim.setCardElevation(10);
        cv_facebook_bilal.setCardElevation(10);

        cv_email.setOnClickListener(v -> {
            if (v.getId() == R.id.cv_email) {
                try {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", "abdesselem_beghriche@univ-setif.dz", null));
                    startActivity(Intent.createChooser(emailIntent, null));
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(), "Installez l'application Gmail pour pouvoir envoyer un message à l'encadreur", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cv_facebook_mahdi.setOnClickListener(view -> {
            String facebookUrl = "https://www.facebook.com/amenallah.aymen/";
            try {
                int versionCode = getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                if (versionCode >= 3002850) {
                    Uri uri = Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/amenallah.aymen/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                } else {
                    Uri uri = Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/amenallah.aymen/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
            } catch (PackageManager.NameNotFoundException e) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
                } catch (ActivityNotFoundException n) {
                    Toast.makeText(getApplicationContext(), "Installez l'application Facebook ou n'importe quel navigateur pour pouvoir ouvrir le lien de la page", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cv_facebook_abd_elkarim.setOnClickListener(view -> {
            String facebookUrl = "https://www.facebook.com/karoum.founes/";
            try {
                int versionCode = getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                if (versionCode >= 3002850) {
                    Uri uri = Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/karoum.founes/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                } else {
                    Uri uri = Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/karoum.founes/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
            } catch (PackageManager.NameNotFoundException e) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
                } catch (ActivityNotFoundException n) {
                    Toast.makeText(getApplicationContext(), "Installez l'application Facebook ou n'importe quel navigateur pour pouvoir ouvrir le lien de la page", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cv_facebook_bilal.setOnClickListener(view -> {
            String facebookUrl = "https://www.facebook.com/bilal.nacer.790/";
            try {
                int versionCode = getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                if (versionCode >= 3002850) {
                    Uri uri = Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/bilal.nacer.790/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                } else {
                    Uri uri = Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/bilal.nacer.790/");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
            } catch (PackageManager.NameNotFoundException e) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
                } catch (ActivityNotFoundException n) {
                    Toast.makeText(getApplicationContext(), "Installez l'application Facebook ou n'importe quel navigateur pour pouvoir ouvrir le lien de la page", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cv_email.setOnTouchListener((v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            touch_email = true;
                            cv_email.setCardElevation(0);
                            cv_email.setCardBackgroundColor(getColor(R.color.raisin_blackdark));
                            break;
                        case MotionEvent.ACTION_UP:
                            touch_email = false;
                            cv_email.setCardElevation(10);
                            cv_email.setCardBackgroundColor(getColor(R.color.raisin_black));
                            float x = event.getX();
                            float y = event.getY();
                            int width = cv_email.getWidth();
                            int height = cv_email.getHeight();
                            if ((!touch_bilal) && (!touch_mahdi) && (!touch_keroum)) {
                                if (((x > 0) && (x < (width))) && ((y > 0) && (y < (height)))) {
                                    cv_email.callOnClick();
                                }
                            }
                            break;
                        default:
                            touch_email = false;
                            cv_email.setCardElevation(10);
                            cv_email.setCardBackgroundColor(getColor(R.color.raisin_black));
                            break;
                    }
                    return true;
                }
        );

        cv_facebook_abd_elkarim.setOnTouchListener((v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            touch_keroum = true;
                            cv_facebook_abd_elkarim.setCardElevation(0);
                            cv_facebook_abd_elkarim.setCardBackgroundColor(getColor(R.color.raisin_blackdark));
                            break;
                        case MotionEvent.ACTION_UP:
                            touch_keroum = false;
                            cv_facebook_abd_elkarim.setCardElevation(10);
                            cv_facebook_abd_elkarim.setCardBackgroundColor(getColor(R.color.raisin_black));
                            float x = event.getX();
                            float y = event.getY();
                            int width = cv_facebook_abd_elkarim.getWidth();
                            int height = cv_facebook_abd_elkarim.getHeight();
                            if ((!touch_bilal) && (!touch_mahdi) && (!touch_email)) {
                                if (((x > 0) && (x < (width))) && ((y > 0) && (y < (height)))) {
                                    cv_facebook_abd_elkarim.callOnClick();
                                }
                            }
                            break;
                        default:
                            touch_keroum = false;
                            cv_facebook_abd_elkarim.setCardElevation(10);
                            cv_facebook_abd_elkarim.setCardBackgroundColor(getColor(R.color.raisin_black));
                            break;
                    }
                    return true;
                }
        );

        cv_facebook_mahdi.setOnTouchListener((v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            touch_mahdi = true;
                            cv_facebook_mahdi.setCardElevation(0);
                            cv_facebook_mahdi.setCardBackgroundColor(getColor(R.color.raisin_blackdark));
                            break;
                        case MotionEvent.ACTION_UP:
                            touch_mahdi = false;
                            cv_facebook_mahdi.setCardElevation(10);
                            cv_facebook_mahdi.setCardBackgroundColor(getColor(R.color.raisin_black));
                            float x = event.getX();
                            float y = event.getY();
                            int width = cv_facebook_mahdi.getWidth();
                            int height = cv_facebook_mahdi.getHeight();
                            if ((!touch_bilal) && (!touch_keroum) && (!touch_email)) {
                                if (((x > 0) && (x < (width))) && ((y > 0) && (y < (height)))) {
                                    cv_facebook_mahdi.callOnClick();
                                }
                            }
                            break;
                        default:
                            touch_mahdi = false;
                            cv_facebook_mahdi.setCardElevation(10);
                            cv_facebook_mahdi.setCardBackgroundColor(getColor(R.color.raisin_black));
                            break;
                    }
                    return true;
                }
        );

        cv_facebook_bilal.setOnTouchListener((v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            touch_bilal = true;
                            cv_facebook_bilal.setCardElevation(0);
                            cv_facebook_bilal.setCardBackgroundColor(getColor(R.color.raisin_blackdark));
                            break;
                        case MotionEvent.ACTION_UP:
                            touch_bilal = false;
                            cv_facebook_bilal.setCardElevation(10);
                            cv_facebook_bilal.setCardBackgroundColor(getColor(R.color.raisin_black));
                            float x = event.getX();
                            float y = event.getY();
                            int width = cv_facebook_bilal.getWidth();
                            int height = cv_facebook_bilal.getHeight();
                            if ((!touch_mahdi) && (!touch_keroum) && (!touch_email)) {
                                if (((x > 0) && (x < (width))) && ((y > 0) && (y < (height)))) {
                                    cv_facebook_bilal.callOnClick();
                                }
                            }
                            break;
                        default:
                            touch_bilal = false;
                            cv_facebook_bilal.setCardElevation(10);
                            cv_facebook_bilal.setCardBackgroundColor(getColor(R.color.raisin_black));
                            break;
                    }
                    return true;
                }
        );

    }

}
