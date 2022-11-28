package com.example.bileksamen.repository;

import com.example.bileksamen.model.Car;
import com.example.bileksamen.model.Lease;
import com.example.bileksamen.model.Costumer;
import com.example.bileksamen.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;


@Repository
public class CarRepository {

  //Opretter attributter og constructor
  //Lasse Dall Mikkelsen
  ArrayList<Car> fleet;
  ArrayList<Lease> leases;
  ArrayList<Costumer> costumers;

  public CarRepository() {
    fleet = new ArrayList<>();
    fleet.add(new Car(1, "BMW", 200000, 10000, true));
    leases = new ArrayList<>();
    costumers = getDBCostumers();
  }


  //Opretter nødvendige variable for at kunne oprette en connection til database gennem enviroment varible
  //Lasse Dall Mikkelsen
  private String url = System.getenv("url");
  private String username = System.getenv("username");
  private String password = System.getenv("password");

  public ArrayList<Car> getFleet() {
    return fleet;
  }

  public void setFleet(ArrayList<Car> fleet) {
    this.fleet = fleet;
  }

  public ArrayList<Lease> getLeases() {
    return leases;
  }

  public void setLeases(ArrayList<Lease> leases) {
    this.leases = leases;
  }

  public ArrayList<Costumer> getCostumers() {
    return costumers;
  }

  public void setCostumers(ArrayList<Costumer> costumers) {
    this.costumers = costumers;
  }

  //Returnerer en liste af alle costumers fra databasen
  //Lasse Dall Mikkelsen
  public ArrayList<Costumer> getDBCostumers() {
    ArrayList<Costumer> costumers = new ArrayList<>();
    try {
      Connection connection = ConnectionManager.getConnection(url, username, password);

      PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer");
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        int costumerID = resultSet.getInt(1);
        String firstName = resultSet.getString(2);
        String lastName = resultSet.getString(3);
        String phone = resultSet.getString(4);
        String email = resultSet.getString(5);
        String enterpriseName = resultSet.getString(6);
        costumers.add(new Costumer(costumerID, firstName, lastName, email, phone, enterpriseName));
      }

    } catch (SQLException sqle) {
      System.out.println("Connection to database failed");
      sqle.printStackTrace();
    }
    System.out.println(costumers);
    return costumers;
  }

  //Opretter et lease i databasen
  //Lasse Dall Mikkelsen
  public void createLease(Lease lease) {

    try {

      Connection connection = ConnectionManager.getConnection(url, username, password);
      String sql = "INSERT INTO lease VALUES (DEFAULT,?,?,?,?,?)";

      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, lease.getLeaseStart());
      preparedStatement.setInt(2, lease.getCar().getPricePerMonth());
      preparedStatement.setInt(3, lease.getCar().getCarID());
      preparedStatement.setInt(4, lease.getCostumer().getCostumerID());
      preparedStatement.setString(5, lease.getLeaseEnd());

      preparedStatement.executeUpdate();

    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }

  }

  //Opretter en costumer i databasen
  //Lasse Dall Mikkelsen
  public void createCostumer(Costumer costumer) {

    try {

      Connection connection = ConnectionManager.getConnection(url, username, password);
      String sql = "INSERT INTO customer VALUES (DEFAULT,?,?,?,?,null)";

      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, costumer.getFirstName());
      preparedStatement.setString(2, costumer.getLastName());
      preparedStatement.setString(3, costumer.getPhone());
      preparedStatement.setString(4, costumer.getEmail());

      preparedStatement.executeUpdate();
      costumers = getDBCostumers();

    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
  }
}
