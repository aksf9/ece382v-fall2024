package org.ece382vfall2024.asg1.server.processor;

import org.ece382vfall2024.asg1.server.Server;
import org.ece382vfall2024.asg1.server.cache.CacheHandler;
import org.ece382vfall2024.asg1.server.model.Order;

import java.util.HashSet;
import java.util.Map;

public class ChoiceProcessor {

    CacheHandler cacheHandler;

    private synchronized int getCount(){
        return Server.orderCount++;
    }
    public ChoiceProcessor( CacheHandler cacheHandler) {
        this.cacheHandler = cacheHandler;
    }
    public String processor(String[] option) {
        if(option.length == 0){
            System.out.println("Not valid option");
            return "Not valid option".concat("\n").concat("end");
        }
        switch (option[0].trim().toLowerCase()) {
            case "purchase" -> {
                Order order = new Order();
                order.setType(option[2]);
                order.setUserName(option[1]);
                order.setQuantity(Integer.valueOf(option[3]));
                order.setOrderId(getCount());
                return purchaseItem(order);
            }
            case "cancel" -> { return cancelOrder(Integer.valueOf(option[1]));}
            case "search" ->{ return  listAllOrder(option[1]);}
            case "list" -> { return availableInventory();}
            default -> {return "Not a valid option".concat("\n").concat("end");}
        }
    }

    private String cancelOrder(int orderId) {
        String message = "";
        if (!this.cacheHandler.orderCache.containsKey(orderId)) {
            message = String.format("%d not found, no such order", orderId);
            message = message.concat("\n").concat("end");
            System.out.println(message );
            return message;
        }
        Order order = this.cacheHandler.orderCache.get(orderId);
        this.cacheHandler.orderCache.remove(orderId);
        this.cacheHandler.userCache.get(order.getUserName()).remove(orderId); //no sync
        if(this.cacheHandler.userCache.get(order.getUserName()).size()==0){
            this.cacheHandler.userCache.remove(order.getUserName());
        }
        this.cacheHandler.inventoryCache.put(order.getType(), this.cacheHandler.inventoryCache.getOrDefault(order.getType(), 0)+order.getQuantity());
        message = String.format("Order %s is canceled", orderId);
        message = message.concat("\n").concat("end");
        System.out.println(message);
        return message;
    }


    private String purchaseItem(Order possibleOrder) {
        String message = null;
        if (!this.cacheHandler.inventoryCache.containsKey(possibleOrder.getType())) {

            message= "Not Available - We do not sell this product.";
            message = message.concat("\n").concat("end");
            return message;
        }


        if (this.cacheHandler.inventoryCache.get(possibleOrder.getType()) < possibleOrder.getQuantity()) {
            message =  " Not Available - Not enough items";
            message = message.concat("\n").concat("end");
            return message;
        }

        this.cacheHandler.inventoryCache.put(possibleOrder.getType(), this.cacheHandler.inventoryCache.get(possibleOrder.getType()) - possibleOrder.getQuantity());
        this.cacheHandler.userCache.computeIfAbsent(possibleOrder.getUserName(), (k) -> new HashSet<>()).add(possibleOrder.getOrderId());
        this.cacheHandler.orderCache.put(possibleOrder.getOrderId(), possibleOrder);
        message = String.format("You order has been placed, %s %s %s %s", possibleOrder.getOrderId(), possibleOrder.getUserName(), possibleOrder.getType(), possibleOrder.getQuantity());
        message = message.concat("\n").concat("end");
        //System.out.println(message);
        return  message;
    }

    // search username

    private String listAllOrder(String userId) {
        final String[] message = {""};
        if (!this.cacheHandler.userCache.containsKey(userId)) {
            message[0] = String.format("No order found for %s", userId);
            message[0] = message[0].concat("\n").concat("end");
            return message[0];
        }

        this.cacheHandler.userCache.get(userId).stream().forEach(x ->  {
            message[0] = message[0].concat(String.format("%s, %s, %s", this.cacheHandler.orderCache.get(x).getOrderId(), this.cacheHandler.orderCache.get(x).getType() , this.cacheHandler.orderCache.get(x).getQuantity()));
            message[0] = message[0].concat("\n");
        });
        message[0] = message[0].concat("end");
        return message[0];
    }

    private String availableInventory() {
        final String[] message = {""};
        this.cacheHandler.inventoryCache.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(y -> {
            message[0] = message[0].concat(String.format("%s  %s", y.getKey(), y.getValue()));
            message[0] = message[0].concat("\n");
        });

        message[0] = message[0].concat("end");
        System.out.println(message[0]);
        return message[0];
    }
}
