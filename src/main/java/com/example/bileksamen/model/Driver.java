package com.example.bileksamen.model;

//Daniel Benjovitz
public class Driver {
    private int driverID;
    private String firstName;
    private String lastName;

    public Driver(int driverID, String firstName, String lastName) {
        this.driverID = driverID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Driver(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driverID=" + driverID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
