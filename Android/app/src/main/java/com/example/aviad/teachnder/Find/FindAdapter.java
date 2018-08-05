package com.example.aviad.teachnder.Find;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aviad.teachnder.Card.Card;
import com.example.aviad.teachnder.Chats.ChatObject;
import com.example.aviad.teachnder.Chats.ChatViewHolders;
import com.example.aviad.teachnder.R;

import java.util.List;

public class FindAdapter extends RecyclerView.Adapter<FindViewHolder> {


    private List<Card> usersList;
    private Context context;

    public FindAdapter(List<Card> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
    }

    @Override
    public FindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        FindViewHolder rcv = new FindViewHolder(layoutView);

        return rcv;

    }

    @Override
    public void onBindViewHolder(FindViewHolder holder, int position) {

        holder.txvPhone.setText(usersList.get(position).getPhone());
        holder.txvName.setText(usersList.get(position).getName());
        holder.txvAbout.setText(usersList.get(position).getAbout());
        holder.txvEmail.setText(usersList.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}
