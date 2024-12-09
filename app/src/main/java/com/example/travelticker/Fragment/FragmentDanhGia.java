package com.example.travelticker.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
import com.example.travelticker.DAO.CommentDAO;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FragmentDanhGia extends Fragment {
    TextView txtAvgRating, txtTotalRating, wordCount, txtUsernameComment;
    RecyclerView recyclerComment;
    EditText txtComment;
    AppCompatButton btnUploadComment;
    ImageView imgAvtUserComment;
    RatingBar ratingBar;
    CommentDAO cmtDao;


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
        cmtDao = new CommentDAO();



        txtAvgRating.setText("");
        txtTotalRating.setText("");

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
        btnUploadComment.setOnClickListener(v -> addComment());
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
        loadComments();
    }

    private void loadComments() {
        String postId = getPostIdFromArguments();
        if (postId != null) {
            cmtDao.getComments(postId, new CommentDAO.OnCommentsLoadedListener() {
                @Override
                public void onSuccess(ArrayList<Comment> comments) {
                    CommentAdapter adapter = new CommentAdapter(getContext(), comments);
                    recyclerComment.setAdapter(adapter);

                    //cập nhật tổng số bình luận
                    txtTotalRating.setText(comments.size() + " đánh giá");

                    //cập nhật tổng số sao
                    if (comments.size() > 0) {
                        float totalRating = 0;
                        for (Comment comment : comments) {
                            totalRating += Float.parseFloat(comment.getRating());
                        }
                        float avgRating = totalRating / comments.size();
                        txtAvgRating.setText(String.format(Locale.getDefault(), "%.1f/5", avgRating));
                    } else {
                        txtAvgRating.setText("0/5");
                    }

                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void addComment() {
        String postId = getPostIdFromArguments();
        if (postId != null) {
            String commentText = txtComment.getText().toString().trim();
            float ratingValue = ratingBar.getRating();

            if (TextUtils.isEmpty(commentText)) {
                Toast.makeText(getContext(), "Vui lòng nhập bình luận", Toast.LENGTH_SHORT).show();
                return;
            }

            if (ratingValue == 0) {
                Toast.makeText(getContext(), "Vui lòng đánh giá sao", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String userId = user.getUid();
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                userRef.addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot){
                        String linkAvt = snapshot.child("linkAvt").getValue(String.class);
                        String username = snapshot.child("username").getValue(String.class);
                        // lấy date
                        long timestamp = System.currentTimeMillis();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String Date = sdf.format(new Date(timestamp));

                        //tạo bình luận mới
                        Comment comment = new Comment(userId, postId, linkAvt, String.valueOf(ratingValue), username, commentText, Date);
                        cmtDao.addComment(comment, postId,new CommentDAO.OnCommentAddedListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getContext(), "Bình luận đã được gửi", Toast.LENGTH_SHORT).show();
                                txtComment.setText("");
                                ratingBar.setRating(0);
                                loadComments();
                            }
                            @Override
                            public void onFailure(String errorMessage) {
                                Toast.makeText(getContext(), "Lỗi khi gửi bình luận: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }

                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        }
    }

    private String getPostIdFromArguments() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("BaiDang", Context.MODE_PRIVATE);
        return sharedPreferences.getString("idBaiDang", null); // Trả về null nếu không có idBaiDang
    }
}
