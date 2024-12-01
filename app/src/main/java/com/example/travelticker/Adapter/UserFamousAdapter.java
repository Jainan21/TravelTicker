package com.example.travelticker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelticker.Model.FamousUser;
import com.example.travelticker.Model.Post;
import com.example.travelticker.R;

import java.util.ArrayList;

public class UserFamousAdapter extends RecyclerView.Adapter<UserFamousAdapter.UserFamousHolder>{
    private Context c;
    private ArrayList<FamousUser> list;

    public UserFamousAdapter(Context c, ArrayList<FamousUser> list) {
        this.c = c;
        this.list = list;
    }

    @NonNull
    @Override
    public UserFamousAdapter.UserFamousHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.layout_user, parent, false);
        return new UserFamousAdapter.UserFamousHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserFamousAdapter.UserFamousHolder holder, int i) {
        FamousUser user = list.get(i);

        Glide.with(holder.imgPost.getContext()).load(user.getLinkImage()).into(holder.imgPost);
        Glide.with(holder.avtUser.getContext()).load(user.getLinkAvt()).into(holder.avtUser);
        holder.txtUsername.setText(user.getName());
        holder.txtDescription.setText(user.getDescription());
        holder.txtTitle.setText(user.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class UserFamousHolder extends RecyclerView.ViewHolder {
        ImageView imgPost, avtUser;
        TextView txtTitle, txtDescription, txtUsername;
        public UserFamousHolder(@NonNull View itemView) {
            super(itemView);
            this.imgPost = itemView.findViewById(R.id.imgPostUser);
            this.avtUser = itemView.findViewById(R.id.avtUser);
            this.txtTitle = itemView.findViewById(R.id.txtTitleUser);
            this.txtDescription = itemView.findViewById(R.id.txtDescriptionUser);
            this.txtUsername = itemView.findViewById(R.id.txtNameUser);
        }
    }
}
