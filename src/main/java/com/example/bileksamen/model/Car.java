package com.example.bileksamen.model;

//Opretter klassen "Car" med attributter, constructor, getters og setters
//Lasse Dall Mikkelsen
public class Car {

  private int carID;
  private String brand;
  private int value;
  private int pricePerMonth;
  private boolean available;

  public Car(int carID, String brand, int value, int pricePerMonth, boolean available) {
    this.carID = carID;
    this.brand = brand;
    this.value = value;
    this.pricePerMonth = pricePerMonth;
    this.available = available;
  }

  public int getCarID() {
    return carID;
  }

  public void setCarID() {
    this.carID = carID;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public int getPricePerMonth() {
    return pricePerMonth;
  }

  public void setPricePerMonth(int pricePerMonth) {
    this.pricePerMonth = pricePerMonth;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  @Override
  public String toString() {
    return "Car{" +
        "carID=" + carID +
        ", brand='" + brand + '\'' +
        ", value=" + value +
        ", pricePerMonth=" + pricePerMonth +
        ", available=" + available +
        '}';
  }
}
