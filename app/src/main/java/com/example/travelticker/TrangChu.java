package com.example.travelticker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.travelticker.Fragment.FavoritesFragment;
import com.example.travelticker.Fragment.FragmentTrangChu;
import com.example.travelticker.Fragment.HoSoFragment;
import com.example.travelticker.Fragment.ThongBaoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TrangChu extends AppCompatActivity {
    private FragmentTrangChu fragmentTrangChu = new FragmentTrangChu();
    private FavoritesFragment fragmentYeuThich = new FavoritesFragment();
    private ThongBaoFragment fragmentThongBao = new ThongBaoFragment();
    private HoSoFragment fragmentHoSo = new HoSoFragment();
//    private FragmentTrangChu framentTrangChu = new FragmentTrangChu();
//    private FragmentTrangChu framentTrangChu = new FragmentTrangChu();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_chu);

        BottomNavigationView bottom_nav = findViewById(R.id.bottomNavigation);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, fragmentTrangChu)
                    .commit();
        };
        bottom_nav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.icon_home_menu) {
                selectedFragment = fragmentTrangChu;
            } else if (item.getItemId() == R.id.icon_fav_menu) {
                selectedFragment = fragmentYeuThich;
            } else if (item.getItemId() == R.id.icon_noti_menu) {
                selectedFragment = fragmentThongBao;
            } else if (item.getItemId() == R.id.icon_user_menu) {
                selectedFragment = fragmentHoSo;
            }
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .commit();
            }
            return true;
        });
    }
}