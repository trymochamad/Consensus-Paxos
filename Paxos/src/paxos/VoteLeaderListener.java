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
    private volatile Thread t;
    private String threadName;
    private BufferedReader is ;
    private volatile boolean stopRequested;
    private boolean socketClosed = false;
    
    private JSONObject tempJSONmsg = null ;
      
    VoteLeaderListener( String name, BufferedReader is_){
        threadName = name;
        this.is = is_ ;

        System.out.println("Creating " +  threadName );
    }
    
    @Override
    public void run() {
        System.out.println("Listener running . . ");
        try {
            tempJSONmsg = new JSONObject(is.readLine());
        } catch (JSONException ex) {
            System.out.println("JSONException on VoteLeaderListener ");
        } catch (IOException ex) {
            System.out.println("IOException on VoteLeaderListener ");
        }
        try {
            is.close();
        } catch (IOException ex) {
            Logger.getLogger(VoteLeaderListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        socketClosed = true;
    }
    
//    public void closeSocket() throws IOException{
//        if(!socketClosed)
//            is.close();
//    }
   
    public JSONObject getJSONMessage() {
        System.out.println("return tempJSONMsg");
        return tempJSONmsg ;
    }
    
//    synchronized void requestStop () {
//        stopRequested = true;
//        if(t!=null)
//            t.interrupt();
//    }
   
//   public static class TimeOutThread extends Thread {
//    final long timeout;
//    final VoteLeaderListener controlledObj;
//
//    TimeOutThread(long timeout, VoteLeaderListener controlledObj) {
//        setDaemon(true);
//        this.timeout = timeout;
//        this.controlledObj = controlledObj;
//    }
//
//    boolean isRunning = true;
//
//    /**
//     * If we done need the {@link TimeOutThread} thread, we may kill it.
//     */
//    public void kill() {
//        isRunning = false;
//        synchronized (this) {
//            notify();
//        }
//    }
//
//    /**
//     * 
//     */
//    @Override
//    public void run() {
//        long deltaT = 0l;
//        try {
//            long start = System.currentTimeMillis();
//            while (isRunning && deltaT < timeout) {
//                synchronized (this) {
//                    wait(Math.max(100, timeout - deltaT));
//                }
//                deltaT = System.currentTimeMillis() - start;
//            }
//        } catch (InterruptedException e) {
//            // If the thread is interrupted,
//            // you may not want to kill the main thread,
//            // but probably yes.
//        } finally {
//            isRunning = false;
//        }
//        //controlledObj.kill();
//    }
//}
   
}

