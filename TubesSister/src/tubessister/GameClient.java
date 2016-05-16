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
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GameClient {

    public static String serverAddress = "127.0.0.1";
    public static int serverPort = 9876;
          
    public static Socket s1 = null;
    public static String line=  null;
    public static BufferedReader br = null;
    public static BufferedReader is = null;
    public static PrintWriter os = null;
    public static JSONObject jsonResponse = null;
    public static String myName = null;
    public static String role = null;
    public static int myId = -1 ;
    public static String time = null;
    public static ArrayList<String> friends = new ArrayList<String>();
    public static int cur_day =0;
    public static String cur_phase ="" ;
    public static int proposal_number = 0 ;
    public static int original_size ;
    public static int previous_prop_id = 0 ;
    public static int previous_player_id = 0 ;
    public static int kpu_id =0 ;
    public static int previous_kpu_id = 0 ;
    public static ArrayList<Player> listPlayer = new ArrayList<Player>();
    public static ArrayList<Player> listPlayerShow = new ArrayList<Player>();
    public static String current_method ="" ;
    public static boolean isProposer = false  ;
    public static int okPrepareProposal = 0 ;
    public static int failPrepareProposal = 0 ;
    public static boolean collectCountProposal = false;
    public static boolean majorityProposal = false;
    public static int biggestKpuID = -1;
    public static boolean leaderSelected = false ;
    public static boolean prepareTimeout = true ;
    public static boolean acceptTimeout = true ;
    public static int idKPU = 0;
    public static boolean voteToKill = false ;
    public static int leaderTempVote = 0 ;
    public static VoteKill VK ;
    public static boolean findToKill = false ;
    public static int idToKill =0;
    public static boolean acceptProposalSent = false;
    public static ClientAcceptProposal cap;
    public static ArrayList<String> receivedMsg = new ArrayList<String>();
    
    public static class ClientAcceptProposal{
        boolean sent = false;
    }
    
    public static class Player{
        int player_id;
        String username;
        String address;
        int port;
        int is_alive;
    }

    /* START CLASS LISTENER */
    public static class Listener implements Runnable {
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
                while(true) {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    serverSocket.receive(receivePacket);
                    String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("RECEIVED: " + sentence + "from" + receivePacket.getPort());
                    receivedMsg.add(sentence);
                    System.out.println("ReceivedMsg length = " + receivedMsg.size());
                    /*jsonR = new JSONObject(sentence);
                    if (!voteToKill) {
                        if (isProposer) {
                            //Prepare Proposal (PROPOSER)
                            if (current_method.equals("prepare_proposal")&&!prepareTimeout) {
                                String status = jsonR.optString("status");
                                if(status.equals("ok")){
                                    okPrepareProposal++;
                                    System.out.println("Get OK " + okPrepareProposal);
                                    String kpu_id_s = null ;
                                    kpu_id_s= jsonR.optString("previous_accepted");
                                    System.out.println("kpu_id_s" + kpu_id_s);
                                    int kpu_id_ = 0;
                                    if (kpu_id_s !=null && kpu_id_s.length()>0) kpu_id_ = Integer.parseInt(kpu_id_s);
                                } else {//fail
                                    failPrepareProposal++;                           
                                    System.out.println("Get OK " + failPrepareProposal);
                                }
                          } else if (current_method.equals("accept_proposal")&&!acceptTimeout) {
                              String status = jsonR.optString("status");
                              String description = jsonR.optString("description");
                              System.out.println("Status: " + status + ", Description: " + description);
                          }
                        } else {
                            //Bukan proposer
                            String method = jsonR.optString("method");
                            if (method.equals("prepare_proposal")) {
                                //Prepare  Proposal (ACCEPTOR)
                                JSONArray list = jsonR.optJSONArray("proposal_id");
                                int proposal_id_  = list.getInt(0);
                                int player_id_ = list.getInt(1);
                                if (proposal_id_<= previous_prop_id) {
                                    //Jika proposal ID yang diterima lebih kecil dari proposal id yang diterima terakhir kali
                                    // Kirim Fail
                                     System.out.println("prop and prev" + proposal_id_ + " " + previous_prop_id);
                                     String msg_ = ClientRequest.statusFail("Proposal ID smaller than already accepted");
                                     Sender s = new Sender("send",msg_,listPlayer.get(player_id_-1).port,listPlayer.get(player_id_-1).address);
                                     System.out.println("p6" + msg_);
                                     s.start();
                               } else if (proposal_id_ > previous_prop_id) {
                                    //Jika proposal ID yang diterima lebih besar dari proposal id yang diterima terakhir kali (maka terima)
                                    // Kirim OK
                                    //Accepted
                                    previous_prop_id = proposal_id_;
                                    previous_player_id = player_id_ ;
                                    System.out.println("proposal_accpeted" + proposal_id_ + " " + player_id_);
                                    String msg_ = ClientRequest.okResponsePrepare(previous_kpu_id);
                                    Sender s = new Sender("send",msg_,listPlayer.get(player_id_-1).port,listPlayer.get(player_id_-1).address);
                                    System.out.println("p6" + msg_ + "to " + listPlayer.get(player_id_-1).port);
                                    s.start();
                               }
                           } else if (method.equals("accept_proposal")) {
                                System.out.println("method accept_proposal");
                                //Accept Proposal (ACCEPTOR)
                                JSONArray list = jsonR.optJSONArray("proposal_id");
                                int proposal_id_  = list.getInt(0);
                                int player_id_ = list.getInt(1);
                                int kpu_id_ = jsonR.optInt("kpu_id");
                                if (proposal_id_ == previous_prop_id && player_id_== previous_player_id) {
                                    //Proposal yang diterima sama dengan proposal yang di accept si client terakhir kali pada saat prepare
                                    //Kirim OK
                                    String msg_ = ClientRequest.okResponseAccepted() ;
                                    Sender s = new Sender("send",msg_,listPlayer.get(player_id_-1).port,listPlayer.get(player_id_-1).address);
                                    System.out.println("Acpt" + msg_);
                                    idKPU = kpu_id_ ;
                                    //Kirim ke learner nilai kpuID
                                    os.println(ClientRequest.clientAcceptProposal(idKPU));
                                    os.flush();
                                    cap.sent = true;
                                } else {
                                    //Proposal yang diterima bukan proposal yang di accept si client terakhir kali pada saat prepare
                                    //Kirim Fail
                                    System.out.println("fail");
                                    String msg_ = ClientRequest.statusFail("Proposal number tidak sesuai dengan proposal number oleh client") ;
                                    System.out.println(msg_);
                                    Sender s = new Sender("send",msg_,listPlayer.get(player_id_-1).port,listPlayer.get(player_id_-1).address);

                                    os.println(ClientRequest.clientAcceptProposal(-1));
                                    os.flush();
                                    cap.sent = true;
                                }
                           } else {

                           }
                        }

                    } else {
                          //Vote untuk memilih pemain yang akan dibunuh
                         String method= "";
                        if (myId == idKPU) {
                            //Pemain adalah leader
                            method = jsonR.optString("method");
                            if (method.equals("vote_civilian")) {
                                //Siang hari (vote civilian)
                                int temp_id = jsonR.optInt("player_id");
                                //simpan vote di bawah
                                VK.votePlayer(temp_id);
                                if (VK.totalVote==original_size-1) {
                                    //Semua udah vote
                                    VK.votePlayer(leaderTempVote);
                                    boolean x = VK.isFindToKill();
                                     voteToKill = false ;
                                     idToKill = -999;

                                    if (x) {
                                        idToKill = VK.findMaxID();
                                    }
                                } 
                            } else if (method.equals("vote_werewolf")) {
                                //Malam hari (vote werewolf)
                                int temp_id = jsonR.optInt("player_id");
                                //simpan vote di bawah
                                VK.votePlayer(temp_id);
                                if (myId == idKPU) {
                                    //Semua udah vote
                                    VK.votePlayer(leaderTempVote);
                                    boolean x = VK.isFindToKill();
                                     voteToKill = false ;
                                     idToKill = -999;

                                    if (x) {
                                        idToKill = VK.findMaxID();
                                    }
                                } else if (myId!=idKPU && VK.totalVote==2) {
                                    VK.votePlayer(leaderTempVote);
                                    boolean x = VK.isFindToKill();
                                     voteToKill = false ;
                                     idToKill = -999;

                                    if (x) {
                                        idToKill = VK.findMaxID();
                                    }
                                } 
                            }
                        } else {
                            //Pemain bukan leader
                        }
                     }*/
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
    /* END CLASS LISTENER */
    
    /* START CLASS PROCESSMSG */
    public static class ProcessMsg implements Runnable {
        
        private Thread t;
        private String threadName;
        private JSONObject jsonR ;

        ProcessMsg( String name ){
            threadName = name;
            System.out.println("Creating " +  threadName );
        }
        
        public void run() {
            System.out.println("ProcessMsg running");
            while(true){
                System.out.println("waiting message");
                while(receivedMsg.size()==0){
                    try {
                        sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                System.out.println("got a message");
                String sentence = receivedMsg.get(0);
                receivedMsg.remove(0);
                System.out.println("Received Message " + sentence);
                try {
                    jsonR = new JSONObject(sentence);
                } catch (JSONException ex) {
                    Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                    if (!voteToKill) {
                        if (isProposer) {
                            //Prepare Proposal (PROPOSER)
                            if (current_method.equals("prepare_proposal")&&!prepareTimeout) {
                                String status = jsonR.optString("status");
                                if(status.equals("ok")){
                                    okPrepareProposal++;
                                    System.out.println("Get OK " + okPrepareProposal);
                                    String kpu_id_s = null ;
                                    kpu_id_s= jsonR.optString("previous_accepted");
                                    System.out.println("kpu_id_s" + kpu_id_s);
                                    int kpu_id_ = 0;
                                    if (kpu_id_s !=null && kpu_id_s.length()>0) kpu_id_ = Integer.parseInt(kpu_id_s);
                                } else {//fail
                                    failPrepareProposal++;                           
                                    System.out.println("Get OK " + failPrepareProposal);
                                }
                          } else if (current_method.equals("accept_proposal")&&!acceptTimeout) {
                              String status = jsonR.optString("status");
                              String description = jsonR.optString("description");
                              System.out.println("Status: " + status + ", Description: " + description);
                          }
                        } else {
                            //Bukan proposer
                            String method = jsonR.optString("method");
                            if (method.equals("prepare_proposal")) {
                                //Prepare  Proposal (ACCEPTOR)
                                JSONArray list = jsonR.optJSONArray("proposal_id");
                                int proposal_id_;
                                try {
                                    proposal_id_ = list.getInt(0);
                                    int player_id_ = list.getInt(1);
                                    if (proposal_id_<= previous_prop_id) {
                                        //Jika proposal ID yang diterima lebih kecil dari proposal id yang diterima terakhir kali
                                        // Kirim Fail
                                         System.out.println("prop and prev" + proposal_id_ + " " + previous_prop_id);
                                         String msg_ = ClientRequest.statusFail("Proposal ID smaller than already accepted");
                                         Sender s = new Sender("send",msg_,listPlayer.get(player_id_-1).port,listPlayer.get(player_id_-1).address);
                                         System.out.println("p6" + msg_);
                                         s.start();
                                    } else if (proposal_id_ > previous_prop_id) {
                                        //Jika proposal ID yang diterima lebih besar dari proposal id yang diterima terakhir kali (maka terima)
                                        // Kirim OK
                                        //Accepted
                                        previous_prop_id = proposal_id_;
                                        previous_player_id = player_id_ ;
                                        System.out.println("proposal_accpeted" + proposal_id_ + " " + player_id_);
                                        String msg_ = ClientRequest.okResponsePrepare(previous_kpu_id);
                                        Sender s = new Sender("send",msg_,listPlayer.get(player_id_-1).port,listPlayer.get(player_id_-1).address);
                                        System.out.println("p6" + msg_ + "to " + listPlayer.get(player_id_-1).port);
                                        s.start();
                                    }
                                } catch (JSONException ex) {
                                    Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
                                }
                           } else if (method.equals("accept_proposal")) {
                                System.out.println("method accept_proposal");
                                //Accept Proposal (ACCEPTOR)
                                JSONArray list = jsonR.optJSONArray("proposal_id");
                                int proposal_id_;
                                try {
                                    proposal_id_ = list.getInt(0);
                                    int player_id_ = list.getInt(1);
                                    int kpu_id_ = jsonR.optInt("kpu_id");
                                    if (proposal_id_ == previous_prop_id && player_id_== previous_player_id) {
                                        //Proposal yang diterima sama dengan proposal yang di accept si client terakhir kali pada saat prepare
                                        //Kirim OK
                                        String msg_ = ClientRequest.okResponseAccepted() ;
                                        Sender s = new Sender("send",msg_,listPlayer.get(player_id_-1).port,listPlayer.get(player_id_-1).address);
                                        System.out.println("Acpt" + msg_);
                                        idKPU = kpu_id_ ;
                                        //Kirim ke learner nilai kpuID
                                        os.println(ClientRequest.clientAcceptProposal(idKPU));
                                        os.flush();
                                        cap.sent = true;
                                    } else {
                                        //Proposal yang diterima bukan proposal yang di accept si client terakhir kali pada saat prepare
                                        //Kirim Fail
                                        System.out.println("fail");
                                        String msg_ = ClientRequest.statusFail("Proposal number tidak sesuai dengan proposal number oleh client") ;
                                        System.out.println(msg_);
                                        Sender s = new Sender("send",msg_,listPlayer.get(player_id_-1).port,listPlayer.get(player_id_-1).address);

                                        os.println(ClientRequest.clientAcceptProposal(-1));
                                        os.flush();
                                        cap.sent = true;
                                    }
                                } catch (JSONException ex) {
                                    Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
                                }
                           } else {

                           }
                        }

                    } else {
                        //Vote untuk memilih pemain yang akan dibunuh
                        String method= "";
                        if (myId == idKPU) {
                            //Pemain adalah leader
                            method = jsonR.optString("method");
                            if (method.equals("vote_civilian")) {
                                //Siang hari (vote civilian)
                                int temp_id = jsonR.optInt("player_id");
                                //simpan vote di bawah
                                VK.votePlayer(temp_id);
                                if (VK.totalVote==original_size-1) {
                                    //Semua udah vote
                                    VK.votePlayer(leaderTempVote);
                                    boolean x = VK.isFindToKill();
                                    voteToKill = false ;
                                    idToKill = -999;

                                    if (x) {
                                        idToKill = VK.findMaxID();
                                    }
                                } 
                            } else if (method.equals("vote_werewolf")) {
                                //Malam hari (vote werewolf)
                                int temp_id = jsonR.optInt("player_id");
                                //simpan vote di bawah
                                VK.votePlayer(temp_id);
                                if (myId == idKPU) {
                                    //Semua udah vote
                                    VK.votePlayer(leaderTempVote);
                                    boolean x = VK.isFindToKill();
                                    voteToKill = false ;
                                    idToKill = -999;
                                    if (x) {
                                        idToKill = VK.findMaxID();
                                    }
                                } else if (myId!=idKPU && VK.totalVote==2) {
                                    VK.votePlayer(leaderTempVote);
                                    boolean x = VK.isFindToKill();
                                     voteToKill = false ;
                                     idToKill = -999;

                                    if (x) {
                                        idToKill = VK.findMaxID();
                                    }
                                } 
                            }
                        } else {
                            //Pemain bukan leader
                        }
                    }
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
    
    /* END CLASS PROCESSMSG */
    
    public static void main(String args[]) throws IOException {
        InetAddress address=InetAddress.getLocalHost();   
   
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
                response = is.readLine(); //Read response from server about listclient
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
                        listPlayerShow.add(player);
                        listPlayer.add(player);
                    }
                    
                    original_size = listPlayer.size() ;
                    for(int i=0; i<original_size; i++){
                        Player player = listPlayer.get(i);
                        if (player.username.equals(myName)) {
                            myId = player.player_id ;
                            if (myId<=original_size&&myId>original_size-2){
                                isProposer = true;
                                System.out.println("_proposer");
                            }
                        }
                    }
                    System.out.println("List client received");
                } else { //response from server is not list client. wait the server send response
                    return;
                }
            }
            /* END-REQUEST LIST CLIENT */
            
            System.out.println("My username : "+myName);
            System.out.println("My player id : "+myId);
            System.out.println("Total player : "+listPlayer.size());
            original_size = listPlayer.size() ;
            int portT = listPlayer.get(myId-1).port ;
            Listener l_thread = new Listener("list",portT); 
            l_thread.start();
            ProcessMsg l_processMsg = new ProcessMsg("process message");
            l_processMsg.start();
            while (true) {
                /*  Get current day */
                voteToKill = false ;
                response = is.readLine(); 
                System.out.println("cur_day_response = " + response);
                jsonResponse = new JSONObject(response);
                String method_ = jsonResponse.optString("method");
                if (method_.equals("change_phase")) {
                    cur_day = jsonResponse.optInt("days");
                    cur_phase = jsonResponse.optString("time");
                }
                System.out.println(cur_phase+" : day "+cur_day);
                if (isProposer) {
                    //Paxos
                    //Kirim paxos prepare proposal
                    System.out.println("i'm proposer");
                    Thread.sleep(5000);
                    prepareTimeout = false ;
                    proposal_number++ ;
                    okPrepareProposal = 0 ;
                    failPrepareProposal = 0;
                    JSONObject obj = new JSONObject();
                    obj.put("method","prepare_proposal");
                    JSONArray pr_id = new JSONArray() ;
                    pr_id.put(proposal_number);
                    pr_id.put(myId);
                    obj.put("proposal_id",pr_id);
                    while(!leaderSelected){
                        current_method = "prepare_proposal" ;
                        for (int i=0;i<original_size-2;i++) {
                            Sender s = new Sender("send",obj.toString(),listPlayer.get(i).port,listPlayer.get(i).address);
                            System.out.println("send prepare proposal " + obj.toString());
                            s.start();
                        }
                        Thread.sleep(15000);
                        prepareTimeout = true ;
                        acceptTimeout = false ;
                        int num_acceptor = original_size - 2 ;
                        current_method = "accept_proposal" ;
                        if (okPrepareProposal > num_acceptor/2 ) {
                            //Tercapai leader
                            int id = myId;
                            String msg = ClientRequest.paxosAcceptProposal(proposal_number, myId, id);
                            for (int i=0;i<original_size-2;i++) {
                                Sender s = new Sender("send",msg,listPlayer.get(i).port,listPlayer.get(i).address);
                                s.start();
                                System.out.println("send paxos accept proposal" + myId + " " + id
                                        + "to " + listPlayer.get(i).port);
                            }
                        } else {
                            //Kirim kalau ledaernya proposer 1 lagi
                            int id = 0 ;
                            if (myId == original_size) id = original_size - 1 ;
                            else id = original_size ;
                            String msg = ClientRequest.paxosAcceptProposal(proposal_number, myId, id);
                            for (int i=0;i<original_size-2;i++) {
                                Sender s = new Sender("send",msg,listPlayer.get(i).port,listPlayer.get(i).address);
                                s.start();
                                System.out.println("send paxos accept proposal leader lain" + myId + " " + id + "to " + listPlayer.get(i).port);
                            }
                        }
                        os.println(ClientRequest.clientAcceptProposal(-1));
                        os.flush();
                        Thread.sleep(15000);
                        acceptTimeout = true ;

                        //GET KPU_ID FROM SERVER
                        boolean getKpuID = false;
                        while(!getKpuID){
                            response = is.readLine();
                            System.out.println("response KpuID" + response);
                            if(response!=null && response.length()>0) {
                                jsonResponse = new JSONObject(response);
                                method = jsonResponse.optString("method");
                                if (method.equals("kpu_selected")) {
                                    idKPU = Integer.parseInt(jsonResponse.optString("kpu_id"));
                                    getKpuID = true;
                                    leaderSelected = true ;
                                }
                            }
                        }
                    }
                    
                } else {
                    //Bukan proposer
                    cap = new ClientAcceptProposal();
                    cap.sent = false;
                    SendAcceptProposal sap = new SendAcceptProposal(os,cap);
                    sap.start();
                    System.out.println("Waiting KPU from server");
                    boolean getKpuID = false ;
                    while(!getKpuID){
                        response = is.readLine();
                        if((response != null) && (response.length()>0)){
                            System.out.println("kpuselectd response" + response);
                            jsonResponse = new JSONObject(response);
                            method = jsonResponse.optString("method");
                            if (method.equals("kpu_selected")) {
                                idKPU = Integer.parseInt(jsonResponse.optString("kpu_id"));
                                getKpuID = true;
                                leaderSelected = true ;
                            }
                        }
                    }
                    System.out.println("End KPU from server");
                }
                
                
                /* GET VOTE NOW FROM SERVER */
                boolean getVoteNow = false;
                String phase = "";
                while(!getVoteNow){
                    response = is.readLine();
                    jsonResponse = new JSONObject(response);
                    method = jsonResponse.optString("method");
                    if (method.equals("vote_now")) {
                        System.out.println("get vote now");
                        phase = jsonResponse.optString("phase");
                        getVoteNow = true;
                        os.println(ClientRequest.statusOK());
                        os.flush();
                    }
                }
                voteToKill = true ;
                VK = new VoteKill(original_size);
                //Minta input pengguna
                
                /* MINTA INPUT PENGGUNA */
                System.out.print("Masukkan id_player yang ingin dibunuh: ");
                Scanner s  = new Scanner(System.in);
                int target = s.nextInt();
                if (idKPU != myId) {
                    String msg_ = ClientRequest.killCivilianVote(target);
                    SenderR sender = new SenderR("send",msg_,listPlayer.get(idKPU-1).port,listPlayer.get(idKPU-1).address);
                    sender.start();
                } else {
                    leaderTempVote = target ;
                }
                
                while (!voteToKill) {
                    sleep(50);
                }
                
                //Ketika keluar ada keputusan ada yang mau di kill atau tidak
                //Kalau belum ketemu siapa yang mau di kill, vote ulang sekali laig
                if (idToKill==-999) {
                    //Belum ketemu siapa yang mau di kill
                    voteToKill = true ;
                    VK = new VoteKill(original_size);
                    //Minta input pengguna
                    s  = new Scanner(System.in);
                    target = s.nextInt();
                    if (idKPU != myId) {
                        String msg_ = ClientRequest.killCivilianVote(target);
                        SenderR sender = new SenderR("send",msg_,listPlayer.get(idKPU-1).port,listPlayer.get(idKPU-1).address);
                        sender.start();
                    } else {
                        leaderTempVote = target ;
                    }

                    while (!voteToKill) {
                        sleep(50);
                    }
                }
                voteToKill = false ;
                if (idToKill!=-999) {
                    //Ketemu yang mau di kill
                    //Andaikan percobaan pertama udah dapat yang mau di kill, dia pasti langsung kesini
                    // Kalau belum masuk yang if di atas dulu
                    //Kalau tidak ketemu juga yang mau di kill langkah ini dilewati
                    //SEND KILL TO SERVER
                    if (myId ==idKPU) {
                        os.println(VK.getJSONVoteSuccess());
                        os.flush(); //Send the message to server
                
                    }
                } else {
                    if (myId == idKPU) {
                        os.println(VK.getJSONVoteUnsuccess());
                        os.flush(); //Send the message to server
                    }
                }
                
                /* ------------------------- M A L A M     H A R I ------------------------------- */
                
                /* GET LIST CLIENT */
                listClientReceived = false;
                listPlayerShow = new ArrayList<Player>();

                /*** NIGHT ***/
                
                /* REQUEST LIST CLIENT */
                os.println(ClientRequest.listClient());
                os.flush();
                listClientReceived = false;
                while(!listClientReceived){
                    response = is.readLine(); //Read response from server about listclient
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
                            listPlayerShow.add(player);
                        }
                    }
                }
                /* GET CURRENT PHASE */
                voteToKill = false ;
                response = is.readLine(); 
                System.out.println("cur_day_response = " + response);
                jsonResponse = new JSONObject(response);
                method_ = jsonResponse.optString("method");
                if (method_.equals("change_phase")) {
                    cur_day = jsonResponse.optInt("days");
                    cur_phase = jsonResponse.optString("time");
                }
                System.out.println(cur_phase+" : day "+cur_day);                
                
                /*GET VOTE NOW */
                 getVoteNow = false;
                 phase = "";
                while(!getVoteNow){
                    response = is.readLine();
                    jsonResponse = new JSONObject(response);
                    method = jsonResponse.optString("method");
                    if (method.equals("vote_now")) {
                        phase = jsonResponse.optString("phase");
                        getVoteNow = true;
                    }
                }
                
                /* VOTE NOW (ONLY WEREWOLF) */
                idToKill=-999 ;
                if (role.equals("werewolf")) {
                    while (idToKill==-999) {
                        //Belum ketemu siapa yang mau di kill
                        VK = new VoteKill(original_size);
                        voteToKill = true ;
                        //Minta input pengguna
                        s  = new Scanner(System.in);
                        target = s.nextInt();
                        if (idKPU != myId) {
                            String msg_ = ClientRequest.killWerewolfVote(target);
                            SenderR sender = new SenderR("send",msg_,listPlayer.get(idKPU-1).port,listPlayer.get(idKPU-1).address);
                            sender.start();
                        } else {
                            leaderTempVote = target ;
                        }

                        while (!voteToKill) {
                            sleep(50);
                        }
                    }
                    //Ketemu yang mau dikill
                    os.println(VK.getJSONVoteWerewolf());
                    os.flush();
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
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        finally{
            is.close();os.close();br.close();s1.close();
            System.out.println("Connection Closed");
        }
    }    
}



