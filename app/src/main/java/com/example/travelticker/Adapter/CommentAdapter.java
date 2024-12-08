package com.example.travelticker.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelticker.DAO.UserDbDAO;
import com.example.travelticker.Model.Comment;
import com.example.travelticker.Model.User;
import com.example.travelticker.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{
    Context c;
    ArrayList<Comment> list;
    UserDbDAO userDbDAO;

    public CommentAdapter(Context c, ArrayList<Comment> list) {
        this.c = c;
        this.list = list;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.layout_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int i) {
        Comment cmt = list.get(i);

        String userId = cmt.getId();

        Glide.with(holder.imgAvtComment.getContext()).load(cmt.getLinkAvt()).into(holder.imgAvtComment);
        holder.txtUsernameComment.setText(cmt.getUsername());
        holder.txtContentComment.setText(cmt.getContent());
        holder.txtTimeComment.setText(cmt.getTime());
        holder.txtRatingComment.setText(cmt.getRating());

        userDbDAO = new UserDbDAO();
        userDbDAO.getUserById(userId, new UserDbDAO.UserCallBack() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    // Cập nhật thông tin người dùng vào giao diện
                    holder.txtUsernameComment.setText(user.getName());

                    // Tải ảnh đại diện người dùng (nếu có)
                    String avatarUrl = user.getAvatarUrl();
                    if (avatarUrl != null) {
                        Glide.with(holder.imgAvtComment.getContext())
                                .load(avatarUrl)
                                .into(holder.imgAvtComment);
                    } else {
                        // Nếu không có avatar, dùng ảnh mặc định
                        Glide.with(holder.imgAvtComment.getContext())
                                .load(R.drawable.avatar) // ảnh mặc định
                                .into(holder.imgAvtComment);
                    }
                }

            }

            @Override
            public void onError(String error) {
                Toast.makeText(c, "Lỗi khi lấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView txtRatingComment, txtUsernameComment, txtContentComment, txtTimeComment;
        ImageView imgAvtComment;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRatingComment = itemView.findViewById(R.id.txtRatingComment);
            txtUsernameComment = itemView.findViewById(R.id.txtUsernameComment);
            txtContentComment = itemView.findViewById(R.id.txtContentComment);
            txtTimeComment = itemView.findViewById(R.id.txtTimeComment);
            imgAvtComment = itemView.findViewById(R.id.imgAvtUserComment);
        }
    }
}
