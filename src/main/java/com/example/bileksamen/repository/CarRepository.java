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

  //Opretter en costumer i databasen
  //Lasse Dall Mikkelsen
  public void createCostumer(Costumer costumer) {

    try {

      Connection connection = ConnectionManager.getConnection(url, username, password);
      String sql = "USE fleetdatabase; INSERT INTO costumer VALUES (DEFAULT, ?, ?, ?, ?, ?)";

      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, costumer.getFirstName());
      preparedStatement.setString(2, costumer.getLastName());
      preparedStatement.setString(3, costumer.getPhone());
      preparedStatement.setString(4, costumer.getEmail());
      preparedStatement.setString(5, null);

      preparedStatement.executeUpdate();

    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
  }

  //Metode til at fjerne bil fra fleetdatabasen når den er solgt.
  //Oscar Storm
  public void RemoveCar(Car car) {
    try {

      Connection connection = ConnectionManager.getConnection(url, username, password);
      String sql = "USE fleetdatabase; DELETE FROM fleet WHERE carId=?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);

      preparedStatement.setInt(1,car.getCarID());

      preparedStatement.executeUpdate();

    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
  }
  //Finder værdien af bilen.
  //Oscar Storm
  public void sellCar(Car car){

    try {

      Connection connection = ConnectionManager.getConnection(url, username, password);
      String sql = "USE flletdatabase; SELECT price sales WHERE carID=? ";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);

      preparedStatement.setInt(1,car.getCarID());
      preparedStatement.executeUpdate();


    }catch (SQLException sqle){
      sqle.printStackTrace();
    }

  }






}
