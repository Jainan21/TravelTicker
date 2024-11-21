package com.example.travelticker.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelticker.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentDiaChi extends Fragment implements OnMapReadyCallback {
    GoogleMap map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dia_chi, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMap);
        if (mapFragment!=null){
            mapFragment.getMapAsync(this);
        }

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker and move the camera

        LatLng location = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(location).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
    }
}
