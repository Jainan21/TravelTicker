package com.example.travelticker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.example.travelticker.Fragment.FavoritesFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    String name = "test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DatabaseReference db;
        db = FirebaseDatabase.getInstance().getReference();
        db.child("tests").child(name).setValue("bl");

//        FavoritesFragment favoritesFragment = new FavoritesFragment();
//        FragmentManager manager = getSupportFragmentManager();
//        manager.beginTransaction()
//                .add(R.id.frame, favoritesFragment)
//                .commit();
    }
}