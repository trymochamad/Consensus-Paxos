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
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONException;
import org.json.JSONObject;


public class GameServer {
    
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
            }

            catch (Exception e) {
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
        JSONObject jsonMessage;
        try {
            is = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = new PrintWriter(s.getOutputStream());
        } catch (IOException e){
            System.out.println("IO error in server thread");
        }

        try {
            //check whether or not the game is waiting
            boolean isAllowedToJoin = false;
            if(myGame.getStatus() == 0){
                System.out.println("Game status is 0");
                //game is waiting -> check client's username
                try {
                    jsonMessage = new JSONObject(is.readLine());
                    username = jsonMessage.optString("username");
                    System.out.println("Client username is " + username);
                    if(username.length()>0){
                        //check whether or not username exists
                        if(!myGame.isUsernameExist(username)){
                            System.out.println("Username not exist");
                            isAllowedToJoin = true;
                            int new_id = myGame.getNewID();
                            System.out.println("Send to client: " + ServerResponse.joinGameOK(new_id).toString());
                            os.println(ServerResponse.joinGameOK(new_id).toString());
                            os.flush();
                        } else {
                            os.println(ServerResponse.joinGameFailUserExists().toString());
                            os.flush();
                        }
                    } else {
                        os.println(ServerResponse.wrongRequestError().toString());
                        os.flush();
                    }
                } catch (JSONException e) {
                    //send wrongRequestError
                    os.println(ServerResponse.wrongRequestError().toString());
                    os.flush();
                    e.printStackTrace();
                }
                //allows client to join the game
            } else {
                //the game already has already started
                System.out.println("the game has already started");
                os.println(ServerResponse.joinGameAlreadyStarted().toString());
                os.flush();
            }
            
            //get Game Input From Client
            line = is.readLine();
            while (line.compareTo("QUIT")!=0) {
                os.println(line + " from server");
                os.flush();
                System.out.println("Response to Client  :  " + line);
                line=is.readLine();
            }
        } catch (IOException e) {
            line=this.getName(); //reused String line for getting thread name
            System.out.println("IO Error/ Client "+line+" terminated abruptly");
        } catch (NullPointerException e) {
            line=this.getName(); //reused String line for getting thread name
            System.out.println("Client "+line+" Closed");
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
    
    
}
