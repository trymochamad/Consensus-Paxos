/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubessister;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
/**
 *
 * @author tama
 */


public class Listener implements Runnable {
   private Thread t;
   private String threadName;
   private int listenPort ;
      
   Listener( String name, int listenPort_){
       threadName = name;
       listenPort = listenPort_ ;
       System.out.println("Creating " +  threadName );
   }
   public void run() {
     System.out.println("Listener running . . ");
       try {
        DatagramSocket serverSocket = new DatagramSocket(listenPort);

        byte[] receiveData = new byte[1024];
        while(true)
        {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String address = receivePacket.getAddress().getHostAddress();
            int port = receivePacket.getPort();
            String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("RECEIVED: " + sentence);
            System.out.println("Address sender : "+address);
            System.out.println("Port sender : "+port);
            if (sentence.contains("quit")) break ;
        }
       } catch (SocketException ex) {
           System.out.println("Socket Exception");
       } catch (IOException ex) {
           System.out.println("IOException");
       }
   }
   
   public void start ()
   {
      System.out.println("Listener Start ");
      if (t == null)
      {
         t = new Thread (this, threadName);
         t.start ();
      }
   }

}

