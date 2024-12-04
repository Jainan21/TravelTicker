package com.example.travelticker.Fragment;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelticker.Adapter.SecondaryImageAdapter;
import com.example.travelticker.Adapter.ServiceAdapter;
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

import java.util.ArrayList;

public class FragmentTongQuan extends Fragment implements OnMapReadyCallback {
    TextView txtContent, txtDescription;
    RecyclerView recyclerDichVu, recyclerHinhAnh;
    GoogleMap map;
    dbDAO dbDAO;
    ArrayList<String> listServiceID = new ArrayList<>();
    ArrayList<dichVu> listService = new ArrayList<>();
    ArrayList<String> listImg = new ArrayList<>();
    String ltglng;
    Double longitude, latitude;
    private String idBaiDang = "-ODF8lHF8_fbXLoUgK-z";
    private boolean dataLoaded = false;  // Flag to indicate when data is loaded

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tong_quan,null, false);

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

        dbDAO.getBaiDangByID(idBaiDang, new dbDAO.PostCallBack() {
            @Override
            public void onSuccess(Post post) {
                txtContent.setText(post.getNoiDung());
                listServiceID = post.getDichvu();
                listImg = post.getImgPhu();
                ltglng = post.getDiaChi();
                String[] location = ltglng.split(",");
                latitude = Double.parseDouble(location[0]);
                longitude = Double.parseDouble(location[1]);

                SecondaryImageAdapter adpImg = new SecondaryImageAdapter(getContext(), listImg);
                recyclerHinhAnh.setAdapter(adpImg);

                dbDAO.getDataList("DichVu", dichVu.class, new dbDAO.FirestoreCallback<dichVu>() {
                    @Override
                    public void onCallback(ArrayList<dichVu> dataList) {
                        for(dichVu a : dataList){
                            for (String serviceID : listServiceID){
                                if (a.getIdDichVu().equals(serviceID)){
                                    listService.add(a);
                                    Log.d("list/service", String.valueOf(listService));
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
            map.addMarker(new MarkerOptions().position(location).title("Location Marker"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        } else {
            Toast.makeText(getContext(), "Location data is unavailable", Toast.LENGTH_SHORT).show();
        }
    }
}
