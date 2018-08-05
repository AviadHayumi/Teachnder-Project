package com.example.aviad.teachnder.LoginAndRegister;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aviad.teachnder.MainActivity;
import com.example.aviad.teachnder.R;
import com.example.aviad.teachnder.Server.ReadDataFromServer;

public class LoginActivity extends AppCompatActivity {


    private Button btnLogin;
    private EditText edtEmail, edtPassword;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        init();
        setListeners();

    }

    void init() {


        btnLogin =  findViewById(R.id.login);
        edtEmail =  findViewById(R.id.email);
        edtPassword =  findViewById(R.id.password);
        sharedPreferences =  getSharedPreferences("myRef",Context.MODE_PRIVATE);

        //TODO: check if user is already logged in by the shared pref , if yes go to the main activity , if not stay here
        String email = sharedPreferences.getString("email" , "none");
        if(!email.equals("none")) {

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish(); return;
        }





    }

    void setListeners() {

        ReadDataFromServer readDataFromServer = new ReadDataFromServer(getApplicationContext());
        //TODO: check if user is already logged in by the shared pref , if yes go to the main activity , if not stay here


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : here we check if the email and password are exist on the data base if yes go to the main activity , if not notify the user
                if(edtEmail.getText() != null && edtPassword.getText() !=null) {


                    ReadDataFromServer.IResult iLoginResult = new ReadDataFromServer.IResult() {
                        @Override
                        public void getResult(boolean isSucceed) {
                            if(isSucceed){
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(),"Email or Password not exists" , Toast.LENGTH_LONG).show();
                            }
                        }
                    };

                    ReadDataFromServer.login(edtEmail.getText().toString(),edtPassword.getText().toString(),iLoginResult);

                }
            }
        });

    }

}
