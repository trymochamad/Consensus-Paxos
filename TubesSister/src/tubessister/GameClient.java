/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubessister;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GameClient {

    public static class Player{
        int player_id;
        String username;
        String address;
        int port;
        int is_alive;
    }
    
    public static void main(String args[]) throws IOException {
        
        InetAddress address=InetAddress.getLocalHost();
        Socket s1 = null;
        String line=  null;
        BufferedReader br = null;
        BufferedReader is = null;
        PrintWriter os = null;
        JSONObject jsonResponse = null;
        String myName = null;
        String role = null;
        int myId = -1 ;
        String time = null;
        ArrayList<String> friends = new ArrayList<String>();
        ArrayList<Player> listPlayer = new ArrayList<Player>();
        int cur_day =0;
        String cur_phase ="" ;
        int proposal_number = 0 ;
        int original_size ;
        
        try {
            s1=new Socket(address, 9876);
            br= new BufferedReader(new InputStreamReader(System.in));
            is=new BufferedReader(new InputStreamReader(s1.getInputStream()));
            os= new PrintWriter(s1.getOutputStream());
        }
        catch (IOException e){
            e.printStackTrace();
            System.err.print("IO Exception");
        }

        System.out.println("Client Address : "+address);
        System.out.println("Enter Data to echo Server ( Enter QUIT to end):");

        String response=null;
        try{
            
            boolean isJoinGame = false;
            while(!isJoinGame){
                System.out.print("Enter username: ");
                line = br.readLine(); //Read username first
                myName = line ;
                System.out.println("Enter address : ");
                String address_ = br.readLine() ;
                System.out.print("Enter port : ");
                int port_ = Integer.valueOf(br.readLine()) ;
                //Send joinGame request to server
                os.println(ClientRequest.joinRequest(line,address_,port_).toString());
                os.flush(); //Send the message to server

                System.out.println("Waiting...");

                response = is.readLine(); //Read response from server about join game
                jsonResponse = new JSONObject(response);
                String status = jsonResponse.optString("status");
                if(status.equals("ok")){
                    myName = line;
                    isJoinGame = true;
                } else if(status.equals("fail")) {
                    System.out.println(jsonResponse.optString("description"));
                } else if(status.equals("error")) {
                    System.out.println(jsonResponse.optString("description"));
                }
            } //End while !isJoinGame
            System.out.println("Join game passed. Ready will be sent");
            boolean isReady = false;
            while(!isReady){
                //Send ready message to server
                os.println(ClientRequest.readyUp());
                os.flush(); //Send the message to server
                
                response = is.readLine(); //Read response from server about readyup game
                jsonResponse = new JSONObject(response);
                String status = jsonResponse.optString("status");
                if(status.equals("ok")){
                    isReady = true;
                    System.out.println("isReady changed to true");
                } else { //can't play, quit
                    return;
                }
            }
            
            /* GET START GAME FROM SERVER */
            System.out.println("Waiting start game");
            response = is.readLine();
            System.out.println(response);
            jsonResponse = new JSONObject(response);
            String method = jsonResponse.optString("method");
            if(method.equals("start")){
                isReady = true;
                System.out.println("isReady (start game) = true");
                role = jsonResponse.optString("role");
                time = jsonResponse.optString("time");
                if(role.equals("werewolf")){
                    JSONArray jsonFriends = jsonResponse.optJSONArray("friend");
                    System.out.println(jsonFriends.toString());
                    for(int i=0; i<jsonFriends.length(); i++){
                        friends.add(jsonFriends.getString(i));
                    }
                }   
            } else { //can't play, quit
                return;
            }
            
            //Playing Game
            System.out.println("Game started");
            
            /* REQUEST LIST CLIENT */
            os.println(ClientRequest.listClient());
            os.flush();
            boolean listClientReceived = false;
            while(!listClientReceived){
                response = is.readLine(); //Read response from server about readyup game
                System.out.println(response);
                jsonResponse = new JSONObject(response);
                String status = jsonResponse.optString("status");
                if(status.equals("ok")){
                    listClientReceived = true;
                    JSONArray clientsJSON = jsonResponse.optJSONArray("clients");
                    for(int i=0; i<clientsJSON.length(); i++){
                        JSONObject client = clientsJSON.getJSONObject(i);
                        Player player = new Player();
                        player.player_id = Integer.parseInt(client.optString("player_id"));
                        player.address = client.optString("address");
                        player.username = client.optString("username");
                        player.port = Integer.parseInt(client.optString("port"));
                        player.is_alive = Integer.parseInt(client.optString("is_alive"));
                        listPlayer.add(player);
                        if (player.username.equals(myName)) myId = player.player_id ;
                    }
                    
                    System.out.println("List client received");
                } else { //response from server is not list client. wait the server send response
                    return;
                }
            }
            System.out.println("My username : "+myName);
            System.out.println("My player id : "+myId);
            System.out.println("Total player : "+listPlayer.size());
            original_size = listPlayer.size() ;
            Listener l_thread = new Listener("list",listPlayer.get(myId-1).port); 
            l_thread.start();
            while (true) {
                /*  Get current day */
                response = is.readLine(); 
                //System.out.println(response);
                jsonResponse = new JSONObject(response);
                String method_ = jsonResponse.optString("method");
                if (method_.equals("change_phase")) {
                    cur_day = jsonResponse.optInt("days");
                    cur_phase = jsonResponse.optString("time");
                }
                System.out.println(cur_phase+" : day "+cur_day);
                if (myId<=original_size&&myId>original_size-2) {
                    //Paxos
                    //Kirim paxos prepare proposal
                     proposal_number++ ;
                     JSONObject obj = new JSONObject();
                     obj.put("method","prepare_proposal");
                     JSONArray pr_id = new JSONArray() ;
                     pr_id.put(proposal_number);
                     pr_id.put(myId);
                     obj.put("proposal_id",pr_id);
                     for (int i=0;i<original_size-2;i++) {
                         if (listPlayer.get(i).is_alive==1) {
                             Sender s = new Sender("send",obj.toString(),listPlayer.get(i).port,listPlayer.get(i).address);
                             s.start();
                         }
                     }
                    //Terima response prepare proposal dari client
                    //hitung jumlah response ok nya berapa
                    //kalo lebih dari separo lanjutkan ke protokol 6
                    //keliatannya perlu pake timeout, misalkan stlh bbrp detik belum jawab semua,
                    //yg blm jawab dianggap gagal
                    
                    
                    //{Protokol 6 Kirim paxos accept proposal (cuma kalo sesuai kondisi di atas)
                    //terima response dari acceptor
                     
                    
                    
                } else {
                    //Acceptor - pake thread
                    //CEK METHOD
                    //1.prepare_proposal
                    //terima request paxos prepare proposal
                    //cek apakah id proposal lebih besar
                    //kalo iya accept
                    //kalo tidak reject
                    //kirim response ke client
                    
                    //2.accept_proposal
                    //Cek id proposal terakhir yg pernah diterima (kalo ada)
                    //Terima yg skrg cum kalo id sblmnya ga lebih besar
                    //Kalo meng-accept, set id proposal terakhir
                    //Response ke proposer
                    //Kirim ke server
                    
                    //3.vote_now
                    //Ubah tampilan jadi untuk ngevote
                    
                }
                
                
            }
            
            
            /*
            while(line.compareTo("QUIT")!=0){
                os.println(ClientRequest.joinRequest(line,"",0).toString());
                os.flush();
                response=is.readLine();
                System.out.println("Server Response : "+response);
                line=br.readLine();
            }*/
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Socket read Error");
        } catch (JSONException e){
            e.printStackTrace();
        }
        finally{
            is.close();os.close();br.close();s1.close();
            System.out.println("Connection Closed");
        }
    }    
}


class Listener implements Runnable {
   private Thread t;
   private String threadName;
   private int listenPort ;
   private JSONObject jsonR ;
   
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
            String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("RECEIVED: " + sentence);
            jsonR = new JSONObject(sentence);
            String method = jsonR.optString("method");
            if (method.equals("prepare_proposal")) {
                JSONArray list = jsonR.optJSONArray("proposal_id");
                int proposal_id_  = list.getInt(0);
                int player_id_ = list.getInt(1);
            }
        }
       } catch (SocketException ex) {
           System.out.println("Socket Exception");
       } catch (IOException ex) {
           System.out.println("IOException");
       } catch (JSONException ex) {
          System.out.println("JSONException");
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
