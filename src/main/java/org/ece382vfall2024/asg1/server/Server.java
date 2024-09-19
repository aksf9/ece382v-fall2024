package org.ece382vfall2024.asg1.server;

import org.ece382vfall2024.asg1.server.cache.CacheHandler;
import org.ece382vfall2024.asg1.server.handler.ClientHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.sql.Timestamp;

public class Server {

    public static double orderCount = 1d;

    public static void main(String ars[]) throws FileNotFoundException {

        new Thread(() -> tcpServer()).start();
        new Thread(() -> udpServer()).start();
    }

    private static void udpServer()  {
        DatagramSocket serverSocket = null;
        try {
             serverSocket = new DatagramSocket(Integer.parseInt("5001"));
        }
        catch (Exception e){
            System.out.println(e);
        }
        System.out.println("Server Started. Listening for Clients on port 5001" + "...");
        // Assume messages are not over 1024 bytes
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket;
        while (true) {
            // Server waiting for clients message
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocket.receive(receivePacket);
            } catch (Exception e) {
                System.out.println(e);
            }
            // Get the client's IP address and port
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            // Convert Byte Data to String
            String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            // Print the message with log header
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            System.out.println("[" + timestamp.toString() + " ,IP: " + IPAddress + " ,Port: " + port + "]  " + clientMessage);
        }
    }
    private static void tcpServer() {
        CacheHandler cacheHandler = new CacheHandler();
        ServerSocket server = null;

        while(true) {
            try {
                server = new ServerSocket(1024);
                server.setReuseAddress(true);

                while (true) {
                    Socket client = server.accept();
                    System.out.println("New client connected"
                            + client.getInetAddress()
                            .getHostAddress());
                    ClientHandler clientHandler = new ClientHandler(client, cacheHandler);
                    new Thread(clientHandler).start();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
