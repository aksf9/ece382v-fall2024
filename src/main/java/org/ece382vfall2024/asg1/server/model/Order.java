package org.ece382vfall2024.asg1.server.model;


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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    String type;

    int quantity;

    String status;


    double orderId;

    String userName;

    @Override
    public String toString() {
        return "Order{" +
                "type='" + type + '\'' +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                ", orderId=" + orderId +
                ", userId=" + userName +
                '}';
    }
}
