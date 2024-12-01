package com.example.travelticker.DAO;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.PictureDrawable;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Context;
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

}
