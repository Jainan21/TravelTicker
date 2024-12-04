package com.example.travelticker.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelticker.Adapter.CommentAdapter;
import com.example.travelticker.DAO.UserDbDAO;
import com.example.travelticker.Model.Comment;
import com.example.travelticker.Model.User;
import com.example.travelticker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Locale;

public class FragmentDanhGia extends Fragment {
    TextView txtAvgRating, txtTotalRating, wordCount, txtUsernameComment;
    RecyclerView recyclerComment;
    EditText txtComment;
    AppCompatButton btnUploadComment;
    ImageView imgAvtUserComment;
    RatingBar ratingBar;

    FirebaseDatabase database;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseUser user;
    DatabaseReference commentsRef;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_gia, container, false);

        txtAvgRating = view.findViewById(R.id.txtAvgRating);
        txtTotalRating = view.findViewById(R.id.txtTotalRating);
        wordCount = view.findViewById(R.id.txtWordCount);
        recyclerComment = view.findViewById(R.id.recyclerComment);
        txtComment = view.findViewById(R.id.txtDanhGia);
        btnUploadComment = view.findViewById(R.id.btnUploadComment);
        ratingBar = view.findViewById(R.id.ratingBar);
        imgAvtUserComment = view.findViewById(R.id.imgAvtUserComment);
        txtUsernameComment = view.findViewById(R.id.txtUsernameComment);

        database = FirebaseDatabase.getInstance();
        commentsRef = database.getReference("comments");
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference().child("avatars");

        user = FirebaseAuth.getInstance().getCurrentUser();



        txtAvgRating.setText("4.7/5");
        txtTotalRating.setText("300 đánh giá");

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    Toast.makeText(getContext(), "Rated: " + rating + " stars", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int words = countWords(s.toString());
                wordCount.setText(words+"/300");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        LinearLayoutManager layoutRecycler = new LinearLayoutManager(getContext());
        layoutRecycler.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerComment.setLayoutManager(layoutRecycler);
        ArrayList<Comment> list = new ArrayList<>();

        CommentAdapter adapter = new CommentAdapter(getContext(), list);
        recyclerComment.setAdapter(adapter);

        btnUploadComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = txtComment.getText().toString().trim();
                float ratingValue = ratingBar.getRating();

                if (commentText.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập bình luận", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (ratingValue == 0){
                    Toast.makeText(getContext(), "Vui lòng đánh giá sao", Toast.LENGTH_SHORT).show();
                    return;
                }


                String avatarUrl = user != null && user.getPhotoUrl() != null
                        ? user.getPhotoUrl().toString()
                        : "https://i.pinimg.com/736x/cd/2b/d1/cd2bd1e52870c292ba5e0abfb96aa8c8.jpg";
                String userName = (user != null && user.getDisplayName() != null) ? user.getDisplayName() : "Ẩn danh";
                String currentDate = new java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new java.util.Date());
                String ratingString = ratingValue + "/5";

                //tạo id cho bình luận
                String key = commentsRef.push().getKey();
                if (key != null) {
                    // Tạo đối tượng bình luận mới với ID
                    Comment newComment = new Comment(key, avatarUrl, ratingString, userName, commentText, currentDate);

                    commentsRef.child(key).setValue(newComment)
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(getContext(), "Bình luận đã được lưu!", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getContext(), "Lỗi khi lưu bình luận: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });

                    // Thêm bình luận vào danh sách và cập nhật adapter
                    list.add(0, newComment);
                    adapter.notifyItemInserted(0);
                    recyclerComment.scrollToPosition(0);
                }
                txtComment.setText("");
                ratingBar.setRating(0);
            }
        });
        return view;
    }
    private int countWords(String input) {
        if (input.trim().isEmpty()) {
            return 0;
        }
        String[] words = input.trim().split("\\s+");
        return words.length;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Lấy bình luận từ Firebase khi người dùng đăng nhập lại
        commentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Comment> list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Comment comment = snapshot.getValue(Comment.class);
                    if (comment != null) {
                        list.add(comment);
                    }
                }
                // Cập nhật adapter với danh sách bình luận mới
                CommentAdapter adapter = new CommentAdapter(getContext(), list);
                recyclerComment.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Lỗi khi tải bình luận", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
