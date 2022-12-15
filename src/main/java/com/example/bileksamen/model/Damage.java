package com.example.bileksamen.model;

public class Damage {
//Anna
    private int damageID;
    private int price;
    private String description;
    private int carID;

    public Damage(int damageID, String description,int price, int carID) {
        this.damageID = damageID;
        this.price = price;
        this.description = description;
        this.carID = carID;
    }


    public Damage(){}

    public Damage(int damageID, String description, int price) {
        this.damageID=damageID;
        this.description=description;
        this.price=price;
    }
    //Daniel
    public Damage(int damageID,int carID){
        this.damageID=damageID;
        this.carID=carID;
    }

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

    @Override
    public String toString() {
        return "Damage{" +
                "damageID=" + damageID +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", carID=" + carID +
                '}';
    }
}
