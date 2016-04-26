/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubessister;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Ivan
 */
public class ClientRequest {
    public static String joinRequest(String username) {
        JSONObject obj = new JSONObject();
        try{
            obj.put("type", "CONNECT");
            obj.put("method", "join");
            obj.put("username",username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obj.toString() ;
    }
}
