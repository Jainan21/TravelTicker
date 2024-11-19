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

public class dangky extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edtName, edtEmail, edtPassword, edtAgainPassword;
    private CheckBox acceptCheckBox;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        mAuth = FirebaseAuth.getInstance();


        edtName = findViewById(R.id.Name);
        edtEmail = findViewById(R.id.Email1);
        edtPassword = findViewById(R.id.Password1);
        edtAgainPassword = findViewById(R.id.AgainPassword);
        acceptCheckBox = findViewById(R.id.accept);
        btnRegister = findViewById(R.id.register);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String againPassword = edtAgainPassword.getText().toString().trim();


                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || againPassword.isEmpty()) {
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


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(dangky.this, task -> {
                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(dangky.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();


                                Intent intent = new Intent(dangky.this, dangnhap.class);
                                startActivity(intent);
                                finish();
                            } else {

                                Toast.makeText(dangky.this, "Đăng ký thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


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
