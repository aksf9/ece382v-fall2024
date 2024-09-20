package org.ece382vfall2024.asg1.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static String IP = "localhost";
    private static int PORT = 1024;
    Socket clientSocket;

    private PrintWriter out;
    private BufferedReader in;

    DatagramPacket sendPacket;
    byte[] sendData;

    DatagramSocket clientUDPSocket;

    public static void main(String args[]) throws IOException {
        Client client = new Client();
        //printAllOption();
        client.startTCPConnection();
        client.startUDPConnection();
        Scanner input = new Scanner(System.in);
        String read;
        PrintWriter out;
        BufferedReader in;

        /**
         *  Choose mode
         *  ask for input
         */
        System.out.println("Type setmode T or setmode U or quit to end");
        while (!(read = input.nextLine()).equalsIgnoreCase("quit")) {
            String[] setmode = read.split("\\s+");

            if (setmode[1].equals("T")) {
                printAllOption();
                read = input.nextLine();

                client.sendAndReadMessage(read);
            } else if (setmode[1].equals("U")) {
                printAllOption();
                read = input.nextLine();
                client.sendUDPMessage(read);
            }
            System.out.println("Type setmode T or setmode U or quit to end");
        }
        client.tcpCleanup();
        client.udpCleanup();
    }

    private void udpCleanup() {

        if (clientUDPSocket != null) {
            clientUDPSocket.close();
        }

    }

    private void tcpCleanup() {
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


    private void sendAndReadMessage(String message) throws IOException {

        out.println(message);
        //String resp = in.readLine(); // read is blocking call
        //return resp;
    }

    private void sendUDPMessage(String message) throws IOException {
        sendData = message.getBytes();
        sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("127.0.0.1"), 5001);
        clientUDPSocket.send(sendPacket);
    }

    private void startTCPConnection() throws IOException {

        clientSocket = new Socket(IP, PORT);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private void startUDPConnection() throws IOException {
        clientUDPSocket = new DatagramSocket();
        clientUDPSocket.setSoTimeout(1000);
    }


    private static void printAllOption() {
        System.out.println("Welcome, Please choose out of following. Enter done to quit");
        System.out.println("purchase <user-name> <product-name> <quantity>");
        System.out.println("cancel <order-id>");
        System.out.println("search <user-name>");
        System.out.println("list");
    }

}
