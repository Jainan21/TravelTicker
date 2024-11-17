package com.example.travelticker.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.travelticker.Fragment.FragmentDanhGia;
import com.example.travelticker.Fragment.FragmentDiaChi;
import com.example.travelticker.Fragment.FragmentTongQuan;

public class DetailAdapter extends FragmentStateAdapter {

    public DetailAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new FragmentDanhGia();
            case 2:
                return new FragmentDiaChi();
            default:
                return new FragmentTongQuan();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Number of tabs
    }
}
