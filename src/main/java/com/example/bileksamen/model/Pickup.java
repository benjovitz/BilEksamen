package com.example.bileksamen.model;

public class Pickup {
    private int pickupID;
    private int carID;
    private String location;
    private String pickupDate;
    private int driverID;

    @Override
    public String toString() {
        return "Pickup{" +
                "pickupID=" + pickupID +
                ", carID=" + carID +
                ", location='" + location + '\'' +
                ", pickupDate='" + pickupDate + '\'' +
                ", driverID=" + driverID +
                '}';
    }

    public Pickup(int pickupID, int carID, String location, String pickupDate, int driverID) {
        this.pickupID = pickupID;
        this.carID = carID;
        this.location = location;
        this.pickupDate = pickupDate;
        this.driverID = driverID;
    }

    public Pickup(int carID, String location, String pickupDate, int driverID) {
        this.carID = carID;
        this.location = location;
        this.pickupDate = pickupDate;
        this.driverID = driverID;
    }

    public int getPickupID() {
        return pickupID;
    }

    public void setPickupID(int pickupID) {
        this.pickupID = pickupID;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }
}
