package com.example.travelticker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailForgotPassword;
    private Button resetPasswordButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        emailForgotPassword = findViewById(R.id.emailForgotPassword);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailForgotPassword.getText().toString().trim();
                if (!email.isEmpty()) {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "Email đặt lại mật khẩu đã được gửi!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Gửi yêu cầu thất bại.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    emailForgotPassword.setError("Email không được để trống");
                }
            }
        });
    }
}
