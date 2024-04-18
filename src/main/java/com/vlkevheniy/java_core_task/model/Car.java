package com.vlkevheniy.java_core_task.model;


/**
 * Car entity class
 * */
public class Car {
    private String model;
    private int year;
    private String brand;
    private String color;
    private double price;

    public Car(String model, int year, String brand, String color, double price) {
        this.model = model;
        this.year = year;
        this.brand = brand;
        this.color = color;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
