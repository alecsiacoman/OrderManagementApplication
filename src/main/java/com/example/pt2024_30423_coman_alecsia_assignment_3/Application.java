package com.example.pt2024_30423_coman_alecsia_assignment_3;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(root, 775, 340);
        stage.setScene(scene);
        stage.setTitle("Order Management Application");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}