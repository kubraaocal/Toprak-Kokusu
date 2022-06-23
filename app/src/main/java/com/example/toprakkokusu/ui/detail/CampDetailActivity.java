package com.example.toprakkokusu.ui.detail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toprakkokusu.CampLocationMapFragment;
import com.example.toprakkokusu.CampModel;
import com.example.toprakkokusu.R;
import com.example.toprakkokusu.SliderAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    private ImageButton wc, paid, transport, facility,park,drink,pet,fire,wifi,beach,walk,favorite,commentSendButton;
    private EditText commentEditText;
    private RecyclerView recyclerView;

    private CommentAdapter commentAdapter;
    private SliderAdapter sliderAdapter;

    private List<CommentModel> commentModelList;

    private String campId,photoId;
    private FirebaseAuth mAuth;
    private DatabaseReference mCampReference,mCampPhotoReference,commentCampReference, mUserFavReference;
    List listCamp=new ArrayList<>();
    ArrayList<String> photoModel=new ArrayList<>();

    ArrayList<CampModel> campModel = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_detail);



        Log.e("ID",campId+" "+photoId);

        txtCampName=findViewById(R.id.txtCampName);
        txtCampExplation=findViewById(R.id.txtCampExplation);
        txtCampLocation=findViewById(R.id.txtCampLocation);

        txtCampLocation.setOnClickListener(this);

        commentEditText=findViewById(R.id.comment_edit_text);

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
        commentSendButton=findViewById(R.id.comment_send_button);
        commentSendButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mCampReference = FirebaseDatabase.getInstance().getReference().child("Camp");
        mUserFavReference = FirebaseDatabase.getInstance().getReference().child("Users");
        mCampPhotoReference = FirebaseDatabase.getInstance().getReference().child("Photo");

        commentModelList=new ArrayList<>();
        commentAdapter= new  CommentAdapter(commentModelList,getApplicationContext());

        recyclerView=findViewById(R.id.recycler_view_comment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.addItemDecoration(new
                DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));


        sliderView=findViewById(R.id.image_slider);

        sliderAdapter=new SliderAdapter(photoModel);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();


        ValueEventListener campListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                campModel.add(snapshot.child(campId).getValue(CampModel.class));
                setDatainTextView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "loadPost:onCancelled", error.toException());
            }
        };

        ValueEventListener commentListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.child(campId).child("Comments").getChildren()) {
                    CommentModel commentModel = ds.getValue(CommentModel.class);
                    commentModelList.add(commentModel);
                    commentAdapter.notifyDataSetChanged();
                    Log.e("ID", "comments sayısı "+ commentModelList.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "loadPost:onCancelled", error.toException());
            }
        };

        ValueEventListener favoriteListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.child(mAuth.getUid()).child("Favorites").getChildren()) {
                    Log.e("TAG",ds.getValue().toString()+" "+campId);
                       if(ds.getValue().equals(campId)){
                           favorite.setSelected(true);
                       }else
                       {
                           Log.e("TAG","girmedi");
                       }
                    }
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
                Log.e("TAG",snapshot.child(photoId).getValue().toString());
                for (DataSnapshot child:snapshot.child(photoId).getChildren()) {
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
        mCampReference.addValueEventListener(commentListener);
        mUserFavReference.addValueEventListener(favoriteListener);
        mCampPhotoReference.addValueEventListener(photosListener);
        //mCampReference.child(campId).child("Comments").addValueEventListener(commentListener);

    }

    @Override
    protected void onStart() {
        super.onStart();
        photoModel.clear();
        Intent intent=getIntent();
        campId = intent.getStringExtra("campid");
        photoId=intent.getStringExtra("photoid");
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
                Intent intent=new Intent(this, CampLocationMapFragment.class);
                intent.putExtra("longitude",longitude);
                intent.putExtra("latitude",latitude);
                startActivity(intent);
                break;

            case R.id.favorite_button:
                if(favorite.isSelected()){
                    favorite.setSelected(false);
                    mUserFavReference.child(mAuth.getUid()).child("Favorites").child(campId).removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            ref.removeValue();
                            Toast.makeText(getApplicationContext(),"Favorilerden çıkarıldı",Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    favorite.setSelected(true);
                    mUserFavReference.child(mAuth.getUid()).child("Favorites").child(campId).setValue(campId).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isComplete()){
                                Toast.makeText(getApplicationContext(),"Favorilere Eklendi",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                break;
            case R.id.comment_send_button:
                String uploadcommentId=mCampReference.push().getKey();
                CommentModel commentModel=new CommentModel(mAuth.getUid(),commentEditText.getText().toString(),"10:45");
                mCampReference.child(campId).child("Comments").child(uploadcommentId).setValue(commentModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Yorum oluşturuldu.",Toast.LENGTH_LONG).show();
                        commentEditText.getText().clear();

                    }
                });
                commentAdapter.clearList();
        }
    }
}