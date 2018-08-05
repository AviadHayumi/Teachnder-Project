package com.example.aviad.teachnder.LoginAndRegister;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.aviad.teachnder.MainActivity;
import com.example.aviad.teachnder.R;
import com.example.aviad.teachnder.Server.ReadDataFromServer;
import com.example.aviad.teachnder.Server.ServerHelper;
import com.example.aviad.teachnder.SettingsActivity;

public class RegistrationActivity extends AppCompatActivity {


    private Button btnRegister;
    private EditText edtEmail, edtPassword,edtPassword2 ,edtNickname;
    private RadioGroup mRadioGroup;


    void init() {


        SharedPreferences sharedPreferences =  getSharedPreferences("myRef", Context.MODE_PRIVATE);

        //TODO: check if user is already logged in by the shared pref , if yes go to the main activity , if not stay here
        String email = sharedPreferences.getString("email" , "none");
        if(!email.equals("none")) {

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish(); return;
        }

        btnRegister =  findViewById(R.id.register);
        edtEmail =  findViewById(R.id.email);
        edtPassword =  findViewById(R.id.password);
        edtPassword2 =  findViewById(R.id.password2);
        edtNickname =  findViewById(R.id.edtName);
        mRadioGroup =  findViewById(R.id.radioGroup);




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();
        setListeners();


    }

    void setListeners(){




        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                if(edtPassword.getText()==null) {
                    Toast.makeText(getApplicationContext(),"Please enter password" , Toast.LENGTH_LONG).show();
                    return;
                }


                if(edtPassword2.getText()==null) {
                    Toast.makeText(getApplicationContext(),"Please enter password" , Toast.LENGTH_LONG).show();
                    return;
                }


                if(edtEmail.getText()==null) {
                    Toast.makeText(getApplicationContext(),"Please enter email" , Toast.LENGTH_LONG).show();
                    return;
                }


                if(edtNickname.getText()==null) {
                    Toast.makeText(getApplicationContext(),"Please enter name" , Toast.LENGTH_LONG).show();
                    return;
                }

                if(!edtPassword2.equals(edtPassword)) {
                    Toast.makeText(getApplicationContext(),"Password must to be equal" , Toast.LENGTH_LONG).show();
                }

                if (! ( edtEmail.getText().toString().equals(".") && edtEmail.getText().toString().equals("@") )) {
                    Toast.makeText(getApplicationContext(),"email is not valid" , Toast.LENGTH_LONG).show();

                }





                //get the id of the selected radio button preference
                int selectId = mRadioGroup.getCheckedRadioButtonId();
                //define the radio button that was selected
                final RadioButton radioButton = (RadioButton) findViewById(selectId);

                //checking if the radio button is valid
                if(radioButton.getText() == null){
               //     Toast.makeText(getApplicationContext(),"Please choose client or business owner",Toast.LENGTH_LONG).show();
                    return;
                }

                final String email = edtEmail.getText().toString();
                final String password = edtPassword.getText().toString();
                final String name = edtNickname.getText().toString();
                final String type = radioButton.getText().toString();
        //TODO : create the user and add to the shared pref the email key of the user

                ReadDataFromServer.IResult iResult = new ReadDataFromServer.IResult() {
                    @Override
                    public void getResult(boolean isSucceed) {
                        if(isSucceed) {
                            Intent intent =new Intent(getApplicationContext(),SettingsActivity.class);
                            intent.putExtra(ServerHelper.FROM_WHICH_ACTIVITY,ServerHelper.INTENT_FROM_REGISTER);
                            intent.putExtra(ServerHelper.NAME , name);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(),"Email has alredy exist" , Toast.LENGTH_LONG).show();
                        }
                    }
                };
                ReadDataFromServer readDataFromServer = new ReadDataFromServer(getApplicationContext());
                readDataFromServer.register(email,password,name,type,iResult);


            }
        });


    }


}
