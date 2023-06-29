package com.yasin.lastproject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.yasin.lastproject.databinding.ActivityAllCarsBinding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class DetailOfCar extends AppCompatActivity {

    private Car car;
    private ArrayList<Car> cars;
    private ActivityAllCarsBinding binding;
    private String documentId;
    private String downloadUrlImage;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseAuth auth;

    public String dateFirst,dateLast;
    public boolean isBack;
    
    private String eMail;

    ArrayList<Integer> dateOfRent;

    int number;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.optiondetails,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.add_post){

            Intent intentToUpload=new Intent(DetailOfCar.this,UploadActivity.class);
            startActivity(intentToUpload);

        }else if(item.getItemId()==R.id.mainPage){
            Intent intentToMain=new Intent(DetailOfCar.this, MainMenu.class);
            startActivity(intentToMain);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllCarsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.rentButton.setVisibility(View.VISIBLE);

        dateOfRent = new ArrayList<Integer>();





        Intent intent = getIntent();
        String positionText = intent.getStringExtra("carposition");
        number = Integer.parseInt(positionText);


        String textForDate=intent.getStringExtra("date");
        if(textForDate==null);
        else
            Toast.makeText(this, textForDate, Toast.LENGTH_SHORT).show();


        cars = new ArrayList<>();


        getCars(this);

        setCalendar();

        rentButtonListener();



    }

    public void getCars(final Context context) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        try {
            CollectionReference collectionReference = firebaseFirestore.collection("Posts");
            collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Toast.makeText(context, error.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                    if (value != null) {
                        int count = 0;
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            if (count == number) {
                                Map<String, Object> data = snapshot.getData();

                                documentId = snapshot.getId();

                                String serialName = (String) data.get("serialname");
                                String userEmail = (String) data.get("useremail");
                                String downloadUrl = (String) data.get("dowloadurl");
                                String modelName = (String) data.get("modelname");
                                String price = (String) data.get("price");
                                String colorName = (String) data.get("colorname");
                                String countName = (String) data.get("count");
                                String yearName = (String) data.get("year");



                                downloadUrlImage=downloadUrl;

                                eMail=userEmail;

                                Car car = new Car(serialName, modelName, colorName, price, downloadUrl, userEmail, countName, yearName);
                                if (car == null) {
                                    Toast.makeText(DetailOfCar.this, "bos", Toast.LENGTH_SHORT).show();
                                } else {
                                    binding.colorEditText.setText(car.color);
                                    binding.modelTextView.setText(car.model);
                                    binding.serialTextView.setText(car.serial);
                                    binding.yearEditText.setText(car.year);
                                    binding.countEditText.setText(car.count);
                                    binding.priceEditText.setText(car.price);
                                    Picasso.get().load(car.imageUrl).into(binding.imageView);
                                    cars.add(car);
                                }

                                break;
                            }
                            count++;
                            continue;
                        }
                    }
                }

            });
        } catch (Exception ad) {
            ad.printStackTrace();
            System.out.println("hata");
        }

    }

    public void rentButtonListener() {
        binding.rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail="";

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    userEmail = user.getEmail();
                } else {
                }



                if(dateFirst==null || dateLast==null) {
                    isBack=true;
                    Intent intent=getIntent();
                    intent.putExtra("date","Please, enter a date");
                    startActivity(intent);
                    finish();
                    }

                    String price = binding.priceEditText.getText().toString();
                    int priceInteger = Integer.parseInt(price);


                    if(userEmail.equals(eMail)){
                        Intent intent=getIntent();
                        intent.putExtra("date","Bu arabayi kiralayamazsiniz");
                        startActivity(intent);
                        finish();
                    }else{


                    int totalPrice = priceInteger * calculatorDate();
                    String totalPriceText = String.valueOf(totalPrice);
                    String fullMessage = "rental fee is $" + totalPriceText;


                    if (calculatorDate() < 0) {
                        Intent intent=getIntent();
                        intent.putExtra("date","Kiralanan tarih baslangic tarihinden buyuk olamaz");
                        startActivity(intent);
                        finish();
                    }else{

                    AlertDialog.Builder alert = new AlertDialog.Builder(DetailOfCar.this);
                    alert.setTitle("Rental");
                    alert.setMessage(fullMessage);
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                            String countEditText = binding.countEditText.getText().toString();
                            int countNumber = Integer.valueOf(countEditText) - 1;
                            String countNewNumber = String.valueOf(countNumber);
                            if (countEditText.equals("")) {
                                Toast.makeText(DetailOfCar.this, "Bos deger", Toast.LENGTH_SHORT).show();
                            } else if (countEditText.equals("1")) {
                                deleteCar(firebaseFirestore);
                            } else{
                                updateCar(firebaseFirestore,countNewNumber);
                            }
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(DetailOfCar.this, "Not updated", Toast.LENGTH_LONG).show();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });
                    alert.show();
                    }
            }}
            });

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void deleteCar(FirebaseFirestore firebaseFirestore){
        if (!documentId.equals("")) {
            firebaseFirestore.collection("Posts").document(documentId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    firebaseStorage = FirebaseStorage.getInstance();
                    storageReference = firebaseStorage.getReferenceFromUrl(downloadUrlImage);
                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                    calculatorDate();
                    dateOfRent.clear();
                    Intent intent = new Intent(DetailOfCar.this, MainMenu.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DetailOfCar.this, "Error deleting document" + e, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void updateCar(FirebaseFirestore firebaseFirestore,String countNewNumber){
        firebaseFirestore.collection("Posts").document(documentId).update("count", countNewNumber).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                calculatorDate();
                Toast.makeText(DetailOfCar.this, "Update successful", Toast.LENGTH_SHORT).show();
                Intent intent=getIntent();
                intent.putExtra("date","Update succesful");
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailOfCar.this, "Task error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int calculatorDate() {

        String firstDay = String.valueOf(dateOfRent.get(0));
        String lastDay = String.valueOf(dateOfRent.get(3));

        String firstMonth = String.valueOf(dateOfRent.get(1));
        String lastMonth = String.valueOf(dateOfRent.get(4));

        String firstYear = String.valueOf(dateOfRent.get(2));
        String lastYear = String.valueOf(dateOfRent.get(5));


        int totalYear = dateOfRent.get(5) - dateOfRent.get(2);
        System.out.println(totalYear+"date5: "+dateOfRent.get(5)+"date2: "+dateOfRent.get(2));
        int totalMonth =dateOfRent.get(4)-dateOfRent.get(1);
        if (totalYear < 0)
            return -1;
        if(totalYear==0 && totalMonth<0)
            return -1;


        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/u");
        String startDate = firstDay + "/" + firstMonth + "/" + firstYear;
        String endDate = lastDay + "/" + lastMonth + "/" + lastYear;

        LocalDate startDateValue = LocalDate.parse(startDate, dateFormatter);
        LocalDate endDateValue = LocalDate.parse(endDate, dateFormatter);
        long days = ChronoUnit.DAYS.between(startDateValue, endDateValue);

        int numberZero=(int)days;

        if(numberZero==0){
            return 1;
        }


        return (int) days;
    }
    public void setCalendar() {

        final DatePickerDialog[] datePicker = new DatePickerDialog[1];
        final Calendar calendar = Calendar.getInstance();

        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);


        datePicker[0] = new DatePickerDialog(DetailOfCar.this);

        binding.firstDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker[0] = new DatePickerDialog(DetailOfCar.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        binding.firstDateButton.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        dateFirst = binding.firstDateButton.getText().toString();
                        String[] dates = dateFirst.split("/");

                        int firstDay = Integer.parseInt(dates[0]);
                        int firstMonth = Integer.parseInt(dates[1]);
                        int firstYear = Integer.parseInt(dates[2]);

                        dateOfRent.add(firstDay);
                        dateOfRent.add(firstMonth);
                        dateOfRent.add(firstYear);

                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker[0].getDatePicker().setMinDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker[0].show();
            }
        });
        binding.lastDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker[0] = new DatePickerDialog(DetailOfCar.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        binding.lastDayButton.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        dateLast = binding.lastDayButton.getText().toString();
                        String[] dates = dateLast.split("/");

                        int lastDay = Integer.parseInt(dates[0]);
                        int lastMonth = Integer.parseInt(dates[1]);
                        int lastYear = Integer.parseInt(dates[2]);

                        dateOfRent.add(lastDay);
                        dateOfRent.add(lastMonth);
                        dateOfRent.add(lastYear);
                    }
                }, year, month, day);

                datePicker[0].getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePicker[0].show();
            }
        });
    }

}

