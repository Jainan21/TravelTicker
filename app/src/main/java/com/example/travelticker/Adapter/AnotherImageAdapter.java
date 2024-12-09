package com.example.travelticker.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelticker.Model.MenuPost;
import com.example.travelticker.R;

import java.util.ArrayList;

public class AnotherImageAdapter extends RecyclerView.Adapter<AnotherImageAdapter.AnotherImageViewHolder> {
    Context context;
    ArrayList<Uri> list;
    MenuPost menuPost;
    OnDataChangedListener listener;

    public interface OnDataChangedListener {
        void onDataChanged();
    }

    // Cập nhật constructor để nhận listener
    public AnotherImageAdapter(Context context, ArrayList<Uri> list, MenuPost menuPost, OnDataChangedListener listener) {
        this.context = context;
        this.list = list;
        this.menuPost = menuPost;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AnotherImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_another_img_post, parent, false);
        return new AnotherImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnotherImageViewHolder holder, int position) {
        Uri img = list.get(position);

        Glide.with(context).load(img).into(holder.imgAnotherImage);

        holder.btnDeleteAnotherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = holder.getAdapterPosition();
                if (index != RecyclerView.NO_POSITION && !list.isEmpty()) {
                    Uri removedUri = list.remove(index);

                    // Cập nhật lại MenuPost
                    menuPost.getListAnotherImage().remove(removedUri);
                    menuPost.setQuantity(menuPost.getQuantity() - 1);
                    menuPost.setListAnotherImage(list);
                    if (listener != null) {
                        listener.onDataChanged();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AnotherImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAnotherImage;
        Button btnDeleteAnotherImage;

        public AnotherImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnotherImage = itemView.findViewById(R.id.imgAnotherImage);
            btnDeleteAnotherImage = itemView.findViewById(R.id.btnDeleteAnotherImage);
        }
    }
}