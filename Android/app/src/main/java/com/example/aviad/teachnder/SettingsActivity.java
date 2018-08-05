package com.example.aviad.teachnder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aviad.teachnder.Card.Card;
import com.example.aviad.teachnder.Server.GetDataFromServer;
import com.example.aviad.teachnder.Server.ReadDataFromServer;
import com.example.aviad.teachnder.Server.ServerHelper;


public class SettingsActivity extends AppCompatActivity {
    private EditText edtName, edtPhone, edtAbout;
    private Button btnBack, btnConfirm;
    private ImageView imgProfileImage;

    private String userId, name, phone, profileImageUrl, userType, about;

    private Uri resultUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        init();
        getData();
    }


    private void init() {

        edtName =  findViewById(R.id.edtName);
        edtPhone =  findViewById(R.id.edtPhone);
        edtAbout = findViewById(R.id.edtAbout);

        imgProfileImage = findViewById(R.id.ImgProfileImage);

        btnBack =  findViewById(R.id.btnBack);
        btnConfirm = findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); return; }
        });

    }

    private void getData() {

        Bundle bundle = getIntent().getExtras();
        if(bundle.getInt(ServerHelper.FROM_WHICH_ACTIVITY) == ServerHelper.INTENT_FROM_REGISTER)
        {
           edtName.setText(bundle.getString(ServerHelper.NAME));
           return;

        } else {

            ReadDataFromServer readDataFromServer = new ReadDataFromServer(getApplicationContext());


            SharedPreferences sharedPreferences =  getSharedPreferences("myRef", Context.MODE_PRIVATE);
            String email = sharedPreferences.getString("email" , "none");

            if(!email.equals("none")) {

                readDataFromServer.getUserByEmail(email, new ReadDataFromServer.IResulUser() {
                    @Override
                    public void getResult(Card isSucceed) {
                        if(isSucceed!=null) {
                            edtAbout.setText(isSucceed.getAbout());
                            edtPhone.setText(isSucceed.getPhone());
                            edtName.setText(isSucceed.getName());
                        }
                    }
                });

            }




        }




    }

    private void saveData() {

        boolean validate = true;

        if(edtPhone.getText()==null) {
            Toast.makeText(getApplicationContext(),"please enter phone number",Toast.LENGTH_LONG).show();
            edtPhone.setBackgroundColor(Color.RED);
            validate = false;
        }
        if(edtName.getText()==null) {
            Toast.makeText(getApplicationContext(),"please enter name",Toast.LENGTH_LONG).show();
            edtName.setBackgroundColor(Color.RED);
            validate = false;
        }
        if(edtAbout.getText()==null) {
            Toast.makeText(getApplicationContext(),"please enter about",Toast.LENGTH_LONG).show();
            edtAbout.setBackgroundColor(Color.RED);
            validate = false;
        }

        if(!validate) {
            return;
        }



        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        String about = edtAbout.getText().toString();


        ReadDataFromServer readDataFromServer = new ReadDataFromServer(getApplicationContext());

        ReadDataFromServer.IResult iResult =new ReadDataFromServer.IResult() {
            @Override
            public void getResult(boolean isSucceed) {
                if(isSucceed) {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
            }
        };
        readDataFromServer.addMoreInformation(name,phone,about,iResult);
    }

    //TODO: VALIDATE DATA BEFORE SAVING => V

    //TODO: SAVE => V

    //TODO: GET DATA
}
