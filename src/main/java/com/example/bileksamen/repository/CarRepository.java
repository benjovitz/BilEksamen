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
  private ArrayList<Car> fleet;

  public CarRepository() {
    fleet = getDBFleet();
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

  //Metode til at fjerne bil fra fleetdatabasen når den er solgt.
  //Oscar Storm
  public void removeCar(Car car) {
    try {

      System.out.println(url);
      Connection connection = ConnectionManager.getConnection(url, username, password);
      String sql = "DELETE FROM fleet WHERE carId=?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);

      preparedStatement.setInt(1, car.getCarID());

      preparedStatement.executeUpdate();

      fleet = getDBFleet();

    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
  }

  //Finder værdien af bilen.
  //Oscar Storm
  public int sellCar(int carID) {

    int price = 0;

    try {

      Connection connection = ConnectionManager.getConnection(url, username, password);
      String sql = "SELECT * FROM sales WHERE carID=? ";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);

      preparedStatement.setInt(1, carID);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        price = resultSet.getInt(2);
      }

    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }

    return price;
  }


  //oscar storm
  public double carProfit(){
    double sellValue = 0;
    double originalPrice = 0;
    double profit;
    for (int i = 0;i <leasedCars().size(); i++){
      originalPrice +=  leasedCars().get(i).getOriginalPrice();
      sellValue += leasedCars().get(i).getSellValue();
    }
    profit = (sellValue + getTotalLeasePrice())-originalPrice;
    return profit;
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

  //oscar storm
  public ArrayList<Car> leasedCars(){
    ArrayList<Car> list = new ArrayList<>();
    for (Car car:fleet) {
      if(!car.isAvailable()){
        list.add(car);
      }
    }
    return list;
  }

  //oscar storm
  public double getTotalLeasePrice(){
    double i =0;
    ArrayList<Car> list = leasedCars();
    for (Car car:list) {
      i+=car.getPricePerMonth();
    }
    return i;
  }
}
