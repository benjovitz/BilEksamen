package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.Lease;
import com.example.bilabonnement.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class CarRepository {

  //Opretter attributter og constructor
  //Lasse Dall Mikkelsen
  ArrayList<Car> fleet;
  ArrayList<Lease> leases;

  public CarRepository() {
    fleet = new ArrayList<>();
    fleet.add(new Car(1, "BMW", 200000, 10000, true));
    leases = new ArrayList<>();
  }


  //Opretter nødvendige variable for at kunne oprette en connection til database gennem enviroment varible
  //Lasse Dall Mikkelsen
  private String url = System.getenv("url");
  private String username = System.getenv("username");
  private String password = System.getenv("password");

  public ArrayList<Car> getFleet() {
    return fleet;
  }

  //Opretter et lease i databasen
  //Lasse Dall Mikkelsen
  public void createLease(Lease lease) {

    try {

      Connection connection = ConnectionManager.getConnection(url, username, password);
      String sql = "USE fleetdatabase; INSERT INTO lease VALUES (DEFAULT, ?, ?, ?, ?, ?)";

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



}
