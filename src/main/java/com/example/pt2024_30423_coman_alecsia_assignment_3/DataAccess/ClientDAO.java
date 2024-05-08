package com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess;

import com.example.pt2024_30423_coman_alecsia_assignment_3.Connection.ConnectionFactory;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Client;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDAO extends AbstractDAO<Client> {
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

    @Override
    protected Client mapResultSetToEntity(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id");
        String name =  resultSet.getString("name");
        String email =  resultSet.getString("email");
        String phone =  resultSet.getString("phone");

        return new Client(id, name, email, phone);
    }
}
