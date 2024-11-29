package com.example.travelticker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.Model.MenuPost;
import com.example.travelticker.Model.dichVu;
import com.example.travelticker.R;

import java.util.ArrayList;

public class DistrictionAdapter extends RecyclerView.Adapter<DistrictionAdapter.DistrictionViewHolder>{
    Context context;
    ArrayList<dichVu> list;
    OnItemClickListener listener;
    MenuPost menuPost;

    public interface OnItemClickListener {
        void onItemClick( int position);
    }

    public DistrictionAdapter(Context context, ArrayList<dichVu> list, MenuPost menuPost) {
        this.context = context;
        this.list = list;
        this.menuPost = menuPost;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public DistrictionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.distriction_item, parent, false);
        return new DistrictionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictionViewHolder holder, int position) {
        dichVu dv = list.get(position);

        holder.chkDis.setChecked(dv.isSelected());
        holder.txtDesName.setText(dv.getName());

        if (menuPost != null) {
            holder.chkDis.setOnCheckedChangeListener((compoundButton, b) -> {
                dv.setSelected(b);
                menuPost.addSeletedImage(dv);
            });
        }else {
            holder.chkDis.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DistrictionViewHolder extends RecyclerView.ViewHolder {
        TextView txtDesName;
        ImageView imgDistriction;
        CheckBox chkDis;

        public DistrictionViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDesName = itemView.findViewById(R.id.txtDesName);
            chkDis = itemView.findViewById(R.id.chkDis);
            imgDistriction = itemView.findViewById(R.id.imgDistriction);

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
