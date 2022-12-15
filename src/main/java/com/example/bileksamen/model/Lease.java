package com.example.bileksamen.model;

import java.time.LocalDate;

//Opretter klassen "Lease" med attributter, constructor, getters og setters
//Lasse Dall Mikkelsen
public class Lease {

  private int leaseID;
  private int pricePerMonth;
  private int carID;
  private int costumerID;
  private LocalDate leaseStart;
  private LocalDate leaseEnd;

  public Lease(int leaseID, int pricePerMonth, int carID, int costumerID, LocalDate leaseStart, LocalDate leaseEnd) {
    this.leaseID = leaseID;
    this.pricePerMonth = pricePerMonth;
    this.leaseStart = leaseStart;
    this.leaseEnd = leaseEnd;
    this.carID = carID;
    this.costumerID = costumerID;
  }

  public Lease(int carID, int costumerID, LocalDate leaseStart, LocalDate leaseEnd) {
    this.leaseStart = leaseStart;
    this.leaseEnd = leaseEnd;
    this.carID = carID;
    this.costumerID = costumerID;
  }

  public int getLeaseID() {
    return leaseID;
  }

  public void setLeaseID(int leaseID) {
    this.leaseID = leaseID;
  }

  public LocalDate getLeaseStart() {
    return leaseStart;
  }

  public void setLeaseStart(LocalDate leaseStart) {
    this.leaseStart = leaseStart;
  }

  public LocalDate getLeaseEnd() {
    return leaseEnd;
  }

  public void setLeaseEnd(LocalDate leaseEnd) {
    this.leaseEnd = leaseEnd;
  }

  public int getCarID() {
    return carID;
  }

  public void setCarID(int carID) {
    this.carID = carID;
  }

  public int getCostumerID() {
    return costumerID;
  }

  public void setCostumer(int costumerID) {
    this.costumerID = costumerID;
  }

  @Override
  public String toString() {
    return "Lease{" +
        "leaseID=" + leaseID +
        ", pricePerMonth=" + pricePerMonth +
        ", carID=" + carID +
        ", costumerID=" + costumerID +
        ", leaseStart=" + leaseStart +
        ", leaseEnd=" + leaseEnd +
        '}';
  }

  public int getPricePerMonth() {
    return pricePerMonth;
  }

  public void setPricePerMonth(int pricePerMonth) {
    this.pricePerMonth = pricePerMonth;
  }
}
