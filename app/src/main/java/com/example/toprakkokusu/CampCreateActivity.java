package com.example.toprakkokusu;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CampCreateActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private DatabaseReference cDbRef,imageDbRef;
    private MediaRecyclerAdapter recyclerAdapter;
    ArrayList<Uri> uris=new ArrayList<>();

    StorageReference imageRef;

    private static final int Read_Permission=101;

    private ImageButton wc, paid, transport, facility,park,drink,pet,fire,wifi,beach,walk,shop;
    private Button btnSave;
    private EditText editTextExplanation, editTextCampName;
    private TextView textMapButton,textAddress,textSelectPhotoButton;
    private RecyclerView recyclerViewMedia;
    private AddressModel addressModel=AddressModel.getInstance();


    public CampCreateActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_create);

        mAuth = FirebaseAuth.getInstance();
        cDbRef = FirebaseDatabase.getInstance().getReference().child("Camp");
        imageDbRef=FirebaseDatabase.getInstance().getReference().child("Photo");

        imageRef = FirebaseStorage.getInstance().getReference().child("images/");

        editTextExplanation = findViewById(R.id.editTextCampExplanation);
        editTextCampName = findViewById(R.id.editTextCampName);
        textMapButton=findViewById(R.id.mapFragmentOpen);
        textAddress=findViewById(R.id.textAddress);
        textMapButton.setOnClickListener(this);
        textSelectPhotoButton=findViewById(R.id.textSelectPhotoButton);
        recyclerViewMedia=findViewById(R.id.recyclerView);

        wc = findViewById(R.id.wc);
        wc.setOnClickListener(this);
        paid = findViewById(R.id.paid);
        paid.setOnClickListener(this);
        transport = findViewById(R.id.transport);
        transport.setOnClickListener(this);
        facility = findViewById(R.id.facility);
        facility.setOnClickListener(this);
        park=findViewById(R.id.parking);
        park.setOnClickListener(this);
        pet=findViewById(R.id.pets);
        pet.setOnClickListener(this);
        drink=findViewById(R.id.drink);
        drink.setOnClickListener(this);
        fire=findViewById(R.id.fire);
        fire.setOnClickListener(this);
        beach=findViewById(R.id.beach);
        beach.setOnClickListener(this);
        walk=findViewById(R.id.walk);
        walk.setOnClickListener(this);
        wifi=findViewById(R.id.wifi);
        wifi.setOnClickListener(this);
        shop=findViewById(R.id.shop);
        shop.setOnClickListener(this);


        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        textSelectPhotoButton.setOnClickListener(this);

        recyclerAdapter=new MediaRecyclerAdapter(uris);
        recyclerViewMedia.setLayoutManager(new GridLayoutManager(CampCreateActivity.this,4));
        recyclerViewMedia.setAdapter(recyclerAdapter);

        if(ContextCompat.checkSelfPermission(CampCreateActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CampCreateActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},Read_Permission);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(addressModel.getData()!=null){
            Log.e("LOG","adres: "+addressModel.getData());
            textAddress.setText(addressModel.getData());
        } else if (addressModel.getData() == null)
        {
            Log.e("LOG","address null");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.wc:
                if(wc.isSelected()){
                    wc.setSelected(false);
                }else{
                    wc.setSelected(true);
                }
                break;
            case R.id.paid:
                if(paid.isSelected()){
                    paid.setSelected(false);
                }else{
                    paid.setSelected(true);
                }
                break;
            case R.id.transport:
                if(transport.isSelected()){
                    transport.setSelected(false);
                }else{
                    transport.setSelected(true);
                }
                break;
            case  R.id.facility:
                if(facility.isSelected()){
                    facility.setSelected(false);
                }else{
                    facility.setSelected(true);
                }
                break;
            case  R.id.fire:
                if(fire.isSelected()){
                    fire.setSelected(false);
                }else{
                    fire.setSelected(true);
                }
                break;
            case  R.id.walk:
                if(walk.isSelected()){
                    walk.setSelected(false);
                }else{
                    walk.setSelected(true);
                }
                break;
            case  R.id.pets:
                if(pet.isSelected()){
                    pet.setSelected(false);
                }else{
                    pet.setSelected(true);
                }
                break;
            case  R.id.beach:
                if(beach.isSelected()){
                    beach.setSelected(false);
                }else{
                    beach.setSelected(true);
                }
                break;
            case  R.id.drink:
                if(drink.isSelected()){
                    drink.setSelected(false);
                }else{
                    drink.setSelected(true);
                }
                break;
            case  R.id.wifi:
                if(wifi.isSelected()){
                    wifi.setSelected(false);
                }else{
                    wifi.setSelected(true);
                }
                break;
            case  R.id.parking:
                if(park.isSelected()){
                    park.setSelected(false);
                }else{
                    park.setSelected(true);
                }
                break;
            case  R.id.shop:
                if(shop.isSelected()){
                    shop.setSelected(false);
                }else{
                    shop.setSelected(true);
                }
                break;
            case R.id.btnSave:
                createCamp();
                break;
            case R.id.mapFragmentOpen:
                startActivity(new Intent(this, MapFragment.class));
                break;
            case R.id.textSelectPhotoButton:
                Intent intent=new Intent();
                intent.setType("image/*");
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                }
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1&& resultCode== Activity.RESULT_OK){
            if(data.getData()!=null){
                ClipData cd = data.getClipData();
                if ( cd == null ) {
                    Uri uri = data.getData();
                    uris.add(uri);
                }
                else {
                    for (int i = 0; i < cd.getItemCount(); i++) {
                        ClipData.Item item = cd.getItemAt(i);
                        Uri uri = item.getUri();
                        uris.add(uri);
                    }
                }
                recyclerAdapter.notifyDataSetChanged();
            }else if(data.getData()!=null){
                String imageURL=data.getData().getPath();
                uris.add(Uri.parse(imageURL));
            }
        }
    }

    int j=0;
    Map<String,Object> map=new HashMap<>();

    private void createCamp() {

        String explanation=editTextExplanation.getText().toString().trim();
        String name=editTextCampName.getText().toString().trim();
        boolean isWc=wc.isSelected();
        boolean isPaid=paid.isSelected();
        boolean isTransport=transport.isSelected();
        boolean isFacility=facility.isSelected();
        boolean isFire=fire.isSelected();
        boolean isPark=park.isSelected();
        boolean isPet=pet.isSelected();
        boolean isBeach=beach.isSelected();
        boolean isWalk=walk.isSelected();
        boolean isDrink=drink.isSelected();
        boolean isWifi=wifi.isSelected();
        String location=textAddress.getText().toString().trim();
        String latitude=addressModel.getLatitude();
        String longitude=addressModel.getLongitude();


        if(explanation.isEmpty()){
            editTextExplanation.setError("Boş bırakmayınız");
            editTextExplanation.requestFocus();
            return;
        }
        if(name.isEmpty()){
            editTextCampName.setError("Boş bırakmayınız");
            editTextCampName.requestFocus();
            return;
        }

        String uploadImageId=imageDbRef.push().getKey();

        for(int i=0;i<uris.size();i++){


            imageRef.child(String.valueOf(Math.random())).putFile(uris.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.e("TAG",uri.toString());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("TAG","elendik");
                        }
                    });
                }
            });

            /*imageRef.child(uris.get(i).getLastPathSegment()).putFile(uris.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = String.valueOf(uri);
                            map.put("photo"+j,url.toString());
                            Log.e("TAG","url "+url);
                            Toast.makeText(CampCreateActivity.this,"Resim storageye kayıt edildi",Toast.LENGTH_SHORT).show();
                            j++;
                        }
                    });
                }
            });*/
        }

        /*CampModel campModel=new CampModel(name,explanation,location,latitude,longitude,isWc,isPaid,isTransport,isFacility,
                isPark,isDrink,isPet,isBeach,isFire,isWifi,isWalk,uploadImageId);
        String uploadId=cDbRef.push().getKey();
        cDbRef.child(uploadId).setValue(campModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isComplete()){
                    Toast.makeText(CampCreateActivity.this,"Kamp yeri kayıt edildi",Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(CampCreateActivity.this,"Kamp yeri kayıt edilemedi",Toast.LENGTH_LONG).show();
                }
            }
        });

        imageDbRef.child(uploadImageId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(CampCreateActivity.this,"Burası kayıt edildi",Toast.LENGTH_SHORT).show();

            }
        });*/


    }
}