package com.yasin.lastproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yasin.lastproject.databinding.ActivityMainBinding;


public class SignIn extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user= mAuth.getCurrentUser();
        if(user!=null){
            Intent intent=new Intent(SignIn.this, MainMenu.class);
            startActivity(intent);
            finish();
        }

        click();

    }
    public void signin(View view){

        String email=binding.email.getText().toString();
        String password=binding.password.getText().toString();

        if(email.equals("") || password.equals("")){
            Toast.makeText(this, "Geçersiz mail veya şifre", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(authResult -> {
                Intent intent=new Intent(SignIn.this, MainMenu.class);
                startActivity(intent);
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            });
        }

    }
    public void signup(View view) {
        Intent intent=new Intent(SignIn.this,SingUp.class);
        startActivity(intent);
        finish();
    }

    public void click(){
        binding.emailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignIn.this,GmailActivity.class);
                intent.putExtra("ChooseKey", "email");
                startActivity(intent);
                finish();
            }
        });

        binding.twitterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignIn.this,GmailActivity.class);
                intent.putExtra("ChooseKey", "twitter");
                startActivity(intent);
                finish();
            }
        });

        binding.facebookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignIn.this,GmailActivity.class);
                intent.putExtra("ChooseKey", "facebook");
                startActivity(intent);
                finish();
            }
        });
    }

}