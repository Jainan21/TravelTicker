package com.example.travelticker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelticker.Model.Tinh;
import com.example.travelticker.R;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationAdapterHolder>{
    private Context c;
    private ArrayList<Tinh> list;

    public LocationAdapter(Context c, ArrayList<Tinh> list) {
        this.c = c;
        this.list = list;
    }

    @NonNull
    @Override
    public LocationAdapter.LocationAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.item_location, parent, false);
        return new LocationAdapter.LocationAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.LocationAdapterHolder holder, int i) {
        Tinh tinh = list.get(i);
        Glide.with(holder.imgLocation.getContext()).load(tinh.getAnh()).into(holder.imgLocation);
        holder.txtLocation.setText(tinh.getTen());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class LocationAdapterHolder extends RecyclerView.ViewHolder {
        ImageView imgLocation;
        TextView txtLocation;
        public LocationAdapterHolder(@NonNull View itemView) {
            super(itemView);
            this.imgLocation = itemView.findViewById(R.id.imgLocation);
            this.txtLocation = itemView.findViewById(R.id.txtLocation);
        }
    }
}
