package com.yasin.lastproject;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yasin.lastproject.databinding.RecylerRowBinding;

import java.util.ArrayList;

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedRecyclerAdapter.CarHolder> {

    private ArrayList<Car> carArrayList;


    public FeedRecyclerAdapter(ArrayList<Car> carArrayList){
        this.carArrayList=carArrayList;
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
        holder.recylerRowBinding.carName.setText(carArrayList.get(position).carName);
        holder.recylerRowBinding.brandName.setText(carArrayList.get(position).brandName);
        holder.recylerRowBinding.colorName.setText(carArrayList.get(position).colorName);
        holder.recylerRowBinding.price.setText(carArrayList.get(position).price);


    }


    @Override
    public int getItemCount() {
        return carArrayList.size();
    }


}

