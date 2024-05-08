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
    private Button btnBack, btnAdd, btnEdit;
    @FXML
    private TextField txtID, txtName, txtEmail, txtPhone;

    private int id;
    private String name;
    private String email;
    private String phone;

    private void getData() throws IllegalArgumentException {
        if (!txtID.getText().isEmpty()) {
            try {
                this.id = Integer.parseInt(txtID.getText());
            } catch (NumberFormatException e) {
                AlertUtils.showAlert("Incorrect ID! You should introduce a number.");
                throw new IllegalArgumentException("Incorrect ID!");
            }
        }

        if (txtName.getText().isEmpty()) {
            AlertUtils.showAlert("You must introduce a name for the client!");
            throw new IllegalArgumentException("Name is required!");
        } else {
            this.name = txtName.getText();
        }

        if (txtEmail.getText().isEmpty()) {
            AlertUtils.showAlert("You must introduce an email for the client!");
            throw new IllegalArgumentException("Email is required!");
        } else {
            this.email = txtEmail.getText();
        }

        if (txtPhone.getText().isEmpty()) {
            AlertUtils.showAlert("You must introduce a phone number for the client!");
            throw new IllegalArgumentException("Phone number is required!");
        } else {
            this.phone = txtPhone.getText();
        }
    }

    @FXML
    private void handleAddButton() {
        try{getData();}catch (IllegalArgumentException e){e.printStackTrace();}
        Client client = new Client(name, email, phone);
        ClientBLL clientBLL = new ClientBLL();
        clientBLL.insertClient(client);
    }

    @FXML
    private void handleEditButton() {
        getData();
        Client client = new Client(id, name, email, phone);
        ClientBLL clientBLL = new ClientBLL();
        clientBLL.editClient(client);
    }

    @FXML
    private void handleDeleteButton(){
        if (!txtID.getText().isEmpty()) {
            try {
                this.id = Integer.parseInt(txtID.getText());
            } catch (NumberFormatException e) {
                AlertUtils.showAlert("Incorrect ID! You should introduce a number.");
                throw new IllegalArgumentException("Incorrect ID!");
            }
        }

        ClientBLL clientBLL = new ClientBLL();
        clientBLL.deleteClient(id);
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

    }
}
