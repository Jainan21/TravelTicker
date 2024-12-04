package com.example.travelticker.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocationViewModel extends ViewModel {
    private final MutableLiveData<Double> latitude = new MutableLiveData<>();
    private final MutableLiveData<Double> longitude = new MutableLiveData<>();

    public void setLatitude(double lat) {
        latitude.setValue(lat);
    }

    public void setLongitude(double lon) {
        longitude.setValue(lon);
    }

    public LiveData<Double> getLatitude() {
        return latitude;
    }

    public LiveData<Double> getLongitude() {
        return longitude;
    }
}
