package com.example.aviad.teachnder.Find;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.aviad.teachnder.Card.Card;
import com.example.aviad.teachnder.Chats.ChatActivity;
import com.example.aviad.teachnder.Chats.ChatObject;
import com.example.aviad.teachnder.R;
import com.example.aviad.teachnder.Server.ReadDataFromServer;

import java.util.ArrayList;
import java.util.List;

public class FindActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mChatLayoutManager;
    EditText edtFind;
    FindAdapter findAdapter;
    List<Card> userList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        edtFind = findViewById(R.id.edtFind);
        userList = new ArrayList<>();


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewFind);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);
        mChatLayoutManager = new LinearLayoutManager(FindActivity.this);
        mRecyclerView.setLayoutManager(mChatLayoutManager);

        findAdapter = new FindAdapter(userList,getApplicationContext());
        mRecyclerView.setAdapter(findAdapter);

    }

    public void doFind(View view) {
        if(edtFind.getText()!=null) {
            //TODO: find
            userList.clear();
            ReadDataFromServer readDataFromServer = new ReadDataFromServer(getApplicationContext());
            readDataFromServer.findWithAbout(edtFind.getText().toString(), new ReadDataFromServer.IResultList() {
                @Override
                public void getResult(ArrayList<Card> isSucceed) {
                    for (int i=0; i<isSucceed.size(); i++) {
                        userList.add(isSucceed.get(i));
                        findAdapter.notifyDataSetChanged();
                    }
                }
            });

        }
    }
}
