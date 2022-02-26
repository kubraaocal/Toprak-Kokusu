package com.example.toprakkokusu;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class CampCreateActivity extends AppCompatActivity {

    ImageButton wc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_create);
        wc=findViewById(R.id.wc);
        wc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wc.isSelected()){
                    wc.setSelected(false);
                }else{
                    wc.setSelected(true);
                }
            }
        });

    }
}