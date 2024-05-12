package com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess;

import com.example.pt2024_30423_coman_alecsia_assignment_3.AlertUtils;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Connection.ConnectionFactory;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Bill;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Orders;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Product;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class OrderDAO extends AbstractDAO<Orders> {
    @Override
    protected Orders mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int clientId =  resultSet.getInt("clientId");
        int productId = resultSet.getInt("productId");
        int quantity =  resultSet.getInt("quantity");

        return new Orders(id, clientId, productId, quantity);
    }

    private boolean verify(Orders order){
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.findById(order.getProductId(), "id");
        int finalQuantity = product.getQuantity() - order.getQuantity();

        if(finalQuantity < 0){
            throw new IllegalArgumentException();
        }

        if(finalQuantity > 0){
            product.setQuantity(finalQuantity);
            productDAO.edit(product, "id");
            return true;
        }

        productDAO.delete(product.getId(), "id");
        return true;
    }

    @Override
    public void insert(Orders entity){
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            if(verify(entity)) {
                StringBuilder insertQuery = new StringBuilder("INSERT INTO orders (");
                StringBuilder values = new StringBuilder(") VALUES (");

                Field[] fields = Orders.class.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    insertQuery.append(fieldName).append(",");
                    values.append("?,");
                }

                insertQuery.deleteCharAt(insertQuery.length() - 1).append(values.deleteCharAt(values.length() - 1)).append(")");
                preparedStatement = connection.prepareStatement(insertQuery.toString());
                int parameterIndex = 1;
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    preparedStatement.setObject(parameterIndex++, value);
                }
                preparedStatement.executeUpdate();
                AlertUtils.showMessage("Order added successfully!");
            }
        } catch (SQLException | IllegalAccessException e) {
            AlertUtils.showAlert("Failed to add order. No rows were affected.");
        } catch (IllegalArgumentException e){
            AlertUtils.showAlert("Not enough stock!");
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(preparedStatement);
        }
    }

    @Override
    public void edit(Orders entity, String idColumnName) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        String tableName = Orders.class.getSimpleName();
        try {
            if(verify(entity)) {
                StringBuilder updateQuery = new StringBuilder("UPDATE ").append(tableName).append(" SET ");

                Field[] fields = Orders.class.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    if (!fieldName.equals(idColumnName)) {
                        updateQuery.append(fieldName).append(" = ?,");
                    }
                }
                updateQuery.deleteCharAt(updateQuery.length() - 1).append(" WHERE ").append(idColumnName).append(" = ?");
                preparedStatement = connection.prepareStatement(updateQuery.toString());
                int parameterIndex = 1;
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    if (field.getName().equals(idColumnName))
                        preparedStatement.setObject(fields.length, value);
                    else
                        preparedStatement.setObject(parameterIndex++, value);
                }
                preparedStatement.executeUpdate();
                LOGGER.info(tableName + " updated successfully");
                AlertUtils.showMessage(tableName + " updated successfully");

            }
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, "AbstractDAO:edit " + e.getMessage());
            AlertUtils.showAlert("Failed to update " + tableName + ". No rows were affected.");
        } catch (IllegalArgumentException e){
            AlertUtils.showAlert("Not enough stock!");
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(preparedStatement);
        }
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
