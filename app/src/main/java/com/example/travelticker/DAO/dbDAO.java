package com.example.travelticker.DAO;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.PictureDrawable;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.example.travelticker.Model.Post;
import com.example.travelticker.Model.dichVu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class dbDAO {
    private final FirebaseFirestore db;
    private Context c;



    public dbDAO() {
        db = FirebaseFirestore.getInstance();
    }

    public <T> void getDataList(String collectionName, Class<T> modelClass, final FirestoreCallback<T> callback) {
        db.collection(collectionName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<T> dataList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            T data = document.toObject(modelClass);  // Chuyển dữ liệu thành object của modelClass
                            dataList.add(data);  // Thêm object vào ArrayList
                        }
                        callback.onCallback(dataList);  // Trả dữ liệu về qua callback
                    } else {
                        callback.onFailure(task.getException());  // Xử lý lỗi
                    }
                });
    }

    // Interface callback tổng quát
    public interface FirestoreCallback<T> {
        void onCallback(ArrayList<T> dataList);
        void onFailure(Exception e);
    }

    public void loadSvgFromUrl(final String svgUrl, final ImageView imageView) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(svgUrl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle error, for example log it
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    InputStream inputStream = response.body().byteStream();
                    try {
                        // Parse SVG from input stream
                        SVG svg = SVG.getFromInputStream(inputStream);
                        final PictureDrawable drawable = new PictureDrawable(svg.renderToPicture());

                        // Chuyển PictureDrawable thành Bitmap
                        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmap);
                        drawable.getPicture().draw(canvas);

                        // Post back to main thread to update UI
                        imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    } catch (SVGParseException e) {
                        // Handle SVG parsing errors
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getBaiDangByID(String idBaiDang, PostCallBack callBack) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("BaiDang");
        String userId = "t828Jl8YtcYXKPBag0JNiSmOG8p1";  // Replace with dynamic userId if necessary

        dbRef.child(idBaiDang).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Extract basic post information
                    String title = snapshot.child("tieuDe").getValue(String.class);
                    String location = snapshot.child("diaChi").getValue(String.class);
                    String content = snapshot.child("noiDung").getValue(String.class);
                    String mainImage = snapshot.child("img").getValue(String.class);
                    String date = snapshot.child("ngayDang").getValue(String.class);

                    // Initialize data lists
                    ArrayList<String> imgPhu = new ArrayList<>();
                    ArrayList<dichVu> dichVuList = new ArrayList<>();

                    // Helper array to track task completion
                    final int[] completedTasks = {0};

                    // Callback method to check when both imgPhu and dichVu are loaded
                    Runnable checkAndCreatePost = () -> {
                        completedTasks[0]++;
                        if (completedTasks[0] == 2) {
                            Post post = new Post(idBaiDang, userId, "0", content, location, mainImage, date, title, imgPhu, dichVuList);
                            callBack.onSuccess(post);
                        }
                    };

                    // Load imgPhu (image array)
                    dbRef.child(idBaiDang).child("imgPhu").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data : snapshot.getChildren()) {
                                String linkSubImg = data.getValue(String.class);
                                if (linkSubImg != null) {
                                    imgPhu.add(linkSubImg);
                                }
                            }
                            checkAndCreatePost.run();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            callBack.onFailure("Failed to load images: " + error.getMessage());
                        }
                    });

                    // Load dichVu (services array)
                    dbRef.child(idBaiDang).child("dichVu").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data : snapshot.getChildren()) {
                                dichVu service = data.getValue(dichVu.class);
                                if (service != null) {
                                    dichVuList.add(service);
                                }
                            }
                            checkAndCreatePost.run();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            callBack.onFailure("Failed to load services: " + error.getMessage());
                        }
                    });
                } else {
                    callBack.onFailure("Post not found for ID: " + idBaiDang);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callBack.onFailure("Error fetching post: " + error.getMessage());
            }
        });
    }

    public interface PostCallBack {
        void onSuccess(Post post);
        void onFailure(String errorMessage);
    }


}
