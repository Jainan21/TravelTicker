package com.example.travelticker.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.R;

public class ThongBaoAdapter extends RecyclerView.ViewHolder {
    ImageView img_1;
    TextView tv_1, tv_2, tv_3;

    public ThongBaoAdapter(@NonNull View itemView) {
        super(itemView);
        img_1 = itemView.findViewById(R.id.img_1);
        tv_1 = itemView.findViewById(R.id.tv_1);
        tv_2 = itemView.findViewById(R.id.tv_2);
        tv_3 = itemView.findViewById(R.id.tv_3);

    }
}
