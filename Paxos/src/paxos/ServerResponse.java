/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paxos;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Ivan
 */
public class ServerResponse {
    
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
    
    public static String joinGameOK(int player_id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("status", "ok");
            obj.put("player_id", player_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString() ;
    }
    
    public static String joinGameAlreadyStarted() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("status", "fail");
            obj.put("description", "game already started");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString() ;
    }
    
    public static String joinGameFailUsernameInvalid() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("status", "fail");
            obj.put("description", "username invalid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj.toString() ;
    }
    
    public static String joinGameFailUserExists() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("status", "fail");
            obj.put("description", "user exists");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj.toString() ;
    }
    
    public static String wrongRequestError() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("status", "error");
            obj.put("description", "wrong request");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj.toString() ;
    }
    
    public static String startGame (ArrayList<String> friends){
        JSONObject obj = new JSONObject();
        try {
            obj.put("method", "start");
            obj.put("time", "day");
            obj.put("role", "werewolf");
            obj.put("friend", friends);
            obj.put("description", "game is started");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }
    
    public static String changePhase (String narration, int days){
        JSONObject obj = new JSONObject();
        try {
            obj.put("method", "change_phase");
            obj.put("time", "day");
            obj.put("days", days);
            obj.put("description", narration);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }
    
    public static String gameOver (String narration){
        JSONObject obj = new JSONObject();
        try {
            obj.put("method", "game_over");
            obj.put("winner", "werewolf");
            obj.put("description", narration);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }
    
}
