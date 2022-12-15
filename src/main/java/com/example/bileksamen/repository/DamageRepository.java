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

}
