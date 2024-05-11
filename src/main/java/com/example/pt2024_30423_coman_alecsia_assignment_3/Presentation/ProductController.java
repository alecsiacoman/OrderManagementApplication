package com.example.pt2024_30423_coman_alecsia_assignment_3.Presentation;

import com.example.pt2024_30423_coman_alecsia_assignment_3.AlertUtils;
import com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.ClientBLL;
import com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.ProductBLL;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Client;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController implements Initializable, TableColumnGenerator {
    @FXML
    private Button btnBack;
    @FXML
    private TextField txtID, txtName, txtPrice, txtQuantity;
    @FXML
    private TableView<Product> productTableView;

    private int id;
    private String name;
    private double price;
    private int quantity;

    private ProductBLL productBLL;

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
            AlertUtils.showAlert("You must introduce a name for the product!");
            throw new IllegalArgumentException("Name is required!");
        } else {
            this.name = txtName.getText();
        }

        if (txtPrice.getText().isEmpty()) {
            AlertUtils.showAlert("You must introduce a price for the product!");
            throw new IllegalArgumentException("Price is required!");
        } else {
            this.price = Double.parseDouble(txtPrice.getText());
        }

        if (txtQuantity.getText().isEmpty()) {
            AlertUtils.showAlert("You must introduce a quantity for the product!");
            throw new IllegalArgumentException("Quantity is required!");
        } else {
            this.quantity = Integer.parseInt(txtQuantity.getText());
        }
    }

    @FXML
    private void handleAddButton() {
        try{getData();}catch (IllegalArgumentException e){e.printStackTrace();}
        Product product = new Product(name, price, quantity);
        productBLL.insertProduct(product);
    }

    @FXML
    private void handleEditButton() {
        getData();
        Product product = new Product(id, name, price, quantity);
        productBLL.editProduct(product);
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

        productBLL.deleteProduct(id);
    }

    @FXML
    private void handleViewAllButton(){
        List<Product> products = productBLL.viewAllProducts();
        ObservableList<Product> productList = FXCollections.observableArrayList(products);
        productTableView.setItems(productList);
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
        productBLL = new ProductBLL();
        generateTableColumns(Product.class, productTableView);
        handleViewAllButton();
    }
}
