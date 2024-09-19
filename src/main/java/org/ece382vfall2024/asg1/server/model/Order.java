package org.ece382vfall2024.asg1.server.model;


import java.util.Objects;

public class Order {

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getOrderId() {
        return orderId;
    }

    public void setOrderId(double orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    String type;

    int quantity;

    String status;


    double orderId;

    int userId;

    @Override
    public String toString() {
        return "Order{" +
                "type='" + type + '\'' +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                ", orderId=" + orderId +
                ", userId=" + userId +
                '}';
    }
}
