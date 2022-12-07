package com.example.bileksamen.repository;

import com.example.bileksamen.model.Driver;
import com.example.bileksamen.model.Pickup;
import com.example.bileksamen.util.ConnectionManager;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
//Daniel Benjovitz
@Repository
public class DriverRepository {

    private ArrayList<Driver> drivers;
    private String url = System.getenv("url");
    private String username = System.getenv("username");
    private String password = System.getenv("password");

    public DriverRepository(){
        drivers = getAllDrivers();
    }

    public ArrayList<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(ArrayList<Driver> drivers) {
        this.drivers = drivers;
    }

    public void createDriver(Driver driver){

        try{
            Connection connection = ConnectionManager.getConnection(url,username,password);
            String sql = "INSERT INTO driver VALUES (DEFAULT,?,?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,driver.getFirstName());
            preparedStatement.setString(2,driver.getLastName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Driver> getAllDrivers(){
        ArrayList<Driver> arr = new ArrayList<>();

        try{
            Connection connection = ConnectionManager.getConnection(url,username,password);
            String sql = "SELECT * FROM driver";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int driverID = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                arr.add(new Driver(driverID,firstName,lastName));
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return arr;
    }
}
