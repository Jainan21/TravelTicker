package com.example.travelticker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class dangky extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edtName, edtEmail, edtPassword, edtAgainPassword;
    private CheckBox acceptCheckBox;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        // Khởi tạo FirebaseAuth
        mAuth = FirebaseAuth.getInstance();


        // Ánh xạ view
        edtName = findViewById(R.id.Name);

        edtEmail = findViewById(R.id.Email1);
        edtPassword = findViewById(R.id.Password1);
        edtAgainPassword = findViewById(R.id.AgainPassword);
        acceptCheckBox = findViewById(R.id.accept);
        btnRegister = findViewById(R.id.register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String againPassword = edtAgainPassword.getText().toString().trim();


                // Kiểm tra thông tin nhập
                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || againPassword.isEmpty()) {



                if (email.isEmpty() || password.isEmpty() || againPassword.isEmpty()) {

                    Toast.makeText(dangky.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(againPassword)) {
                    Toast.makeText(dangky.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!acceptCheckBox.isChecked()) {
                    Toast.makeText(dangky.this, "Bạn phải chấp nhận điều khoản dịch vụ", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Đăng ký với Firebase Authentication
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(dangky.this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    String userId = user.getUid();

                                    // Lưu thông tin người dùng vào Realtime Database
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                                    User newUser = new User(name, email);
                                    databaseReference.child(userId).setValue(newUser)
                                            .addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    Toast.makeText(dangky.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(dangky.this, dangnhap.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Toast.makeText(dangky.this, "Không thể lưu thông tin: " + task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            } else {
                                Toast.makeText(dangky.this, "Đăng ký thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Liên kết tới màn hình đăng nhập
        TextView loginLink = findViewById(R.id.loginLink);
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dangky.this, dangnhap.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
