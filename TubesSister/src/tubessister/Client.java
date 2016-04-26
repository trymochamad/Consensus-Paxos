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
import org.json.simple.JSONObject;
import tubessister.UnreliableSender;


public class Client {
	/**
	 * Contoh kode program untuk node yang mengirimkan paket. Paket dikirim
	 * menggunakan UnreliableSender untuk mensimulasikan paket yang hilang.
	 */
	public static void main(String args[]) throws Exception
	{
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

		String targetAddress = "localhost";
		InetAddress IPAddress = InetAddress.getByName(targetAddress);
		int targetPort = 9876;

		DatagramSocket datagramSocket = new DatagramSocket();
		UnreliableSender unreliableSender = new UnreliableSender(datagramSocket);

		while (true)
		{
                    System.out.println("Enter message: ");
                    String sentence = inFromUser.readLine();
                    String message = "";
                    if (sentence.equals("quit"))
                    {
                        break;
                    } else {
                        message = joinRequest(sentence);
                    }

                    byte[] sendData = message.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, targetPort);
                    unreliableSender.send(sendPacket);
		}
		datagramSocket.close();
	}
        
        public static String joinRequest(String username) {
            JSONObject obj = new JSONObject() ;
            obj.put("method", "join");
            obj.put("username",username);
            return  obj.toString() ;
        }
        
        public static String leaveRequest() {
            JSONObject obj = new JSONObject() ;
            obj.put("method","leave");
            return obj.toString();
        }
        
        public static String readyRequest() {
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
        
        public static String ClientAcceptRequest(int kpuId) {
            JSONObject obj = new JSONObject() ;
            obj.put("method","prepare_proposal");
            obj.put("kpu_id",kpuId);
            obj.put("Description","Kpu is selected");
            return obj.toString();
        }
        
        public static String killWereWolfVote (int playerID) {
            JSONObject obj = new JSONObject();
            obj.put("method", "vote_werewolf");
            obj.put("player_id", playerID);
            return obj.toString();  
        }
        
        public static String infoWereWolfKilled (int status, int playerKilled){
            JSONObject obj = new JSONObject();
            obj.put("method", "vote_result_werewolf");
            obj.put("vote_status", status);
            obj.put("player_killed", playerKilled);
            //obj.put("vote_resul", playerKilled);
            return obj.toString();  
        }
        
        public static String killCivilianVote (int playerID){
            JSONObject obj = new JSONObject();
            obj.put("method", "vote_civilian");
            obj.put("player_id", playerID);
            return obj.toString();
        }
        
        //11
        // cara bedain status = 1 atau = -1 gimana yah :( 
        public static String infoCivilianKilled (int status, int playerKilled){
            JSONObject obj = new JSONObject(); 
            obj.put("method", "vote_result_civilian");
            obj.put("vote_status", status);
            obj.put("player_killed", playerKilled);
            //obj.put("vote_result", "werewolf");
            return obj.toString();
        }
        
        
        //12
        public static String startGame (){
            JSONObject obj = new JSONObject(); 
            obj.put("method", "start");
            obj.put("time", "day");
            obj.put("role", "werewolf");
            //obj.put("friend", "werewolf");
            obj.put("description", "game is started");
            return obj.toString();
        }
        
        //13
        public static String changePhase (String narration, int days){
            JSONObject obj = new JSONObject(); 
            obj.put("method", "change_phase");
            obj.put("time", "day");
            obj.put("days", days);
            obj.put("description", narration);
            return obj.toString();
        }
        
        //14 
        public static String gameOver (String narration){
            JSONObject obj = new JSONObject(); 
            obj.put("method", "game_over");
            obj.put("winner", "werewolf");
            obj.put("description", narration);
            return obj.toString();
        }
                
}
