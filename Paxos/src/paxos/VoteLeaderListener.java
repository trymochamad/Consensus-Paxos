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
import static paxos.GameServer.tempJSONmsg;


/**
 *
 * @author tama
 */
public class VoteLeaderListener extends Thread {
   private Thread t;
   private String threadName;
   private BufferedReader is ;
   
   private JSONObject tempJSONmsg = null ;
      
   VoteLeaderListener( String name, BufferedReader is_){
       threadName = name;
       this.is = is_ ;
       
       System.out.println("Creating " +  threadName );
   }
   public void run() {
     System.out.println("Listener running . . ");
       try {
           tempJSONmsg = new JSONObject(is.readLine());
       } catch (JSONException ex) {
           System.out.println("JSONException on VoteLeaderListener ");
       } catch (IOException ex) {
           System.out.println("IOException on VoteLeaderListener ");
       }
   }
   
   
   
   public JSONObject getJSONMessage() {
       return tempJSONmsg ;
   }
}
