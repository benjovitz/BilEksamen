package com.example.bileksamen.model;

//Opretter klassen "Costumer" med attributter, constructor, getters og setters
//Lasse Dall Mikkelsen
public class Costumer {

  private int costumerID;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private String enterpriseName;

  public Costumer(int costumerID, String firstName, String lastName, String email, String phone, String enterpriseName) {
    this.costumerID = costumerID;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.enterpriseName = enterpriseName;
  }

  public int getCostumerID() {
    return costumerID;
  }

  public void setCostumerID(int costumerID) {
    this.costumerID = costumerID;
  }

  public String getEnterpriseName() {
    return enterpriseName;
  }

  public void setEnterpriseName(String enterpriseName) {
    this.enterpriseName = enterpriseName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getFirstName() {
    return firstName;
  }


  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String toString() {
    return
        " First name:  " + firstName +
        ", Last name:  " + lastName +
        ", Email: " + email +
        ", Phone: " + phone +
        "";
  }
}
