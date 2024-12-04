package com.example.travelticker.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.Adapter.SecondaryImageAdapter;
import com.example.travelticker.Adapter.ServiceAdapter;
import com.example.travelticker.Model.LocationViewModel;
import com.example.travelticker.Model.Post;
import com.example.travelticker.Model.dichVu;
import com.example.travelticker.DAO.dbDAO;
import com.example.travelticker.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.play.core.integrity.IntegrityTokenRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class FragmentTongQuan extends Fragment implements OnMapReadyCallback {
    TextView txtContent;
    RecyclerView recyclerDichVu, recyclerHinhAnh;
    GoogleMap map;
    dbDAO dbDAO;
    ArrayList<String> listServiceID = new ArrayList<>();
    ArrayList<dichVu> listService = new ArrayList<>();
    ArrayList<String> listImg = new ArrayList<>();
    String ltglng;
    Double longitude, latitude;
    boolean dataLoaded;
    private LocationViewModel locationViewModel;
    String locationName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tong_quan,null, false);

        locationViewModel = new ViewModelProvider(requireActivity()).get(LocationViewModel.class);


        txtContent = view.findViewById(R.id.txtContent);
        recyclerDichVu = view.findViewById(R.id.recyclerDichVu);
        recyclerHinhAnh = view.findViewById(R.id.recyclerHinhAnh);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMapTongQuan);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        layout.setOrientation(RecyclerView.HORIZONTAL);
        recyclerDichVu.setLayoutManager(layout);

        GridLayoutManager gridlayout = new GridLayoutManager(getContext(), 2, RecyclerView.HORIZONTAL, false);
        recyclerHinhAnh.setLayoutManager(gridlayout);

        listServiceID = new ArrayList<>();
        listImg = new ArrayList<>();

        dbDAO = new dbDAO();

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("BaiDang", Context.MODE_PRIVATE);
        String idBaiDang = sharedPreferences.getString("idBaiDang", "");
        String idNguoiDang = sharedPreferences.getString("idUser", "");

        dbDAO.getBaiDangByID(idBaiDang, idNguoiDang, new dbDAO.PostCallBack() {
            @Override
            public void onSuccess(Post post) {
                txtContent.setText(post.getNoiDung());
                listServiceID = post.getDichvu();
                listImg = post.getImgPhu();
                ltglng = post.getDiaChi();
                String[] location = ltglng.split(",");
                latitude = Double.parseDouble(location[0]);
                longitude = Double.parseDouble(location[1]);

                locationViewModel.setLatitude(latitude);
                locationViewModel.setLongitude(longitude);

                SecondaryImageAdapter adpImg = new SecondaryImageAdapter(getContext(), listImg);
                recyclerHinhAnh.setAdapter(adpImg);

                dbDAO.getDataList("DichVu", dichVu.class, new dbDAO.FirestoreCallback<dichVu>() {
                    @Override
                    public void onCallback(ArrayList<dichVu> dataList) {
                        for(dichVu a : dataList){
                            for (String serviceID : listServiceID){
                                if (a.getIdDichVu().equals(serviceID)){
                                    listService.add(a);
                                }
                            }
                        }
                        ServiceAdapter adpService = new ServiceAdapter(getContext(), listService);
                        recyclerDichVu.setAdapter(adpService);
                    }
                    @Override
                    public void onFailure(Exception e) {
                    }
                });

                dataLoaded = true;

                if (map != null) {
                    updateMap();
                }
            }
            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getContext(), "Failed to load post data", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (dataLoaded) {
            updateMap();
        }
    }
    private void updateMap() {
        if (latitude != null && longitude != null) {
            LatLng location = new LatLng(latitude, longitude);
            map.addMarker(new MarkerOptions().position(location).title("Marker"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        } else {
            Toast.makeText(getContext(), "Location data is unavailable", Toast.LENGTH_SHORT).show();
        }
    }

}
