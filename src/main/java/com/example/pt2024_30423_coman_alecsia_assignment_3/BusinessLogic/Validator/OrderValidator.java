package com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.Validator;

import com.example.pt2024_30423_coman_alecsia_assignment_3.AlertUtils;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Orders;

public class OrderValidator implements Validator<Orders> {

    @Override
    public void validate(Orders orders) {
        if(orders.getQuantity() < 0){
            AlertUtils.showAlert("Positive number for quantity is required!");
            throw new IllegalArgumentException("Invalid quantity");
        }
    }
}
