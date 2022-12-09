package com.yasin.lastproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yasin.lastproject.databinding.ActivityFeedBinding;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Car> carArrayList;
    FeedRecyclerAdapter feedRecyclerAdapter;
    private ActivityFeedBinding binding;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.option,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.add_post){

            Intent intentToUpload=new Intent(FeedActivity.this,UploadActivity.class);
            startActivity(intentToUpload);


        }else if(item.getItemId()==R.id.signout){

            auth.signOut();

            Intent intentToMain=new Intent(FeedActivity.this, MainActivity.class);
            startActivity(intentToMain);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        carArrayList=new ArrayList<Car>();

        auth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

   //     getDataFromFirestore();
        getCars(FeedActivity.this);


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedRecyclerAdapter=new FeedRecyclerAdapter(carArrayList);
        binding.recyclerView.setAdapter(feedRecyclerAdapter);




    }
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
                            Map<String,Object> data = snapshot.getData();

                            String carName = (String) data.get("carname");
                            String userEmail = (String) data.get("useremail");
                            String downloadUrl = (String) data.get("dowloadurl");
                            String brandName=(String) data.get("brandname");
                            String price=(String) data.get("price");
                            String colorName=(String) data.get("colorname");
                            System.out.println(data.get("carname"));
                            Car car=new Car(carName,brandName,colorName,price,downloadUrl,userEmail);
                            carArrayList.add(car);
                        }
                        feedRecyclerAdapter.notifyDataSetChanged();
                    }

                }
            });
        }catch (Exception ad){
            ad.printStackTrace();
            System.out.println("hata");
        }

    }
}