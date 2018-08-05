package com.example.aviad.teachnder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.aviad.teachnder.Card.Card;
import com.example.aviad.teachnder.Card.MyCustomArrayAdapter;
import com.example.aviad.teachnder.Find.FindActivity;
import com.example.aviad.teachnder.LoginAndRegister.ChooseLoginOrRegistarationActivity;
import com.example.aviad.teachnder.Matches.MatchesActivity;
import com.example.aviad.teachnder.Server.GetDataFromServer;
import com.example.aviad.teachnder.Server.ReadDataFromServer;
import com.example.aviad.teachnder.Server.ServerHelper;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ReadDataFromServer readDataFromServer;


    MyCustomArrayAdapter arrayAdapter;
    List<Card> cards;
    private String userType;
    private String oppositeType;
    private String userEmail;
    private static final String TAG = "MainActivity";
    SwipeFlingAdapterView flingContainer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getOppositeTypeUsers();



        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                cards.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //TODO set Nope

                Card card = ((Card)dataObject);
                readDataFromServer.setNope(userEmail, card.getEmail(), new ReadDataFromServer.IResult() {
                    @Override
                    public void getResult(boolean isSucceed) {
                        if(!isSucceed) {
                            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

            @Override
            public void onRightCardExit(Object dataObject) {

                //TODO set YEP
                final Card card = ((Card)dataObject);
                readDataFromServer.setYep(userEmail, card.getEmail(), new ReadDataFromServer.IResult() {
                    @Override
                    public void getResult(boolean isSucceed) {
                        if(!isSucceed) {
                            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
                        } else {
                            //TODO check if Match
                            readDataFromServer.isContainYep(card.getEmail(), userEmail, new ReadDataFromServer.IResult() {
                                @Override
                                public void getResult(boolean isSucceed) {
                                    if(isSucceed) {

                                        readDataFromServer.createChat(userEmail, card.getEmail(), new ReadDataFromServer.IResultChat() {
                                            @Override
                                            public void getResult(boolean isSucceed, final String chatId) {
                                                if(isSucceed) {
                                                    final String id = chatId;

                                                    readDataFromServer.setMatch(userEmail, card.getEmail(), chatId, new ReadDataFromServer.IResult() {
                                                        @Override
                                                        public void getResult(boolean isSucceed) {

                                                            readDataFromServer.setMatch(card.getEmail(), userEmail, chatId, new ReadDataFromServer.IResult() {
                                                                @Override
                                                                public void getResult(boolean isSucceed) {

                                                                    Toast.makeText(getApplicationContext(),"New Match !" , Toast.LENGTH_LONG).show();

                                                                }
                                                            });

                                                        }
                                                    });

                                                }



                                    }
                                });


                                    }
                                }
                            });
                        }
                    }
                });


            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // here we ask for more data here

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });


        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, final Object dataObject) {
                Card obj = (Card) dataObject;
                String about = obj.getAbout();
                if(about==null) {
                    about = "user has not inset about information";
                }

                Toast.makeText(MainActivity.this,about,Toast.LENGTH_LONG).show();

            }


        });


    }



    public void getOppositeTypeUsers() {


        final ReadDataFromServer.IResultList iResultList = new ReadDataFromServer.IResultList() {
            @Override
            public void getResult(ArrayList<Card> isSucceed) {
                if(isSucceed!=null && isSucceed.size()>0 ) {

                    for (int i=0 ; i<isSucceed.size(); i++) {
                        final Card card = isSucceed.get(i);

                        isSeenBefore(card.getEmail(), new ReadDataFromServer.IResult() {
                            @Override
                            public void getResult(boolean isSucceed) {

                                if(!isSucceed) {

                                    cards.add(card);
                                    arrayAdapter.notifyDataSetChanged();


                                }

                            }
                        });
                        }

              //      }

                }
            }
        };


        switch (userType) {
            case  "client" :
                readDataFromServer.getBusinessOwners(new ReadDataFromServer.IResultList() {
                    @Override
                    public void getResult(ArrayList<Card> isSucceed) {
                        readDataFromServer.getBusinessOwners(iResultList);
                    }
                });
                break;

            case "business owner" :

                readDataFromServer.getClient(new ReadDataFromServer.IResultList() {
                    @Override
                    public void getResult(ArrayList<Card> isSucceed) {
                        readDataFromServer.getClient(iResultList);
                    }
                });
                break;
        }

    }


    public void onSettingClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        intent.putExtra(ServerHelper.FROM_WHICH_ACTIVITY, 3);
        startActivity(intent);
    }

    public void goToMatches(View view) {


        Intent intent = new Intent(getApplicationContext(), MatchesActivity.class);
        startActivity(intent);

    }



    public void init() {

        readDataFromServer = new ReadDataFromServer(getApplicationContext());

        //TODO : check user type from shared pref => V
        SharedPreferences sharedPreferences =  getSharedPreferences("myRef", Context.MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email" , "none");
        userType = sharedPreferences.getString("type" , "none");

        switch (userType){
            case "business owner" : oppositeType ="client";
                break;

            case "client": oppositeType = "business owner";
                break;
        }

        cards = new ArrayList<>();
        arrayAdapter = new MyCustomArrayAdapter(getApplicationContext(),R.layout.item, cards);
        flingContainer = findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);

    }





    public void isSeenBefore(final String otherEmail , final ReadDataFromServer.IResult iResult) {

        readDataFromServer.isContainYep(userEmail, otherEmail, new ReadDataFromServer.IResult() {
            @Override
            public void getResult(boolean isSucceed) {
                if(isSucceed) {

                    final boolean YepsResult = isSucceed;

                    readDataFromServer.isContainNope(userEmail, otherEmail, new ReadDataFromServer.IResult() {
                        @Override
                        public void getResult(boolean isSucceed) {
                                iResult.getResult(isSucceed  || YepsResult);
                        }
                    });
                }
                else
                {
                    iResult.getResult(false);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout :
                if (readDataFromServer.logout()) {

                    Intent intent = new Intent(getApplicationContext(), ChooseLoginOrRegistarationActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "error logout", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.find :
                Intent intent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(intent);
                break;
            case R.id.about :
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setPositiveButton("Got it",
                        new DialogInterface.OnClickListener() {

                            //here we create the document and insert the data to the database
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });

                alertDialogBuilder.setTitle("About the app")
                        .setMessage("if you are a teacher this app will help you find stuends for private lessons , and if you are a student this app will help you find the best teachers.\n" +
                                "\n" +
                                "In this app you get all the potential teachers.\n" +
                                "\n" +
                                "if you are intersted in this teacher just swipe right and if not swipe left. if you want to get more information about the teacher just tap on him.\n" +
                                "\n" +
                                "if there is a connection , you would be able to chat with your teacher , set price and etc..\n" +
                                "\n" +
                                " also you have the option You have the option to search for students and teachers by category, for example English.\n" +
                                "\n " ).show();

                break;
        }





        return super.onOptionsItemSelected(item);
    }
}
