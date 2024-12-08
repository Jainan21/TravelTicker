package com.example.travelticker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.Adapter.PostAdapter;
import com.example.travelticker.Adapter.SearchAdapter;
import com.example.travelticker.DAO.LikeDAO;
import com.example.travelticker.Model.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    Toolbar tbSearch;
    EditText edtSearch;
    Button btnSearch;
    RecyclerView rcvSearch;

    String userID;

    ArrayList<Post> listPost;
    ArrayList<Post> listSearch;
    ArrayList<Post> listLikePost;

    SearchAdapter adapter;

    LikeDAO likeDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.search_fragment);

        tbSearch = findViewById(R.id.tbSearch);
        edtSearch = findViewById(R.id.edtSearch);
        btnSearch = findViewById(R.id.btnSearch);
        rcvSearch = findViewById(R.id.rcvSearch);

        getUserId();

        likeDAO = new LikeDAO();
        getListLike();

        listPost = new ArrayList<>();

        rcvSearch.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(tbSearch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tìm kiếm");

        getListPost();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // lọc
                String query = s.toString().toLowerCase().trim();
                if (query != null && !query.isEmpty()) {
                    findListSearch(query);
                } else {
                    // Nếu không có từ khóa tìm kiếm, hiển thị tất cả bài đăng
                    findListSearch("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void getListPost() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("BaiDang");

        // Lắng nghe sự thay đổi dữ liệu trong Firebase
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Post post = snapshot1.getValue(Post.class);

                    if (post != null) {
                        listPost.add(post);
                    } else {
                        Toast.makeText(Search.this, "Lỗi lưu dữ liệu !!!", Toast.LENGTH_SHORT).show();
                    }
                }
                Toast.makeText(Search.this, "Dữ liệu đã được tải thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Lỗi khi đọc dữ liệu
                Toast.makeText(Search.this, "Lỗi đọc dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void findListSearch(String query) {
        // Tìm kiếm các bài đăng theo từ khóa
        if (listSearch != null) {
            listSearch.clear();
        } else {
            listSearch = new ArrayList<>();
        }

        for (Post post : listPost) {
            if (post.getTieuDe().toLowerCase().contains(query)) {
                listSearch.add(post);
            }
        }

        // Cập nhật lại RecyclerView
        if (!listSearch.isEmpty()) {
            adapter = new SearchAdapter(listSearch, this, listLikePost);
            rcvSearch.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Không tìm thấy kết quả", Toast.LENGTH_SHORT).show();
        }
    }

    public void getListLike(){
        if (userID != null){
            likeDAO.getListLike(userID, new LikeDAO.ongetListLike() {
                @Override
                public void onSuccess(ArrayList<Post> listLike) {
                    if (listLike != null){
                        listLikePost = listLike;
                    }else {
                        Toast.makeText(Search.this, "Không có danh sách yêu thích", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailue(String errorMessage) {
                    Toast.makeText(Search.this, "Lỗi !!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void getUserId(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userID = sharedPreferences.getString("userId", null);

        if (userID == null){
            Toast.makeText(Search.this, "Lấy Id người dùng thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}