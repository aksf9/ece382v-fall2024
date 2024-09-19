package org.ece382vfall2024.asg1.server.model;

public class Product {

    public Product(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }
    String name;

    int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
