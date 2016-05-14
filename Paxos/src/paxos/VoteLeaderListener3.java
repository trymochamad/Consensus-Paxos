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
public class VoteLeaderListener3 extends Thread {
    
    
    
    private volatile Thread t;
    private String threadName;
    private BufferedReader is ;
    private volatile boolean stopRequested;
    boolean isRunning = true;
    boolean everythingDone = false;
    final private TimeOutThread timeOutThread;
    Throwable endedWithException = null;
    
    private JSONObject tempJSONmsg = null ;
      
    VoteLeaderListener3( String name, BufferedReader is_){
        threadName = name;
        this.is = is_ ;
        timeOutThread = new TimeOutThread(10000, this);
        System.out.println("Creating " +  threadName );
    }
    
    @Override
    public void run() {
        timeOutThread.start();
        System.out.println("Listener running . . ");
        try {
            tempJSONmsg = new JSONObject(is.readLine());
//            synchronized (this) {
//                wait(10);
//            }
        } catch (JSONException ex) {
            System.out.println("JSONException on VoteLeaderListener ");
        } catch (IOException ex) {
            System.out.println("IOException on VoteLeaderListener ");
        } finally {
            timeOutThread.kill();
            System.out.println("thread finish");
            isRunning = false;
            synchronized (this) {
                notifyAll();
            }
        }
    }

    public void kill() {
        if (isRunning) {
            isRunning = false;
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            }
            synchronized (this) {
                notify();
            }
        }
    }
   
    public JSONObject getJSONMessage() {
        System.out.println("return tempJSONMsg");
        return tempJSONmsg ;
    }
   
    public static class TimeOutThread extends Thread {
        final long timeout;
        final VoteLeaderListener3 controlledObj;

        TimeOutThread(long timeout, VoteLeaderListener3 controlledObj) {
            setDaemon(true);
            this.timeout = timeout;
            this.controlledObj = controlledObj;
        }

        boolean isRunning = true;

        /**
         * If we done need the {@link TimeOutThread} thread, we may kill it.
         */
        public void kill() {
            isRunning = false;
            synchronized (this) {
                notify();
            }
        }

        /**
         * 
         */
        @Override
        public void run() {
            long deltaT = 0l;
            try {
                long start = System.currentTimeMillis();
                while (isRunning && deltaT < timeout) {
                    synchronized (this) {
                        wait(Math.max(100, timeout - deltaT));
                    }
                    deltaT = System.currentTimeMillis() - start;
                }
            } catch (InterruptedException e) {
                // If the thread is interrupted,
                // you may not want to kill the main thread,
                // but probably yes.
            } finally {
                isRunning = false;
            }
            controlledObj.kill();
        }
    }
   
}

