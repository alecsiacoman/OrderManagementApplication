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

public class OrderController implements Initializable {
    @FXML
    private Button btnBack;
    @FXML
    private TextField txtID, txtQuantity;
    @FXML
    private ComboBox<Integer> cmbClient, cmbProduct;
    @FXML
    private TableView<Orders> orderTableView;
    @FXML
    private TableColumn<Orders, Integer> idColumn;
    @FXML
    private TableColumn<Orders, Integer> clientColumn;
    @FXML
    private TableColumn<Orders, Integer> productColumn;
    @FXML
    private TableColumn<Orders, Integer> quantityColumn;

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
                AlertUtils.showAlert("Incorrect ID! You should introduce a number.");
                throw new IllegalArgumentException("Incorrect ID!");
            }
        }

        this.client = cmbClient.getValue();
        this.product = cmbProduct.getValue();

        if (txtQuantity.getText().isEmpty()) {
            AlertUtils.showAlert("You must introduce a quantity for the order!");
            throw new IllegalArgumentException("Quantity is required!");
        } else {
            this.quantity = Integer.parseInt(txtQuantity.getText());
        }
    }

    private void manageQuantity(int action){
        ProductBLL productBLL = new ProductBLL();
        Product product1 = productBLL.findProductById(product);
        int currentQuantity = product1.getQuantity();
        Orders orders;

        if(currentQuantity - quantity >= 0){
            if(action == 0) // insert
            {
                orders = new Orders(client, product, quantity);
                orderBLL.insertOrder(orders);
            }
            else //edit
            {
                orders = new Orders(id, client, product, quantity);
                orderBLL.editOrder(orders);
            }
            product1.setQuantity(currentQuantity - quantity);
            productBLL.editProduct(product1);
        }
        else {
            AlertUtils.showAlert("Not enough quantity of chosen product!");
        }
    }

    @FXML
    private void handleAddButton() {
        try{
            getData();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        manageQuantity(0);
    }

    @FXML
    private void handleEditButton() {
        try{
            getData();
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        manageQuantity(1);
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

        orderBLL.deleteOrder(id);
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

        idColumn.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("id"));
        clientColumn.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("clientId"));
        productColumn.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("productId"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("quantity"));

        List<Integer> clientIds = OrderDAO.getClientIds();
        ObservableList<Integer> clientIdsObservable = FXCollections.observableArrayList(clientIds);
        cmbClient.setItems(clientIdsObservable);

        List<Integer> productIds = OrderDAO.getProductIds();
        ObservableList<Integer> productIdsObservable = FXCollections.observableArrayList(productIds);
        cmbProduct.setItems(productIdsObservable);
    }
}
