package com.example.pt2024_30423_coman_alecsia_assignment_3;

import javafx.scene.control.Alert;

public class AlertUtils {
    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Action succeeded!");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
