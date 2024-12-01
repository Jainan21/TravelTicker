package com.example.travelticker.DAO;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Context;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

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

}
