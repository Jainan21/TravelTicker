package com.example.travelticker.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelticker.Model.Post;
import com.example.travelticker.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder>{
    ArrayList<Post> list;
    Context context;

    public PostAdapter(ArrayList<Post> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sanpham_view_holder, parent, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Post item = list.get(position);

        holder.txtTomTat.setText(item.getNoiDung());
        holder.txtTieuDe.setText(item.getTieuDe());
        holder.txtDiaChi.setText(item.getDiaChi());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class PostHolder extends RecyclerView.ViewHolder {
        public TextView txtTieuDe, txtTomTat, txtDiaChi;
        public ImageView imgPost;
        public Button btnHasFavo;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            this.btnHasFavo = itemView.findViewById(R.id.btnHasFavo);
            this.imgPost = itemView.findViewById(R.id.imgPost);
            this.txtTieuDe = itemView.findViewById(R.id.txtTieuDe);
            this.txtDiaChi = itemView.findViewById(R.id.txtDiaChi);
            this.txtTomTat = itemView.findViewById(R.id.txtTomTat);
        }
    }
}
