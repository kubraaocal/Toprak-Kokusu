package com.example.toprakkokusu.ui.create;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toprakkokusu.AddressModel;
import com.example.toprakkokusu.CampModel;
import com.example.toprakkokusu.LoginActivity;
import com.example.toprakkokusu.MapFragment;
import com.example.toprakkokusu.MediaRecyclerAdapter;
import com.example.toprakkokusu.R;
import com.example.toprakkokusu.databinding.FragmentCreateCampBinding;
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

public class CreateFragment extends Fragment implements View.OnClickListener {


    private DatabaseReference cDbRef,imageDbRef;
    private MediaRecyclerAdapter recyclerAdapter;
    ArrayList<Uri> uris=new ArrayList<>();

    private FirebaseAuth mAuth;


    StorageReference imageRef;

    int j=0;
    Map<String,String> map=new HashMap<>();



    private static final int Read_Permission=101;

    private ImageButton wc, paid, transport, facility,park,drink,pet,fire,wifi,beach,walk;
    private Button btnSave;
    private EditText editTextExplanation, editTextCampName;
    private TextView textMapButton,textAddress,textSelectPhotoButton;
    private RecyclerView recyclerViewMedia;
    private AddressModel addressModel=AddressModel.getInstance();

    private FragmentCreateCampBinding binding;

    RelativeLayout relativeLayout;
    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CreateViewModel createViewModel =
                new ViewModelProvider(this).get(CreateViewModel.class);

        binding = FragmentCreateCampBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textDashboard;
        //createViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



        cDbRef = FirebaseDatabase.getInstance().getReference().child("Camp");
        imageDbRef=FirebaseDatabase.getInstance().getReference().child("Photo");

        imageRef = FirebaseStorage.getInstance().getReference().child("images/");

        mAuth = FirebaseAuth.getInstance();

        relativeLayout=binding.relativeLayout;
        progressBar=binding.progressBar;

        editTextExplanation = binding.editTextCampExplanation;
        editTextCampName = binding.editTextCampName;
        textMapButton=binding.mapFragmentOpen;
        textAddress=binding.textAddress;
        textMapButton.setOnClickListener(this);
        textSelectPhotoButton=binding.textSelectPhotoButton;
        recyclerViewMedia=binding.recyclerView;

        wc = binding.wc;
        wc.setOnClickListener(this);
        paid = binding.paid;
        paid.setOnClickListener(this);
        transport = binding.transport;
        transport.setOnClickListener(this);
        facility = binding.facility;
        facility.setOnClickListener(this);
        park=binding.parking;
        park.setOnClickListener(this);
        pet=binding.pets;
        pet.setOnClickListener(this);
        drink=binding.drink;
        drink.setOnClickListener(this);
        fire=binding.fire;
        fire.setOnClickListener(this);
        beach=binding.beach;
        beach.setOnClickListener(this);
        walk=binding.walk;
        walk.setOnClickListener(this);
        wifi=binding.wifi;
        wifi.setOnClickListener(this);


        btnSave = binding.btnSave;
        btnSave.setOnClickListener(this);

        textSelectPhotoButton.setOnClickListener(this);

        recyclerAdapter=new MediaRecyclerAdapter(uris);
        recyclerViewMedia.setLayoutManager(new GridLayoutManager(getContext(),4));
        recyclerViewMedia.setAdapter(recyclerAdapter);

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},Read_Permission);
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
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
            case R.id.btnSave:
                createCamp();
                break;
            case R.id.mapFragmentOpen:
                startActivity(new Intent(getContext(), MapFragment.class));
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1&& resultCode== Activity.RESULT_OK){
            if(data.getData()!=null){
                progressBar.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);
                ClipData cd = data.getClipData();
                if ( cd == null ) {
                    Uri uri = data.getData();
                    uris.add(uri);
                    saveStorageImage(uri);
                }
                else {
                    for (int i = 0; i < cd.getItemCount(); i++) {
                        ClipData.Item item = cd.getItemAt(i);
                        Uri uri = item.getUri();
                        uris.add(uri);
                        saveStorageImage(uri);
                    }
                }
                recyclerAdapter.notifyDataSetChanged();
            }else if(data.getData()!=null){
                progressBar.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);
                String imageURL=data.getData().getPath();
                uris.add(Uri.parse(imageURL));
                saveStorageImage(Uri.parse(imageURL));
            }
        }
    }

    private void saveStorageImage(Uri uri) {
        Log.e("ABCC","girdi");
        imageRef.child(uri.getLastPathSegment()).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uriUrl) {
                        String url = String.valueOf(uriUrl);
                        map.put("photo"+uri.getLastPathSegment(),url);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("ABCC","fail");
                    }
                });
                //burada çekme durumuna bağlı olarak sadece resmin adı yazdırılabilir.
                recyclerViewMedia.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("ABCC","elendik");
            }
        });
    }


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

        CampModel campModel=new CampModel(name,explanation,location,latitude,longitude,isWc,isPaid,isTransport,isFacility,
                isPark,isDrink,isPet,isBeach,isFire,isWifi,isWalk,uploadImageId,mAuth.getUid());
        String uploadId=cDbRef.push().getKey();
        cDbRef.child(uploadId).setValue(campModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isComplete()){
                    Toast.makeText(getContext(),"Kamp yeri kayıt edildi",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(),"Kamp yeri kayıt edilemedi",Toast.LENGTH_LONG).show();
                }
            }
        });

        imageDbRef.child(uploadImageId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(),"Burası kayıt edildi",Toast.LENGTH_SHORT).show();
            }
        });
    }
}