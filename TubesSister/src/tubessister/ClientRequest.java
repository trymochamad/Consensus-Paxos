/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubessister;

import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONArray;
/**
 *
 * @author Ivan
 */
public class ClientRequest {
    
    public static String statusOK() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("status", "ok");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString() ;
    }
    
    public static String statusFail (String description) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("status", "fail");
            obj.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString() ;
    }
    
    public static String statusError (String description) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("status", "error");
            obj.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString() ;
    }
    
    public static String joinRequest (String username,String address,int port) {
        JSONObject obj = new JSONObject();
        try{
            obj.put("type", "CONNECT");
            obj.put("method", "join");
            obj.put("username",username);
            obj.put("udp_address",address);
            obj.put("udp_port",port);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj.toString() ;
    }
    
    public static String leaveRequest () {
        JSONObject obj = new JSONObject();
        try{
            obj.put("method", "leave");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj.toString() ;
    }
    
    public static String readyUp () {
        JSONObject obj = new JSONObject();
        try{
            obj.put("method", "ready");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj.toString() ;
    }
    
    public static String listClient () {
        JSONObject obj = new JSONObject();
        try{
            obj.put("method", "client_address");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj.toString() ;
    }
    
    public static String paxosPrepareProposal (int player_id) {
        JSONObject obj = new JSONObject();
        try{
            obj.put("method", "prepare_proposal");
            obj.put("proposal_id", "(1," + player_id + ")");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj.toString() ;
    }
    
    public static String paxosAcceptProposal (int proposal_id, int player_id, int kpu_id) {
        int[] prop_id = new int[2];
        prop_id[0] = proposal_id;
        prop_id[1] = player_id;
        JSONObject obj = new JSONObject();
        try{
            obj.put("method", "accept_proposal");
            JSONArray list = new JSONArray() ;
            list.put(proposal_id);
            list.put(player_id);
            obj.put("proposal_id", prop_id);
            obj.put("kpu_id", kpu_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj.toString() ;
    }
    
    public static String okResponsePrepare(int previous_accepted_id) {
        JSONObject obj = new JSONObject();
        try{
            obj.put("status", "ok");
            obj.put("description", "accepted");
            if (previous_accepted_id != 0 ) obj.put("previous_accepted", previous_accepted_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj.toString() ;
    }
    
    public static  String okResponseAccepted() {
      JSONObject obj = new JSONObject();
        try{
            obj.put("status", "ok");
            obj.put("description", "accepted");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj.toString() ;   
    }
    
    
    
    public static String clientAcceptProposal (int player_id) {
        JSONObject obj = new JSONObject();
        try{
            obj.put("method", "prepare_proposal");
            obj.put("kpu_id", player_id);
            obj.put("Description","Kpu is selected");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj.toString() ;
    }
    
    public static String killWerewolfVote (int player_id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("method", "vote_werewolf");
            obj.put("player_id", player_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj.toString() ;
    }
    
    public static String killCivilianVote (int player_id){
        JSONObject obj = new JSONObject();
        try {
            obj.put("method", "vote_civilian");
            obj.put("player_id", player_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }
   
  
   public static String sendKPUtoServer(int kpu_id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("method", "accepted_proposal");
            obj.put("kpu_id", kpu_id);
            obj.put("description","kpu is selected");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();   
   }
    
}
