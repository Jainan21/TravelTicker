package com.example.travelticker.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.travelticker.Model.Service;
import com.example.travelticker.R;
import com.example.travelticker.TrangChu;

import java.util.ArrayList;

public class ServiceAdapter extends BaseAdapter{
    private final Context c;
    private final ArrayList<Service> list;

    public ServiceAdapter(Context c, ArrayList<Service> list) {
        this.c = c;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inf = ((TrangChu) c).getLayoutInflater();
        view = inf.inflate(R.layout.item_dichvu, null);

        ImageView imgDichVu = view.findViewById(R.id.imgDichVu);
        TextView txtTenDichVu = view.findViewById(R.id.txtTenDichVu);
        CardView cardDichVu = view.findViewById(R.id.cardView);

//        imgDichVu.setImageResource(list.get(i).getIconService());
        txtTenDichVu.setText(list.get(i).getNameService());
        cardDichVu.setBackground(Drawable.createFromPath("#"+list.get(i).getColorCode()));

        return view;
    }
}