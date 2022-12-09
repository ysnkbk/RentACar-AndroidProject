package com.yasin.lastproject;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.yasin.lastproject.databinding.ActivityMessagingBinding;

public class Messaging extends AppCompatActivity {

    private ActivityMessagingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessagingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful()){
                    Log.w(TAG,"Token belirlenemedi",task.getException());
                    return;
                }

                String token=task.getResult();

         //       String msg=getString(R.string.msg_token_fmt,token);
                Toast.makeText(Messaging.this, token+"msg", Toast.LENGTH_SHORT).show();

            }
        });

    }
    private final ActivityResultLauncher<String> requestPermissionLauncher=
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),isGranted->{
                if(isGranted){
                    //bildirim
                }else{
                    Toast.makeText(this, "Bildirim gözükmeyecek.", Toast.LENGTH_SHORT).show();
                }
            });





}