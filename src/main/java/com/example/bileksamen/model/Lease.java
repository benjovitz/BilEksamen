package com.example.bilabonnement.model;

//Opretter klassen "Lease" med attributter, constructor, getters og setters
//Lasse Dall Mikkelsen
public class Lease {

  private int leaseID;
  private com.example.bilabonnement.model.Car car;
  private com.example.bilabonnement.model.Costumer costumer;
  private String leaseStart;
  private String leaseEnd;

  public Lease(int leaseID, com.example.bilabonnement.model.Car car, com.example.bilabonnement.model.Costumer costumer, String leaseStart, String leaseEnd) {
    this.leaseID = leaseID;
    this.leaseStart = leaseStart;
    this.leaseEnd = leaseEnd;
    this.car = car;
    this.costumer = costumer;
  }

  public Lease(com.example.bilabonnement.model.Car car, com.example.bilabonnement.model.Costumer costumer, String leaseStart, String leaseEnd) {
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

  public String getLeaseStart() {
    return leaseStart;
  }

  public void setLeaseStart(String leaseStart) {
    this.leaseStart = leaseStart;
  }

  public String getLeaseEnd() {
    return leaseEnd;
  }

  public void setLeaseEnd(String leaseEnd) {
    this.leaseEnd = leaseEnd;
  }

  public com.example.bilabonnement.model.Car getCar() {
    return car;
  }

  public void setCar(com.example.bilabonnement.model.Car car) {
    this.car = car;
  }

  public com.example.bilabonnement.model.Costumer getCostumer() {
    return costumer;
  }

  public void setCostumer(com.example.bilabonnement.model.Costumer costumer) {
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
}
