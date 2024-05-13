package com.example.pt2024_30423_coman_alecsia_assignment_3.Presentation;

import com.example.pt2024_30423_coman_alecsia_assignment_3.AlertUtils;
import com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.ClientBLL;
import com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess.ClientDAO;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for handling client-related operations in the user interface.
 */
public class ClientController implements Initializable, TableColumnGenerator {
    @FXML
    private Button btnBack;
    @FXML
    private TextField txtID, txtName, txtEmail, txtPhone;
    @FXML
    private TableView<Client> clientTableView;

    private int id;
    private String name;
    private String email;
    private String phone;

    private ClientBLL clientBLL;

    /**
     * Retrieves data from the input fields.
     * @throws IllegalArgumentException If any required field is empty or invalid.
     */
    private void getData() throws IllegalArgumentException {
        if (!txtID.getText().isEmpty()) {
            try {
                this.id = Integer.parseInt(txtID.getText());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("ID");
            }
        }

        if (txtName.getText().isEmpty()) {
            throw new IllegalArgumentException("name");
        } else {
            this.name = txtName.getText();
        }

        if (txtEmail.getText().isEmpty()) {
            throw new IllegalArgumentException("email");
        } else {
            this.email = txtEmail.getText();
        }

        if (txtPhone.getText().isEmpty()) {
            throw new IllegalArgumentException("phone");
        } else {
            this.phone = txtPhone.getText();
        }
    }

    /**
     * Handles the action when the "Add" button is clicked.
     */
    @FXML
    private void handleAddButton() {
        try{ getData();
            Client client = new Client(name, email, phone);
            clientBLL.insertClient(client);
        }catch (IllegalArgumentException e){
            throwMessages(e);
        }
    }

    /**
     * Handles the action when the "Edit" button is clicked.
     */
    @FXML
    private void handleEditButton() {
        try{
            getData();
            Client client = new Client(id, name, email, phone);
            clientBLL.editClient(client);
        } catch (IllegalArgumentException e){
            throwMessages(e);
        }
    }

    private void throwMessages(Exception e){
        if(e.getMessage().equals("name"))
            AlertUtils.showAlert("You must introduce a name for the client!");
        else if (e.getMessage().equals("email"))
            AlertUtils.showAlert("You must introduce an email for the client!");
        else if (e.getMessage().equals("phone"))
            AlertUtils.showAlert("You must introduce a phone number for the client!");
        else if (e.getMessage().equals("ID"))
            AlertUtils.showAlert("Incorrect ID! You should introduce a number.");
    }

    /**
     * Handles the action when the "Delete" button is clicked.
     */
    @FXML
    private void handleDeleteButton(){
        if (!txtID.getText().isEmpty()) {
            try {
                this.id = Integer.parseInt(txtID.getText());
                clientBLL.deleteClient(id);
            } catch (NumberFormatException e) {
                AlertUtils.showAlert("Incorrect ID! You should introduce a number.");
            }
        } else  AlertUtils.showAlert("You should introduce an ID.");
    }

    /**
     * Handles the action when the "View All" button is clicked.
     */
    @FXML
    private void handleViewAllButton(){
        List<Client> clients = clientBLL.viewAllClients();
        ObservableList<Client> clientList = FXCollections.observableArrayList(clients);
        clientTableView.setItems(clientList);
    }

    /**
     * Handles the action when the "Back" button is clicked.
     * @param event The ActionEvent triggered by clicking the button.
     * @throws Exception If an exception occurs while handling the action.
     */
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

    /**
     * Initializes the controller.
     * @param url The location used to resolve relative paths for the root object.
     * @param rb The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        clientBLL = new ClientBLL();
        generateTableColumns(Client.class, clientTableView);
        handleViewAllButton();
    }
}
