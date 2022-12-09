package com.example.bileksamen.model;

//Opretter klassen "Car" med attributter, constructor, getters og setters
//Lasse Dall Mikkelsen
public class Car {

  private int carID;
  private String description;
  private String brand;
  private int originalPrice;
  private int pricePerMonth;
  private boolean available;

  public Car(int carID, String description, String brand, int originalPrice, int pricePerMonth, boolean available) {
    this.carID = carID;
    this.description = description;
    this.brand = brand;
    this.originalPrice = originalPrice;
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

  public int getOriginalPrice() {
    return originalPrice;
  }

  public void setOriginalPrice(int originalPrice) {
    this.originalPrice = originalPrice;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }



  @Override
  public String toString() {
    return "Car{" +
        "carID=" + carID +
        ", description='" + description + '\'' +
        ", brand='" + brand + '\'' +
        ", originalPrice=" + originalPrice +
        ", pricePerMonth=" + pricePerMonth +
        ", available=" + available +
        '}';
  }
}
