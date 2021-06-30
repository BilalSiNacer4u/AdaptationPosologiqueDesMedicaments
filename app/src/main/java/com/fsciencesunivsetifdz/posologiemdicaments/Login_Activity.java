package com.fsciencesunivsetifdz.posologiemdicaments;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login_Activity extends AppCompatActivity {

    EditText editText_username;
    EditText editText_password;
    Button btn_login;
    Button btn_signup;
    TextView txtv_msg_error;
    CheckBox checkbox_show_password_login;

    @SuppressLint("StaticFieldLeak")
    public static Activity activity;

    private boolean doubleBackToExitPressedOnce;
    private final Handler mHandler = new Handler();

    DataBase_SQLite dataBase_apm = new DataBase_SQLite(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Window window = this.getWindow();

        activity = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setNavigationBarColor(getColor(R.color.snow));
            window.setStatusBarColor(getColor(R.color.snow));

        }

        editText_username = findViewById(R.id.editText_username);
        editText_password = findViewById(R.id.editText_password);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);
        txtv_msg_error = findViewById(R.id.txtv_msg_error);
        checkbox_show_password_login = findViewById(R.id.checkbox_show_password_login);

        editText_username.requestFocus(editText_username.getText().toString().length());

        checkbox_show_password_login.setOnCheckedChangeListener((compoundButton, b) -> {

            int charFocus = -1;

            try{

                if(this.getCurrentFocus().getId() == editText_password.getId()) {

                    charFocus = editText_password.getSelectionEnd();

                }

            }catch(Exception e){

                e.printStackTrace();

            }

            if (checkbox_show_password_login.isChecked()) {

                editText_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

            }else{

                editText_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            }

            editText_password.setTypeface(Typeface.DEFAULT_BOLD);

            if(charFocus!=-1){

                try{

                    if(this.getCurrentFocus().getId() == editText_password.getId()) {

                        editText_password.setSelection(charFocus);

                    }

                }catch(Exception e){

                    e.printStackTrace();

                }

            }
        });

        btn_login.setOnClickListener(view -> {

            hide_keyboard(this);

            if (!editText_username.getText().toString().equals("")) {

                if (!editText_password.getText().toString().equals("")) {

                    if(dataBase_apm.checkAccount(editText_username.getText().toString(),editText_password.getText().toString())){

                        MainActivity.medecine = dataBase_apm.getMedecine(editText_username.getText().toString());

                        Intent intent = new Intent(this,MainActivity.class);
                        startActivity(intent);

                        editText_username.setText(null);
                        editText_password.setText(null);
                        checkbox_show_password_login.setChecked(false);
                        txtv_msg_error.setText(null);
                        finish();

                    }else{

                        if(dataBase_apm.checkAccount(editText_username.getText().toString())){

                            txtv_msg_error.setText("Le mot de passe que vous avez entré est incorrect !.");
                            Toast.makeText(this, "Le mot de passe que vous avez entré est incorrect !.", Toast.LENGTH_SHORT).show();
                            editText_password.requestFocus(editText_password.getText().toString().length());

                        }else{

                            txtv_msg_error.setText("Le compte que vous avez entré est erroné !.");
                            Toast.makeText(this, "Le compte que vous avez entré est erroné !.", Toast.LENGTH_SHORT).show();
                            editText_username.requestFocus(editText_username.getText().toString().length());

                        }
                        show_keyboard(this);

                    }

                }else{

                    txtv_msg_error.setText("Entrer le mot de passe S.V.P !.");
                    Toast.makeText(this, "Entrer le mot de passe S.V.P !.", Toast.LENGTH_SHORT).show();
                    editText_password.requestFocus(editText_password.getText().toString().length());
                    show_keyboard(this);

                }

            }else{

                txtv_msg_error.setText("Entrez le compte S.V.P !.");
                Toast.makeText(this, "Entrez le compte S.V.P !.", Toast.LENGTH_SHORT).show();
                editText_username.requestFocus(editText_username.getText().toString().length());
                show_keyboard(this);

            }

        });

        btn_signup.setOnClickListener(view -> {

            Intent intent = new Intent(this,SignUp_Activity.class);
            startActivity(intent);

        });

        editText_password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                btn_login.callOnClick();

            }
            return false;
        });

        editText_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(getCurrentFocus().getId() != editText_password.getId()){

                    try {

                        editText_password.requestFocus(editText_password.getText().toString().length());

                    }catch(Exception e){

                        e.printStackTrace();

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        hide_keyboard(this);

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

    public static void show_keyboard(Activity activity){

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);

    }

    public static void hide_keyboard(Activity activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();

        if (view == null) {

            view = new View(activity);

        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

}