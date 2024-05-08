package com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess;

import com.example.pt2024_30423_coman_alecsia_assignment_3.AlertUtils;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Connection.ConnectionFactory;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Orders;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends AbstractDAO<Orders> {
    @Override
    protected Orders mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int clientId =  resultSet.getInt("clientId");
        int productId = resultSet.getInt("productId");
        int quantity =  resultSet.getInt("quantity");

        return new Orders(id, clientId, productId, quantity);
    }

    public static List<Integer> getClientIds() {
        return getDistinctIds("client", "id");
    }

    public static List<Integer> getProductIds() {
        return getDistinctIds("product", "id");
    }

    private static List<Integer> getDistinctIds(String tableName, String idColumnName) {
        List<Integer> ids = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            String query = "SELECT DISTINCT " + idColumnName + " FROM " + tableName;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(idColumnName);
                ids.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(resultSet);
        }

        return ids;
    }

}
