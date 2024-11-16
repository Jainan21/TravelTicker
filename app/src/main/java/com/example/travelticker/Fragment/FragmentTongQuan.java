package com.example.travelticker.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.Adapter.SecondaryImageAdapter;
import com.example.travelticker.Adapter.ServiceAdapter;
import com.example.travelticker.Model.Image;
import com.example.travelticker.Model.Service;
import com.example.travelticker.R;

import java.util.ArrayList;

public class FragmentTongQuan extends Fragment {
    TextView txtContent, txtDescription;
    RecyclerView recyclerDichVu, recyclerHinhAnh;
    WebView map;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tong_quan,container, false);

        txtContent = view.findViewById(R.id.txtContent);
        txtDescription = view.findViewById(R.id.txtDescription);
        recyclerDichVu = view.findViewById(R.id.recyclerDichVu);
        map = view.findViewById(R.id.webView_map);
        recyclerHinhAnh = view.findViewById(R.id.recyclerHinhAnh);

        txtContent.setText("Đây là phần nội dung chính của bài đăng");
        txtDescription.setText("Đây là phần mô tả của người viết bài về khu vực du lịch trong bài đăng");
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        layout.setOrientation(RecyclerView.HORIZONTAL);
        recyclerDichVu.setLayoutManager(layout);

        ArrayList<Service> listService = new ArrayList<>();
        listService.add(new Service(R.drawable.icon_sailing, "Cheo Thuyen", "FC5757"));
        ServiceAdapter adpService = new ServiceAdapter(getContext(), listService);

        WebSettings webSettings = map.getSettings();
        webSettings.setJavaScriptEnabled(true);
        map.setWebViewClient(new WebViewClient());

        String googleMapsUrl = "https://www.google.com/maps";
        map.loadUrl(googleMapsUrl);

        GridLayoutManager gridlayout = new GridLayoutManager(getContext(), 2, RecyclerView.HORIZONTAL, false);
        recyclerHinhAnh.setLayoutManager(gridlayout);

        ArrayList<Integer> listImg = new ArrayList<>();
        listImg.add(R.drawable.halongbay);
        SecondaryImageAdapter adpImage = new SecondaryImageAdapter(getContext(), listImg);

        recyclerHinhAnh.setAdapter(adpImage);

        return view;
    }
}
