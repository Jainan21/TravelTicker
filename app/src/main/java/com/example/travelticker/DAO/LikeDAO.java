package com.example.travelticker.DAO;

import androidx.annotation.NonNull;

import com.example.travelticker.Model.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LikeDAO {
    private DatabaseReference likeRef;

    public LikeDAO(){
        this.likeRef = FirebaseDatabase.getInstance().getReference("YeuThich");
    }

    public interface OnCheckLikedListener{
        void onLiked(boolean isLiked);
    }

    public interface onLikeListener{
        void onSuccess();
        void onFailure();
    }

    public interface onUnLikeListener{
        void onSuccess();
        void onFailure();
    }

    public void checkIsLiked(String userID, String postID, final OnCheckLikedListener listener){
        likeRef.child(userID).child(postID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()){
                listener.onLiked(true);
            }else {
                listener.onLiked(false);
            }
        });
    }

    public void likePost(String userID, String postID, final onLikeListener listener){
        likeRef.child(userID).child(postID).setValue(true).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                listener.onSuccess();
            }else {
                listener.onFailure();
            }
        });
    }

    public void unLike(String userID, String postID, final onUnLikeListener listener){
        if (userID == null || postID == null) {
            listener.onFailure();
            return;
        }

        likeRef.child(userID).child(postID).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                listener.onSuccess();
            }else {
                listener.onFailure();
            }
        });
    }

    public interface ongetListLike{
        void onSuccess(ArrayList<Post> listLike);
        void onFailue(String errorMessage);
    }

    public void getListLike(String userID, final ongetListLike listener){
        DatabaseReference postRef = FirebaseDatabase.getInstance().getReference("BaiDang");

        likeRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Post> listLike = new ArrayList<>();

                //kiểm tra
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        String postID = snapshot1.getKey(); //lâ id

                        //lấy toàn bộ bài đăng
                        postRef.child(postID).get().addOnCompleteListener(task -> {
                           if (task.isSuccessful() && task.getResult().exists()){
                               Post post = task.getResult().getValue(Post.class);
                               if (post != null){
                                   listLike.add(post);
                               }
                           }

                           //gọi callback khi thành công
                            if (listLike.size() == snapshot.getChildrenCount()){
                                listener.onSuccess(listLike);
                            }
                        });
                    }
                }else {
                    listener.onFailue("Không có bài đăng yêu thích !!!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailue(error.getMessage());
            }
        });
    }
}
