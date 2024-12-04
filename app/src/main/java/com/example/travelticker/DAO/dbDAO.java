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
import com.example.travelticker.Model.FamousUser;
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
import com.google.protobuf.Value;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
                            T data = document.toObject(modelClass);
                            dataList.add(data);
                        }
                        callback.onCallback(dataList);
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

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
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    InputStream inputStream = response.body().byteStream();
                    try {
                        SVG svg = SVG.getFromInputStream(inputStream);
                        final PictureDrawable drawable = new PictureDrawable(svg.renderToPicture());

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
                    ArrayList<String> imgPhu = (ArrayList<String>) snapshot.child("imgPhu").getValue();
                    ArrayList<String> dichVu = (ArrayList<String>) snapshot.child("dichvu").getValue();

                    callBack.onSuccess(new Post(idBaiDang, userId, "1", content, location, mainImage, date, title, imgPhu, dichVu));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public interface PostCallBack {
        void onSuccess(Post post);
        void onFailure(String errorMessage);
    }
    public void getRandomPost(RandomPostCallBack callback){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("BaiDang");
        ArrayList<FamousUser> listPost = new ArrayList<>();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    String idBaiDang = data.getKey();
                    String idNguoiDang = data.child("idNguoiDang").getValue(String.class);
                    String title = data.child("tieuDe").getValue(String.class);
                    String content = data.child("noiDung").getValue(String.class);
                    String mainImage = data.child("img").getValue(String.class);

                    listPost.add(new FamousUser(idBaiDang, idNguoiDang, mainImage, content, title));
                }
                Collections.shuffle(listPost);
                ArrayList<FamousUser> randomPosts = new ArrayList<>(listPost.subList(0, Math.min(5, listPost.size())));
                callback.onSuccess(randomPosts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface RandomPostCallBack {
        void onSuccess(ArrayList<FamousUser> post);
        void onFailure(String errorMessage);
    }
}
