package com.example.travelticker;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.Adapter.PersonAdapter;

public class ThongTinTK extends AppCompatActivity {

    private RecyclerView rcv_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_tk);

        rcv_1 = findViewById(R.id.rcv_1);
        rcv_1.setLayoutManager(new LinearLayoutManager(this));

        Drawable[] icon = new Drawable[]{
                getDrawable(R.drawable.settings),
                getDrawable(R.drawable.help),
                getDrawable(R.drawable.star),
                getDrawable(R.drawable.logout)
        };
        String arr[] = {"Cài đặt", "Hỗ trợ", "Đánh giá", "Đăng xuất"};
        rcv_1.setAdapter(new PersonAdapter(arr, icon));
    }
}