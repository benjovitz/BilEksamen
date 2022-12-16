package com.example.bileksamen.repository;
import com.example.bileksamen.model.Damage;
import com.example.bileksamen.util.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Anna Schmidt
@Repository
public class DamageRepository {

    private String url = System.getenv("url");
    private String username = System.getenv("username");
    private String password = System.getenv("password");

    public DamageRepository() {}

    //Anna
    public void create(Damage damage){

        try {
            Connection connection = ConnectionManager.getConnection(url, username, password);
            String sql = "INSERT INTO damage VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,damage.getDamageID());
            preparedStatement.setInt(2,damage.getCarID());

            preparedStatement.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

    }


    //Anna
    public ArrayList<Damage> getAllDamageDim(){
        ArrayList<Damage> damages = new ArrayList<>();

        try{
            Connection connection = ConnectionManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM damage_dim");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int damageID = resultSet.getInt(1);
                String description = resultSet.getString(2);
                int price = resultSet.getInt(3);

                damages.add(new Damage(damageID,description,price));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return damages;
    }
    //Daniel Benjovitz
    public ArrayList<Damage> getAllDamage(){

        ArrayList<Damage> list = new ArrayList<>();
        try{
            Connection connection = ConnectionManager.getConnection(url,username,password);
            String sql = "SELECT * FROM damage";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int damageID = resultSet.getInt(1);
                int carID = resultSet.getInt(2);
                list.add(new Damage(damageID,carID));
            }

        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return list;
    }
    //Første udkast til metode til at få alle skader ud med descriptions og price fra damage_dim
    //Daniel Benjovitz
    public ArrayList<Damage> getAllDamageDone(){
        ArrayList<Damage> damageList = getAllDamage();
        ArrayList<Damage> damageDim = getAllDamageDim();


        for (int i = 0; i < damageList.size(); i++) {
            Damage current = damageList.get(i);
            for (int j = 0; j < damageDim.size(); j++) {
                if(current.getDamageID()==damageDim.get(j).getDamageID()){
                    current.setDescription(damageDim.get(j).getDescription());
                    current.setPrice(damageDim.get(j).getPrice());
                }
            }
        }
        return damageList;
    }
    //Endelige metodet til at få alle skader ud med bilinformationer og skadesinformationer
    //Daniel Benjovitz
    public ArrayList<Damage> getDamageAndCar(){
        ArrayList<Damage> list = new ArrayList<>();
        try{
            Connection connection = ConnectionManager.getConnection(url,username,password);
            String sql = "select fleet.brand, fleet.description, damage.damageID, damage_dim.description, damage_dim.price, fleet.carID from fleet inner join damage on fleet.carID=damage.carID inner join damage_dim on damage.damageID=damage_dim.damageID";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String brand = resultSet.getString(1);
                String carDescription = resultSet.getString(2);
                int damageID = resultSet.getInt(3);
                String description = resultSet.getString(4);
                double price = resultSet.getDouble(5);
                int carID = resultSet.getInt(6);
                list.add(new Damage(damageID,price,description,carID,brand,carDescription));
            }

        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return list;
    }
    //Daniel Benjovitz
    public ArrayList<Damage> specificGetDamageAndCar(int carID){
        ArrayList<Damage> list = new ArrayList<>();
        try{
            Connection connection = ConnectionManager.getConnection(url,username,password);
            String sql = "select fleet.brand, fleet.description, damage.damageID, damage_dim.description, damage_dim.price, fleet.carID from fleet inner join damage on fleet.carID=damage.carID inner join damage_dim on damage.damageID=damage_dim.damageID where fleet.carID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,carID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String brand = resultSet.getString(1);
                String carDescription = resultSet.getString(2);
                int damageID = resultSet.getInt(3);
                String description = resultSet.getString(4);
                double price = resultSet.getDouble(5);
                list.add(new Damage(damageID,price,description,carID,brand,carDescription));
            }

        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return list;
    }
    //Daniel Benjovitz
    public double totalPrice(ArrayList<Damage> list){
        double d =0;
        for (Damage damage:list) {
            d += damage.getPrice();
        }
        System.out.println(d);
        return d;
    }
}
