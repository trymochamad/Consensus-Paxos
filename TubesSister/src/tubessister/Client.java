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
			String sentence = inFromUser.readLine();
			if (sentence.equals("quit"))
			{
				break;
			}

			byte[] sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, targetPort);
			unreliableSender.send(sendPacket);
		}
		datagramSocket.close();
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
