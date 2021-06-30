package com.fsciencesunivsetifdz.posologiemdicaments;

import androidx.appcompat.app.AppCompatActivity;

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

public class SignUp_Activity extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce;
    private final Handler mHandler = new Handler();

    Button btn_signup;

    DataBase_SQLite dataBase_apm = new DataBase_SQLite(this);

    static boolean closeToast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Window window = this.getWindow();



        try {

            String s = "12";

            int id = Integer.parseInt(s);

        }catch (Exception e){

            e.printStackTrace();

        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setNavigationBarColor(getColor(R.color.snow));
            window.setStatusBarColor(getColor(R.color.snow));

        }

        TextView txtv_msg_error = findViewById(R.id.txtv_msg_error);

        EditText editText_first_name = findViewById(R.id.editText_first_name);
        EditText editText_last_name = findViewById(R.id.editText_last_name);
        EditText editText_account = findViewById(R.id.editText_account);
        EditText editText_password = findViewById(R.id.editText_password);
        EditText editText_confirm_password = findViewById(R.id.editText_confirm_password);

        CheckBox checkbox_show_password = findViewById(R.id.checkbox_show_password);

        btn_signup = findViewById(R.id.btn_signup);

        editText_first_name.requestFocus(editText_first_name.getText().toString().length());
        show_keyboard(this);

        checkbox_show_password.setOnCheckedChangeListener((compoundButton, b) -> {

            int charFocus = -1;

            try {

                if (this.getCurrentFocus().getId() == editText_password.getId()) {

                    charFocus = editText_password.getSelectionEnd();

                } else {

                    if (this.getCurrentFocus().getId() == editText_confirm_password.getId()) {

                        charFocus = editText_confirm_password.getSelectionEnd();

                    }

                }

            }catch(Exception e){

                e.printStackTrace();

            }

            if (checkbox_show_password.isChecked()) {

                editText_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                editText_password.setTypeface(Typeface.DEFAULT_BOLD);
                editText_confirm_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

            }else{

                editText_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText_password.setTypeface(Typeface.DEFAULT_BOLD);
                editText_confirm_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            }
            editText_confirm_password.setTypeface(Typeface.DEFAULT_BOLD);

            if(charFocus!=-1){

                try {

                    if (this.getCurrentFocus().getId() == editText_password.getId()) {

                        editText_password.setSelection(charFocus);

                    } else {

                        if (this.getCurrentFocus().getId() == editText_confirm_password.getId()) {

                            editText_confirm_password.setSelection(charFocus);

                        }

                    }

                }catch(Exception e){

                    e.printStackTrace();

                }

            }
        });

        btn_signup.setOnClickListener(view -> {

            hide_keyboard(this);

            if(!editText_first_name.getText().toString().equals("")) {

                if (!editText_last_name.getText().toString().equals("")) {

                    if (!editText_account.getText().toString().equals("")) {

                        if(!dataBase_apm.checkAccount(editText_account.getText().toString())){

                            if (!editText_password.getText().toString().equals("")) {

                                if (editText_password.getText().length()>=8) {

                                    if (!editText_confirm_password.getText().toString().equals("")) {

                                        if (editText_confirm_password.getText().length()>=8) {

                                            if (editText_password.getText().toString().equals(editText_confirm_password.getText().toString())) {

                                                if (dataBase_apm.insertMedecine(editText_first_name.getText().toString(),editText_last_name.getText().toString(),
                                                        editText_account.getText().toString(),editText_password.getText().toString())) {

                                                    MainActivity.medecine = dataBase_apm.getMedecine(editText_account.getText().toString());

                                                    txtv_msg_error.setText(null);

                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivity(intent);

                                                    editText_first_name.setText(null);
                                                    editText_last_name.setText(null);
                                                    editText_account.setText(null);
                                                    editText_password.setText(null);
                                                    editText_confirm_password.setText(null);

                                                    checkbox_show_password.setChecked(false);

                                                    Toast.makeText(this, "Votre compte a été créé avec succès", Toast.LENGTH_SHORT).show();

                                                    finish();

                                                    try{

                                                        Login_Activity.activity.finish();

                                                    }catch (Exception e){

                                                        e.printStackTrace();

                                                    }

                                                } else {

                                                    txtv_msg_error.setText("Erreur : Le compte n'a pas été créé !.");

                                                }

                                            }else{

                                                txtv_msg_error.setText("Le mot de passe ne correspond pas !.");
                                                Toast.makeText(this, "Le mot de passe ne correspond pas !.", Toast.LENGTH_SHORT).show();
                                                editText_password.requestFocus(editText_password.getText().toString().length());
                                                show_keyboard(this);

                                            }

                                        }else{

                                            txtv_msg_error.setText("Entrez au moins 8 caractères pour vérifier votre mot de passe !.");
                                            Toast.makeText(this, "Entrez au moins 8 caractères pour vérifier votre mot de passe !.", Toast.LENGTH_SHORT).show();
                                            editText_confirm_password.requestFocus(editText_confirm_password.getText().toString().length());
                                            show_keyboard(this);

                                        }

                                    }else{

                                        txtv_msg_error.setText("Entrez le mot de passe pour le vérifier S.V.P !.");
                                        Toast.makeText(this, "Entrez le mot de passe pour le vérifier S.V.P !.", Toast.LENGTH_SHORT).show();
                                        editText_confirm_password.requestFocus(editText_confirm_password.getText().toString().length());
                                        show_keyboard(this);

                                    }

                                }else{

                                    txtv_msg_error.setText("Entrez au moins 8 caractères pour le mot de passe !.");
                                    Toast.makeText(this, "Entrez au moins 8 caractères pour le mot de passe !.", Toast.LENGTH_SHORT).show();
                                    editText_password.requestFocus(editText_password.getText().toString().length());
                                    show_keyboard(this);

                                }

                            }else{

                                txtv_msg_error.setText("Entrer le mot de passe S.V.P !.");
                                Toast.makeText(this, "Entrer le mot de passe S.V.P !.", Toast.LENGTH_SHORT).show();
                                editText_password.requestFocus(editText_password.getText().toString().length());
                                show_keyboard(this);

                            }

                        }else{

                            txtv_msg_error.setText("Ce compte existe, Veuillez saisir un autre compte !.");
                            Toast.makeText(this, "Ce compte existe, Veuillez saisir un autre compte !.", Toast.LENGTH_SHORT).show();
                            editText_account.requestFocus(editText_account.getText().toString().length());
                            show_keyboard(this);

                        }

                    } else {

                        txtv_msg_error.setText("Entrez le compte S.V.P !.");
                        Toast.makeText(this, "Entrez le compte S.V.P !.", Toast.LENGTH_SHORT).show();
                        editText_account.requestFocus(editText_account.getText().toString().length());
                        show_keyboard(this);

                    }

                } else {

                    txtv_msg_error.setText("Entrez le prénom S.V.P !.");
                    Toast.makeText(this, "Entrez le prénom S.V.P !.", Toast.LENGTH_SHORT).show();
                    editText_last_name.requestFocus(editText_last_name.getText().toString().length());
                    show_keyboard(this);

                }
            }else {

                txtv_msg_error.setText("Entrez le nom S.V.P !.");
                Toast.makeText(this, "Entrez le nom S.V.P !.", Toast.LENGTH_SHORT).show();
                editText_first_name.requestFocus(editText_first_name.getText().toString().length());
                show_keyboard(this);

            }

        });

        editText_confirm_password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                btn_signup.callOnClick();

            }
            return false;
        });

        editText_first_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(getCurrentFocus().getId() != editText_first_name.getId()){

                    try {

                        editText_account.requestFocus(editText_account.getText().toString().length());

                    }catch(Exception e){

                        e.printStackTrace();

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText_last_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(getCurrentFocus().getId() != editText_last_name.getId()){

                    try {

                        editText_account.requestFocus(editText_account.getText().toString().length());

                    }catch(Exception e){

                        e.printStackTrace();

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        editText_confirm_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(getCurrentFocus().getId() != editText_confirm_password.getId()){

                    try{

                        editText_confirm_password.requestFocus(editText_confirm_password.getText().toString().length());

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

    public static void hide_keyboard(Activity activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();

        if (view == null) {

            view = new View(activity);

        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }


    public static void show_keyboard(Activity activity){

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);

    }



    @Override
    public void onBackPressed() {

        hide_keyboard(this);

        if(closeToast) {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Cliquez à nouveau pour fermer l'application", Toast.LENGTH_SHORT).show();
            mHandler.postDelayed(mRunnable, 2000);

        }else{

            super.onBackPressed();

        }

    }

    //Timer => double click for close application
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

}