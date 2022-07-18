package com.example.toprakkokusu;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

public class MapFragment extends FragmentActivity implements View.OnClickListener, OnMapReadyCallback  {

    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Button btnLocationSave;
    private static final int REQURST_CODE = 101;
    private Geocoder geocoder;
    private List<Address> addresses;

    AddressModel addressModel=AddressModel.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        btnLocationSave=findViewById(R.id.btnLocationSave);
        geocoder=new Geocoder(this, Locale.getDefault());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btnLocationSave.setOnClickListener(this);

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
                        currentLocation=location;
                        SupportMapFragment supportMapFragment=(SupportMapFragment)
                                getSupportFragmentManager().findFragmentById(R.id.mapFragment);
                        supportMapFragment.getMapAsync(MapFragment.this);

                    }
                }
            });
        }
    }

        @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLocationSave:
                onBackPressed();
                break;
        }
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng latLng=new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        MarkerOptions markerOptions=new MarkerOptions().position(latLng)
                .title("Konum");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
        googleMap.addMarker(markerOptions);
        coordinateToAddress(latLng);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions=new MarkerOptions().position(latLng).title(addressModel.getData());
                coordinateToAddress(latLng);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                googleMap.addMarker(markerOptions);
            }
        });
    }

    private void coordinateToAddress(LatLng latLng) {
        try {
            addresses=geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            //addresses=geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            addressModel.setData(addresses.get(0).getAddressLine(0));
            addressModel.setLatitude(String.valueOf(latLng.latitude));
            addressModel.setLongitude(String.valueOf(latLng.longitude));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQURST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                }
                else{
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
                break;
        }
    }
}