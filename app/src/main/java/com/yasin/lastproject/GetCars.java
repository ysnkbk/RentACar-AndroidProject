package com.yasin.lastproject;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class GetCars {
    public void  getCars(final Context context){

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        try {
            CollectionReference collectionReference = firebaseFirestore.collection("Posts");

            collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Toast.makeText(context,error.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                    if (value!=null){
                        System.out.println(value.getDocuments().size());
                        for (DocumentSnapshot snapshot : value.getDocuments()){

                        }

    
                    }


                }
            });
        }catch (Exception ad){
            ad.printStackTrace();
            System.out.println("hata");
        }

    }

}