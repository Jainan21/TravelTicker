package com.example.travelticker.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelticker.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentDiaChi extends Fragment{
    GoogleMap map;
    private static final String TAG = "MapFragment";
    private static final String SHORT_LINK = "https://maps.app.goo.gl/eK2sMoYEVh8JjumC9";
    String latitude;
    String longitude;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dia_chi, container, false);
        resolveShortLink();
        initializeMap();
        return view;
    }

    private void initializeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMap);
        if (mapFragment != null) {
            mapFragment.getMapAsync(googleMap -> {
                LatLng location = new LatLng(37.7749, -122.4194);
                googleMap.addMarker(new MarkerOptions().position(location).title("Resolved Location"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
            });
        }
    }

    private void resolveShortLink() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(FragmentDiaChi.SHORT_LINK)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Failed to resolve link: " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isRedirect()) {
                    String resolvedUrl = response.header("Location");
                    Log.d(TAG, "Resolved URL: " + resolvedUrl);

                    if (resolvedUrl != null && resolvedUrl.contains("/place/")) {
                        // Extract latitude and longitude
                        String[] parts = resolvedUrl.split("/place/")[1].split(",");
                        latitude = parts[0];
                        longitude = parts[1].split("@")[0];

                        Log.i(TAG, "Latitude: " + latitude + ", Longitude: " + longitude);

                        // Load map with the resolved location
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(() -> initializeMap());
                        }
                    }
                }
            }
        });
    }


}
