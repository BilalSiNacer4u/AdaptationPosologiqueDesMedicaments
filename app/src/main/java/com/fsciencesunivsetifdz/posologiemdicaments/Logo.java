package com.fsciencesunivsetifdz.posologiemdicaments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Logo extends AppCompatActivity {

    Handler handler = new Handler();

    DataBase_SQLite dataBase_apm = new DataBase_SQLite(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setNavigationBarColor(getColor(R.color.snow));
            window.setStatusBarColor(getColor(R.color.snow));

        }

        handler.postDelayed(time_wait_logo,2000);

        setContentView(R.layout.activity_logo);

    }

    Runnable time_wait_logo = new Runnable(){
        @Override
        public void run(){

            Intent intent;

            if(dataBase_apm.getCountAccounts()!=0){

                intent = new Intent(getApplicationContext(), Login_Activity.class);
                SignUp_Activity.closeToast=false;

            }else{

                intent = new Intent(getApplicationContext(), SignUp_Activity.class);
                SignUp_Activity.closeToast=true;

            }


            startActivity(intent);
            finish();
            handler.removeCallbacks(time_wait_logo);

            }

        };

}

