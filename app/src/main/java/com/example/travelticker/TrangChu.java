package com.example.travelticker;

import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.travelticker.Fragment.FragmentOnboarding1;
import com.example.travelticker.Fragment.FragmentOnboarding2;
import com.example.travelticker.Fragment.FragmentOnboarding3;

public class TrangChu extends AppCompatActivity {
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_chu);

        //Hiển thị fragment đầu tiên
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new FragmentOnboarding1())
                .commit();

        //Tự động chuyển Fragment
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if(currentFragment instanceof FragmentOnboarding1){

                    //Chuyển sang Onboarding2
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new FragmentOnboarding2())
                            .commit();
                } else if (currentFragment instanceof FragmentOnboarding2) {
                    //Chuyển sang Onboarding 3
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new FragmentOnboarding3())
                            .commit();

                }
            }
        };

        handler.postDelayed(runnable, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}