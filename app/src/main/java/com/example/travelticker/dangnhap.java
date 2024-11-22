package com.example.travelticker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.common.api.ApiException;

public class dangnhap extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private CheckBox rememberCheckBox;
    private TextView forgotPasswordTextView, signUpTextView;
    private FirebaseAuth mAuth;

    // Google Sign-In
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        mAuth = FirebaseAuth.getInstance();

        // Initialize Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Initialize UI components
        emailEditText = findViewById(R.id.Email);
        passwordEditText = findViewById(R.id.Password);
        loginButton = findViewById(R.id.Login);
        rememberCheckBox = findViewById(R.id.Remember);
        forgotPasswordTextView = findViewById(R.id.ForgotPassword);
        signUpTextView = findViewById(R.id.signUpTextView);

        // Email/Password login logic
        loginButton.setOnClickListener(v -> {
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

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(dangnhap.this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(dangnhap.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(dangnhap.this, TrangChu.class); // Chuyển đến TrangChu Activity
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(dangnhap.this, "Đăng nhập thất bại. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Forgot password
        forgotPasswordTextView.setOnClickListener(v -> {
            Intent intent = new Intent(dangnhap.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        // Sign up
        signUpTextView.setOnClickListener(v -> {
            Intent intent = new Intent(dangnhap.this, dangky.class);
            startActivity(intent);
        });

        // Google Sign-In button logic
        ImageView googleLoginButton = findViewById(R.id.google);
        googleLoginButton.setOnClickListener(v -> signInWithGoogle());
    }

    // Google Sign-In logic
    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(this, "Đăng nhập bằng Google thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(dangnhap.this, "Đăng nhập bằng Google thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(dangnhap.this, TrangChu.class); // Chuyển đến TrangChu Activity
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(dangnhap.this, "Đăng nhập bằng Google thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
