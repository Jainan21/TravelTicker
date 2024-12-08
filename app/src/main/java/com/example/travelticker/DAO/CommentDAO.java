package com.example.travelticker.DAO;

import com.example.travelticker.Model.Comment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class CommentDAO {
    private DatabaseReference commentRef;

    public CommentDAO(){
        this.commentRef = FirebaseDatabase.getInstance().getReference("DanhGia");
    }

    public interface OnCommentAddedListener{
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public interface OnCommentsLoadedListener {
        void onSuccess(ArrayList<Comment> comments);
        void onFailure(String errorMessage);
    }



    //thêm bình luận mới vào firebase
    public void addComment(Comment comment, String postId, final OnCommentAddedListener listener){
        comment.setIdPost(postId);
        //lấy id cho bình luận
        DatabaseReference newCommentRef = commentRef.child(postId).push();
        //lưu bình luận vào firebase
        newCommentRef.setValue(comment)
                .addOnSuccessListener(aVoid -> listener.onSuccess())
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }

    // lấy danh sách bình luận
    public void getComments(String postId,final OnCommentsLoadedListener listener){
        commentRef.child(postId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                if (task.getResult() != null && task.getResult().exists()){
                    //chuyển dữ liệu từ firebase thành danh sách bình luận
                    ArrayList<Comment> comments = new ArrayList<>();
                    for (DataSnapshot snapshot : task.getResult().getChildren()){
                        Comment comment = snapshot.getValue(Comment.class);
                        if (comment != null){
                            comments.add(comment);
                        }
                    }
                    listener.onSuccess(comments);
                }else {
                    listener.onFailure("K có bình luận nào");
                }
            }else {
                listener.onFailure("Lỗi kết nối hoặc truy vấn Firebase thất bại.");
            }
        });
    }
}
