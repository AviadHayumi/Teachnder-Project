package com.example.aviad.teachnder.Card;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aviad.teachnder.R;

import java.util.List;

public class MyCustomArrayAdapter extends ArrayAdapter<Card> {

    private Context context;


    public MyCustomArrayAdapter(@NonNull Context context, int resourceID , List<Card>items) {
        super(context, resourceID, items);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Card card_item = getItem(position);
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        }

        TextView txvName = convertView.findViewById(R.id.edtName);
        ImageView imageView = convertView.findViewById(R.id.image);

        txvName.setText(card_item.getName());


        imageView.setImageResource(R.mipmap.ic_launcher);


//        switch (card_item.getProfileImageURL()) {
//            case "default" :
//                imageView.setImageResource(R.mipmap.ic_launcher);
//                break;
//            default:
//                Glide.clear(imageView);
//                Glide.with(convertView.getContext()).load(card_item.getProfileImageURL()).into(imageView);
//                break;
//        }

        return convertView;
    }



}
