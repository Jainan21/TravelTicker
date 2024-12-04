package com.example.travelticker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
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
import com.example.travelticker.DAO.dbDAO;
import com.example.travelticker.Fragment.FragmentDanhGia;
import com.example.travelticker.Fragment.FragmentDiaChi;
import com.example.travelticker.Fragment.FragmentTongQuan;
import com.example.travelticker.Model.Post;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;


public class BaiDang extends AppCompatActivity {
    dbDAO dbDAO = new dbDAO();
    String locationName;
    ImageView backBaiDang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bai_dang);

        ImageView mainimg = findViewById(R.id.mainimg_baidang);
        TextView txtLocationName = findViewById(R.id.txtLocationName);
        TextView txtLocation = findViewById(R.id.txtLocation);
        backBaiDang =findViewById(R.id.backBaiDang);

        Bundle bundle = getIntent().getExtras();
        String idBaiDang = bundle.getString("idBaiDang");
        String idNguoiDang =bundle.getString("idNguoiDung");

        SharedPreferences sharedPreferences = getSharedPreferences("BaiDang", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("idUser", idNguoiDang);
        editor.putString("idBaiDang", idBaiDang);
        editor.apply();
        


        dbDAO.getBaiDangByID(idBaiDang, idNguoiDang, new dbDAO.PostCallBack() {
            @Override
            public void onSuccess(Post post) {
                String[] location = post.getDiaChi().split(",");
                Double latitude = Double.valueOf(location[0]);
                Double longitude = Double.valueOf(location[1]);

                getLocationName(latitude, longitude);
                if (post.getImg() != null){
                    Glide.with(getBaseContext()).load(post.getImg()).into(mainimg);
                }else {
                    mainimg.setImageResource(R.drawable.tienggian);
                }
                txtLocation.setText(locationName);
                txtLocationName.setText(post.getTieuDe());
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(BaiDang.this, "Fail to get baidang", Toast.LENGTH_SHORT).show();
            }
        });


        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        DetailAdapter adapter = new DetailAdapter(this);
        viewPager.setAdapter(adapter);

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


    }
    private void getLocationName(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            ArrayList<Address> addresses = (ArrayList<Address>) geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                locationName = address.getAdminArea();
            } else {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}