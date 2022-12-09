package com.yasin.lastproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yasin.lastproject.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

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
            Intent intent=new Intent(MainActivity.this,FeedActivity.class);
            startActivity(intent);
            finish();
        }



    }
    public void singin(View view){

        String email=binding.mail.getText().toString();
        String password=binding.password.getText().toString();

        if(email.equals("") || password.equals("")){
            Toast.makeText(this, "Geçersiz mail veya şifre", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(authResult -> {
                Intent intent=new Intent(MainActivity.this,FeedActivity.class);
                startActivity(intent);
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            });
        }



    }
    public void singup(View view){

        String email=binding.mail.getText().toString();
        String password=binding.password.getText().toString();

        if(email.equals("") || password.equals("")){
            Toast.makeText(this, "Geçersiz mail veya şifre", Toast.LENGTH_SHORT).show();
        }else
            mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(authResult -> {
                Intent intent=new Intent(MainActivity.this,FeedActivity.class);
                startActivity(intent);
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            });


    }

}