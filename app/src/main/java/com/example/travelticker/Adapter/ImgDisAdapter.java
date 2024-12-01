package com.example.travelticker.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelticker.Model.dichVu;
import com.example.travelticker.R;

import java.util.ArrayList;

public class ImgDisAdapter extends RecyclerView.Adapter<ImgDisAdapter.ImgDisViewHolder>{
    Context context;
    ArrayList<dichVu> listImg;

    public ImgDisAdapter(Context context, ArrayList<dichVu> listImg) {
        this.context = context;
        this.listImg = listImg;
    }

    @NonNull
    @Override
    public ImgDisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_img_dis_post, parent, false);
        return new ImgDisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImgDisViewHolder holder, int position) {
        dichVu dv = listImg.get(position);
        holder.cvItemDisPost.setCardBackgroundColor(Color.parseColor("#" + dv.getNen()));
        Glide.with(context)
                .load(dv.getAnh())
                .into(holder.imgDisChild);
    }

    @Override
    public int getItemCount() {
        return listImg.size();
    }

    public class ImgDisViewHolder extends RecyclerView.ViewHolder{
        ImageView imgDisChild;
        CardView cvItemDisPost;

        public ImgDisViewHolder(@NonNull View itemView) {
            super(itemView);

            imgDisChild = itemView.findViewById(R.id.imgDisChild);
            cvItemDisPost = itemView.findViewById(R.id.cvItemDisPost);
        }
    }
}
