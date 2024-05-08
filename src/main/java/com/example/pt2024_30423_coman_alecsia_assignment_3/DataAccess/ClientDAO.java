package com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess;

import com.example.pt2024_30423_coman_alecsia_assignment_3.AlertUtils;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Connection.ConnectionFactory;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDAO {
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO client (name, email, phone)" + " VALUES (?, ?, ?)";
    private final static String findStatementString = "SELECT * from client where id = ?";
    private static final String updateStatementString = "UPDATE client SET name = ?, email = ?, phone = ? WHERE id = ?";
    private static final String deleteStatementString = "DELETE FROM client WHERE id = ?";
    private static final String viewAllStatementString = "SELECT * FROM client";


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
            client = new Client(name, email, phone);
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
            preparedStatement = connection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getEmail());
            preparedStatement.setString(3, client.getPhone());
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

    public static void update(Client client) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        System.out.println(client);

        try {
            preparedStatement = connection.prepareStatement(updateStatementString);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getEmail());
            preparedStatement.setString(3, client.getPhone());
            preparedStatement.setInt(4, client.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                LOGGER.info("Client updated successfully");
            } else {
                LOGGER.warning("Failed to update client. No rows were affected.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(preparedStatement);
        }
    }

    public static void delete(int id) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(deleteStatementString);
            preparedStatement.setInt(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                LOGGER.info("Client deleted successfully");
            } else {
                LOGGER.warning("Failed to delete client. No rows were affected.");
                AlertUtils.showAlert("No such id in the table!");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(preparedStatement);
        }
    }

    public static List<Client> viewAll(){
        List<Client> clients = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            preparedStatement = connection.prepareStatement(viewAllStatementString);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");

                Client client = new Client(id, name, email, phone);
                clients.add(client);
            }
        } catch (SQLException e){
            LOGGER.log(Level.WARNING, "ClientDAO:viewAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(resultSet);
        }

        return clients;
    }
}
