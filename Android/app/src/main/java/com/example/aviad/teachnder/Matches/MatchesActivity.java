package com.example.aviad.teachnder.Matches;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.aviad.teachnder.R;
import com.example.aviad.teachnder.Server.ReadDataFromServer;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {

    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter mMatchesAdapter;
    private RecyclerView.LayoutManager mMatchesLayoutManager;
    private ReadDataFromServer readDataFromServer;
    private List<MatchesObject> matchesObjectList = new ArrayList<>();

    void init() {
        myRecyclerView = findViewById(R.id.recyclerView);
        myRecyclerView.setNestedScrollingEnabled(false);
        myRecyclerView.setHasFixedSize(true);

        mMatchesLayoutManager = new LinearLayoutManager(getApplicationContext());
        myRecyclerView.setLayoutManager(mMatchesLayoutManager);

        readDataFromServer = new ReadDataFromServer(getApplicationContext());


        mMatchesAdapter = new MatchesAdapter(matchesObjectList , getApplicationContext());

        //TODO : create list of matches and set to adapter
        readDataFromServer.getUserMatches(new ReadDataFromServer.IResultMatches() {
            @Override
            public void getResult(List<MatchesObject> isSucceed) {

                for(int i = 0 ; i<isSucceed.size() ; i++) {
                    matchesObjectList.add(isSucceed.get(i));
                    mMatchesAdapter.notifyDataSetChanged();
                }

            }
        });

        myRecyclerView.setAdapter(mMatchesAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        init();
    }
}
