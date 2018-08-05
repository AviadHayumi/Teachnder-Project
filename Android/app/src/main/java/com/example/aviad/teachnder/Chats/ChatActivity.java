package com.example.aviad.teachnder.Chats;


import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aviad.teachnder.R;
import com.example.aviad.teachnder.Server.ReadDataFromServer;
import com.example.aviad.teachnder.Server.ServerHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mChatAdapter;
    private RecyclerView.LayoutManager mChatLayoutManager;
    private ArrayList<ChatObject> resultsChat;


    private ReadDataFromServer readDataFromServer;

    private EditText mSendEditText;

    private Button mSendButton;

    private String matchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle bundle = getIntent().getExtras();
        matchId= bundle.getString("matchId" , "-1");


        readDataFromServer= new ReadDataFromServer(getApplicationContext());

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);
        mChatLayoutManager = new LinearLayoutManager(ChatActivity.this);
        mRecyclerView.setLayoutManager(mChatLayoutManager);
        resultsChat = new ArrayList<>();

        mChatAdapter = new ChatAdapter(resultsChat, ChatActivity.this);
        mRecyclerView.setAdapter(mChatAdapter);

        mSendEditText = findViewById(R.id.message);
        mSendButton = findViewById(R.id.send);

        getChatMessages();

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });


    }



    private void sendMessage() {

        if(mSendEditText.getText()!=null) {
            readDataFromServer.sendMessage(mSendEditText.getText().toString(),matchId, new ReadDataFromServer.IResult() {
                @Override
                public void getResult(boolean isSucceed) {
                    if(!isSucceed) {
                        Toast.makeText(getApplicationContext(),"error" , Toast.LENGTH_LONG).show();
                    } else  {
                        resultsChat.clear();
                        getChatMessages();
                        mSendEditText.setText("");
                    }
                }
            });

        }

    }


    private void getChatMessages() {

        readDataFromServer.getMessages(matchId, new ReadDataFromServer.IResultChatMessages() {
            @Override
            public void getResult(List<ChatObject> isSucceed) {
                if(isSucceed!=null) {
                    for (int i=0 ; i<isSucceed.size(); i++) {
                        resultsChat.add(isSucceed.get(i));
                        mChatAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }



}
