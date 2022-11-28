package com.example.bileksamen.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

  //Singleton connection manager af Lasse Dall Mikkelsen

  private static Connection connection;


  //Opretter forbindelse, hvis den ikke i forevejen eksisterer
  public static Connection getConnection(String url, String username, String password) {

    if (connection != null) return connection;

    try {

      connection = DriverManager.getConnection(url, username, password);

    } catch (SQLException sqle) {
      System.out.println("Kunne ikke oprette forbindelse til databasen");
      sqle.printStackTrace();
    }
    return connection;
  }
}

