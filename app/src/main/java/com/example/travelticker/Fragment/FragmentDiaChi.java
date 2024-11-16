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

public class FragmentDiaChi extends Fragment {
    WebView map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dia_chi, container, false);
        map = view.findViewById(R.id.web_frag_diachi);

        WebSettings webSettings = map.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true); // Enable DOM storage for better web performance
        map.setWebViewClient(new WebViewClient());

        String googleMapsUrl = "https://www.google.com/maps";
        map.loadUrl(googleMapsUrl);

        return view;
    }

    // Handle back button to navigate within WebView
    @Override
    public void onResume() {
        super.onResume();
        map.requestFocus();
    }
}
