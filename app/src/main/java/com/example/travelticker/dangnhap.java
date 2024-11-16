    package com.example.travelticker;

    import android.annotation.SuppressLint;
    import android.content.Intent;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.view.View;
    import android.widget.Button;
    import android.widget.CheckBox;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;

public class dangnhap extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private CheckBox rememberCheckBox;
    private TextView forgotPasswordTextView, signUpTextView;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap); // Đảm bảo layout tên là dangnhap.xml

        // Khởi tạo Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Liên kết các view từ XML
        emailEditText = findViewById(R.id.Email);
        passwordEditText = findViewById(R.id.Password);
        loginButton = findViewById(R.id.Login);
        rememberCheckBox = findViewById(R.id.Remember);
        forgotPasswordTextView = findViewById(R.id.ForgotPassword);

        // Xử lý sự kiện nhấn nút đăng nhập
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError("Vui lòng nhập email");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordEditText.setError("Vui lòng nhập mật khẩu");
                    return;
                }

                // Đăng nhập với Firebase
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(dangnhap.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(dangnhap.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                                    // Chuyển sang màn hình chính hoặc activity khác
                                    Intent intent = new Intent(dangnhap.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(dangnhap.this, "Đăng nhập thất bại. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // Xử lý sự kiện "Quên mật khẩu"
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dangnhap.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện "Đăng ký" (thêm TextView hoặc Button cho Đăng ký nếu chưa có trong layout)
        signUpTextView = findViewById(R.id.signUpTextView);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dangnhap.this, dangky.class);
                startActivity(intent);
            }
        });
    }
}