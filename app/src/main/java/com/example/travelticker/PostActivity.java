package com.example.travelticker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    ImageView imgMainPost;
    MenuPostAdapter adapter;
    ArrayList<MenuPost> menupost;
    DistrictionAdapter districtionAdapter;
    ArrayList<dichVu> listDis;
    String location = "";

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post);

        rcvMenuPost = findViewById(R.id.rcvMenuPost);
        toolbar = findViewById(R.id.toolBarPost);
        imgMainPost = findViewById(R.id.imgMainPost);

        rcvMenuPost.setLayoutManager(new LinearLayoutManager(this));
        menupost = new ArrayList<>();
        menupost.add(new MenuPost("Thêm địa chỉ", 0, 0));
        menupost.add(new MenuPost("Dịch vụ giải trí", 1, 0));
        menupost.add(new MenuPost("Thêm ảnh phụ", 2, 0));
        adapter = new MenuPostAdapter(this, menupost);

        rcvMenuPost.setAdapter(adapter);

        adapter.setOnItemClickListener(new MenuPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int poisition) {
                if(poisition == 0){
                    addLocation();
                }else if(poisition == 1){
                    addDistriction(menupost.get(1));
                }
            }
        });

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tạo bài viết");

        //nhấn vào chọn ảnh
        imgMainPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST){
            //lấy đường ảnh đã chọn
            imageUri = data.getData();

            //hiển thị ảnh
            ImageView imageView = findViewById(R.id.imgMainPost);
            imageView.setImageURI(imageUri);
        }
    }

    public void addLocation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_location_post, null);
        builder.setView(dialogView);

        EditText edtLinkLocation = dialogView.findViewById(R.id.edtLinkLocation);
        Button btnAddLocation = dialogView.findViewById(R.id.btnAddLocation);

        AlertDialog dialog = builder.create();

        if (!location.isEmpty()){
            edtLinkLocation.setText(location);
        }

        btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtLinkLocation.getText().toString().trim().isEmpty()){
                    location = "";
                    adapter.updateQuantity(0, 0);
                    Toast.makeText(PostActivity.this, "Bạn chưa nhập thông tin !!!", Toast.LENGTH_SHORT).show();
                }else {
                    location = edtLinkLocation.getText().toString().trim();
                    adapter.updateQuantity(0, 1);
                    Toast.makeText(PostActivity.this, "Thêm thành công !!!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    public void  addDistriction(MenuPost menuPost){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_distriction_post, null);

        RecyclerView rcvDis = dialogView.findViewById(R.id.rcvDistriction);
        rcvDis.setLayoutManager(new GridLayoutManager(this, 2));
        listDis = new ArrayList<>();
        listDis.add(new dichVu("android.resource://com.example.travelticker/drawable/halongbay", "thả dù"));
        listDis.add(new dichVu("android.resource://com.example.travelticker/drawable/halongbay", "lướt sóng"));
        listDis.add(new dichVu("android.resource://com.example.travelticker/drawable/halongbay", "lặn"));
        listDis.add(new dichVu("android.resource://com.example.travelticker/drawable/halongbay", "trượt băng"));
        listDis.add(new dichVu("android.resource://com.example.travelticker/drawable/halongbay", "xông khói"));

        // Tạo danh sách dịch vụ đã chọn từ MenuPost
        ArrayList<dichVu> selectedItems = menuPost.getSelectedImages();
        if (selectedItems == null) {
            selectedItems = new ArrayList<>();  // Khởi tạo nếu null
        }

        for (dichVu dis : listDis) {
            for (dichVu selected : selectedItems) {
                if (dis.getName().equals(selected.getName())) {
                    dis.setSelected(true);
                }
            }
        }

        districtionAdapter = new DistrictionAdapter(this, listDis, menuPost);
        rcvDis.setAdapter(districtionAdapter);

        builder.setView(dialogView);

        Button btnAddDis = dialogView.findViewById(R.id.btnAddDistriction);

        if (selectedItems.isEmpty()){
            btnAddDis.setText("Thêm");
        }else {
            btnAddDis.setText("Chỉnh sửa");
        }

        AlertDialog dialog = builder.create();

        btnAddDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy các dịch vụ đã chọn
                ArrayList<dichVu> selectedItems = new ArrayList<>();
                for (dichVu imageItem : listDis) {
                    if (imageItem.isSelected()) {
                        selectedItems.add(imageItem);
                    }
                }

                //cập nật danh sách
                menuPost.setSelectedImages(selectedItems);

                adapter.notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        dialog.show();
    }

}