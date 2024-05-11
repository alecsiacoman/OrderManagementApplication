package com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.Validator;

import com.example.pt2024_30423_coman_alecsia_assignment_3.AlertUtils;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Product;

public class ProductValidator implements Validator<Product> {
    @Override
    public void validate(Product product) {
        if(product.getPrice() < 0){
            AlertUtils.showAlert("Positive number for price is required!");
            throw new IllegalArgumentException("Invalid price");
        }

        if(product.getQuantity() < 0){
            AlertUtils.showAlert("Positive number for quantity is required!");
            throw new IllegalArgumentException("Invalid quantity");
        }
    }
}
