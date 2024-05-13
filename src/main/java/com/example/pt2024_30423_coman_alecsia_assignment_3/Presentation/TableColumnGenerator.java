package com.example.pt2024_30423_coman_alecsia_assignment_3.Presentation;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Field;

public interface TableColumnGenerator {
//    default <T> void generateTableColumns(Class<T> c, TableView<T> tableView) {
//        Field[] fields = c.getDeclaredFields();
//
//        for (Field field : fields) {
//            if (field.getType() == int.class) {
//                TableColumn<T, Integer> column = new TableColumn<>(field.getName());
//                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
//                tableView.getColumns().add(column);
//                column.setMinWidth(80);
//            } else if (field.getType() == String.class) {
//                TableColumn<T, String> column = new TableColumn<>(field.getName());
//                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
//                tableView.getColumns().add(column);
//                column.setMinWidth(80);
//            } else if (field.getType() == double.class) {
//                TableColumn<T, Double> column = new TableColumn<>(field.getName());
//                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
//                tableView.getColumns().add(column);
//                column.setMinWidth(80);
//            }
//
//        }
//    }

    default <T> void generateTableColumns(Class<T> c, TableView<T> tableView) {
        Field[] fields = c.getDeclaredFields();

        for (Field field : fields) {
            TableColumn<T, Object> column = new TableColumn<>(field.getName());
            String propertyName = field.getName();
            column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
            tableView.getColumns().add(column);
            column.setMinWidth(80);
        }
    }
}

