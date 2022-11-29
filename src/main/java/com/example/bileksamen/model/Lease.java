package com.example.bileksamen.model;

import java.time.LocalDate;

//Opretter klassen "Lease" med attributter, constructor, getters og setters
//Lasse Dall Mikkelsen
public class Lease {

  private int leaseID;
  private int pricePerMonth;
  private Car car;
  private Costumer costumer;
  private LocalDate leaseStart;
  private LocalDate leaseEnd;

  public Lease(int leaseID, int pricePerMonth, Car car, Costumer costumer, LocalDate leaseStart, LocalDate leaseEnd) {
    this.leaseID = leaseID;
    this.pricePerMonth = pricePerMonth;
    this.leaseStart = leaseStart;
    this.leaseEnd = leaseEnd;
    this.car = car;
    this.costumer = costumer;
  }

  public Lease(Car car, Costumer costumer, LocalDate leaseStart, LocalDate leaseEnd) {
    this.leaseStart = leaseStart;
    this.leaseEnd = leaseEnd;
    this.car = car;
    this.costumer = costumer;
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

  public Car getCar() {
    return car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  public Costumer getCostumer() {
    return costumer;
  }

  public void setCostumer(Costumer costumer) {
    this.costumer = costumer;
  }

  @Override
  public String toString() {
    return "Lease{" +
        "leaseID=" + leaseID +
        ", car=" + car +
        ", costumer=" + costumer +
        ", leaseStart='" + leaseStart + '\'' +
        ", leaseEnd='" + leaseEnd + '\'' +
        '}';
  }

  public int getPricePerMonth() {
    return pricePerMonth;
  }

  public void setPricePerMonth(int pricePerMonth) {
    this.pricePerMonth = pricePerMonth;
  }
}
