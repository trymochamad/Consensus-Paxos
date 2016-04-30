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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;



public class Client2  {
	/**
	 * Contoh kode program untuk node yang mengirimkan paket. Paket dikirim
	 * menggunakan UnreliableSender untuk mensimulasikan paket yang hilang.
	 */
    
         	
    
	
	public static void main(String args[]) throws Exception
	{
           String msg="" ;
           System.out.println("Client 2");             
           int listenPort = 10877 ;
           int[] targetPort = {0,10876,10878} ;
           System.out.println("Creating listener");
           Listener listener = new Listener("list",listenPort) ;
           listener.start();
           System.out.println("Creating sender");
           Scanner scanner = new Scanner(System.in) ;
           BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
           do {               
                System.out.println("Message : ");
               String sentence = inFromUser.readLine(); 
               System.out.print("Number client : ");
                int c = scanner.nextInt();
                System.out.println("Message : "+sentence+" | port : "+targetPort[c]);
                Sender sender = new Sender("send",msg,targetPort[c],"localhost") ;
                sender.start();
           } while (!msg.contains("quit"));           
           
	}
}
