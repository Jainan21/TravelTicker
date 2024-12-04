package com.example.travelticker;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelticker.Adapter.DistrictionAdapter;
import com.example.travelticker.Adapter.MenuPostAdapter;
import com.example.travelticker.DAO.UserDbDAO;
import com.example.travelticker.Model.MenuPost;
import com.example.travelticker.Model.Post;
import com.example.travelticker.Model.User;
import com.example.travelticker.Model.dichVu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class PostActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rcvMenuPost;
    ImageView imgMainPost, imgUserPost;
    TextView txtTitlePost, txtContentPost,txtUserPost;

    MenuPostAdapter adapter;
    DistrictionAdapter districtionAdapter;

    ArrayList<MenuPost> menupost;
    ArrayList<dichVu> listDis;
    ArrayList<String> anotherImages = new ArrayList<>();

    String location = "";
    Uri mainImg;
    Boolean isDisLoad = false;
    String userID;

    UserDbDAO userDbDAO;


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
        txtTitlePost = findViewById(R.id.txtTitlePost);
        txtContentPost = findViewById(R.id.txtContentPost);
        imgUserPost = findViewById(R.id.imgUserPost);
        txtUserPost = findViewById(R.id.txtUserPost);

        getUserId();

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
                }else {
                    addAnotherImage(menupost.get(2));
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
        } else if (item.getItemId() == R.id.btnPost){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == PICK_IMAGE_REQUEST){//thêm ảnh chính
                //lấy đường ảnh đã chọn
                imageUri = data.getData();
                //hiển thị ảnh
                ImageView imageView = findViewById(R.id.imgMainPost);
                imageView.setImageURI(imageUri);
            } else if (requestCode == 2){//thêm ảnh phụ
                if (data.getClipData() != null){
                    //chọn nhiều ảnh
                    int count = data.getClipData().getItemCount();
                    for (int i =0; i < count; i++){
                        Uri uri = data.getClipData().getItemAt(i).getUri();
                        //thêm vào list
                        menupost.get(2).addAnotherImage(uri);
                    }
                }else if (data.getData() != null){
                    //chọn 1 ảnh
                    Uri uri = data.getData();
                    menupost.get(2).addAnotherImage(uri);
                }
                adapter.notifyDataSetChanged();
            }
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

        edtLinkLocation.setOnKeyListener(((view, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_V){
                pasteLinkMap(PostActivity.this, edtLinkLocation);
                return true;
            }else {
                return false;
            }
        }));

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

        if (!isDisLoad){
            listDis = new ArrayList<>();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("DichVu")
                    .get() // Lấy dữ liệu một lần
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            listDis.clear();

                            // Duyệt qua các document trong collection "DichVu"
                            for (DocumentSnapshot document : querySnapshot) {
                                dichVu dv = document.toObject(dichVu.class);
                                if (dv != null) {
                                    listDis.add(dv);
                                }
                            }

                            // Cập nhật Adapter
                            districtionAdapter = new DistrictionAdapter(PostActivity.this, listDis, menuPost);
                            rcvDis.setAdapter(districtionAdapter);
                            isDisLoad = true;

                        } else {
                            Log.w("FirebaseData", "Error getting documents.", task.getException());
                            Toast.makeText(PostActivity.this, "Lỗi khi tải dịch vụ", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(PostActivity.this, "Lỗi khi tải dịch vụ", Toast.LENGTH_SHORT).show();
                    });
        } else {
            districtionAdapter = new DistrictionAdapter(PostActivity.this, listDis, menuPost);
            rcvDis.setAdapter(districtionAdapter);
        }

        // Tạo danh sách dịch vụ đã chọn từ MenuPost
        ArrayList<dichVu> selectedItems = menuPost.getSelectedImages();
        ArrayList<String> idDV = menuPost.getIdDV();
        if (selectedItems == null) {
            selectedItems = new ArrayList<>();
            idDV = new ArrayList<>();// Khởi tạo nếu null
        }

        for (dichVu dis : listDis) {
            for (dichVu selected : selectedItems) {
                if (dis.getTen().equals(selected.getTen())) {
                    dis.setSelected(true);
                }
            }
        }

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
                ArrayList<String> idDV = new ArrayList<>();
                for (dichVu imageItem : listDis) {
                    if (imageItem.isSelected()) {
                        selectedItems.add(imageItem);
                        idDV.add(imageItem.getIdDichVu());
                    }
                }

                //cập nật danh sách
                menuPost.setSelectedImages(selectedItems);
                menuPost.setIdDV(idDV);

                adapter.notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void addAnotherImage(MenuPost menuPost){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //chọn nhiểu ảnh
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        //trả về vị trí cập nhật
        startActivityForResult(intent, 2);
    }

    private void uploadMainImg(Uri imageUri, UploadCallback callback){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + UUID.randomUUID().toString());
        storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                    //lấy url ảnh khi upload thành công
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        mainImg = uri;
                        callback.onUploadSuccess(mainImg);
                    });
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Tải ảnh lên thất bại !!!", Toast.LENGTH_SHORT).show());
    }

    interface UploadCallback{
        void onUploadSuccess(Uri imageUrls);
    }

    private void uploadAnotherImages(Runnable onComplete){
        for (Uri uri : menupost.get(2).getListAnotherImage()){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + UUID.randomUUID().toString());
            storageReference.putFile(uri)
                    .addOnSuccessListener(taskSnapshot -> {
                        storageReference.getDownloadUrl().addOnSuccessListener(uri1 -> {
                            anotherImages.add(uri1.toString());
                            //nếu upload hêt ảnh phụ
                            if (anotherImages.size() == menupost.get(2).getListAnotherImage().size()){
                                onComplete.run();
                            }
                        });
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Tải ảnh phụ lên thất bại !!!", Toast.LENGTH_SHORT).show());
        }
    }

    public void DangBai(View view){
        String tieude = txtTitlePost.getText().toString();
        String noidung = txtContentPost.getText().toString();
        String ngaydang = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        if (tieude.isEmpty() || noidung.isEmpty() || menupost.get(1).getSelectedImages().isEmpty() || location.isEmpty() || menupost.get(2).getListAnotherImage().isEmpty() || mainImg != null){
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        //kiểm tra upload ảnh
        if (imageUri != null && !menupost.get(2).getListAnotherImage().isEmpty()){
            uploadMainImg(imageUri, imageUrls -> {
                uploadAnotherImages(() -> {
                    Post post = new Post(userID, noidung, location, mainImg.toString(), ngaydang, tieude, anotherImages, menupost.get(1).getIdDV());

                    //lưu bài viết
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    String idBaiDang = databaseReference.push().getKey();
                    if (idBaiDang != null){
                        databaseReference.child("BaiDang").child(idBaiDang).setValue(post).addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                Toast.makeText(this, "Đăng bài thành công", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(this, "Đăng bài thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            });
        }else {
            Toast.makeText(this, "Vui lòng chọn ảnh chính và phụ !!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void getUserId(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userID = sharedPreferences.getString("userId", null);

        if (userID == null){
            Toast.makeText(this, "Lấy Id người dùng thất bại", Toast.LENGTH_SHORT).show();
        }else {
            userDbDAO = new UserDbDAO();
            userDbDAO.getUserById(userID, new UserDbDAO.UserCallBack() {
                @Override
                public void onSuccess(User user) {
                    Glide.with(PostActivity.this).load(user.getAvatarUrl()).into(imgUserPost);
                    txtUserPost.setText(user.getName());
                }

                @Override
                public void onError(String error) {
                    System.err.println(error);
                }
            });
        }
    }

    public void pasteLinkMap(Context context, EditText edt){
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        if (clipboardManager.hasPrimaryClip()){
            ClipData clipData = clipboardManager.getPrimaryClip();
            ClipData.Item item = clipData.getItemAt(0);
            String paste = item.getText().toString();
            edt.setText(paste);
        }else {
            Toast.makeText(context, "Clipboard trống", Toast.LENGTH_SHORT).show();
        }
    }

}