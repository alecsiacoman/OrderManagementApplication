package com.example.pt2024_30423_coman_alecsia_assignment_3.Model;

import java.time.LocalDateTime;

public record Bill(int orderId, double totalPrice, LocalDateTime createdAt) {
    public Bill(int orderId, double totalPrice) {
        this(orderId, totalPrice, LocalDateTime.now());
    }
}
