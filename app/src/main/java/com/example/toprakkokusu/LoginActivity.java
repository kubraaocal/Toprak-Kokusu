package com.example.toprakkokusu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;

    private EditText editTextEmail,editTextPass;
    private Button buttonLogin;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPass=findViewById(R.id.editTextPassword);

        relativeLayout=findViewById(R.id.relative_layout);

        progressBar=findViewById(R.id.progressBar);

        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(this);

        findViewById(R.id.textSingIn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textSingIn:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            case R.id.buttonLogin:
                loginUser();
                break;

        }
    }

    private void loginUser() {
        String email=editTextEmail.getText().toString().trim();
        String pass=editTextPass.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Boş bırakmayınız");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Email giriniz");
            editTextEmail.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            editTextPass.setError("Boş bırakmayınız");
            editTextPass.requestFocus();
            return;
        }
        if(pass.length()<6){
            editTextPass.setError("Şifreniz 6 ya da daha fazla karakterden oluşmalıdır");
            editTextPass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"Giriş yapılamadı",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                }
            }
        });
    }
}
