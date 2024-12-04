package com.example.travelticker.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelticker.Adapter.PersonAdapter;
import com.example.travelticker.ChinhSuaHoSo;
import com.example.travelticker.DAO.dbDAO;
import com.example.travelticker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HoSoFragment extends Fragment {

    RecyclerView rcv_1;
    FloatingActionButton edit_button;
    ImageView avatar_up;
    TextView tvTenHS;
    DatabaseReference userRef;
    dbDAO db;
    PersonAdapter perAdt;
    String userName = new String();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);

        rcv_1 = view.findViewById(R.id.rcv_1);
        rcv_1.setLayoutManager(new LinearLayoutManager(requireContext()));
        edit_button = view.findViewById(R.id.edit_button);
        avatar_up = view.findViewById(R.id.avatar_up);
        tvTenHS = view.findViewById(R.id.tvTenHS);

        Drawable[] icon = new Drawable[]{
                requireContext().getDrawable(R.drawable.settings),
                requireContext().getDrawable(R.drawable.help),
                requireContext().getDrawable(R.drawable.star),
                requireContext().getDrawable(R.drawable.logout)
        };
        String arr[] = {"Cài đặt", "Hỗ trợ", "Đánh giá", "Đăng xuất"};
//        rcv_1.setAdapter(new PersonAdapter(arr, icon));
        perAdt = new PersonAdapter(requireContext(), arr, icon);
        rcv_1.setAdapter(perAdt);

        edit_button.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ChinhSuaHoSo.class);
            startActivity(intent);
        });

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", "Guest");
        tvTenHS.setText(userName);
        String cachedAvatarUrl = sharedPreferences.getString("avatarUrl", null);

        // Hiển thị ảnh từ SharedPreferences
        if (cachedAvatarUrl != null) {
            Glide.with(this).load(cachedAvatarUrl).into(avatar_up);
        }

        userRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        userRef.get().addOnSuccessListener(dataSnapshot -> {
            if (dataSnapshot.exists() && isAdded()) {
                String avatarUrl = dataSnapshot.child("avatarUrl").getValue(String.class);
                String firebaseUserName = dataSnapshot.child("userName").getValue(String.class);

                // cập nhật ảnh nếu khác
                if (avatarUrl != null) {
                    Glide.with(requireContext()).load(avatarUrl).into(avatar_up); // Hiển thị avatar
                    // Lưu URL mới vào SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("avatarUrl", avatarUrl);
                    editor.apply();
                }

                // cập nhật tên nếu khác
                if (firebaseUserName != null && !firebaseUserName.equals(userName)) {
                    tvTenHS.setText(firebaseUserName);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userName", firebaseUserName);
                    editor.apply();
                }
            }
        });
        return view;
    }
    public void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", "Guest");
        tvTenHS.setText(userName);
    }
}
