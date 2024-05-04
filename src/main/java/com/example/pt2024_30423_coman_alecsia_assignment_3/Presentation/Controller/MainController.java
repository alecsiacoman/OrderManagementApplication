package com.example.pt2024_30423_coman_alecsia_assignment_3.Presentation.Controller;

import com.example.pt2024_30423_coman_alecsia_assignment_3.Application;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Presentation.View.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    private final MainView mainView;

    public MainController(MainView mainView){
        this.mainView = mainView;
        setButtons();
    }

    private void setButtons(){
        mainView.getBtnClient().setOnAction(event -> {
                String view = "client-view.fxml";
                switchToAnotherView(event, view);
        });

        mainView.getBtnProduct().setOnAction(event -> {
                String view = "product-view.fxml";
                switchToAnotherView(event, view);
        });

        mainView.getBtnOrder().setOnAction(event -> {
                String view = "order-view.fxml";
                switchToAnotherView(event, view);
        });
    }

    public void switchToAnotherView(ActionEvent event, String view) {
        try {
            Parent root = FXMLLoader.load(Application.class.getResource(view));
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML file: " + view);
        }
    }

}
