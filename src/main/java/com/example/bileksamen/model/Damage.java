package com.example.bileksamen.model;

public class Damage {

    private int damageID;
    private int price;
    private String description;
    private int carID;

    public Damage(int damageID, int price, String description, int carID) {
        this.damageID = damageID;
        this.price = price;
        this.description = description;
        this.carID = carID;
    }

    public Damage(int price, String description, int carID) {
        this.price = price;
        this.description = description;
        this.carID = carID;
    }

    public Damage(){}

    public int getDamageID() {
        return damageID;
    }

    public void setDamageID(int damageID) {
        this.damageID = damageID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }
}
