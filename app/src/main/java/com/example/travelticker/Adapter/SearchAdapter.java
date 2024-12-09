package com.example.travelticker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelticker.Model.Post;
import com.example.travelticker.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{

    ArrayList<Post> list;
    ArrayList<Post> listLikePost;
    Context context;

    public SearchAdapter(ArrayList<Post> list, Context context, ArrayList<Post> listLikePost) {
        this.list = list;
        this.context = context;
        this.listLikePost = listLikePost;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sanpham_view_holder, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Post item = list.get(position);

        Glide.with(context).load(item.getImg()).into(holder.imgPost);
        holder.txtTomTat.setText(item.getNoiDung());
        holder.txtTieuDe.setText(item.getTieuDe());

        if (listLikePost != null){
            for (int i = 0; i < listLikePost.size(); i++){
                if (listLikePost.get(i).getIdBaiDang() == item.getIdBaiDang()){
                    holder.btnHasFavo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.favorite_24, 0, 0,0 );
                    break;
                }else {
                    holder.btnHasFavo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_fav_trangchu, 0, 0,0 );
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTieuDe, txtTomTat, txtDiaChi;
        public ImageView imgPost;
        public Button btnHasFavo;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            this.btnHasFavo = itemView.findViewById(R.id.btnHasFavo);
            this.imgPost = itemView.findViewById(R.id.imgPost);
            this.txtTieuDe = itemView.findViewById(R.id.txtTieuDe);
            this.txtDiaChi = itemView.findViewById(R.id.txtDiaChi);
            this.txtTomTat = itemView.findViewById(R.id.txtTomTat);
        }
    }
}
