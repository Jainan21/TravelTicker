package com.example.travelticker.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.Model.MenuPost;
import com.example.travelticker.Model.dichVu;
import com.example.travelticker.R;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import android.graphics.drawable.PictureDrawable;

import java.io.IOException;
import java.io.InputStream;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

        holder.cvItem.setCardBackgroundColor(Color.parseColor("#" + dv.getNen()));

        loadSvgFromUrl(dv.getAnh(), holder.imgDistriction);

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

    public void loadSvgFromUrl(final String svgUrl, final ImageView imageView) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(svgUrl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle error, for example log it
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    InputStream inputStream = response.body().byteStream();
                    try {
                        // Parse SVG from input stream
                        SVG svg = SVG.getFromInputStream(inputStream);
                        final PictureDrawable drawable = new PictureDrawable(svg.renderToPicture());

                        // Chuyển PictureDrawable thành Bitmap
                        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmap);
                        drawable.getPicture().draw(canvas);

                        // Post back to main thread to update UI
                        imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    } catch (SVGParseException e) {
                        // Handle SVG parsing errors
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public class DistrictionViewHolder extends RecyclerView.ViewHolder {
        TextView txtDesName;
        ImageView imgDistriction;
        CheckBox chkDis;
        CardView cvItem;

        public DistrictionViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDesName = itemView.findViewById(R.id.txtDesName);
            chkDis = itemView.findViewById(R.id.chkDis);
            imgDistriction = itemView.findViewById(R.id.imgDistriction);
            cvItem = itemView.findViewById(R.id.cvItem);

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
