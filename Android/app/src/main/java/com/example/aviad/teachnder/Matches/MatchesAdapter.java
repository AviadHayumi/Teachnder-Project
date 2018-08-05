package com.example.aviad.teachnder.Matches;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.aviad.teachnder.R;

import java.util.List;


public class MatchesAdapter extends RecyclerView.Adapter<MatchesViewHolder> {

    private List<MatchesObject> matchList;
    private Context context;

    public MatchesAdapter(List<MatchesObject> matchList, Context context) {
        this.matchList = matchList;
        this.context = context;
    }

    @Override
    public MatchesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(context).inflate(R.layout.item_matches, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        MatchesViewHolder rcv = new MatchesViewHolder(layoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(MatchesViewHolder holder, int position) {


        holder.mMatchID.setText(matchList.get(position).getUserID());
        holder.mMatchName.setText(matchList.get(position).getUserName());
        if (!matchList.get(position).getProfileImageUrl().equals("default")) {
           // Glide.with(context).load(matchList.get(position).getProfileImageUrl()).into(holder.mMatchImage);
        }
    }



    @Override
    public int getItemCount() {
        return this.matchList.size();
    }


}