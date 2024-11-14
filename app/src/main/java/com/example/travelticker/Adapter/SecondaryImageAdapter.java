package com.example.travelticker.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelticker.Model.Image;
import com.example.travelticker.Model.Service;
import com.example.travelticker.R;
import com.example.travelticker.TrangChu;

import java.util.ArrayList;

public class SecondaryImageAdapter extends BaseAdapter {
    private final Context c;
    private final ArrayList<Image> list;

    public SecondaryImageAdapter(Context c, ArrayList<Image> list) {
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
        view = inf.inflate(R.layout.item_image, null);

        ImageView item_image = view.findViewById(R.id.item_image);

        return view;
    }
}
