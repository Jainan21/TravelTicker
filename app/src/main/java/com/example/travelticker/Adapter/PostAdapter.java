package com.example.travelticker.Adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.travelticker.DAO.LikeDAO;
import com.example.travelticker.Model.Post;
import com.example.travelticker.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder>{
    ArrayList<Post> list;
    Context context;
    OnLikeClickListener listener;
    String userID;
    LikeDAO likeDAO;

    public interface OnLikeClickListener {
        void onLikeClick(int position, boolean isLiked);
    }

    public PostAdapter(ArrayList<Post> list, Context context, String userID) {
        this.list = list;
        this.context = context;
        this.userID = userID;
        this.likeDAO = new LikeDAO();
    }

    public void setOnLikeClickListener(OnLikeClickListener listener) {
        this.listener = listener;
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
        Glide.with(context).load(item.getImg()).into(holder.imgPost);
        holder.btnHasFavo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.favorite_24, 0, 0, 0);

        String postID = item.getIdBaiDang();

        holder.btnHasFavo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setMessage("Bạn có chắc muốn bỏ yêu thích bài viết này không?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                likeDAO.unLike(userID, postID, new LikeDAO.onUnLikeListener() {
                                    @Override
                                    public void onSuccess() {
                                        int index = holder.getAdapterPosition();
                                        list.remove(index);
                                        notifyItemRemoved(index);
                                        Toast.makeText(context, "Đã bỏ yêu thích", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure() {
                                        Toast.makeText(context, "Lỗi khi bỏ yêu thích", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

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
