/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paxos;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONObject;

public class Receiver
{
	/**
	 * Contoh kode program untuk node yang menerima paket. Idealnya dalam paxos
	 * balasan juga dikirim melalui UnreliableSender.
	 */
	public static void main(String args[]) throws Exception
	{
            System.out.println("STARTED:");
            int listenPort = 9876;
            /*DatagramSocket serverSocket = new DatagramSocket(listenPort);

            byte[] receiveData = new byte[1024];
            while(true)
            {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    serverSocket.receive(receivePacket);

                    String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    JSONObject receivedMessage = new JSONObject(sentence);
                    System.out.println("RECEIVED: " + receivedMessage.optString("method"));
            }*/
            try{
                
                ServerSocket serverSocket = new ServerSocket(listenPort);
                System.out.println("Server connected\n");
                
                while(true){
                    Socket server = serverSocket.accept();
                    DataInputStream in =
                            new DataInputStream(server.getInputStream());
                      System.out.println(in.readUTF());
                      DataOutputStream out =
                           new DataOutputStream(server.getOutputStream());
                      out.writeUTF("Thank you for connecting to "
                        + server.getLocalSocketAddress() + "\nGoodbye!");
                    
                    
                }
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("It didn't work");
            }
	}
}
