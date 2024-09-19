package org.ece382vfall2024.asg1.server.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    int userId;

    List<Order> orderList = new ArrayList<>();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrder(Order order) {
        this.orderList.add(order);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", orderList=" + orderList +
                '}';
    }
}
