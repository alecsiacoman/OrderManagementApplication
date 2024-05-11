package com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess;

import com.example.pt2024_30423_coman_alecsia_assignment_3.AlertUtils;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Connection.ConnectionFactory;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Client;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Product;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO(){
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createSelectQuery(String field){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + "=?");
        return sb.toString();
    }

    public T findById(int id, String idColumnName) {
        T entity = null;
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String selectQuery = createSelectQuery(idColumnName);
        try {
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                entity = mapResultSetToEntity(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "AbstractDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(resultSet);
        }
        return entity;
    }

    public void insert(T entity){
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        String tableName = type.getSimpleName();
        StringBuilder insertQuery = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
        StringBuilder values = new StringBuilder(") VALUES (");

        try{
            Field[] fields = type.getDeclaredFields();
            for(Field field : fields){
                field.setAccessible(true);
                String fieldName = field.getName();
                insertQuery.append(fieldName).append(",");
                values.append("?,");
            }

            insertQuery.deleteCharAt(insertQuery.length() - 1).append(values.deleteCharAt(values.length() - 1)).append(")");
            preparedStatement = connection.prepareStatement(insertQuery.toString());
            int parameterIndex = 1;
            for(Field field : fields){
                field.setAccessible(true);
                Object value = field.get(entity);
                preparedStatement.setObject(parameterIndex++, value);
            }
            preparedStatement.executeUpdate();
            AlertUtils.showMessage(tableName + " added successfully!");
        } catch (SQLException | IllegalAccessException e){
            e.printStackTrace();
            AlertUtils.showAlert("Failed to add " + tableName + ". No rows were affected.");
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(preparedStatement);
        }
    }

    public void edit(T entity, String idColumnName) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        String tableName = type.getSimpleName();

        try {
            Field idField = type.getDeclaredField(idColumnName);
            idField.setAccessible(true);
            Object idValue = idField.get(entity);

            if (findById((int) idValue, idColumnName) == null) {
                AlertUtils.showAlert("The " + tableName + " with ID " + idValue + " does not exist.");
                return;
            }

            StringBuilder updateQuery = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
            Field[] fields = type.getDeclaredFields();

            // Construct the UPDATE query
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
                if(field.getName().equals(idColumnName)) {
                    preparedStatement.setObject(fields.length, value);
                } else {
                    preparedStatement.setObject(parameterIndex++, value);
                }
            }

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                LOGGER.info(tableName + " updated successfully");
                AlertUtils.showMessage(tableName + " updated successfully");
            } else {
                LOGGER.warning("Failed to update " + tableName + ". No rows were affected.");
                AlertUtils.showAlert("Failed to update " + tableName + ". No rows were affected.");
            }
        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            LOGGER.log(Level.WARNING, "AbstractDAO:edit " + e.getMessage());
            AlertUtils.showAlert("Failed to update " + tableName + ". No rows were affected.");
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(preparedStatement);
        }
    }


    public void delete(int id, String idColumnName) {
        String tableName = type.getSimpleName();
        if(findById(id, "id") != null) {
            if (type == Client.class) {
                deleteClientOrProduct("clientId", id);
            } else if (type == Product.class) {
                deleteClientOrProduct("productId", id);
            }
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = null;
            String deleteQuery = "DELETE FROM " + tableName + " WHERE " + idColumnName + "=?";
            try {
                preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setInt(1, id);
                int rowsDeleted = preparedStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    LOGGER.info(tableName + " deleted successfully");
                    AlertUtils.showMessage(tableName + " deleted successfully");
                } else {
                    LOGGER.warning("Failed to delete " + tableName + ". No rows were affected.");
                    AlertUtils.showAlert("Failed to delete " + tableName + ". No rows were affected.");
                }

            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "AbstractDAO:delete " + e.getMessage());
            } finally {
                ConnectionFactory.close(connection);
                ConnectionFactory.close(preparedStatement);
            }
        } else { AlertUtils.showAlert("Inexistent " + tableName + "!");}
    }

    private void deleteClientOrProduct(String columnName, int id){
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        String deleteStatement = "DELETE FROM orders WHERE " + columnName + " = ?";
        try{
            preparedStatement = connection.prepareStatement(deleteStatement);
            preparedStatement.setInt(1, id);
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated > 0){
                AlertUtils.showMessage("Orders deleted successfully");
            }
        } catch (SQLException e){
            AlertUtils.showAlert("Failed to delete orders. No rows were affected.");
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(preparedStatement);
        }
    }

    public List<T> viewAll() {
        List<T> entities = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String tableName = type.getSimpleName();
        String selectAllQuery = "SELECT * FROM " + tableName;

        try {
            preparedStatement = connection.prepareStatement(selectAllQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T entity = mapResultSetToEntity(resultSet); //to create an object with the data from the result set
                entities.add(entity);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "AbstractDAO:viewAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(resultSet);
        }
        return entities;
    }

    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;
}
