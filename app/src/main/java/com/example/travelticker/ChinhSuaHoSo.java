package com.example.travelticker;


import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.travelticker.DAO.UserDbDAO;
import com.example.travelticker.DAO.dbDAO;
import com.example.travelticker.Model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class ChinhSuaHoSo extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    EditText editHoTen, editEmail, editSdth;
    Button btCapNhat;
    ImageButton ImgbtBackCS;
    DatabaseReference userRef;
    FirebaseUser currentUser;
    Uri avatarUri;
    StorageReference avatarRef;
    TextView tvTenCS;
    ImageView avatar_edit;
    FloatingActionButton camera_button;
    UserDbDAO userDbDAO;
    String userID, userImg;
    String userName = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_ho_so);
        editHoTen = findViewById(R.id.editHoTen);
        editEmail = findViewById(R.id.editEmail);
        editSdth = findViewById(R.id.editSdth);
        btCapNhat = findViewById(R.id.btCapNhat);
        ImgbtBackCS = findViewById(R.id.ImgbtBackCS);
        tvTenCS = findViewById(R.id.tvTenCS);
        avatar_edit = findViewById(R.id.avatar_edit);
        camera_button = findViewById(R.id.camera_button);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String avatarUrl = sharedPreferences.getString("avatarUrl", null);
        userID = sharedPreferences.getString("userId", "");
        userDbDAO = new UserDbDAO();
        userDbDAO.getUserById(userID, new UserDbDAO.UserCallBack() {
            @Override
            public void onSuccess(User user) {
                userImg = user.getAvatarUrl();
                if (userImg == null){
                    Glide.with(ChinhSuaHoSo.this).load(R.drawable.avatar).into(avatar_edit);
                }else{
                    Glide.with(ChinhSuaHoSo.this).load(user.getAvatarUrl()).into(avatar_edit);
                }
                editHoTen.setText(""+ user.getName());
            }
            @Override
            public void onError(String error) {
            }
        });


        tvTenCS.setText(userName);
        editHoTen.setText(userName);

        // Hiển thị ảnh nếu có URL
        if (avatarUrl != null) {
            Glide.with(this).load(avatarUrl).into(avatar_edit); // Sử dụng Glide để tải ảnh
        }

        //lấy thông tin ng dùng từ Firebase Authentication
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null){
            editEmail.setText(currentUser.getEmail()); //hiển thị email ng dùng

        }

        //tham chiếu đến Firebase Realtime Database
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());

        //gắn sk nút cập nhật
        btCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capNhatThongTin();
            }
        });

        ImgbtBackCS.setOnClickListener(v -> finish());

        camera_button.setOnClickListener(v -> openFileChooser());

    }

    //hàm mở thư viện ảnh
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh đại diện"), PICK_IMAGE_REQUEST);
    }

    //hàm xử lý chọn ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            avatarUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), avatarUri);
                avatar_edit.setImageBitmap(bitmap); // Hiển thị ảnh đã chọn lên `ImageView`
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //hàm cập nhật thông tin
    private void capNhatThongTin() {
        String hoTen = editHoTen.getText().toString().trim();
        String email = editEmail.getText().toString().trim();


        if (TextUtils.isEmpty(hoTen)) {
            editHoTen.setError("Vui lòng nhập họ tên");
            return;
        }
        if (avatarUri != null) {
            //tải ảnh lên Firebase Storage
            avatarRef = FirebaseStorage.getInstance().getReference("avatars/" + currentUser.getUid() + ".jpg");
            avatarRef.putFile(avatarUri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    avatarRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String avatarUrl = uri.toString();

                        // Lưu URL ảnh vào SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("avatarUrl", avatarUrl);
                        editor.putString("userName", hoTen);
                        editor.apply();

                        // Cập nhật dữ liệu lên Firebase
                        User user = new User(hoTen, email, avatarUrl);
                        userRef.setValue(user).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });
                } else {
                    Toast.makeText(this, "Tải ảnh thất bại!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Lưu thông tin vào SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userName", hoTen); // Cập nhật tên mới
            editor.apply();

            User user = new User(hoTen, email, null);
            userRef.setValue(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
//    public void getUserId(){
//        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
//        userID = sharedPreferences.getString("userId", null);
//
//        if (userID == null){
//            Toast.makeText(this, "Lấy Id người dùng thất bại", Toast.LENGTH_SHORT).show();
//        }else {
//            userDbDAO = new UserDbDAO();
//            userDbDAO.getUserById(userID, new UserDbDAO.UserCallBack() {
//                @Override
//                public void onSuccess(User user) {
//                    Glide.with(getBaseContext()).load(user.getAvatarUrl()).into(imgUserPost);
//                    editHoTen.setText(user.getName());
//                }
//
//                @Override
//                public void onError(String error) {
//                    System.err.println(error);
//                }
//            });
//        }
//    }
}