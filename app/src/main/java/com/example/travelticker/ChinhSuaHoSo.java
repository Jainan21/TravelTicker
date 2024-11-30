package com.example.travelticker;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.travelticker.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChinhSuaHoSo extends AppCompatActivity {
    EditText editHoTen, editEmail, editSdth;
    Button btCapNhat;
    DatabaseReference userRef;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_ho_so);
        editHoTen = findViewById(R.id.editHoTen);
        editEmail = findViewById(R.id.editEmail);
        editSdth = findViewById(R.id.editSdth);
        btCapNhat = findViewById(R.id.btCapNhat);

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

    }

    private void capNhatThongTin(){
        String hoTen = editHoTen.getText().toString().trim();
        String email = editEmail.getText().toString().trim();


        if (TextUtils.isEmpty(hoTen)) {
            editHoTen.setError("Vui lòng nhập họ tên");
            return;
        }

        //cập nhật thông tin vào Firebase Database
        User user = new User(hoTen, email);
        userRef.setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ChinhSuaHoSo.this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ChinhSuaHoSo.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
            }

        });
    }
}