package com.example.travelticker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.travelticker.Adapter.DetailAdapter;
import com.example.travelticker.DAO.LikeDAO;
import com.example.travelticker.DAO.UserDbDAO;
import com.example.travelticker.DAO.dbDAO;
import com.example.travelticker.Model.Post;
import com.example.travelticker.Model.User;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class BaiDang extends AppCompatActivity {

    ImageView imgBtnLike;

    dbDAO dbDAO = new dbDAO();
    LikeDAO likeDAO = new LikeDAO();
    String userID;
    String idBaiDang;
    Boolean isLiked = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bai_dang);

        ImageView mainimg = findViewById(R.id.mainimg_baidang);
        TextView txtLocationName = findViewById(R.id.txtLocationName);
        TextView txtLocation = findViewById(R.id.txtLocation);
        imgBtnLike = findViewById(R.id.imgBtnLike);

        Bundle bundle = getIntent().getExtras();
        idBaiDang = bundle.getString("idBaiDang");

        getUserId();
        checkLike();

        dbDAO.getBaiDangByID(idBaiDang, new dbDAO.PostCallBack() {
            @Override
            public void onSuccess(Post post) {
                Toast.makeText(BaiDang.this, "test:  "+post.getIdBaiDang(), Toast.LENGTH_SHORT).show();
                if (post.getImg() != null){
                    Glide.with(getBaseContext()).load(post.getImg()).into(mainimg);
                }else {
                    mainimg.setImageResource(R.drawable.tienggian);
                }
                txtLocation.setText("12345");
                txtLocationName.setText(post.getTieuDe());
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(BaiDang.this, "FAil to get baidang", Toast.LENGTH_SHORT).show();
            }
        });


        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        // Set up the adapter
        DetailAdapter adapter = new DetailAdapter(this);
        viewPager.setAdapter(adapter);

        // Link the TabLayout and ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Tổng quan");
                    break;
                case 1:
                    tab.setText("Đánh giá");
                    break;
                case 2:
                    tab.setText("Địa chỉ");
                    break;
            }
        }).attach();

        imgBtnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLiked){
                    dialofUnLike();
                }else {
                    likePost();
                }
            }
        });

    }

    public void getUserId(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userID = sharedPreferences.getString("userId", null);

        if (userID == null){
            Toast.makeText(this, "Lấy Id người dùng thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkLike(){
        likeDAO.checkIsLiked(userID, idBaiDang, isLiked1 -> {
            if (isLiked1){
                isLiked = true;
                imgBtnLike.setImageResource(R.drawable.favorite_24);
            } else {
                isLiked = false;
                imgBtnLike.setImageResource(R.drawable.icon_fav_trangchu);
            }
        });
    }

    public void likePost(){
        likeDAO.likePost(userID, idBaiDang, new LikeDAO.onLikeListener() {
            @Override
            public void onSuccess() {
                isLiked = true;
                imgBtnLike.setImageResource(R.drawable.favorite_24);
                Toast.makeText(BaiDang.this, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(BaiDang.this, "Thêm vào yêu thích thất bại !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void dialofUnLike(){
        new AlertDialog.Builder(this)
                .setMessage("Bạn có muốn bỏ yêu thích?")
                .setPositiveButton("Có", ((dialogInterface, i) -> unLike()))
                .setNegativeButton("không", null)
                .show();
    }

    public void unLike(){
        likeDAO.unLike(userID, idBaiDang, new LikeDAO.onUnLikeListener() {
            @Override
            public void onSuccess() {
                isLiked = false;
                imgBtnLike.setImageResource(R.drawable.icon_fav_trangchu);
                Toast.makeText(BaiDang.this, "Đã bỏ thích thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(BaiDang.this, "Lỗi khi bỏ thích !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}