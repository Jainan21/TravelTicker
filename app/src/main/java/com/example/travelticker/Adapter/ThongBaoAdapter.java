package com.example.travelticker.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.Model.ThongBaoModel;
import com.example.travelticker.R;

import java.util.ArrayList;

public class ThongBaoAdapter extends RecyclerView.Adapter<ThongBaoAdapter.holder> {

    ArrayList<ThongBaoModel> data;

    public ThongBaoAdapter(ArrayList<ThongBaoModel> data){
        this.data = data;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_thongbao, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongBaoAdapter.holder holder, int position) {
        holder.tv_1.setText(data.get(position).getTv_1());
        holder.tv_2.setText(data.get(position).getTv_2());
        holder.tv_3.setText(data.get(position).getTv_3());
        holder.img_1.setImageResource(data.get(position).getImgTB());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class holder extends RecyclerView.ViewHolder{
        ImageView img_1;
        TextView tv_1, tv_2, tv_3;

        public holder(@NonNull View itemView) {
            super(itemView);
            img_1 = itemView.findViewById(R.id.img_1);
            tv_1 = itemView.findViewById(R.id.tv_1);
            tv_2 = itemView.findViewById(R.id.tv_2);
            tv_3 = itemView.findViewById(R.id.tv_3);
        }
    }
}
