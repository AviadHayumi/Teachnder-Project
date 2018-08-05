package com.example.aviad.teachnder.LoginAndRegister;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aviad.teachnder.LoginAndRegister.LoginActivity;
import com.example.aviad.teachnder.LoginAndRegister.RegistrationActivity;
import com.example.aviad.teachnder.MainActivity;
import com.example.aviad.teachnder.R;


public class ChooseLoginOrRegistarationActivity extends AppCompatActivity {

    private Button btnRegister , btnLogin ;

    void init() {
        btnLogin = findViewById(R.id.btnLogin_login_activity);
        btnRegister = findViewById(R.id.btnRegister_registraion_activity);


        SharedPreferences sharedPreferences =  getSharedPreferences("myRef", Context.MODE_PRIVATE);

        //check if user is already logged in by the shared pref , if yes go to the main activity , if not stay here
        String email = sharedPreferences.getString("email" , "none");
        if(!email.equals("none")) {

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish(); return;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login_or_registaration);

        init();
    }

    public void GoToLogin(View view) {
        startActivity(new Intent( getApplicationContext() ,LoginActivity.class));
    }

    public void GoToRegister(View view) {
        startActivity(new Intent( getApplicationContext() ,RegistrationActivity.class));
    }

}
