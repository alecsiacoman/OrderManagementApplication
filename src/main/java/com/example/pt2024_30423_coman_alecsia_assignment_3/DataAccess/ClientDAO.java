package com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess;

import com.example.pt2024_30423_coman_alecsia_assignment_3.Connection.ConnectionFactory;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDAO {
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO client (name, email, phone, address, age)" + " VALUES (?, ?, ?, ?, ?)";
    private final static String findStatementString = "SELECT * from client where id = ?";

    public static Client findById(int id){
        Client client = null;
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(findStatementString);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String phone = resultSet.getString("phone");
            String address = resultSet.getString("address");
            int age = resultSet.getInt("age");
            client = new Client(name, email, phone, address, age);
        }catch (SQLException e){
            LOGGER.log(Level.WARNING, "ClientDAO:findById " + e.getMessage());
        }finally{
            ConnectionFactory.close(connection);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(resultSet);
        }
        return client;
    }

    public static int insert(Client client){
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int id = -1;
        try{
            preparedStatement = connection.prepareStatement(insertStatementString);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getEmail());
            preparedStatement.setString(3, client.getPhone());
            preparedStatement.setString(4, client.getAddress());
            preparedStatement.setLong(5, client.getAge());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next())
                id = resultSet.getInt(1);
        }catch (SQLException e){
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        }finally{
            ConnectionFactory.close(connection);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(resultSet);
        }
        return id;
    }
}
