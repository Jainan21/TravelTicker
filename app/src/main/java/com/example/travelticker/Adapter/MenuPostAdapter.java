package com.example.travelticker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.Model.MenuPost;
import com.example.travelticker.R;

import java.util.ArrayList;

public class MenuPostAdapter extends RecyclerView.Adapter<MenuPostAdapter.MenuPostViewHolder>{
    Context context;
    ArrayList<MenuPost> list;
    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int poisition);
    }

    public MenuPostAdapter(Context context, ArrayList<MenuPost> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_post_view_holder, parent, false);
        return new MenuPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuPostViewHolder holder, int position) {
        MenuPost item = list.get(position);
        holder.txtTitleMenuPost.setText(item.getTitle());
        if (item.getIconUrl() == 0){
            holder.txtIconMenu.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_add_location_alt_24, 0, 0, 0);
        }else if (item.getIconUrl() == 1){
            holder.txtIconMenu.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
        }else if (item.getIconUrl() == 2){
            holder.txtIconMenu.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.outline_collections_24, 0, 0, 0);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MenuPostViewHolder extends RecyclerView.ViewHolder {
        TextView txtIconMenu, txtTitleMenuPost, txtBtnAddMenuPost;
        RecyclerView rcvAnotherImgPost;

        public MenuPostViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIconMenu = itemView.findViewById(R.id.txtIconMenu);
            txtTitleMenuPost = itemView.findViewById(R.id.txtTitleMenuPost);
            txtBtnAddMenuPost = itemView.findViewById(R.id.txtBtnAddMenuPost);
            rcvAnotherImgPost = itemView.findViewById(R.id.rcvAnotherImgPost);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

    }
}
