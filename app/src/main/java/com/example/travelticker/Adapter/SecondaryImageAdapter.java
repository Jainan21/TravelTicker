package com.example.travelticker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.R;

import java.util.ArrayList;

public class SecondaryImageAdapter extends RecyclerView.Adapter<SecondaryImageAdapter.SecondaryImageHolder> {
    private final Context c;
    private final ArrayList<Integer> list;

    public SecondaryImageAdapter(Context c, ArrayList<Integer> list) {
        this.c = c;
        this.list = list;
    }

    @NonNull
    @Override
    public SecondaryImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.item_image, parent, false);
        return new SecondaryImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SecondaryImageHolder holder, int i) {
        Integer pic = list.get(i);
        holder.img.setImageResource(pic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class SecondaryImageHolder extends RecyclerView.ViewHolder {
        public ImageView img;

        public SecondaryImageHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.item_image);
        }
    }
}
