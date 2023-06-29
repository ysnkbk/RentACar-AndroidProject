package com.yasin.lastproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.yasin.lastproject.databinding.ActivitySingUpBinding;

public class SingUp extends AppCompatActivity {

    private ActivitySingUpBinding binding;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySingUpBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);


        mAuth = FirebaseAuth.getInstance();

    }

    public void signup(View view){

        String email=binding.email.getText().toString();
        String password=binding.password.getText().toString();
        String rePassword=binding.againpassword.getText().toString();


        if(email.equals("") || password.equals("")){
            Toast.makeText(this, "Geçersiz mail veya şifre", Toast.LENGTH_SHORT).show();
        }else if(!password.equals(rePassword)){
            Toast.makeText(this, "Sifreler eslesmedi sifreleri ayni girin", Toast.LENGTH_SHORT).show();
            binding.password.setText("");
            binding.againpassword.setText("");
        }
        else

            mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(authResult -> {
                Intent intent=new Intent(this, MainMenu.class);
                startActivity(intent);
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            });
        }


}
