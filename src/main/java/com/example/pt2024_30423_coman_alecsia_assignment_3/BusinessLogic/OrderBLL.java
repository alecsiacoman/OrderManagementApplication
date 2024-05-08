package com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic;

import com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess.OrderDAO;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Orders;

import java.util.List;

public class OrderBLL {
    //private List<Validator<Order>> validators;
    private OrderDAO orderDAO;

    public OrderBLL(){
//        validators = new ArrayList<Validator<Order>>();
//        validators.add(new EmailValidator());
//        validators.add(new PhoneValidator());

        orderDAO = new OrderDAO();
    }

    public void insertOrder(Orders order){
//        for(Validator<Order> item : validators)
//            item.validate(client);
        orderDAO.insert(order);
    }

    public void editOrder(Orders order){
//        for(Validator<Order> item : validators)
//            item.validate(client);
        orderDAO.edit(order, "id");
    }

    public void deleteOrder(int id){
        orderDAO.delete(id, "id");
    }

    public List<Orders> viewAllOrders(){
        return orderDAO.viewAll();
    }

}
