package com.example.aviad.teachnder.Find;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.aviad.teachnder.R;

public class FindViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txvEmail;
    public TextView txvName;
    public TextView txvAbout;
    public TextView txvPhone;


    public FindViewHolder(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);

        txvEmail = itemView.findViewById(R.id.txvEmail);
        txvName = itemView.findViewById(R.id.txvName);
        txvAbout = itemView.findViewById(R.id.txvAbout);
        txvPhone = itemView.findViewById(R.id.txvPhone);

    }

    @Override
    public void onClick(View view) {
    }
}
