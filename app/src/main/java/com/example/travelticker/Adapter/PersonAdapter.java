package com.example.travelticker.Adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.R;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.holder> {
    String data[];
    Drawable icon[];

    public PersonAdapter(String[] data, Drawable[] icon){
        this.data = data;
        this.icon = icon;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_tttk, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.tv.setText(data[position]);

        if (position < icon.length){
            holder.img.setImageDrawable(icon[position]);
        }

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    class holder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView tv;
        ImageButton imgbt;

        public holder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.icon);
            tv = itemView.findViewById(R.id.title);
            imgbt = itemView.findViewById(R.id.next1);
        }
    }
}
