package com.example.toprakkokusu.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.toprakkokusu.R;
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
import com.squareup.picasso.Picasso;

public class CreateActivityActivity extends AppCompatActivity {
    private DatabaseReference activityDbRef;
    private FirebaseAuth mAuth;
    private StorageReference imageRef;


    private ImageView photo;
    private EditText activityTitle,activityText;
    private Button activitySaveButton;
    private String url;
    private RelativeLayout relativeLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_activity);

        photo=findViewById(R.id.image_add_view);
        activityTitle=findViewById(R.id.title_edit_view);
        activityText=findViewById(R.id.text_edit_view);
        activitySaveButton=findViewById(R.id.button_activity_save);
        relativeLayout=findViewById(R.id.relative_layout);
        progressBar=findViewById(R.id.progress_bar);

        activityDbRef = FirebaseDatabase.getInstance().getReference().child("Activity");
        imageRef = FirebaseStorage.getInstance().getReference().child("activityImages/");

        mAuth = FirebaseAuth.getInstance();

        activitySaveButton=findViewById(R.id.button_activity_save);
        activitySaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createActivity();
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                }
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);
            }
        });
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
                    saveStorageImage(uri);
                }
            }
        }
    }

    private void saveStorageImage(Uri uri) {
        imageRef.child(uri.getLastPathSegment()).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uriUrl) {
                        url = String.valueOf(uriUrl);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Error","Fail");
                    }
                });
                //burada çekme durumuna bağlı olarak sadece resmin adı yazdırılabilir.
                progressBar.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.GONE);
                Picasso.get().load(uri).noFade().into(photo);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Error","Fail");
            }
        });
    }

    private void createActivity(){
        String text=activityText.getText().toString().trim();
        String title=activityTitle.getText().toString().trim();
        if(text.isEmpty()){
            activityText.setError("Boş bırakmayınız");
            activityText.requestFocus();
            return;
        }
        if(title.isEmpty()){
            activityTitle.setError("Boş bırakmayınız");
            activityTitle.requestFocus();
            return;
        }

        ActivityModel activityModel=new ActivityModel(url,title,text);
        String uploadId=activityDbRef.push().getKey();
        activityDbRef.child(uploadId).setValue(activityModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isComplete()){
                    Toast.makeText(getApplicationContext(),"Etkinlik Paylaşıldı",Toast.LENGTH_LONG).show();
                    activityText.getText().clear();
                    activityTitle.getText().clear();
                    finish();
                }
                else{
                    Log.e("Error","Error");
                }
            }
        });
    }
}