/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubessister;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author tama
 */
public class VoteKill {
    
    int[] voteResult ;
    int numP ;
    int totalVote ;
    VoteKill(int num_player) {
        totalVote = 0;
        numP = num_player ;
        voteResult = new int[num_player+1];
        for (int i=0;i<=num_player;i++) voteResult[i]=0;
    }
    
    public void votePlayer(int id_player) {
        voteResult[id_player]++ ;
        totalVote++ ;
    }
    
    public boolean isFindToKill() {
        int x = findMaxID() ;
        int curVote = voteResult[x] ;
        int counter = 0;
        for (int i=1;i<=numP;i++) {
            if (voteResult[i]==curVote) counter++ ;
        }
        if (counter==1) return true ;
        else return false ;
    }
    
    public int findMaxID() {
        int x =1;
        int max = voteResult[x] ;
        for (int i=2;i<=numP;i++) {
            if (max<voteResult[i]) {
                max = voteResult[i] ;
                x = i;
            }
        }
        return x ;
    }
    
    public String getJSONVoteSuccess() throws JSONException {
        JSONObject obj = new JSONObject() ;
        int p = findMaxID();
        obj.put("method","vote_result_civilian");
        obj.put("vote_status",1);
        obj.put("player_kiled",p);
        JSONArray l = new JSONArray() ;
        for (int i=1;i<=numP;i++) {
            JSONArray ltemp = new JSONArray() ;
            ltemp.put(i);
            ltemp.put(voteResult[i]);
            l.put(ltemp);
        }
        obj.put("vote_result",l);
        return obj.toString();
    }
    
    public String getJSONVoteUnsuccess() throws JSONException {
        JSONObject obj = new JSONObject() ;
        int p = findMaxID();
        obj.put("method","vote_result");
        obj.put("vote_status",-1);
        JSONArray l = new JSONArray() ;
        for (int i=1;i<=numP;i++) {
            JSONArray ltemp = new JSONArray() ;
            ltemp.put(i);
            ltemp.put(voteResult[i]);
            l.put(ltemp);
        }
        obj.put("vote_result",l);
        return obj.toString();
    }
    
    public String getJSONVoteWerewolf() throws JSONException {
        JSONObject obj = new JSONObject() ;
        int p = findMaxID();
        obj.put("method","vote_result_werewolf");
        obj.put("vote_status",1);
        obj.put("player_kiled",p);
        JSONArray l = new JSONArray() ;
        for (int i=1;i<=numP;i++) {
            JSONArray ltemp = new JSONArray() ;
            ltemp.put(i);
            ltemp.put(voteResult[i]);
            l.put(ltemp);
        }
        obj.put("vote_result",l);
        return obj.toString();
    }
    
    public void printVote() {
        for (int i=1;i<=numP;i++) System.out.print(voteResult[i]+" ");
        System.out.println("");
    }
}
