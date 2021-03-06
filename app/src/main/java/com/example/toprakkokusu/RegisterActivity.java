package com.example.toprakkokusu;

import android.content.Intent;
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
                startActivity(new Intent(this,LoginActivity.class));
                //onBackPressed();
                finish();
                break;
        }
    }

    private void registerUser() {
        String nameSurname=editTextNameSurname.getText().toString().trim();
        String email=editTextEmail.getText().toString().trim();
        String pass=editTextPass.getText().toString().trim();
            String photo = "https://firebasestorage.googleapis.com/v0/b/toprakkokusu-c3451.appspot.com/o/profile%2Fuser.png?alt=media&token=e0309447-660d-40b3-8346-ab8028664c1e";
        //trim metodu, metnin sonunda ve ba????nda yer alan bo??luklar?? yok ederken kelime aralar??ndaki bo??luklara dokunmaz

        if(nameSurname.isEmpty()){
            editTextNameSurname.setError("Bo?? b??rakmay??n??z");
            editTextNameSurname.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Bo?? b??rakmay??n??z");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Email giriniz");
            editTextEmail.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            editTextPass.setError("Bo?? b??rakmay??n??z");
            editTextPass.requestFocus();
            return;
        }
        if(pass.length()<6){
            editTextPass.setError("??ifreniz 6 ya da daha fazla karakterden olu??mal??d??r");
            editTextPass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isComplete()){
                    UserModel user= new UserModel(nameSurname,email,photo);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isComplete()){
                                Toast.makeText(RegisterActivity.this,"Kullan??c?? kay??t edildi",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                relativeLayout.setVisibility(View.GONE);
                                editTextNameSurname.setText("");
                                editTextEmail.setText("");
                                editTextPass.setText("");
                                startActivity(new Intent(RegisterActivity.this,BottomNavigationActivity.class));

                            }else{
                                Toast.makeText(RegisterActivity.this,"Kullan??c?? kay??t edilemedi",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                relativeLayout.setVisibility(View.GONE);
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this,"Kullan??c?? kay??t edilemedi",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                }
            }
        });


    }
}
