package com.example.toprakkokusu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

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

public class CampDetailActivity extends AppCompatActivity {

    SliderView sliderView;
    int[] images={R.drawable.logo,R.drawable.logo};

    private FirebaseAuth mAuth;
    private DatabaseReference mCampReference,mCampPhotoReference;
    List listCamp=new ArrayList<>();
    ArrayList<String> photoModel=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_detail);

        mAuth = FirebaseAuth.getInstance();
        mCampReference = FirebaseDatabase.getInstance().getReference().child("Camp");
        mCampPhotoReference = mCampReference.child("photos");



        sliderView=findViewById(R.id.image_slider);

        SliderAdapter sliderAdapter=new SliderAdapter(photoModel);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();


        ValueEventListener campListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<CampModel> campModel= new ArrayList<>();
                campModel.add(snapshot.child("-N26LfvgfD_m2yZ1D1G7").getValue(CampModel.class));
                //Log.e("Log",campModel.get(0).getCampName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "loadPost:onCancelled", error.toException());
            }
        };

        ValueEventListener photosListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("TAG",snapshot.child("-N26LfvgfD_m2yZ1D1G7").getValue().toString());
                for (DataSnapshot child:snapshot.child("-N26LfvgfD_m2yZ1D1G7").getChildren()) {
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
}