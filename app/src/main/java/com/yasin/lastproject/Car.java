package com.yasin.lastproject;

public class Car {

    String email;
    String serial;
    String model;
    String color;
    String price;
    String imageUrl;
    String count;


    String year;

    public Car(String serial, String model, String color, String price, String imageUrl,String email,String count,String year) {
        this.serial = serial;
        this.model = model;
        this.color = color;
        this.price = price;
        this.imageUrl = imageUrl;
        this.email=email;
        this.count=count;
        this.year=year;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCount() {
        return count;
    }

   public void setCount(String count) {
        this.count = count;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }






}
