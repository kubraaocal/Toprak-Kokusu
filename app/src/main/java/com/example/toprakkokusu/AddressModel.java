package com.example.toprakkokusu;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddressModel extends ViewModel {
    private static String data;
    private static String latitude,longitude;
    private static AddressModel instance;

    static {
        instance = new AddressModel();
    }

    private AddressModel(){}

    public static AddressModel getInstance(){
        return instance;
    }

    public void setData(String data){
       this.data= data;
    }

    public String getData(){
        return data;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        AddressModel.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        AddressModel.longitude = longitude;
    }
}
