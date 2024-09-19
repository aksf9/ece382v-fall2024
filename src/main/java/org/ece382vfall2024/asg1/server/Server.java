package org.ece382vfall2024.asg1.server;

import org.ece382vfall2024.asg1.server.cache.CacheHandler;
import org.ece382vfall2024.asg1.server.handler.ClientHandler;

import java.io.FileNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static double orderCount = 1d;

    public static void main(String ars[]) throws FileNotFoundException {

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
