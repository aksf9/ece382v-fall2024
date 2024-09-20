package org.ece382vfall2024.asg1.server.processor;

import org.ece382vfall2024.asg1.server.Server;
import org.ece382vfall2024.asg1.server.cache.CacheHandler;
import org.ece382vfall2024.asg1.server.model.Order;

import java.net.Socket;
import java.util.HashSet;
import java.util.Map;

public class ChoiceProcessor {

    CacheHandler cacheHandler;

    private synchronized double getCount(){
        return Server.orderCount++;
    }
    public ChoiceProcessor( CacheHandler cacheHandler) {
        this.cacheHandler = cacheHandler;
    }
    public void processor(String[] option) {
        if(option.length == 0){
            System.out.println("Not valid option");
            return;
        }
        switch (option[0].trim().toLowerCase()) {
            case "purchase":
                Order order = new Order();
                order.setType(option[2]);
                order.setUserId(Integer.valueOf(option[1]));
                order.setQuantity(Integer.valueOf(option[3]));
                order.setOrderId(getCount());
                purchaseItem(order);
                break;
            case "cancel":
                cancelOrder(Double.valueOf(option[1]));
                break;
            case "search":
                listAllOrder(Integer.valueOf(option[1]));
                break;
            case "list":
                availableInventory();
                break;
            default:
                System.out.println("Not valid option");
        }
    }

    private void cancelOrder(double orderId) {
        if (!this.cacheHandler.orderCache.containsKey(orderId)) {
            System.out.println( String.format("%d not found, no such order", orderId));
            return;
        }
        Order order = this.cacheHandler.orderCache.get(orderId);
        this.cacheHandler.orderCache.remove(orderId);
        this.cacheHandler.userCache.get(order.getUserId()).remove(orderId); //no sync
        this.cacheHandler.inventoryCache.put(order.getType(), this.cacheHandler.inventoryCache.getOrDefault(order.getType(), 0)+order.getQuantity());
        System.out.println( String.format("Order %s is canceled", orderId));
    }


    private void purchaseItem(Order possibleOrder) {

        if (!this.cacheHandler.inventoryCache.containsKey(possibleOrder.getType())) {
            System.out.println( " Not Available - We do not sell this product.");
            return;
        }


        if (this.cacheHandler.inventoryCache.get(possibleOrder.getType()) < possibleOrder.getQuantity()) {
            System.out.println( " Not Available - Not enough items");
            return;
        }

        this.cacheHandler.inventoryCache.put(possibleOrder.getType(), this.cacheHandler.inventoryCache.get(possibleOrder.getType()) - possibleOrder.getQuantity());
        this.cacheHandler.userCache.computeIfAbsent(possibleOrder.getUserId(), (k) -> new HashSet<>()).add(possibleOrder.getOrderId());
        this.cacheHandler.orderCache.put(possibleOrder.getOrderId(), possibleOrder);
        System.out.println((String.format("You order has been placed, %s %s %s %s", possibleOrder.getOrderId(), possibleOrder.getUserId(), possibleOrder.getType(), possibleOrder.getQuantity())));

    }

    // search username

    private void listAllOrder(int userId) {

        if (!this.cacheHandler.userCache.containsKey(userId)) {
            System.out.println((String.format("No order found for userId", userId)));
            return;
        }
        this.cacheHandler.userCache.get(userId).stream().forEach(x ->  System.out.println( String.format("Order id=%s, Product name=%s, Quantity=%s", this.cacheHandler.orderCache.get(x).getOrderId(), this.cacheHandler.orderCache.get(x).getType() , this.cacheHandler.orderCache.get(x).getQuantity())));
    }

    private void availableInventory() {
        this.cacheHandler.inventoryCache.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(y -> System.out.println( String.format("Product name =%s , Quantity=%s",y.getKey(), y.getValue()) ));
    }
}
