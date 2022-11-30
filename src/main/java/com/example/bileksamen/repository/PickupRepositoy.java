package com.example.bileksamen.repository;

import com.example.bileksamen.model.Pickup;
import com.example.bileksamen.util.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public void createPickup(Pickup pickup){

        try{
            Connection connection = ConnectionManager.getConnection(url,username,password);
            String sql = "INSERT INTO pickup VALUES (DEFAULT,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,pickup.getCarID());
            preparedStatement.setString(2,pickup.getLocation());
            preparedStatement.setString(3,pickup.getPickupDate());
            //preparedStatement.setDate(3, Date.valueOf(pickup.getPickupDate()));
            preparedStatement.setInt(4,pickup.getDriverID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
