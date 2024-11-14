package com.example.travelticker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(WelcomeActivity.this, Onboarding1.class);
            startActivity(intent);
            finish();
        }, 3000);

    }
}