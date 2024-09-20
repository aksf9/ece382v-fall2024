package org.ece382vfall2024.asg1.server.handler;

import org.ece382vfall2024.asg1.server.processor.ChoiceProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Timestamp;

public class UDPClientHandler implements Runnable{

    DatagramSocket client;
    ChoiceProcessor choiceProcessor;

    public UDPClientHandler( DatagramSocket datagramSocket, ChoiceProcessor choiceProcessor){
        this.client = datagramSocket;
        this.choiceProcessor = choiceProcessor;
    }
    @Override
    public void run() {

        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket;
        while (true) {
            // Server waiting for clients message
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                client.receive(receivePacket);
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
            String[] choice = clientMessage.toString().split("\\s+");
            //System.out.println(choice[0]);
            choiceProcessor.processor(choice);
        }
    }
}
