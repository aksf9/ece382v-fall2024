package org.ece382vfall2024.asg1.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static String IP = "localhost";
    private static int PORT = 1024;
      Socket clientSocket;

     private PrintWriter out;
     private BufferedReader in;

    public static void main(String args[]) throws IOException {
        Client client = new Client();
        //printAllOption();
        client.startConnection();
        Scanner input = new Scanner(System.in);
        String read;
        PrintWriter out;
        BufferedReader in;
        while(!(read = input.nextLine()).isEmpty())
        {
            client.sendAndReadMessage(read);
        }
        client.cleanup();
        }

    private  void cleanup() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private  void sendAndReadMessage(String message) throws IOException {

            out.println(message);
            //String resp = in.readLine(); // read is blocking call
            //return resp;
    }

    private  void startConnection() throws IOException {
        clientSocket = new Socket(IP, PORT);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }


    private static void printAllOption(){
        System.out.println("Welcome, Please choose out of following. Enter done to quit");
        System.out.println("1 for setmode");
        System.out.println("2 for purchasing an available item");
        System.out.println("3 for cancelling order");
        System.out.println("4 for search order for an user");
        System.out.println("5 for list all available items");
        System.out.println("6 Test server with tcp");
        System.out.println("7 Return to choices");
    }

}
