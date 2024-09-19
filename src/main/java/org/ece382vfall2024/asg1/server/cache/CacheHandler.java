package org.ece382vfall2024.asg1.server.cache;

import lombok.extern.slf4j.Slf4j;
import org.ece382vfall2024.asg1.server.model.Order;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class CacheHandler {

    public ConcurrentHashMap<Integer, Set<Double>> userCache = new ConcurrentHashMap<>();

    public ConcurrentHashMap<Double, Order> orderCache = new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, Integer> inventoryCache = new ConcurrentHashMap<>();


     public  CacheHandler() {
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/Input.txt"));
            System.out.println("Preparing cache for ::");
            while ((line = bufferedReader.readLine())!=null){
                String[] each = line.split("\\s+");
                inventoryCache.put(each[0].trim(), Integer.valueOf(each[1].trim()));
                System.out.println(each[0] + " " + each[1]);
            }
        } catch (IOException e) {
            log.warn("Error", e);
        }
    }
}
