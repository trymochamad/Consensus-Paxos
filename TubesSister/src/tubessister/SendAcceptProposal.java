/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubessister;

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
import static tubessister.GameClient.os;


/**
 *
 * @author tama
 */
public class SendAcceptProposal extends Thread {
    private volatile Thread t;
    private String threadName;
    private volatile boolean stopRequested;
    private PrintWriter os;
    GameClient.ClientAcceptProposal cap;
    
    private JSONObject tempJSONmsg = null ;
      
    SendAcceptProposal(PrintWriter os, GameClient.ClientAcceptProposal cap){
        this.os = os;
        this.cap = cap;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(50000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SendAcceptProposal.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(!cap.sent){
            os.println(ClientRequest.clientAcceptProposal(-1));
            os.flush();
        }
    }   
}


