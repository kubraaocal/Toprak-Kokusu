package com.example.toprakkokusu;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddressModel extends ViewModel {
    private static String data;
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

}
