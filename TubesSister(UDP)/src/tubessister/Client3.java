/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tubessister;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import org.json.simple.JSONObject;
import tubessister.UnreliableSender;


public class Client3 {
	/**
	 * Contoh kode program untuk node yang mengirimkan paket. Paket dikirim
	 * menggunakan UnreliableSender untuk mensimulasikan paket yang hilang.
	 */
        
	public static void main(String args[]) throws Exception
	{
           String msg="" ;
           System.out.println("Client 3");             
           int listenPort = 10878 ;
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
                Sender sender = new Sender("send",sentence,targetPort[c],"localhost") ;
                sender.start();
           } while (!msg.contains("quit"));           
           
	}
        
        public String joinRequest(String username) {
            JSONObject obj = new JSONObject() ;
            obj.put("method", "join");
            obj.put("username",username);
            return  obj.toString() ;
        }
        
        public String leaveRequest() {
            JSONObject obj = new JSONObject() ;
            obj.put("method","leave");
            return obj.toString();
        }
        
        public String readyRequest() {
            JSONObject obj = new JSONObject() ;
            obj.put("method","ready");
            return obj.toString();
        }
        
        public String listClientRequest() {
            JSONObject obj = new JSONObject() ;
            obj.put("method","client_adress");
            return obj.toString();
        }
        /*
        public String PaxosPrepareRequest(int proposalId, int playerID) {
            JSONObject obj = new JSONObject() ;
            obj.put("method", "prepare_proposal");
            String pid = "("+Integer.toString(proposalId)+","+Integer.toString(playerID)+")";
            obj.put("proposal_id", pid);
            return obj.toString();
        }  */
        
       /* public String paxosAcceptRequest(int proposalId,int playerID,int kpuId) {
            JSONObject obj = new JSONObject() ;
            obj.put("method","accept_proposal");
            String pid = "("+Integer.toString(proposalId)+","+Integer.toString(playerID)+")";
            obj.put("proposal_id", pid);
            obj.put("kpu_id",kpuId);
            return obj.toString();
        } */
        
        public String ClientAcceptRequest(int kpuId) {
            JSONObject obj = new JSONObject() ;
            obj.put("method","prepare_proposal");
            obj.put("kpu_id",kpuId);
            obj.put("Description","Kpu is selected");
            return obj.toString();
        }
}
