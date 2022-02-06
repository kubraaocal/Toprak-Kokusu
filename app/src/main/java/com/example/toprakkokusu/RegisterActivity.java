package com.example.toprakkokusu;

import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;

    private RelativeLayout relativeLayout;
    private EditText editTextNameSurname,editTextEmail,editTextPass;
    private Button buttonRegister;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();

        relativeLayout=findViewById(R.id.relative_layout);

        buttonRegister = findViewById(R.id.buttonRegister);


        editTextNameSurname=findViewById(R.id.editTextNameSurname);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPass=findViewById(R.id.editTextPassword);

        progressBar=findViewById(R.id.progressBar);

        buttonRegister.setOnClickListener(this);

        findViewById(R.id.textLogin).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonRegister:
                registerUser();
                break;
            case R.id.textLogin:
                ///startActivity(new Intent(this,LoginActivity.class));

                //Burası doğru mu bilmiyorum amacım var olan activity arkada kapatmak önceki activity geri dönmek

                onBackPressed();
                this.finish();
                break;
        }
    }

    private void registerUser() {
        String nameSurname=editTextNameSurname.getText().toString().trim();
        String email=editTextEmail.getText().toString().trim();
        String pass=editTextPass.getText().toString().trim();
        //trim metodu, metnin sonunda ve başında yer alan boşlukları yok ederken kelime aralarındaki boşluklara dokunmaz

        if(nameSurname.isEmpty()){
            editTextNameSurname.setError("Boş bırakmayınız");
            editTextNameSurname.requestFocus();
            return;
        }
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
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isComplete()){
                    UserModel user= new UserModel(nameSurname,email);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isComplete()){
                                Toast.makeText(RegisterActivity.this,"Kullanıcı kayıt edildi",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                relativeLayout.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(RegisterActivity.this,"Kullanıcı kayıt edilemedi",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                relativeLayout.setVisibility(View.GONE);
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this,"Kullanıcı kayıt edilemedi",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                }
            }
        });


    }
}
