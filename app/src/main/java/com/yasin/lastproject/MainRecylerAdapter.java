package com.yasin.lastproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yasin.lastproject.databinding.RecylerRowBinding;

import java.util.ArrayList;

public class MainRecylerAdapter extends RecyclerView.Adapter<MainRecylerAdapter.CarHolder> {

    private ArrayList<Car> carArrayList;
    private Context context;

    public MainRecylerAdapter(ArrayList<Car> carArrayList, Context context){
        this.carArrayList=carArrayList;
        this.context=context;
    }

    class CarHolder extends RecyclerView.ViewHolder{
        RecylerRowBinding recylerRowBinding;
        public CarHolder(@NonNull RecylerRowBinding recylerRowBinding) {
            super(recylerRowBinding.getRoot());
            this.recylerRowBinding=recylerRowBinding;
        }
    }

    @NonNull
    @Override
    public CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecylerRowBinding recylerRowBinding=RecylerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CarHolder(recylerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CarHolder holder, int position) {

        Picasso.get().load(carArrayList.get(position).imageUrl).into(holder.recylerRowBinding.carImage);
        holder.recylerRowBinding.priceEditText.setText("$"+carArrayList.get(position).price+"/day");
            holder.recylerRowBinding.modelTextView.setText(carArrayList.get(position).model);
        holder.recylerRowBinding.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String number=String.valueOf(position);
                Intent intent=new Intent(view.getContext(), DetailOfCar.class);
                intent.putExtra("carposition",number);
                context.startActivity(intent);
            }
        });

    }

    public void filterList(ArrayList<Car> filterlist) {
        carArrayList = filterlist;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return carArrayList.size();
    }




}

