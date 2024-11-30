package com.example.travelticker.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.Adapter.ThongBaoAdapter;
import com.example.travelticker.Model.ThongBaoModel;
import com.example.travelticker.R;

import java.util.ArrayList;

public class ThongBaoFragment extends Fragment {

    RecyclerView rcv_4;
    ThongBaoAdapter tbAdapter;
    ArrayList<ThongBaoModel> tb;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        // Khởi tạo RecyclerView
        rcv_4 = view.findViewById(R.id.rcv_4);
        rcv_4.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo Adapter và gán dữ liệu
        tbAdapter = new ThongBaoAdapter(getContext(), data());
        rcv_4.setAdapter(tbAdapter);
        return view;
    }

    //tạo dữ liệu
    private ArrayList<ThongBaoModel> data(){
        tb = new ArrayList<>();

        tb.add(new ThongBaoModel(
                R.drawable.monkey,
                "Kateruku",
                "Anh ấy vừa yêu thích bài viết của bạn",
                "08:08 20/11/2024"
        ));

        tb.add(new ThongBaoModel(
                R.drawable.monkey,
                "Aphi",
                "Cô ấy vừa bình luận bài viết của bạn",
                "10:08 20/11/2024"
        ));
        return tb;
    }
}
