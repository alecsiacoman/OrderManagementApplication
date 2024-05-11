package com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic;

import com.example.pt2024_30423_coman_alecsia_assignment_3.AlertUtils;
import com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.Validator.OrderValidator;
import com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess.OrderDAO;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Orders;

import java.util.List;

public class OrderBLL {
    private OrderDAO orderDAO;

    public OrderBLL(){
        orderDAO = new OrderDAO();
    }

    public void insertOrder(Orders order){
        new OrderValidator().validate(order);
        orderDAO.insert(order);
    }

    public void editOrder(Orders order){
        new OrderValidator().validate(order);
        orderDAO.edit(order, "id");
    }

    public void deleteOrder(int id){
        orderDAO.delete(id, "id");
    }

    public List<Orders> viewAllOrders(){
        return orderDAO.viewAll();
    }

}
