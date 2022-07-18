package com.example.toprakkokusu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CampLocationMapFragment extends FragmentActivity implements OnMapReadyCallback{
        private FusedLocationProviderClient fusedLocationProviderClient;
        private Button btnLocationSave;
        private static final int REQURST_CODE = 101;
        private Geocoder geocoder;
        private List<Address> addresses;

        AddressModel addressModel=AddressModel.getInstance();


    String longitude,latitude;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_map);
            btnLocationSave=findViewById(R.id.btnLocationSave);
            geocoder=new Geocoder(this, Locale.getDefault());
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            btnLocationSave.setClickable(false);
            btnLocationSave.setVisibility(View.GONE);

            Intent intent=getIntent();

            longitude=intent.getStringExtra("longitude");
            latitude=intent.getStringExtra("latitude");

            fetchLastLocation();

        }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQURST_CODE);
            onBackPressed();
            return;
        }
        else{
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null){
                        SupportMapFragment supportMapFragment=(SupportMapFragment)
                                getSupportFragmentManager().findFragmentById(R.id.mapFragment);
                        supportMapFragment.getMapAsync(CampLocationMapFragment.this);
                    }
                }
            });
        }
    }

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            LatLng latLng=new LatLng(Double.valueOf(latitude),Double.valueOf(longitude));
            MarkerOptions markerOptions=new MarkerOptions().position(latLng)
                    .title("Konum");
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
            googleMap.addMarker(markerOptions);
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            switch (requestCode) {
                case REQURST_CODE:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    }
                    else{
                        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                    }
                    break;
            }
        }
    }

