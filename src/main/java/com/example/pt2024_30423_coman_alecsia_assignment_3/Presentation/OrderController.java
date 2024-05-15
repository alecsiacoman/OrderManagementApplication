package com.example.pt2024_30423_coman_alecsia_assignment_3.Presentation;

import com.example.pt2024_30423_coman_alecsia_assignment_3.AlertUtils;
import com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.OrderBLL;
import com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.ProductBLL;
import com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess.OrderDAO;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Orders;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Product;
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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderController implements Initializable, TableColumnGenerator {
    @FXML
    private Button btnBack;
    @FXML
    private TextField txtID, txtQuantity;
    @FXML
    private ComboBox<Integer> cmbClient, cmbProduct;
    @FXML
    private TableView<Orders> orderTableView;

    private int id;
    private int client;
    private int product;
    private int quantity;

    private OrderBLL orderBLL;

    private void getData() throws IllegalArgumentException {
        if (!txtID.getText().isEmpty()) {
            try {
                this.id = Integer.parseInt(txtID.getText());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("ID");
            }
        }

        this.client = cmbClient.getValue();
        this.product = cmbProduct.getValue();

        if (txtQuantity.getText().isEmpty()) {
            throw new IllegalArgumentException("quantity");
        } else {
            this.quantity = Integer.parseInt(txtQuantity.getText());
        }
    }

    @FXML
    private void handleAddButton() {
        try{
            getData();
            Orders order = new Orders(client, product, quantity);
            orderBLL.insertOrder(order);
        }catch (IllegalArgumentException e){
            if(e.getMessage().equals("quantity")){
                AlertUtils.showAlert("You must introduce a quantity for the order!");
            } else if (e.getMessage().equals("ID"))
                AlertUtils.showAlert("Incorrect ID! You should introduce a number.");
        }
    }

    @FXML
    private void handleEditButton() {
        try{
            getData();
            Orders orders = new Orders(id, client, product, quantity);
            orderBLL.editOrder(orders);
        }catch (IllegalArgumentException e) {
            if(e.getMessage().equals("quantity")){
                AlertUtils.showAlert("You must introduce a quantity for the order!");
            } else if (e.getMessage().equals("ID"))
                AlertUtils.showAlert("Incorrect ID! You should introduce a number.");
        }
    }

    @FXML
    private void handleDeleteButton(){
        if (!txtID.getText().isEmpty()) {
            try {
                this.id = Integer.parseInt(txtID.getText());
                orderBLL.deleteOrder(id);
            } catch (NumberFormatException e) {
                AlertUtils.showAlert("Incorrect ID! You should introduce a number.");
            }
        } else  AlertUtils.showAlert("You should introduce an ID.");
    }

    @FXML
    private void handleViewAllButton(){
        List<Orders> orders = orderBLL.viewAllOrders();
        ObservableList<Orders> clientList = FXCollections.observableArrayList(orders);
        orderTableView.setItems(clientList);
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderBLL = new OrderBLL();
        generateTableColumns(Orders.class, orderTableView);
        handleViewAllButton();

        List<Integer> clientIds = OrderDAO.getClientIds();
        ObservableList<Integer> clientIdsObservable = FXCollections.observableArrayList(clientIds);
        cmbClient.setItems(clientIdsObservable);

        List<Integer> productIds = OrderDAO.getProductIds();
        ObservableList<Integer> productIdsObservable = FXCollections.observableArrayList(productIds);
        cmbProduct.setItems(productIdsObservable);
    }
}
