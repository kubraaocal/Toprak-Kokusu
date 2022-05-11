package com.example.toprakkokusu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CampCreateActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private DatabaseReference cDbRef;

    private ImageButton wc, paid, transport, facility;
    private Button btnSave;
    private EditText editTextExplanation, editTextCampName;
    private TextView textMapButton,textAddress;
    private AddressModel addressModel=AddressModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_create);

        mAuth = FirebaseAuth.getInstance();
        cDbRef = FirebaseDatabase.getInstance().getReference().child("Camp");

        editTextExplanation = findViewById(R.id.editTextCampExplanation);
        editTextCampName = findViewById(R.id.editTextCampName);
        textMapButton=findViewById(R.id.mapFragmentOpen);
        textAddress=findViewById(R.id.textAddress);
        textMapButton.setOnClickListener(this);

        wc = findViewById(R.id.wc);
        wc.setOnClickListener(this);
        paid = findViewById(R.id.paid);
        paid.setOnClickListener(this);
        transport = findViewById(R.id.transport);
        transport.setOnClickListener(this);
        facility = findViewById(R.id.facility);
        facility.setOnClickListener(this);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);



    }

    @Override
    protected void onStart() {
        super.onStart();
        if(addressModel.getData()!=null){
            Log.e("LOG","adres: "+addressModel.getData());
            textAddress.setText(addressModel.getData());
        }
        /*Intent intent=getIntent();
        String a=intent.getStringExtra("addresses");
        Log.e("LOG","a: "+a);
        Bundle addresses = getIntent().getExtras();
        if (addresses != null)
        {
            String address = addresses.getString("addresses");
            Log.e("LOG","adres: "+address);
            System.out.println("adres: "+address);

        }*/
        else if (addressModel.getData() == null)
        {
            Toast.makeText(this, "Bundle is null", Toast.LENGTH_SHORT).show();
            System.out.println("adres: "+addressModel.getData());
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
            case R.id.btnSave:
                createCamp();
                this.finish();
                break;
            case R.id.mapFragmentOpen:
                startActivity(new Intent(this, MapFragment.class));

        }
    }




    private void createCamp() {
        String explanation=editTextExplanation.getText().toString().trim();
        String name=editTextCampName.getText().toString().trim();
        boolean isWc=wc.isSelected();
        boolean isPaid=paid.isSelected();
        boolean isTransport=transport.isSelected();
        boolean isFacility=facility.isSelected();

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

        CampModel campModel=new CampModel(name,explanation,isWc,isPaid,isTransport,isFacility);
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
    }
}