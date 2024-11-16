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

import com.example.travelticker.Adapter.PostAdapter;
import com.example.travelticker.Model.Post;
import com.example.travelticker.R;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {
    RecyclerView rcvFavo;
    PostAdapter postAdapter;
    ArrayList<Post> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_fragment, container, false);
        //khởi taoj recycleView
        rcvFavo = view.findViewById(R.id.rcvFavo);
        rcvFavo.setLayoutManager(new LinearLayoutManager(getContext()));

        //khởi tạo danh sách và adapter
        list = new ArrayList<>();
        list.add(new Post(1, 1, 1, "Helo phi day", "Thua Thien Hue", "img", "27/10", "Cua Mon"));
        list.add(new Post(1, 1, 1, "Helo minhanh day", "Ho Chi Minh", "img", "27/10", "Landmark 81"));
        postAdapter = new PostAdapter(list, getContext());

        //Gán Adapter cho rcv
        rcvFavo.setAdapter(postAdapter);
        return view;
    }
}
