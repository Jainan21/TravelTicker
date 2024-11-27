package com.example.travelticker.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.Adapter.LocationAdapter;
import com.example.travelticker.Adapter.UserFamousAdapter;
import com.example.travelticker.Model.FamousUser;
import com.example.travelticker.Model.Location;
import com.example.travelticker.R;

import java.util.ArrayList;

public class FragmentTrangChu extends Fragment {
    ImageView imgAvt;
    TextView txtWelcome, txtGreeting;
    RecyclerView recyclerFamousUser, recyclerLocation;
    String userName = new String();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu,null, false);

        imgAvt = view.findViewById(R.id.imgAvtTrangChu);
        txtWelcome = view.findViewById(R.id.txtWelcome);
        recyclerLocation = view.findViewById(R.id.recyclerLocation);
        recyclerFamousUser = view.findViewById(R.id.recyclerFamousUser);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", "Guest");
        String userEmail = sharedPreferences.getString("userEmail", "");
        String userPhoto = sharedPreferences.getString("userPhoto", "");

        imgAvt.setImageResource(R.drawable.monkey);
        txtWelcome.setText("Xin chao " + userName);

        LinearLayoutManager layout = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        LinearLayoutManager layout2 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);

        recyclerLocation.setLayoutManager(layout);
        ArrayList<Location> listLocation = new ArrayList<>();
        listLocation.add(new Location(R.drawable.quangninh, "Quang Ninh"));
        listLocation.add(new Location(R.drawable.quangtri, "Quang Tri"));
        listLocation.add(new Location(R.drawable.tienggian, "Tien Giang"));
        LocationAdapter locationAdapter = new LocationAdapter(getContext(), listLocation);
        recyclerLocation.setAdapter(locationAdapter);

        recyclerFamousUser.setLayoutManager(layout2);
        ArrayList<FamousUser> listUser = new ArrayList<>();
        listUser.add(new FamousUser(R.drawable.tienggian, R.drawable.monkey, "Tien Giang, VietNam", "Chuyen tham Tien Giang that dep va vui ve", "Nguyen Van Khi"));
        UserFamousAdapter userAdapter = new UserFamousAdapter(getContext(), listUser);
        recyclerFamousUser.setAdapter(userAdapter);

        return view;
    }
}
