/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paxos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;


public class GameServer {
    public static JSONObject tempJSONmsg = null ;
    
    
    final static int port = 9876;
    
    public static void main (String args[]) {
        Game myGame = new Game();
        Socket socket = null;
        ServerSocket GameServer = null;
        System.out.println("Server Listening......");
        try {
            GameServer = new ServerSocket(port);
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Server error");
        }

        while (true) {
            try {
                socket = GameServer.accept();
                String clientAddress = socket.getRemoteSocketAddress().toString();
                int clientPort = socket.getPort();
                System.out.println("Connection established. Client: " + clientAddress + "on port " + clientPort);
                
                
                
                ServerThread st = new ServerThread(socket,myGame);
                st.start();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Connection Error");
            }
        }
    }
}

class ServerThread extends Thread{  

    String line=null;
    BufferedReader  is = null;
    PrintWriter os=null;
    Socket s=null;
    Game myGame=null;
    
    public ServerThread(Socket s, Game myGame){
        this.s = s;
        this.myGame = myGame;
    }
    
    public void run() {
        String username = null;
        String address = null ;
        int port = 0 ;
        int id_player = -1;
        String method = null;
        JSONObject jsonMessage;
        try {
            is = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = new PrintWriter(s.getOutputStream());
        } catch (IOException e){
            System.out.println("IO error in server thread");
        }

        try {
            //JOINGAME
            //check whether or not the game is waiting
            boolean isAllowedToJoin = false;
            if(myGame.getStatus() == 0){
                System.out.println("Game status is 0");
                //game is waiting -> check client's username
                try {
                    jsonMessage = new JSONObject(is.readLine());
                    username = jsonMessage.optString("username");
                    port = jsonMessage.optInt("udp_port");
                    address = jsonMessage.optString("udp_address");
                    System.out.println("Client username is " + username);
                    if (username.length()>0) {
                        //check whether or not username exists
                        if(!myGame.isUsernameExist(username)){
                            System.out.println("Username not exist");
                            isAllowedToJoin = true;
                            int new_id = myGame.getNewID();
                            id_player = new_id;
                            System.out.println("Id player is : "+new_id);
                            Player newP = new Player(new_id,address,port,username);
                            myGame.addPlayer(newP);
                            System.out.println("Send to client: " + ServerResponse.joinGameOK(new_id).toString());
                            os.println(ServerResponse.joinGameOK(new_id).toString());
                            os.flush();
                        } else {
                            os.println(ServerResponse.statusFail("user exists"));
                            os.flush();
                        }
                    } else {
                        os.println(ServerResponse.statusFail("username not valid"));
                        os.flush();
                    }
                } catch (JSONException e) {
                    //send wrongRequestError
                    os.println(ServerResponse.wrongRequestError().toString());
                    os.flush();
                    e.printStackTrace();
                }
            } else {
                //the game already has already started
                os.println(ServerResponse.statusFail("please wait, game is currently running").toString());
                os.flush();
            }
            if(!isAllowedToJoin)
                return ; //close connection
            //END-JOINGAME
            
            //READY
            //waiting ready message from client
            //check whether or not the game is waiting
            boolean isReady = false;
            System.out.println("Waiting ready message from client");
            while(!isReady){
                if(myGame.getStatus() == 0){
                    System.out.println("Game status is 0");
                    //game is waiting -> check client's username
                    try {
                        String temp_ = is.readLine() ;
                        
                        jsonMessage = new JSONObject(temp_);
                        System.out.println("Message in !isReady : "+temp_);
                        method = jsonMessage.optString("method");
                        if(method.equals("ready")){
                            if(myGame.getStatus() != 0){
                                os.println(ServerResponse.statusFail("please wait, game is currently running"));
                                os.flush();
                                myGame.setPlayerReady(id_player);
                                
                            } else {
                                os.println(ServerResponse.statusOK());
                                os.flush();
                                myGame.setPlayerReady(id_player);//else if error, send error response to client, blm dibuat
                                isReady = true;
                            }                            
                        } else if(method.equals("leave")){
                            //leave();
                            myGame.removePlayerWithID(id_player);
                            os.println(ServerResponse.statusOK());
                            os.flush();
                            myGame.removePlayerWithID(id_player);
                            return ; //exit
                        } else {
                            os.println(ServerResponse.statusError("Method not allowed"));
                            os.flush();
                        }
                    } catch (JSONException e) {
                        //send wrongRequestError
                        os.println(ServerResponse.wrongRequestError().toString());
                        os.flush();
                        e.printStackTrace();
                    }
                } else {
                    //the game already has already started
                    System.out.println("the game has already started");
                    os.println(ServerResponse.statusFail("please wait, game is currently running").toString());
                    os.flush();
                    break;
                }
            }
            //END-READY
            System.out.println("End-ready. Wait until other players ready");
            //WAIT UNTIL OTHER PLAYERS READY

            while(myGame.getStatus()!=1){
                System.out.println("status : "+myGame.getStatus());
                try {
                sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }   
            }               
                /*** START GAME (ROLE) ***/
            if(myGame.findPlayerWithID(id_player).getRole().equals("werewolf")){
                
                os.println(ServerResponse.startGame("werewolf", myGame.getWerewolfFriends()));
                os.flush();
                System.out.println("Send werewolf role");
            } else { //civilian
                os.println(ServerResponse.startGame("civilian", null));
                os.flush();
                System.out.println("Send civilian role");
            }
            /*** END-START GAME (ROLE) ***/
            System.out.println("End start game (role)");
            
            /***** NIGHT 1 *****/
            int targetDay = 1;

            /***** DAILY LOOP *****/
            while(myGame.getStatus() == 1){
                
                if(myGame.getDay() < targetDay)
                    nextDay();
                
                int cur_day = myGame.getDay();
                /*** DAY ***/
                /*** LIST CLIENT ***/
                boolean requestListClient = false;
                while(!requestListClient){
                    try {
                        jsonMessage = new JSONObject(is.readLine());
                        method = jsonMessage.optString("method");
                        if(method.equals("client_address")){
                            os.println(ServerResponse.listClient(myGame.getPlayers()));
                            os.flush();
                            requestListClient = true;
                        } else {
                            os.println(ServerResponse.statusError("Method not allowed"));
                            os.flush();
                        }
                    } catch (JSONException e) {
                        //send wrongRequestError
                        os.println(ServerResponse.wrongRequestError().toString());
                        os.flush();
                        e.printStackTrace();
                    }

                }
                /*** END-LIST CLIENT ***/
                
                os.println(ServerResponse.changePhase("day",myGame.getDay(),""));
                os.flush();
                System.out.println("after send change phase" + ServerResponse.changePhase("day",myGame.getDay(),""));
                /* PAXOS */
                /* proposer dan client menjalankan paxos */
                int kpu_id = -1;
                
                /* MENERIMA JAWABAN DARI ACCEPTOR */
                boolean getLeaderVote = false;
                while(!getLeaderVote){
//                    long start = System.currentTimeMillis();
//                    VoteLeaderListener3 main = new VoteLeaderListener3("vll",is);
//                    main.start();
//                    try {
//                        while (main.isRunning) {
//                            synchronized (main) {
//                                main.wait(1000);
//                            }
//                        }
//                        long stop = System.currentTimeMillis();
//
//                        if (main.everythingDone)
//                            System.out.println("all done in " + (stop - start) + " ms.");
//                        else {
//                            System.out.println("could not do everything in "
//                                    + (stop - start) + " ms.");
//                            if (main.endedWithException != null)
//                                main.endedWithException.printStackTrace();
//                        }
//                    } catch (InterruptedException e) {
//                        System.out.println("You've killed me!");
//                    }
//                    jsonMessage = main.getJSONMessage() ;
//                    System.out.println("acceptor vll " + jsonMessage.toString());
                    
//                    VoteLeaderListener VLL = new VoteLeaderListener("vll",is);
//                    System.out.println("gil " + id_player);
//                    VLL.start();
//                    System.out.println("gila " + id_player);
//                    Thread.sleep(20000);
//                    System.out.println("gila2" + id_player);
//                    //VLL.closeSocket();
//                    
//                    VLL.interrupt();
                    
//                    System.out.println("after join " + id_player);
                    jsonMessage = new JSONObject(is.readLine());
                    System.out.println("acceptor vll " + jsonMessage.toString());
                    
                    try {
                        System.out.println("after set timeout");
                        //jsonMessage = new JSONObject(is.readLine());
                        if (jsonMessage == null) {
                            System.out.println("client send null");
                        } else {
                            method = jsonMessage.optString("method");
                            System.out.println("method " + method);
                            if (method.equals("accepted_proposal")) {
                                os.println(ServerResponse.statusOK());
                                os.flush();
                                System.out.println("after send OK" + id_player);
                                kpu_id = Integer.parseInt(jsonMessage.optString("kpu_id"));
                                myGame.voteLeader(kpu_id);
                                
                            } else if (method.equals("leave")){
                                myGame.removePlayerWithID(id_player);
                                os.println(ServerResponse.statusOK());
                                os.flush();
                                myGame.removePlayerWithID(id_player);
                                return ;
                            }
                        }
                        Thread.sleep(15000);
                        if (myGame.getVoteLeaderFinish()!=true) {//ada yg ga ngirim atau ga ada yg n/2
                            System.out.println("kpuselected finsih false" + myGame.getLeader());
                            os.println(ServerResponse.KPUSelected(myGame.getLeader()));
                            os.flush();
                        } else {
                            System.out.println("kpuselected finsih true" + myGame.getLeader());
                            os.println(ServerResponse.KPUSelected(myGame.getLeader()));
                            os.flush();
                            getLeaderVote = true;
                        }
                        
                    }/* catch (JSONException e) {
                        e.printStackTrace();
                    }*/ catch (InterruptedException ex) {
                        Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    /*while(myGame.getVoteLeaderFinish() == false) {}
                    if (kpu_id == myGame.getLeader()) {
                        os.println(ServerResponse.statusOK());
                    } else {
                        os.println(ServerResponse.statusFail("KPU incorrect"));
                    }*/
                    //os.flush();
                }
                s.setSoTimeout(0);
                
                /* END-MENERIMA JAWABAN DARI ACCEPTOR */
                os.println(ServerResponse.KPUSelected(myGame.getLeader()));
                os.flush();
                /* MENGIRIM INFORMASI LEADER KE CLIENT */
                                           

                /* END-MENGIRIM INFORMASI LEADER KE CLIENT */
                
                /* SEND VOTE NOW DAY */
                boolean voteCivilianNow = false;
                while(!voteCivilianNow){
                    System.out.println("Send civilian now" + id_player);
                    os.println(ServerResponse.voteNow("day"));
                    os.flush();
                    String response = is.readLine();
                    try {
                        System.out.println("Client get civilian now" + id_player);
                        JSONObject jsonResponse = new JSONObject(response);
                        String status = jsonResponse.optString("status");
                        if(status.equals("ok")){
                            System.out.pritln("Client has got kpu_id");
                            os.println(ServerResponse.listClient(myGame.getPlayers()));
                            os.flush();
                            voteCivilianNow = true;
                        } else if (jsonResponse.optString("method").equals("leave")) {
                            return ;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                /* END-SEND VOTE NOW DAY */
                
                /* KILL CIVILIAN VOTE */
                if(myGame.getLeader() == id_player){//if the client is leader
                    boolean killCivilianVote = false;
                    while(!killCivilianVote){
                        try {
                            System.out.println("Waiting client civilian vote" + id_player);
                            jsonMessage = new JSONObject(is.readLine());
                            System.out.println(jsonMessage.toString());
                            method = jsonMessage.optString("method");
                            if ((method.equals("vote_result_civilian")) || (method.equals("vote_result"))) {
                                int vote_status = Integer.parseInt(jsonMessage.optString("vote_status"));
                                if (vote_status == 1) {
                                    int player_to_kill = Integer.parseInt(jsonMessage.optString("player_killed"));
                                    myGame.voteKillCivilian(player_to_kill);
                                    os.println(ServerResponse.statusOK());
                                    killCivilianVote = true;
                                } else { //vote_status = -1
                                    os.println(ServerResponse.statusFail("Tie"));
                                }
                                os.flush();
                            } else if (method.equals("leave")){
                                myGame.removePlayerWithID(id_player);
                                os.println(ServerResponse.statusOK());
                                os.flush();
                                myGame.removePlayerWithID(id_player);
                                return ;
                            } 
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                /* END-KILL CIVILIAN VOTE */
                
                /*** END-DAY ***/
                
                /*** NIGHT ***/
                /*** LIST CLIENT ***/
                requestListClient = false;
                while(!requestListClient){
                    try {
                        jsonMessage = new JSONObject(is.readLine());
                        method = jsonMessage.optString("method");
                        if(method.equals("client_address")){
                            os.println(ServerResponse.listClient(myGame.getPlayers()));
                            os.flush();
                            requestListClient = true;
                        } else {
                            os.println(ServerResponse.statusError("Method not allowed"));
                            os.flush();
                        }
                    } catch (JSONException e) {
                        //send wrongRequestError
                        os.println(ServerResponse.wrongRequestError().toString());
                        os.flush();
                        e.printStackTrace();
                    }

                }
                /*** END-LIST CLIENT ***/
                
                
                os.println(ServerResponse.changePhase("night",myGame.getDay(),""));
                os.flush();
                
                /* SEND VOTE NOW NIGHT */
                boolean voteWerewolfNow = false;
                while(!voteWerewolfNow){
                    os.println(ServerResponse.voteNow("night"));
                    os.flush();
                    String response = is.readLine();
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String status = jsonResponse.optString("status");
                        if(status.equals("ok")){
                            os.println(ServerResponse.listClient(myGame.getPlayers()));
                            os.flush();
                            voteWerewolfNow = true;
                        } else if (jsonResponse.optString("method").equals("leave")) {
                            return ;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                /* END-SEND VOTE NOW NIGHT */
                
                /* KILL WEREWOLF VOTE */
                if(myGame.getLeader() == id_player){//if the client is leader
                    int vote_attempt = 0;
                    boolean killWerewolfVote = false;
                    while(!killWerewolfVote && vote_attempt<2){
                        try {
                            jsonMessage = new JSONObject(is.readLine());
                            method = jsonMessage.optString("method");
                            if ((method.equals("vote_result_werewolf")) || (method.equals("vote_result"))) {
                                int vote_status = Integer.parseInt(jsonMessage.optString("vote_status"));
                                if (vote_status == 1) {
                                    int player_to_kill = Integer.parseInt(jsonMessage.optString("player_killed"));
                                    myGame.voteKillWerewolf(player_to_kill);
                                    os.println(ServerResponse.statusOK());
                                    killWerewolfVote = true;
                                } else { //vote_status = -1
                                    os.println(ServerResponse.statusFail("Tie"));
                                    vote_attempt++;
                                }
                                os.flush();
                            } else if (method.equals("leave")){
                                myGame.removePlayerWithID(id_player);
                                os.println(ServerResponse.statusOK());
                                os.flush();
                                myGame.removePlayerWithID(id_player);
                                return ;
                            } 
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                /* END-KILL WEREWOLF VOTE */
                /*** END-NIGHT ***/
                targetDay = cur_day + 1;
            }
            /***** END-DAILY LOOP *****/

            //get Game Input From Client
            /*line = is.readLine();
            while (line.compareTo("QUIT")!=0) {
                os.println(line + " from server");
                os.flush();
                System.out.println("Response to Client  :  " + line);
                line=is.readLine();
            }*/
        } catch (IOException e) {
            line=this.getName(); //reused String line for getting thread name
            System.out.println("IO Error/ Client "+line+" terminated abruptly");
        } catch (NullPointerException e) {
            line=this.getName(); //reused String line for getting thread name
            System.out.println("Client "+line+" Closed");
        } catch (JSONException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{    
            try {
                System.out.println("Connection Closing..");
                if (is!=null){
                    is.close(); 
                    System.out.println(" Socket Input Stream Closed");
                }

                if(os!=null){
                    os.close();
                    System.out.println("Socket Out Closed");
                }
                if (s!=null){
                    s.close();
                    System.out.println("Socket Closed");
                }
            }
            catch (IOException ie) {
                System.out.println("Socket Close Error");
            }
        }
    }
    
    public static void leave (int id_player) {
        
    }
   
     public synchronized void nextDay() {
        myGame.nextDay();
    }
     
}
