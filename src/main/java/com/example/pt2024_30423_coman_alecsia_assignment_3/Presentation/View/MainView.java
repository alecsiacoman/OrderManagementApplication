package com.example.pt2024_30423_coman_alecsia_assignment_3.Presentation.View;

import com.example.pt2024_30423_coman_alecsia_assignment_3.Presentation.Controller.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainView {
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public Button getBtnClient() {
        return btnClient;
    }

    public Button getBtnOrder() {
        return btnOrder;
    }

    public Button getBtnProduct() {
        return btnProduct;
    }

    private MainController mainController;
    @FXML
    private Button btnClient;
    @FXML
    private Button btnProduct;
    @FXML
    private Button btnOrder;
}
