package com.example.bileksamen.repository;

import com.example.bileksamen.model.Damage;
import com.example.bileksamen.util.ConnectionManager;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.WebRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class DamageRepository {

    private String url = System.getenv("url");
    private String username = System.getenv("username");
    private String password = System.getenv("password");
    public void create(Damage damage){


        try {

            Connection connection = ConnectionManager.getConnection(url, username, password);
            String sql = "INSERT INTO damage VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
           // preparedStatement.setInt(1,damage.getPrice());
           // preparedStatement.setString(2,damage.getDescription());
            preparedStatement.setInt(1,damage.getDamageID());
            preparedStatement.setInt(2,damage.getCarID());

            preparedStatement.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

    }




}
