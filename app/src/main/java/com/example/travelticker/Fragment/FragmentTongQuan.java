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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class FragmentTongQuan extends Fragment implements OnMapReadyCallback {
    TextView txtContent, txtDescription;
    RecyclerView recyclerDichVu, recyclerHinhAnh;
    GoogleMap map;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tong_quan,null, false);

        txtContent = view.findViewById(R.id.txtContent);
        txtDescription = view.findViewById(R.id.txtDescription);
        recyclerDichVu = view.findViewById(R.id.recyclerDichVu);
        recyclerHinhAnh = view.findViewById(R.id.recyclerHinhAnh);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMapTongQuan);
        if (mapFragment!=null){
            mapFragment.getMapAsync( this);
        }
        txtContent.setText("Đây là phần nội dung chính của bài đăng");
        txtDescription.setText("Đây là phần mô tả của người viết bài về khu vực du lịch trong bài đăng");



        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        layout.setOrientation(RecyclerView.HORIZONTAL);
        recyclerDichVu.setLayoutManager(layout);

        ArrayList<Service> listService = new ArrayList<>();
        listService.add(new Service(R.drawable.icon_sailing, "Cheo Thuyen", "FC5757"));
        listService.add(new Service(R.drawable.icon_sailing, "Cheo Thuyen", "03A9F4"));
        listService.add(new Service(R.drawable.icon_sailing, "Cheo Thuyen", "FC5757"));
        ServiceAdapter adpService = new ServiceAdapter(getContext(), listService);

        recyclerDichVu.setAdapter(adpService);

        GridLayoutManager gridlayout = new GridLayoutManager(getContext(), 2, RecyclerView.HORIZONTAL, false);


        recyclerHinhAnh.setLayoutManager(gridlayout);

        ArrayList<Integer> listImg = new ArrayList<>();
        listImg.add(R.drawable.halongbay);
        listImg.add(R.drawable.halongbay);
        listImg.add(R.drawable.halongbay);
        listImg.add(R.drawable.halongbay);
        listImg.add(R.drawable.halongbay);
        listImg.add(R.drawable.halongbay);
        listImg.add(R.drawable.halongbay);


        SecondaryImageAdapter adpImage = new SecondaryImageAdapter(getContext(), listImg);

        recyclerHinhAnh.setAdapter(adpImage);

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
