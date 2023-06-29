package com.yasin.lastproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
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

public class MainMenu extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Car> carArrayList;
    MainRecylerAdapter mainRecylerAdapter;
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

            Intent intentToUpload=new Intent(MainMenu.this,UploadActivity.class);
            startActivity(intentToUpload);


        }else if(item.getItemId()==R.id.signout){

            auth.signOut();

            Intent intentToMain=new Intent(MainMenu.this, SignIn.class);
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

        getCars(MainMenu.this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainRecylerAdapter =new MainRecylerAdapter(carArrayList, MainMenu.this);
        binding.recyclerView.setAdapter(mainRecylerAdapter);


        filterCars();

        refreshButton();

        clickIcon();


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
                        for (DocumentSnapshot snapshot : value.getDocuments()){
                            Map<String,Object> data = snapshot.getData();

                            String serialName = (String) data.get("serialname");
                            String userEmail = (String) data.get("useremail");
                            String downloadUrl = (String) data.get("dowloadurl");
                            String modelName=(String) data.get("modelname");
                            String price=(String) data.get("price");
                            String colorName=(String) data.get("colorname");
                            String countName=(String)data.get("count");
                            String year=(String)data.get("year");
                            Car car=new Car(serialName,modelName,colorName,price,downloadUrl,userEmail,countName,year);
                            carArrayList.add(car);
                        }
                        mainRecylerAdapter.notifyDataSetChanged();
                    }

                }
            });
        }catch (Exception ad){
            ad.printStackTrace();
            System.out.println("hata");
        }
    }
    public void filterCars(){
        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

    }
    private void filter(String text) {
        ArrayList<Car> filteredlist = new ArrayList<Car>();

        if(text.equals("allCars")){
            for (Car item : carArrayList) {
                    filteredlist.add(item);
            }
            System.out.println("Girdi");
        }else{
            for (Car item : carArrayList) {
                if (item.getModel().toLowerCase().contains(text.toLowerCase())) {
                    filteredlist.add(item);
                }
            }
            if (filteredlist.isEmpty()) {
                Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
            } else {
                mainRecylerAdapter.filterList(filteredlist);
            }
        }
    }
    public void refreshButton(){
        binding.refreshClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    carArrayList.clear();
                    getCars(MainMenu.this);
                    Toast.makeText(MainMenu.this, "Updated", Toast.LENGTH_SHORT).show();
                    mainRecylerAdapter =new MainRecylerAdapter(carArrayList, MainMenu.this);
                    binding.recyclerView.setAdapter(mainRecylerAdapter);
            }
        });
    }
    public void clickIcon(){
        binding.mercedesamblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 filter("Mercedes");
            }
        });
        binding.bmwAmblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter("Bmw");
            }
        });
        binding.cadillacAmblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter("Cadillac");
            }
        });
        binding.ferrariAmblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter("Ferrari");
            }
        });
    }

}