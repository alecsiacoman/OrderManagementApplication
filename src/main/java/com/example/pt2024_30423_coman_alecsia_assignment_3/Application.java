package com.example.pt2024_30423_coman_alecsia_assignment_3;

import com.example.pt2024_30423_coman_alecsia_assignment_3.Presentation.Controller.MainController;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Presentation.View.MainView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main-view.fxml"));
        MainView mainView = new MainView();
        fxmlLoader.setController(mainView);
        Scene scene = new Scene(fxmlLoader.load(), 775, 340);
        stage.setTitle("Order Management Application");
        stage.setScene(scene);
        stage.show();

        MainController mainController = new MainController(mainView);
        mainView.setMainController(mainController);
    }

    public static void main(String[] args) {
        launch();
    }
}