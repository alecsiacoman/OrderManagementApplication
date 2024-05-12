package com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess;

import com.example.pt2024_30423_coman_alecsia_assignment_3.AlertUtils;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Connection.ConnectionFactory;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    private static final String TABLE_NAME = "Log";

    public void insert(Bill bill) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            String insertQuery = "INSERT INTO " + TABLE_NAME + " (orderId, totalPrice, createdAt) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, bill.orderId());
            preparedStatement.setDouble(2, bill.totalPrice());
            preparedStatement.setObject(3, bill.createdAt());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                AlertUtils.showMessage("Bill inserted successfully!");
            } else {
                AlertUtils.showAlert("Failed to insert bill.");
            }
        } catch (SQLException e) {
            AlertUtils.showAlert("Failed to insert bill. Error: " + e.getMessage());
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(preparedStatement);
        }
    }

    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME;
            preparedStatement = connection.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int orderId = resultSet.getInt("orderId");
                double totalPrice = resultSet.getDouble("totalPrice");
                LocalDateTime createdAt = resultSet.getTimestamp("createdAt").toLocalDateTime();
                Bill bill = new Bill(orderId, totalPrice, createdAt);
                bills.add(bill);
            }
        } catch (SQLException e) {
            AlertUtils.showAlert("Failed to retrieve bills. Error: " + e.getMessage());
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(resultSet);
        }
        return bills;
    }
}
