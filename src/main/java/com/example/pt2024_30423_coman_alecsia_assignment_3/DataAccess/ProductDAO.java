package com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess;

import com.example.pt2024_30423_coman_alecsia_assignment_3.AlertUtils;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Connection.ConnectionFactory;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Client;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class ProductDAO extends AbstractDAO<Product> {

    @Override
    protected Product mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name =  resultSet.getString("name");
        double price =  resultSet.getDouble("price");
        int quantity =  resultSet.getInt("quantity");

        return new Product(id, name, price, quantity);
    }
}
