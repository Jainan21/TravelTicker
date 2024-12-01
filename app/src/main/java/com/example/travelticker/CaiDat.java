package com.example.travelticker;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.Adapter.PersonAdapter;

public class CaiDat extends AppCompatActivity {
    PersonAdapter perAdt2, perAdt3;
    RecyclerView rcv_2, rcv_3;
    ImageButton Imgbt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai_dat);

        rcv_2 = findViewById(R.id.rcv_2);
        rcv_2.setLayoutManager(new LinearLayoutManager(this));
        rcv_3 = findViewById(R.id.rcv_3);
        rcv_3.setLayoutManager(new LinearLayoutManager(this));

        Imgbt2 = findViewById(R.id.Imgbt2);

        Drawable[] icon2 = new Drawable[]{
                getDrawable(R.drawable.language),
                getDrawable(R.drawable.settings),
                getDrawable(R.drawable.people),
                getDrawable(R.drawable.bookmart)
        };

        Drawable[] icon3 = new Drawable[]{
                getDrawable(R.drawable.shield),
                getDrawable(R.drawable.lock),
                getDrawable(R.drawable.dataset)
        };

        String arr2[] = {"Cài đặt ngôn ngữ", "Cài đặt hồ sơ", "Mời bạn bè", "Chính sách đã lưu"};
        String arr3[] = {"Chính sách bảo mật", "Điều khoản dịch vụ", "Phân bổ dữ liệu"};
        perAdt2 = new PersonAdapter(this, arr2, icon2);
        rcv_2.setAdapter(perAdt2);
        perAdt3 = new PersonAdapter(this, arr3, icon3);
        rcv_3.setAdapter(perAdt3);

        Imgbt2.setOnClickListener(v -> finish());
    }
}