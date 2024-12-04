package com.example.travelticker.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelticker.Adapter.PostAdapter;
import com.example.travelticker.DAO.LikeDAO;
import com.example.travelticker.DAO.UserDbDAO;
import com.example.travelticker.Model.Post;
import com.example.travelticker.Model.User;
import com.example.travelticker.PostActivity;
import com.example.travelticker.R;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {
    RecyclerView rcvFavo;
    PostAdapter postAdapter;
    ArrayList<Post> list;
    String userID;
    LikeDAO likeDAO = new LikeDAO();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_fragment, container, false);
        //khởi tao recycleView
        rcvFavo = view.findViewById(R.id.rcvFavo);
        rcvFavo.setLayoutManager(new LinearLayoutManager(getContext()));

        getUserId();

        //khởi tạo danh sách và adapter
        likeDAO.getListLike(userID, new LikeDAO.ongetListLike() {
            @Override
            public void onSuccess(ArrayList<Post> listLike) {
                if (listLike != null){
                    list = listLike;
                    postAdapter = new PostAdapter(listLike, getContext(), userID);
                    rcvFavo.setAdapter(postAdapter);
                }else {
                    Toast.makeText(getContext(), "Không có danh sách yêu thích", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailue(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void getUserId(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userID = sharedPreferences.getString("userId", null);

        if (userID == null){
            Toast.makeText(getContext(), "Lấy Id người dùng thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
