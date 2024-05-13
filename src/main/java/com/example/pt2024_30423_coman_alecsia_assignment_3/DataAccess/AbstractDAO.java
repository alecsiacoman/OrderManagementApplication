package com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess;

import com.example.pt2024_30423_coman_alecsia_assignment_3.AlertUtils;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Connection.ConnectionFactory;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Client;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Product;

import java.lang.reflect.Constructor;
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

/**
 * Abstract Data Access Object (DAO) class for handling CRUD operations on entities.
 * @param <T> Type of the entity this DAO operates on.
 */
public abstract class AbstractDAO<T> {
    private final Class<T> type;

    /**
     * Constructor to initialize the type of the entity class.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO(){
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Creates a select query based on a specified field.
     * @param field The field to filter the query on.
     * @return The generated select query.
     */
    private String createSelectQuery(String field){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + "=?");
        return sb.toString();
    }

    /**
     * Retrieves an entity by its ID.
     * @param id The ID of the entity.
     * @param idColumnName The name of the ID column in the database.
     * @return The retrieved entity, or null if not found.
     */
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
           AlertUtils.showAlert("Couldn't find the " + type.getSimpleName());
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(resultSet);
        }
        return entity;
    }

    /**
     * Creates an insert query for the entity based on its fields.
     * @param fields The fields of the entity.
     * @return The generated insert query.
     */
    private String createInsertQuery(Field[] fields){
        StringBuilder insertQuery = new StringBuilder();
        insertQuery.append("INSERT INTO ").append(type.getSimpleName()).append(" (");
        StringBuilder values = new StringBuilder();
        values.append(") VALUES (");
        for(Field field : fields){
            field.setAccessible(true);
            String fieldName = field.getName();
            insertQuery.append(fieldName).append(",");
            values.append("?,");
        }
        return insertQuery.deleteCharAt(insertQuery.length() - 1).append(values.deleteCharAt(values.length() - 1)).append(")").toString();
    }

    /**
     * Inserts a new entity into the database.
     * @param entity The entity to be inserted.
     */
    public void insert(T entity){
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        String tableName = type.getSimpleName();

        try{
            Field[] fields = type.getDeclaredFields();
            preparedStatement = connection.prepareStatement(createInsertQuery(fields));
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

    /**
     * Creates an update query for the entity based on its fields.
     * @param fields The fields of the entity.
     * @return The generated update query.
     */
    private String createEditQuery(Field[] fields){
        StringBuilder updateQuery = new StringBuilder("UPDATE ").append(type.getSimpleName()).append(" SET ");
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            if (!fieldName.equals("id")) {
                updateQuery.append(fieldName).append(" = ?,");
            }
        }
        return updateQuery.deleteCharAt(updateQuery.length() - 1).append(" WHERE ").append("id").append(" = ?").toString();
    }

    /**
     * Edits an existing entity in the database.
     * @param entity The entity with updated data.
     * @param idColumnName The name of the ID column in the database.
     */
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
            Field[] fields = type.getDeclaredFields();

            preparedStatement = connection.prepareStatement(createEditQuery(fields));

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
                AlertUtils.showMessage(tableName + " updated successfully");
            } else {
                 AlertUtils.showAlert("Failed to update " + tableName + ". No rows were affected.");
            }
        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
                AlertUtils.showAlert("Failed to update " + tableName + ". No rows were affected.");
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(preparedStatement);
        }
    }

    /**
     * Deletes an entity from the database by its ID.
     * @param id The ID of the entity to delete.
     * @param idColumnName The name of the ID column in the database.
     */
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
                    AlertUtils.showMessage(tableName + " deleted successfully");
                } else {
                    AlertUtils.showAlert("Failed to delete " + tableName + ". No rows were affected.");
                }

            } catch (SQLException e) {
                AlertUtils.showAlert("Failed to delete " + tableName + ". No rows were affected.");
            } finally {
                ConnectionFactory.close(connection);
                ConnectionFactory.close(preparedStatement);
            }
        } else { AlertUtils.showAlert("Inexistent " + tableName + "!");}
    }

    /**
     * Deletes related records when deleting a client or product.
     * @param columnName The name of the column related to client or product.
     * @param id The ID of the client or product.
     */
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

    /**
     * Retrieves all entities from the database.
     * @return A list of all entities.
     */
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
            AlertUtils.showAlert("Failed to import " + tableName + ". No rows were affected.");
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(resultSet);
        }
        return entities;
    }

    /**
     * Maps a result set to an entity object.
     * @param resultSet The result set from a database query.
     * @return The mapped entity object.
     * @throws SQLException If an SQL exception occurs.
     */
   protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;
}
