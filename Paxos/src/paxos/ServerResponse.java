/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paxos;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Ivan
 */
public class ServerResponse {
    public static String joinGameOK(int player_id) {
        JSONObject obj = new JSONObject();
        try{
            obj.put("status", "ok");
            obj.put("player_id", player_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString() ;
    }
    
    public static String joinGameAlreadyStarted() {
        JSONObject obj = new JSONObject();
        try{
            obj.put("status", "fail");
            obj.put("description", "game already started");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString() ;
    }
    
    public static String joinGameFailUsernameInvalid() {
        JSONObject obj = new JSONObject();
        try{
            obj.put("status", "fail");
            obj.put("description", "username invalid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj.toString() ;
    }
    
    public static String joinGameFailUserExists() {
        JSONObject obj = new JSONObject();
        try{
            obj.put("status", "fail");
            obj.put("description", "user exists");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj.toString() ;
    }
    
    public static String wrongRequestError() {
        JSONObject obj = new JSONObject();
        try{
            obj.put("status", "error");
            obj.put("description", "wrong request");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj.toString() ;
    }
}
