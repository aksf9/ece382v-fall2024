package org.ece382vfall2024.asg1.server;

import org.ece382vfall2024.asg1.server.cache.CacheHandler;
import org.ece382vfall2024.asg1.server.handler.TCPClientHandler;
import org.ece382vfall2024.asg1.server.handler.UDPClientHandler;
import org.ece382vfall2024.asg1.server.processor.ChoiceProcessor;

import java.io.FileNotFoundException;
import java.net.*;
import java.sql.Timestamp;

public class Server {

    public static int orderCount = 1;
    public static CacheHandler cacheHandler = new CacheHandler();

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
        //System.out.println("Server Started. Listening for Clients on port 5001" + "...");
        // Assume messages are not over 1024 bytes

            ChoiceProcessor choiceProcessor = new ChoiceProcessor(cacheHandler);
            UDPClientHandler clientHandler = new UDPClientHandler(serverSocket, choiceProcessor);
            new Thread(clientHandler).start();

    }
    private static void tcpServer() {
        ServerSocket server = null;

        while(true) {
            try {
                server = new ServerSocket(1024);
                server.setReuseAddress(true);

                while (true) {
                    Socket client = server.accept();
                    /*System.out.println("New client connected"
                            + client.getInetAddress()
                            .getHostAddress());*/
                    ChoiceProcessor choiceProcessor = new ChoiceProcessor(cacheHandler);
                    TCPClientHandler TCPClientHandler = new TCPClientHandler(client, choiceProcessor);
                    new Thread(TCPClientHandler).start();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
