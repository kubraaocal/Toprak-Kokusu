package com.example.toprakkokusu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    EditText userEmail;
    Button sendEmail;
    ProgressBar progressBar;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0000ff")));

        progressBar=findViewById(R.id.progressBar);
        userEmail=findViewById(R.id.editTextUserEmail);
        sendEmail=findViewById(R.id.buttonSendMail);

        firebaseAuth=FirebaseAuth.getInstance();

        sendEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSendMail:
                forgotPassSendEmail();
        }
    }

    private void forgotPassSendEmail() {

        String email=userEmail.getText().toString().trim();

        if(email.isEmpty()){
            userEmail.setError("Boş bırakmayınız");
            userEmail.requestFocus();
            return;
        }


        firebaseAuth.sendPasswordResetEmail(userEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this,"Email Gönderildi",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ForgotPasswordActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}