package com.example.travelticker.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.DAO.dbDAO;
import com.example.travelticker.Model.dichVu;
import com.bumptech.glide.Glide;
import com.example.travelticker.R;

import java.util.ArrayList;


public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceAdapterHolder> {
    private final Context c;
    private final ArrayList<dichVu> list;

    dbDAO dbDAO = new dbDAO();

    public ServiceAdapter(Context c, ArrayList<dichVu> list) {
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
        dichVu service = list.get(i);


        dbDAO.loadSvgFromUrl(service.getAnh(), holder.imgDichVu);
        holder.txtTenDichVu.setText(service.getTen());
        holder.cardDichVu.setBackgroundColor(Color.parseColor("#" + service.getNen()));
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

