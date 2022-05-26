package com.example.toprakkokusu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class CampDetailActivity extends AppCompatActivity implements View.OnClickListener {

    SliderView sliderView;
    String longitude,latitude;
    int[] images={R.drawable.logo,R.drawable.logo};

    private TextView txtCampName,txtCampExplation,txtCampLocation;
    private ImageButton wc, paid, transport, facility,park,drink,pet,fire,wifi,beach,walk,favorite;


    private FirebaseAuth mAuth;
    private DatabaseReference mCampReference,mCampPhotoReference;
    List listCamp=new ArrayList<>();
    ArrayList<String> photoModel=new ArrayList<>();

    ArrayList<CampModel> campModel = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_detail);

        txtCampName=findViewById(R.id.txtCampName);
        txtCampExplation=findViewById(R.id.txtCampExplation);
        txtCampLocation=findViewById(R.id.txtCampLocation);

        txtCampLocation.setOnClickListener(this);

        wc = findViewById(R.id.wc);
        paid = findViewById(R.id.paid);
        transport = findViewById(R.id.transport);
        facility = findViewById(R.id.facility);
        park=findViewById(R.id.parking);
        pet=findViewById(R.id.pets);
        drink=findViewById(R.id.drink);
        fire=findViewById(R.id.fire);
        beach=findViewById(R.id.beach);
        walk=findViewById(R.id.walk);
        wifi=findViewById(R.id.wifi);
        favorite=findViewById(R.id.favorite_button);
        favorite.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mCampReference = FirebaseDatabase.getInstance().getReference().child("Camp");
        mCampPhotoReference = FirebaseDatabase.getInstance().getReference().child("Photo");



        sliderView=findViewById(R.id.image_slider);

        SliderAdapter sliderAdapter=new SliderAdapter(photoModel);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();


        ValueEventListener campListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                campModel.add(snapshot.child("-N3-m940IkBNOC-3kUl2").getValue(CampModel.class));
                setDatainTextView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "loadPost:onCancelled", error.toException());
            }
        };

        //string olarak veridiğim id değerleri tıklanan paylaşımın id sine göre olacak
        ValueEventListener photosListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("TAG",snapshot.child("-N3-m94-IqOMmerC2JCs").getValue().toString());
                for (DataSnapshot child:snapshot.child("-N3-m94-IqOMmerC2JCs").getChildren()) {
                    //Log.e("Log", child.toString());
                    photoModel.add(child.getValue().toString());
                    sliderAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "loadPost:onCancelled", error.toException());
            }
        };

        mCampReference.addValueEventListener(campListener);
        mCampPhotoReference.addValueEventListener(photosListener);

    }

    @Override
    protected void onStart() {
        super.onStart();
        photoModel.clear();
    }

    void setDatainTextView(){
        txtCampName.setText(campModel.get(0).getCampName());
        txtCampExplation.setText(campModel.get(0).getExplanation());
        if (campModel.get(0).getFire()==true){
            fire.setBackgroundResource(R.drawable.ic_baseline_local_fire_department_true);
        }
        if(campModel.get(0).getWc()==true){
            wc.setBackgroundResource(R.drawable.ic_launcher_wc_true);
        }
        if(campModel.get(0).getFacility()==true){
            facility.setBackgroundResource(R.drawable.ic_baseline_facility_true);
        }
        if(campModel.get(0).getNetwork()==true){
            wifi.setBackgroundResource(R.drawable.ic_baseline_wifi_true);
        }
        if(campModel.get(0).getPaid()==true){
            paid.setBackgroundResource(R.drawable.ic_baseline_payments_true);
        }
        if(campModel.get(0).getSea()==true){
            beach.setBackgroundResource(R.drawable.ic_baseline_beach_access_true);
        }
        if(campModel.get(0).getPark()==true){
            park.setBackgroundResource(R.drawable.ic_baseline_local_parking_true);
        }
        if(campModel.get(0).getTransport()==true){
            transport.setBackgroundResource(R.drawable.ic_baseline_emoji_transportation_true);
        }
        if(campModel.get(0).getTrekking()==true){
            walk.setBackgroundResource(R.drawable.ic_baseline_directions_walk_true);
        }
        if(campModel.get(0).getWater()==true){
            drink.setBackgroundResource(R.drawable.ic_baseline_local_drink_true);
        }
        if(campModel.get(0).getWildAnimal()==true){
            pet.setBackgroundResource(R.drawable.ic_baseline_pets_true);
        }
        txtCampLocation.setText(campModel.get(0).getLocation());
        longitude=campModel.get(0).getLongitude();
        latitude=campModel.get(0).getLatitude();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtCampLocation:
                Intent intent=new Intent(this,CampLocationMapFragment.class);
                intent.putExtra("longitude",longitude);
                intent.putExtra("latitude",latitude);
                startActivity(intent);
                break;

            case R.id.favorite_button:
                if(favorite.isSelected()){
                    favorite.setSelected(false);
                }else{
                    favorite.setSelected(true);
                }
                break;

        }
    }
}