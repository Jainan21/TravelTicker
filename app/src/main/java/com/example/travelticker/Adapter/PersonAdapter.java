package com.example.travelticker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.CaiDat;
import com.example.travelticker.R;
import com.example.travelticker.dangnhap;
import com.google.firebase.auth.FirebaseAuth;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.holder> {
    Context c;
    String data[];
    Drawable icon[];

    public PersonAdapter(Context c,String[] data, Drawable[] icon){
        this.c = c;
        this.data = data;
        this.icon = icon;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.item_tttk, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.tv.setText(data[position]);
        holder.img.setImageDrawable(icon[position]);

        holder.itemView.setOnClickListener(v -> {
            if (data[position].equals("Cài đặt")) {
                Intent intent = new Intent(holder.itemView.getContext(), CaiDat.class);
                holder.itemView.getContext().startActivity(intent);
            } else if (data[position].equals("Đăng xuất")) {
                new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle("Xác nhận")
                        .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
                        .setPositiveButton("Đăng xuất", (dialog, which) -> {
                            // đăng xuất
                            FirebaseAuth.getInstance().signOut();

                            Intent intent = new Intent(holder.itemView.getContext(), dangnhap.class);
                            holder.itemView.getContext().startActivity(intent);
                            Toast.makeText(holder.itemView.getContext(), "Đã đăng xuất!", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class holder extends RecyclerView.ViewHolder{
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
