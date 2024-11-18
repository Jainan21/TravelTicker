package com.example.travelticker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.Adapter.ThongBaoAdapter;
import com.example.travelticker.Model.ThongBaoModel;

import java.util.ArrayList;

public class ThongBao extends AppCompatActivity {

    RecyclerView rcv_4;
    ThongBaoAdapter tbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);

        rcv_4 = findViewById(R.id.rcv_4);
        rcv_4.setLayoutManager(new LinearLayoutManager(this));

        tbAdapter = new ThongBaoAdapter(data());
        rcv_4.setAdapter(tbAdapter);


    }

    public ArrayList<ThongBaoModel> data(){
        ArrayList<ThongBaoModel> holder = new ArrayList<>();

        ThongBaoModel ob1 = new ThongBaoModel();
        ob1.setImgTB(R.drawable.monkey);
        ob1.setTv_1("Kateruku");
        ob1.setTv_2("Anh ấy vừa thích bài viết của bạn");
        ob1.setTv_3(("8:08 11/17/2024"));
        holder.add(ob1);

        ThongBaoModel ob2 = new ThongBaoModel();
        ob2.setImgTB(R.drawable.monkey);
        ob2.setTv_1("Monkey");
        ob2.setTv_2("Anh ấy vừa thích bài viết của bạn");
        ob2.setTv_3(("8:12 11/17/2024"));
        holder.add(ob2);

        ThongBaoModel ob3 = new ThongBaoModel();
        ob3.setImgTB(R.drawable.monkey);
        ob3.setTv_1("aphi");
        ob3.setTv_2("Cô ấy vừa thích bài viết của bạn");
        ob3.setTv_3(("8:08 11/17/2024"));
        holder.add(ob3);

        return holder;

    }
}