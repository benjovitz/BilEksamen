package com.example.bileksamen.repository;

import com.example.bileksamen.model.Driver;
import com.example.bileksamen.model.Pickup;
import com.example.bileksamen.util.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

//Daniel Benjovitz
@Repository
public class PickupRepositoy {
    private ArrayList<Pickup> pickups;

    private String url = System.getenv("url");
    private String username = System.getenv("username");
    private String password = System.getenv("password");

    public ArrayList<Pickup> getPickups() {
        return pickups;
    }

    public void createPickup(Pickup pickup) {

        try {
            Connection connection = ConnectionManager.getConnection(url, username, password);
            String sql = "INSERT INTO pickups VALUES (DEFAULT,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, pickup.getCarID());
            preparedStatement.setString(2, pickup.getLocation());
            preparedStatement.setString(3, pickup.getPickupDate());
            preparedStatement.setInt(4, pickup.getDriverID());
            preparedStatement.setBoolean(5, pickup.isPickedUp());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePickup(String location, String pickupDate, int pickupID) {
        try {
            Connection connection = ConnectionManager.getConnection(url, username, password);

            String sql = "UPDATE pickups SET location=?, pickup_date=? WHERE pickupID=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, location);
            preparedStatement.setString(2, pickupDate);
            preparedStatement.setInt(3, pickupID);

            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("cant connect to database");
            sqlException.printStackTrace();
        }
    }

    public ArrayList<Pickup> getAllPickups() {
        ArrayList<Pickup> arr = new ArrayList<>();

        try {
            Connection connection = ConnectionManager.getConnection(url, username, password);
            String sql = "SELECT * FROM pickups";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int pickupID = resultSet.getInt(1);
                int carID = resultSet.getInt(2);
                String location = resultSet.getString(3);
                String pickupDate = resultSet.getString(4);
                int driverID = resultSet.getInt(5);
                boolean pickedUp = resultSet.getBoolean(6);
                arr.add(new Pickup(pickupID, carID, location, pickupDate, driverID, pickedUp));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arr;
    }

    public void removePickup(int pickupID) {
        try {
            Connection connection = ConnectionManager.getConnection(url, username, password);
            String sql = "DELETE FROM pickups WHERE pickupID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, pickupID);

            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void registerPickup(int pickupID, boolean pickedUp) {

        try {
            Connection connection = ConnectionManager.getConnection(url, username, password);

            String sql = "UPDATE pickups SET picked_up=? WHERE pickupID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setBoolean(1, pickedUp);
            preparedStatement.setInt(2, pickupID);

            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println("cant connect to database");
            sqlException.printStackTrace();
        }
    }
}
