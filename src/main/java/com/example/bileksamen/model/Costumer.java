package com.example.bileksamen.model;

//Opretter klassen "Costumer" med attributter, constructor, getters og setters
//Lasse Dall Mikkelsen
public class Costumer {

  private int costumerID;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;

  public Costumer(int costumerID, String firstName, String lastName, String email, String phone) {
    this.costumerID = costumerID;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
  }

  public int getCostumerID() {
    return costumerID;
  }

  public void setCostumerID(int costumerID) {
    this.costumerID = costumerID;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public Costumer(String firstName, String lastName, String email, String phone) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFullName(String fullName) {
    this.firstName = fullName;
  }

  public String getLastName() {
    return firstName;
  }

  public void setLastName(String fullName) {
    this.firstName = fullName;
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
    return "Costumer{" +
        "costumerID=" + costumerID +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", phone='" + phone + '\'' +
        '}';
  }
}
