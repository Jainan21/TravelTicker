package com.example.travelticker.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelticker.Model.Service;
import com.example.travelticker.R;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceAdapterHolder> {
    private final Context c;
    private final ArrayList<Service> list;

    public ServiceAdapter(Context c, ArrayList<Service> list) {
        this.c = c;
        this.list = list;
    }

    @NonNull
    @Override
    public ServiceAdapter.ServiceAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.item_dichvu, parent, false);
        return new ServiceAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ServiceAdapterHolder holder, int i) {
        Service service = list.get(i);

        Glide.with(holder.imgDichVu.getContext()).load(service.getIconService()).into(holder.imgDichVu);
        holder.txtTenDichVu.setText(service.getNameService());
        holder.cardDichVu.setBackgroundColor(Color.parseColor("#" + service.getColorCode()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ServiceAdapterHolder extends RecyclerView.ViewHolder {
        public ImageView imgDichVu;
        public TextView txtTenDichVu;
        public LinearLayout cardDichVu;

        public ServiceAdapterHolder(@NonNull View itemView) {
            super(itemView);
            this.imgDichVu = itemView.findViewById(R.id.imgDichVu);
            this.txtTenDichVu = itemView.findViewById(R.id.txtTenDichVu);
            this.cardDichVu = itemView.findViewById(R.id.cardView);
        }
    }
}

