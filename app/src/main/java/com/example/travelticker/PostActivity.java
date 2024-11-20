package com.example.travelticker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.Adapter.DistrictionAdapter;
import com.example.travelticker.Adapter.MenuPostAdapter;
import com.example.travelticker.Model.MenuPost;
import com.example.travelticker.Model.dichVu;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rcvMenuPost;
    MenuPostAdapter adapter;
    ArrayList<MenuPost> menupost;
    DistrictionAdapter districtionAdapter;
    ArrayList<dichVu> listDis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post);

        rcvMenuPost = findViewById(R.id.rcvMenuPost);
        toolbar = findViewById(R.id.toolBarPost);

        rcvMenuPost.setLayoutManager(new LinearLayoutManager(this));
        menupost = new ArrayList<>();
        menupost.add(new MenuPost("Thêm địa chỉ", 0));
        menupost.add(new MenuPost("Dịch vụ giải trí", 1));
        menupost.add(new MenuPost("Thêm ảnh phụ", 2));
        adapter = new MenuPostAdapter(this, menupost);

        rcvMenuPost.setAdapter(adapter);

        adapter.setOnItemClickListener(new MenuPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int poisition) {
                if(poisition == 0){
                    addLocation();
                }else if(poisition == 1){
                    addDistriction();
                }
            }
        });

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tạo bài viết");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void addLocation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_location_post, null);
        builder.setView(dialogView);

        EditText edtLinkLocation = dialogView.findViewById(R.id.edtLinkLocation);
        Button btnAddLocation = dialogView.findViewById(R.id.btnAddLocation);

        btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void  addDistriction(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_distriction_post, null);

        RecyclerView rcvDis = dialogView.findViewById(R.id.rcvDistriction);
        rcvDis.setLayoutManager(new GridLayoutManager(this, 2));
        listDis = new ArrayList<>();
        listDis.add(new dichVu("img1", "thả dù"));
        listDis.add(new dichVu("img1", "lướt sóng"));
        listDis.add(new dichVu("img1", "lặn"));
        listDis.add(new dichVu("img1", "trượt băng"));
        listDis.add(new dichVu("img1", "xông khói"));

        districtionAdapter = new DistrictionAdapter(this, listDis);
        rcvDis.setAdapter(districtionAdapter);

        builder.setView(dialogView);

        Button btnAddDis = dialogView.findViewById(R.id.btnAddDistriction);

        btnAddDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}