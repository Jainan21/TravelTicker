package com.example.travelticker.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelticker.Adapter.LocationAdapter;
import com.example.travelticker.Adapter.UserFamousAdapter;
import com.example.travelticker.BaiDang;
import com.example.travelticker.DAO.UserDbDAO;
import com.example.travelticker.Model.FamousUser;
import com.example.travelticker.DAO.dbDAO;
import com.example.travelticker.Model.Post;
import com.example.travelticker.Model.Tinh;
import com.example.travelticker.Model.User;
import com.example.travelticker.R;

import java.util.ArrayList;

public class FragmentTrangChu extends Fragment {

    ImageView imgAvt;
    TextView txtWelcome, txtGreeting;
    RecyclerView recyclerFamousUser, recyclerLocation;
    ArrayList<Tinh> listTinh = new ArrayList<>();
    String userId, userImg;
    dbDAO dbDAO;
    UserDbDAO userDbDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu,null, false);
        imgAvt = view.findViewById(R.id.imgAvtTrangChu);
        txtWelcome = view.findViewById(R.id.txtWelcome);
        recyclerLocation = view.findViewById(R.id.recyclerLocation);
        recyclerFamousUser = view.findViewById(R.id.recyclerFamousUser);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
        userDbDAO = new UserDbDAO();
        userDbDAO.getUserById(userId, new UserDbDAO.UserCallBack() {
            @Override
            public void onSuccess(User user) {
                userImg = user.getAvatarUrl();
                if (userImg == null){
                    Glide.with(getContext()).load(R.drawable.avatar).into(imgAvt);
                }else{
                    Glide.with(getContext()).load(user.getAvatarUrl()).into(imgAvt);
                }
                txtWelcome.setText("Xin chào "+ user.getName());
            }
            @Override
            public void onError(String error) {
            }
        });

        LinearLayoutManager layout = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        LinearLayoutManager layout2 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);

        recyclerLocation.setLayoutManager(layout);
        recyclerFamousUser.setLayoutManager(layout2);

        dbDAO = new dbDAO();
        dbDAO.getRandomPost(new dbDAO.RandomPostCallBack() {
            @Override
            public void onSuccess(ArrayList<FamousUser> listPost) {
                UserFamousAdapter adpFamousUser = new UserFamousAdapter(getContext(), listPost, new UserFamousAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(FamousUser post) {
                        Bundle bundle = new Bundle();
                        bundle.putString("idBaiDang", post.getIdBaiDang());
                        Intent intent = new Intent(getContext(), BaiDang.class);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });
                recyclerFamousUser.setAdapter(adpFamousUser);
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });

        dbDAO = new dbDAO();
        dbDAO.getDataList("Tinh", Tinh.class, new dbDAO.FirestoreCallback<Tinh>() {
            @Override
            public void onCallback(ArrayList<Tinh> dataList) {
                for (Tinh a: dataList){
                    listTinh.add(a);
                    LocationAdapter locationAdapter = new LocationAdapter(getContext(), listTinh);
                    recyclerLocation.setAdapter(locationAdapter);
                }
            }
            @Override
            public void onFailure(Exception e) {
                System.err.println("Lỗi khi lấy dữ liệu Tinh: " + e.getMessage());
            }
        });

        return view;
    }
}
