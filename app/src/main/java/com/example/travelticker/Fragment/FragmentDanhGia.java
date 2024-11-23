package com.example.travelticker.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.Adapter.CommentAdapter;
import com.example.travelticker.Model.Comment;
import com.example.travelticker.R;

import java.util.ArrayList;

public class FragmentDanhGia extends Fragment {
    TextView txtAvgRating, txtTotalRating, wordCount;
    RecyclerView recyclerComment;
    EditText txtComment;
    AppCompatButton btnUploadComment;

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

        txtAvgRating.setText("4.7/5");
        txtTotalRating.setText("300 đánh giá");

        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerComment.setLayoutManager(layout);
        ArrayList<Comment> list = new ArrayList<>();
        list.add(new Comment(R.drawable.monkey, "4.5/5", "Nguyen Van Khi", "Địa danh này có nhiều dịch vụ giải trí thú vị, rất đáng để trải nghiệm", "10/2/2024"));
        list.add(new Comment(R.drawable.monkey, "5/5", "Nguyen Van Khi 2", "Địa danh này có nhiều dịch vụ giải trí thú vị, rất đáng để trải nghiệm lắm nha", "18/10/2024"));
        list.add(new Comment(R.drawable.monkey, "4.8/5", "Nguyen Van Khi 3", "Địa danh này có nhiều dịch vụ giải trí thú vị, rất đáng để trải nghiệm, cũng cũng vui", "14/6/2024"));
        list.add(new Comment(R.drawable.monkey, "4.5/5", "Nguyen Van Khi", "Địa danh này có nhiều dịch vụ giải trí thú vị, rất đáng để trải nghiệm", "10/2/2024"));
        list.add(new Comment(R.drawable.monkey, "5/5", "Nguyen Van Khi 2", "Địa danh này có nhiều dịch vụ giải trí thú vị, rất đáng để trải nghiệm lắm nha", "18/10/2024"));
        list.add(new Comment(R.drawable.monkey, "4.8/5", "Nguyen Van Khi 3", "Địa danh này có nhiều dịch vụ giải trí thú vị, rất đáng để trải nghiệm, cũng cũng vui", "14/6/2024"));

        CommentAdapter adapter = new CommentAdapter(getContext(), list);
        recyclerComment.setAdapter(adapter);




        return view;
    }
}
