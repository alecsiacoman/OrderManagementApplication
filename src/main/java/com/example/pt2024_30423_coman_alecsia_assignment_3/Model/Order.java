package com.example.pt2024_30423_coman_alecsia_assignment_3.Model;

public class Order {
    private int id;
    private int clientId;
    private int productId;
    private int quantity;

    public Order(int clientId, int productId, int quantity){
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
    }

    @Override
    public String toString(){
        return "Order [id=" + id + ", Client id=" + clientId + ", Product id=" + productId + ", quantity=" + quantity + "]";
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
