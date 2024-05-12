package com.example.pt2024_30423_coman_alecsia_assignment_3.Presentation;

import com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.ClientBLL;
import com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.OrderBLL;
import com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.ProductBLL;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Bill;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button btnClient, btnProduct, btnOrder, btnGetBill;

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception{
        Stage stage;
        Parent root;
        if(event.getSource() == btnClient){
            stage = (Stage) btnClient.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/com/example/pt2024_30423_coman_alecsia_assignment_3/client-view.fxml"));
        }
        else{
            if(event.getSource() == btnProduct){
                stage = (Stage) btnProduct.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("/com/example/pt2024_30423_coman_alecsia_assignment_3/product-view.fxml"));
            }
            else if (event.getSource() == btnOrder){
                stage = (Stage) btnOrder.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("/com/example/pt2024_30423_coman_alecsia_assignment_3/order-view.fxml"));
            } else {
                Bill bill = new Bill(new ClientBLL(), new ProductBLL(), new OrderBLL());
                bill.makeBill();
                stage = (Stage) btnGetBill.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("/com/example/pt2024_30423_coman_alecsia_assignment_3/main-view.fxml"));
            }
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
