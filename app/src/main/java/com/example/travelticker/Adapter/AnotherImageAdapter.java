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

public class AnotherImageAdapter extends RecyclerView.Adapter<AnotherImageAdapter.AnotherImageViewHolder>{
    Context context;
    ArrayList<Uri> list;
    MenuPost menuPost;

    public AnotherImageAdapter(Context context, ArrayList<Uri> list, MenuPost menuPost) {
        this.context = context;
        this.list = list;
        this.menuPost = menuPost;
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

        //hiển thị ảnh
        Glide.with(context).load(img).into(holder.imgAnotherImage);

        //nhấn nút xóa
        holder.btnDeleteAnotherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = holder.getAdapterPosition();
                if (index != RecyclerView.NO_POSITION && !list.isEmpty()) {
                    // Lấy URI đã bị xóa
                    Uri removedUri = list.remove(index);

                    // Cập nhật lại danh sách ảnh trong MenuPost
                    menuPost.getListAnotherImage().remove(removedUri);

                    // Giảm số lượng ảnh trong MenuPost
                    menuPost.setQuantity(menuPost.getQuantity() - 1);

                    // Cập nhật lại RecyclerView
                    notifyItemRemoved(index);
                    notifyItemRangeChanged(index, list.size());

                    // Cập nhật lại danh sách của adapter trong MenuPost
                    menuPost.setListAnotherImage(list);

                    // Đảm bảo cập nhật lại thông tin số lượng ảnh trong RecyclerView cha (MenuPostAdapter)
                    // Bạn có thể gọi notifyDataSetChanged() trong RecyclerView cha nếu cần.
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AnotherImageViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAnotherImage;
        Button btnDeleteAnotherImage;

        public AnotherImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnotherImage = itemView.findViewById(R.id.imgAnotherImage);
            btnDeleteAnotherImage = itemView.findViewById(R.id.btnDeleteAnotherImage);
        }
    }
}
