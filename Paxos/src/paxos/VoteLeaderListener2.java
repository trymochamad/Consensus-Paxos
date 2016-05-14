/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paxos;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
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
public class VoteLeaderListener2 extends Thread {
    
    Thread interrupter;
    Thread target;
    long timeout;
    boolean success;
    boolean forceStop;

    CyclicBarrier barrier;
    
    
    /**
     * 
     * @param target The Runnable target to be executed
     * @param timeout The time in milliseconds before target will be interrupted or stopped
     * @param forceStop If true, will Thread.stop() this target instead of just interrupt() 
     */
    public VoteLeaderListener2(Runnable target, long timeout, boolean forceStop) {      
        this.timeout = timeout;
        this.forceStop = forceStop;

        this.target = new Thread(target);       
        this.interrupter = new Thread(new Interrupter());

        barrier = new CyclicBarrier(2); // There will always be just 2 threads waiting on this barrier
    }
    
    public boolean execute() throws InterruptedException {  

        // Start target and interrupter
        target.start();
        interrupter.start();

        // Wait for target to finish or be interrupted by interrupter
        target.join();  

        interrupter.interrupt(); // stop the interrupter    
        try {
            barrier.await(); // Need to wait on this barrier to make sure status is set
        } catch (BrokenBarrierException e) {
            // Something horrible happened, assume we failed
            success = false;
        } 

        return success; // status is set in the Interrupter inner class
    }
    
    private class Interrupter implements Runnable {

        Interrupter() {}

        public void run() {
            try {
                Thread.sleep(timeout); // Wait for timeout period and then kill this target
                if (forceStop) {
                  target.stop(); // Need to use stop instead of interrupt since we're trying to kill this thread
                }
                else {
                    target.interrupt(); // Gracefully interrupt the waiting thread
                }
                System.out.println("done");             
                success = false;
            } catch (InterruptedException e) {
                success = true;
            }


            try {
                barrier.await(); // Need to wait on this barrier
            } catch (InterruptedException e) {
                // If the Child and Interrupter finish at the exact same millisecond we'll get here
                // In this weird case assume it failed
                success = false;                
            } 
            catch (BrokenBarrierException e) {
                // Something horrible happened, assume we failed
                success = false;
            }

        }

    }
    
    
    
    
    private volatile Thread t;
    private String threadName;
    private BufferedReader is ;
    private volatile boolean stopRequested;
    
    private JSONObject tempJSONmsg = null ;
      
    VoteLeaderListener2( String name, BufferedReader is_){
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
    }
   
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

