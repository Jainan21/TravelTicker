package com.example.travelticker.DAO;

import com.example.travelticker.Model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDbDAO {
    private FirebaseDatabase database;
    private DatabaseReference userRef;

    public UserDbDAO(){
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("Users");
    }

    public interface UserCallBack{
        void onSuccess(User user);
        void onError(String error);
    }

    public void getUserById(String userId, final UserCallBack callBack){
        userRef.child(userId).get()
                .addOnSuccessListener(dataSnapshot -> {
                    if (dataSnapshot.exists()){
                        //nếu tìm thấy
                        User user = dataSnapshot.getValue(User.class);
                        callBack.onSuccess(user);
                    }else {
                        callBack.onError("Không tìm thấy tài khoảng");
                    }
                })
                .addOnFailureListener(e -> {
                    callBack.onError("Lỗi lấy dữ liệu: " + e.getMessage());
                });
    }
}
