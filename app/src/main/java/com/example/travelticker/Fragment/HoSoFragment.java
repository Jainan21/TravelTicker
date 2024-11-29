package com.example.travelticker.Fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.Adapter.PersonAdapter;
import com.example.travelticker.ChinhSuaHoSo;
import com.example.travelticker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HoSoFragment extends Fragment {

    RecyclerView rcv_1;
    FloatingActionButton edit_button;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);

        rcv_1 = view.findViewById(R.id.rcv_1);
        rcv_1.setLayoutManager(new LinearLayoutManager(requireContext()));
        edit_button = view.findViewById(R.id.edit_button);

        Drawable[] icon = new Drawable[]{
                requireContext().getDrawable(R.drawable.settings),
                requireContext().getDrawable(R.drawable.help),
                requireContext().getDrawable(R.drawable.star),
                requireContext().getDrawable(R.drawable.logout)
        };
        String arr[] = {"Cài đặt", "Hỗ trợ", "Đánh giá", "Đăng xuất"};
        rcv_1.setAdapter(new PersonAdapter(arr, icon));

        edit_button.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ChinhSuaHoSo.class);
            startActivity(intent);
        });

        return view;
    }
}
