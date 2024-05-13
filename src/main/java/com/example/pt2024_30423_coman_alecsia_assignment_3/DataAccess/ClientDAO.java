package com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess;

import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Client;
import java.sql.*;

/**
 * Data Access Object (DAO) class for handling client data.
 */
public class ClientDAO extends AbstractDAO<Client> {
    /**
     * Maps a result set to a client entity.
     * @param resultSet The result set from a database query.
     * @return The mapped client entity.
     * @throws SQLException If an SQL exception occurs.
     */
    @Override
    protected Client mapResultSetToEntity(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id");
        String name =  resultSet.getString("name");
        String email =  resultSet.getString("email");
        String phone =  resultSet.getString("phone");

        return new Client(id, name, email, phone);
    }
}
