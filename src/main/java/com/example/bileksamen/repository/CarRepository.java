package com.example.bileksamen.repository;

import com.example.bileksamen.model.Car;
import com.example.bileksamen.model.Lease;
import com.example.bileksamen.model.Costumer;
import com.example.bileksamen.util.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


@Repository
public class CarRepository {

  //Opretter attributter og constructor
  //Lasse Dall Mikkelsen
  ArrayList<Car> fleet;
  ArrayList<Lease> leases;
  ArrayList<Costumer> costumers;

  public CarRepository() {
    fleet = getDBFleet();
    costumers = getDBCostumers();
    leases = getDBLeases();
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
    return costumers;
  }

  //Returnerer en liste af alle biler i flåden fra databasen
  //Lasse Dall Mikkelsen
  public ArrayList<Car> getDBFleet() {
    ArrayList<Car> cars = new ArrayList<>();

    try {
      Connection connection = ConnectionManager.getConnection(url, username, password);

      PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM fleet");
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        int carID = resultSet.getInt(1);
        String description = resultSet.getString(2);
        int originalPrice = resultSet.getInt(3);
        String brand = resultSet.getString(4);
        int pricePerMonth = resultSet.getInt(5);
        boolean available = resultSet.getBoolean(6);

        cars.add(new Car(carID, description, brand, originalPrice, pricePerMonth, available));
      }

    } catch (SQLException sqle) {
      System.out.println("Connection to database failed");
      sqle.printStackTrace();
    }
    return cars;
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
        leases.add(new Lease(leaseID, pricePerMonth, findCarByID(carID), findCostumerByID(costumerID), leaseStart, leaseEnd));
      }

    } catch (SQLException sqle) {
      System.out.println("Connection to database failed");
      sqle.printStackTrace();
    }
    return leases;
  }

  //Opretter et lease i databasen
  //Lasse Dall Mikkelsen
  public void createLease(Lease lease) {

    try {

      Connection connection = ConnectionManager.getConnection(url, username, password);
      String sql = "INSERT INTO lease VALUES (DEFAULT,?,?,?,?,?)";

      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, String.valueOf(lease.getLeaseStart()));
      preparedStatement.setInt(2, lease.getCar().getPricePerMonth());
      preparedStatement.setInt(3, lease.getCar().getCarID());
      preparedStatement.setInt(4, lease.getCostumer().getCostumerID());
      preparedStatement.setString(5, String.valueOf(lease.getLeaseEnd()));

      preparedStatement.executeUpdate();
      changeAvailability(lease.getCar().getCarID());
      costumers = getDBCostumers();
      leases = getDBLeases();

    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }

  }

  //Ændre availability i databasen
  //Lasse Dall Mikkelsen
  public void changeAvailability(int carID) {

    try {

      Connection connection = ConnectionManager.getConnection(url, username, password);
      String sql = "UPDATE fleet SET available=? WHERE carID=?";

      PreparedStatement preparedStatement = connection.prepareStatement(sql);

      if (findCarByID(carID).isAvailable()) {
        preparedStatement.setInt(1, 0);
      } else {
        preparedStatement.setInt(1, 1);
      }
      preparedStatement.setInt(2, carID);

      preparedStatement.executeUpdate();
      fleet = getDBFleet();

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

  //Metode til at fjerne bil fra fleetdatabasen når den er solgt.
  //Oscar Storm
  public void removeCar(Car car) {
    try {

      Connection connection = ConnectionManager.getConnection(url, username, password);
      String sql = "DELETE FROM fleet WHERE carId=?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);

      preparedStatement.setInt(1, car.getCarID());

      preparedStatement.executeUpdate();

    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
  }

  //Finder værdien af bilen.
  //Oscar Storm
  public void sellCar(Car car) {

    try {

      Connection connection = ConnectionManager.getConnection(url, username, password);
      String sql = "SELECT price FROM sales WHERE carID=? ";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);

      preparedStatement.setInt(1, car.getCarID());
      preparedStatement.executeUpdate();

      fleet = getDBFleet();

    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
  }


  //oscar storm
  public int carProfit(){
    int sellValue = 0;
    int x = 0;
    int y;
    for (int i = 0;i <leasedCars().size(); i++){
      x +=  leasedCars().get(i).getOriginalPrice();

    }
    y = (sellValue + getTotalLeasePrice())-x;
    return y;
  }





  //Returnere en bil ud fra carID
  //Lasse Dall Mikkelsen
  public Car findCarByID(int carID) {
    for (Car car : fleet) {
      if (car.getCarID() == carID) {
        return car;
      }
    }
    return null;
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

  //Tager en String (yyyy-mm-dd) og laver den til et LocalDate objekt
  //Lasse Dall Mikkelsen
  public LocalDate stringToLocalDate(String date) {
    int year = Integer.parseInt(date.substring(0, 4));
    int month = Integer.parseInt(date.substring(5, 7));
    int day = Integer.parseInt(date.substring(8));
    return LocalDate.of(year, month, day);
  }

  //Opdaterer oplysninger på en bil i flåden
  //Lasse Dall Mikkelsen
  public void updateCar(int carID, String brand, String description, int originalPrice, int pricePerMonth) {

    try {
      Connection connection = ConnectionManager.getConnection(url, username, password);

      String sql = "UPDATE fleet SET brand=?, description=?, original_price=?, price_per_month=? WHERE carID=?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);

      preparedStatement.setString(1, brand);
      preparedStatement.setString(2, description);
      preparedStatement.setInt(3, originalPrice);
      preparedStatement.setInt(4, pricePerMonth);
      preparedStatement.setInt(5, carID);

      preparedStatement.executeUpdate();
      fleet = getDBFleet();

    } catch (SQLException sqle) {
      System.out.println("Connection to database failed");
      sqle.printStackTrace();
    }
  }

  //Tilføjer en ny bil til databasen
  //Lasse Dall Mikkelsen
  public void addCar(String brand, String description, int originalPrice, int pricePerMonth) {

    try {
      Connection connection = ConnectionManager.getConnection(url, username, password);

      String sql = "INSERT INTO fleet VALUES (DEFAULT, ?, ?, ?, ?, TRUE)";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);

      preparedStatement.setString(1, description);
      preparedStatement.setInt(2, originalPrice);
      preparedStatement.setString(3, brand);
      preparedStatement.setInt(4, pricePerMonth);

      preparedStatement.executeUpdate();

      fleet = getDBFleet();

    } catch (SQLException sqle) {
      System.out.println("Connection to database failed");
      sqle.printStackTrace();
    }
  }

  //Finder ledige biler i en given tidsperiode og returnere dem i en ArrayList
  //Lasse Dall Mikkelsen
  public ArrayList<Car> findAvailableCars(String startDate, String endDate) {
    LocalDate startLocalDate = stringToLocalDate(startDate);
    LocalDate endLocalDate = stringToLocalDate(endDate);
    
    ArrayList<Integer> leasedCars = new ArrayList<>();

    //Looper gennem alle leases og finder tilføjer alle carID'er, der er udlejede i perioden
    for (Lease lease:leases) {
      
      if (lease.getLeaseStart().isBefore(startLocalDate) && lease.getLeaseEnd().isAfter(startLocalDate)) {
        leasedCars.add(lease.getCar().getCarID());
      } else if (lease.getLeaseStart().isBefore(endLocalDate) && lease.getLeaseEnd().isAfter(endLocalDate)) {
        leasedCars.add(lease.getCar().getCarID());
      }
    }

    //Nested Loop, der looper gennem alle biler i flåden og looper gennem ArrayListen af carID'er og frasortere de udlejede biler i perioden.
    ArrayList<Car> availableCars = getDBFleet();
    for (int i = 0; i < availableCars.size(); i++) {
      for (int carID:leasedCars) {
        if (availableCars.get(i).getCarID() == carID) {
          availableCars.remove(i);
        }
      }
    }
    return availableCars;
  }
  //Fælleskodning
  public ArrayList<Car> leasedCars(){
    ArrayList<Car> list = new ArrayList<>();
    for (Car car:fleet) {
      if(!car.isAvailable()){
        list.add(car);
      }
    }
    return list;
  }
  //Fælleskodning
  public int getTotalLeasePrice(){
    int i =0;
    ArrayList<Car> list = leasedCars();
    for (Car car:list) {
      i+=car.getPricePerMonth();
    }
    return i;
  }
}
