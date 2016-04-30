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

public class Sender implements Runnable
{
   private Thread t;
   private String threadName;
   private int targetPort ;
   private String message ;
   private String targetAddress ;
   
   Sender( String name,String message_,int targetPort_,String targetAddress_){
       targetPort = targetPort_ ;
       message = message_ ;
       targetAddress = targetAddress_ ;       
       threadName = name;
       System.out.println("Sender created" );
   }
   
   public void run() {
      System.out.println("Sender Running " );
       try {
           InetAddress IPAddress = InetAddress.getByName(targetAddress);          
           DatagramSocket datagramSocket = new DatagramSocket();
           UnreliableSender unreliableSender = new UnreliableSender(datagramSocket);
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, targetPort);
            unreliableSender.send(sendPacket);
            datagramSocket.close();
       } catch (UnknownHostException ex) {
           System.out.println("Unknown Host Exception");
       } catch (SocketException ex) {
           System.out.println("Socket  Exception");
       } catch (IOException ex) {
           System.out.println("IO Exception");
       }
       System.out.println("Send finished");
   }
   
   public void start ()
   {
     
         t = new Thread (this, threadName);
         t.start ();
      
   }

}