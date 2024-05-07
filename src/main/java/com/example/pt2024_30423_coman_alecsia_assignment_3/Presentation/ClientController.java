package com.example.pt2024_30423_coman_alecsia_assignment_3.Presentation;

import com.example.pt2024_30423_coman_alecsia_assignment_3.AlertUtils;
import com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.ClientBLL;
import com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess.ClientDAO;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private Button btnBack, btnAdd;
    @FXML
    private TextField txtID, txtName, txtEmail, txtPhone;

    private int id;
    private String name;
    private String email;
    private String phone;

    private void getData(){
        if(!txtID.getText().isEmpty())
            try{
                id = Integer.parseInt(txtID.getText());
            }catch (NumberFormatException e){
                AlertUtils.showAlert("Incorrect ID! You should introduce a number.");
            }
        if(!txtName.getText().isEmpty())
            name = txtName.getText();
        else{
            AlertUtils.showAlert("You must introduce a name for the client!");
            return;
        }
        if(!txtEmail.getText().isEmpty())
            email = txtEmail.getText();
        else {
            AlertUtils.showAlert("You must introduce an email for the client!");
            return;
        }
        if(!txtPhone.getText().isEmpty())
            phone = txtPhone.getText();
        else
            AlertUtils.showAlert("You must introduce a phone number for the client!");
    }


    @FXML
    private void handleAddButton() {
        getData();
        Client client = new Client(name, email, phone);
        ClientBLL clientBLL = new ClientBLL();
        clientBLL.insertClient(client);
    }

    @FXML
    private void handleBackAction(ActionEvent event) throws Exception{
        Stage stage;
        Parent root;
        if(event.getSource() == btnBack){
            stage = (Stage) btnBack.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/com/example/pt2024_30423_coman_alecsia_assignment_3/main-view.fxml"));
        }
        else{
            return;
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
//        getData();
    }
}
