package com.example.aviad.teachnder.Matches;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aviad.teachnder.Chats.ChatActivity;
import com.example.aviad.teachnder.R;

public class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mMatchID;
    public TextView mMatchName;
    public ImageView mMatchImage;


    public MatchesViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchID = itemView.findViewById(R.id.txvMatchID);
        mMatchName = itemView.findViewById(R.id.txvMatchName);
        mMatchImage = itemView.findViewById(R.id.imgMatch);


    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(v.getContext(), ChatActivity.class);
        Bundle b = new Bundle();
        b.putString("matchId",mMatchID.getText().toString());
        intent.putExtras(b);

        v.getContext().startActivity(intent);
    }
}
