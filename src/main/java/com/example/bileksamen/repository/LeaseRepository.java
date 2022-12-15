package com.example.bileksamen.repository;

import com.example.bileksamen.model.Car;
import com.example.bileksamen.model.Costumer;
import com.example.bileksamen.model.Lease;
import com.example.bileksamen.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

//Lasse Dall Mikkelsen
public class LeaseRepository {

  private ArrayList<Lease> leases;
  private ArrayList<Costumer> costumers;

  private String url = System.getenv("url");
  private String username = System.getenv("username");
  private String password = System.getenv("password");

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

  public LeaseRepository() {
    leases = getDBLeases();
    costumers = getDBCostumers();
  }

  //Returnerer en liste af alle leases fra databasen
  //Lasse Dall Mikkelsen
  public ArrayList<Lease> getDBLeases() {
    ArrayList<Lease> leases = new ArrayList<>();

    try {
      Connection connection = ConnectionManager.getConnection(url, username, password);

      PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM lease");
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        int leaseID = resultSet.getInt(1);
        LocalDate leaseStart = stringToLocalDate(resultSet.getString(2));
        int pricePerMonth = resultSet.getInt(3);
        int carID = resultSet.getInt(4);
        int costumerID = resultSet.getInt(5);
        LocalDate leaseEnd = stringToLocalDate(resultSet.getString(6));
        leases.add(new Lease(leaseID, pricePerMonth, carID, costumerID, leaseStart, leaseEnd));
      }

    } catch (SQLException sqle) {
      System.out.println("Connection to database failed");
      sqle.printStackTrace();
    }
    return leases;
  }

  //Tager en String (yyyy-mm-dd) og laver den til et LocalDate objekt
  //Lasse Dall Mikkelsen
  public LocalDate stringToLocalDate(String date) {
    int year = Integer.parseInt(date.substring(0, 4));
    int month = Integer.parseInt(date.substring(5, 7));
    int day = Integer.parseInt(date.substring(8));
    return LocalDate.of(year, month, day);
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
    return costumers;
  }

  //Opretter et lease i databasen
  //Lasse Dall Mikkelsen
  public void createLease(Lease lease) {

    try {

      Connection connection = ConnectionManager.getConnection(url, username, password);
      String sql = "INSERT INTO lease VALUES (DEFAULT,?,?,?,?,?)";

      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, String.valueOf(lease.getLeaseStart()));
      preparedStatement.setInt(2, lease.getPricePerMonth());
      preparedStatement.setInt(3, lease.getCarID());
      preparedStatement.setInt(4, lease.getCostumerID());
      preparedStatement.setString(5, String.valueOf(lease.getLeaseEnd()));

      preparedStatement.executeUpdate();
      costumers = getDBCostumers();
      leases = getDBLeases();

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

  //Returnere en kunde ud fra costumerID
  //Lasse Dall Mikkelsen
  public Costumer findCostumerByID(int costumerID) {
    for (Costumer costumer : costumers) {
      if (costumer.getCostumerID() == costumerID) {
        return costumer;
      }
    }
    return null;
  }

  //Finder ledige biler i en given tidsperiode og returnere dem i en ArrayList
  //Lasse Dall Mikkelsen
  public ArrayList<Car> findAvailableCars(String startDate, String endDate, ArrayList<Car> fleet) {
    LocalDate startLocalDate = stringToLocalDate(startDate);
    LocalDate endLocalDate = stringToLocalDate(endDate);

    ArrayList<Integer> leasedCars = new ArrayList<>();

    //Looper gennem alle leases og finder tilføjer alle carID'er, der er udlejede i perioden
    for (Lease lease:leases) {

      if (lease.getLeaseStart().isBefore(startLocalDate) && lease.getLeaseEnd().isAfter(startLocalDate)) {
        leasedCars.add(lease.getCarID());
      } else if (lease.getLeaseStart().isBefore(endLocalDate) && lease.getLeaseEnd().isAfter(endLocalDate)) {
        leasedCars.add(lease.getCarID());
      }
    }

    //Nested Loop, der looper gennem alle biler i flåden og looper gennem ArrayListen af carID'er og frasortere de udlejede biler i perioden.
    ArrayList<Car> availableCars = fleet;
    for (int i = fleet.size()-1; i > 0 ; i--) {
      for (int carID:leasedCars) {
        if (fleet.get(i).getCarID() == carID) {
          availableCars.remove(i);
        }
      }
    }
    return availableCars;
  }
}
