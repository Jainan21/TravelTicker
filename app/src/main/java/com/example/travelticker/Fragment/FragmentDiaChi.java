package com.example.travelticker.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.travelticker.Model.LocationViewModel;
import com.example.travelticker.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentDiaChi extends Fragment implements OnMapReadyCallback {
    GoogleMap map;
    private static final String TAG = "MapFragment";
    Double latitude, longitude;
    private LocationViewModel locationViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dia_chi, container, false);
        locationViewModel = new ViewModelProvider(requireActivity()).get(LocationViewModel.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMap);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        latitude = locationViewModel.getLatitude().getValue();

        longitude = locationViewModel.getLongitude().getValue();

        return view;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        updateMap();
    }

    private void updateMap() {
        if (latitude != null && longitude != null) {
            LatLng location = new LatLng(latitude, longitude);
            map.addMarker(new MarkerOptions().position(location).title("Location Marker"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        } else {
            Toast.makeText(getContext(), "Location data is unavailable", Toast.LENGTH_SHORT).show();
        }
    }
}
