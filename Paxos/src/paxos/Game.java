/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paxos;

import java.util.ArrayList;

/**
 *
 * @author Ivan
 */
public class Game {
    
    public static class Vote {
        int player_id;
        int count;
    }
    
    private int day;
    private int status;
    /* 0: Waiting
     * 1: Playing
     * 2: Stopped
     */
    private ArrayList<Player> players;
    
    private boolean vote_leader_failed = false;
    private boolean vote_leader_finish = false;
    int leader = -1;
    //int[] vote_leader;
    private ArrayList<Vote> vote_leader;
    private int vote_leader_count;
    
    private ArrayList<Vote> vote_werewolf;
    private boolean vote_werewolf_finish = false;
    private boolean vote_werewolf_success = false;
    private int vote_werewolf_count = 0;
    
    private ArrayList<Vote> vote_civilian;
    private boolean vote_civilian_finish = false;
    private boolean vote_civilian_success = false;
    private int vote_civilian_count = 0;
    
    
    public Game(){
        day = 0;
        status = 0;
        players = new ArrayList<Player>();
    }
    
    public void setDay(int day){this.day = day;}
    public int getDay(){return day;}
    public void nextDay(){
        this.day++;
        leader = -1;
        vote_leader = new ArrayList<Vote>();
        vote_leader_count = 0;
        
        vote_werewolf = new ArrayList<Vote>();
        vote_werewolf_finish = false;
        vote_werewolf_count = 0;
        
        vote_civilian = new ArrayList<Vote>();
        vote_civilian_finish = false;
        vote_civilian_count = 0;
    }
    
    public void setStatus(int status){this.status = status;}
    public int getStatus(){return status;}
    
    public void addPlayer(Player player) {this.players.add(player);}
    public ArrayList<Player> getPlayers() {return players;}
    
    public boolean foundPlayerWithID (int id) {
        boolean found = false;
        for(int i=0; i < players.size(); i++){
            Player player = players.get(i);
            if(player.getPlayer_id() == id){
                found = true;
                break;
            }
        }
        return found;
    }
    
    public Player findPlayerWithID (int id) {
        for(int i=0; i < players.size(); i++){
            Player player = players.get(i);
            if(player.getPlayer_id() == id)
                return player;
        }
        return null;
    }
    
    public void setPlayerAlive (int id, int status) {
        Player player = findPlayerWithID(id);
        if(player != null){
            player.setPlayerAlive();
        }
    }
    
    public void setPlayerKilled (int id, int status) {
        Player player = findPlayerWithID(id);
        if(player != null){
            player.setPlayerKilled();
        }
    }
    
    public void removePlayerWithID (int id) {
        if(id != -1){
            int position = -1;
            for(int i=0; i < players.size(); i++){
                Player player = players.get(i);
                if(player.getPlayer_id() == id){
                    position = i;
                    break;
                }   
            }
            if(position != -1){//found
                players.remove(position);
            }
        }
    }
    
    public void setPlayerWithIDAsWerewolf (int id) {
        Player player = findPlayerWithID(id);
        if(player != null){
            player.setRole("werewolf");
        }
    }
    
    public boolean isUsernameExist (String username) {
        boolean exist = false;
        for(int i=0; i < players.size(); i++){
            Player player = players.get(i);
            if(player.getUsername().equals(username)){
                exist = true;
                break;
            }
        }
        return exist;
    }
    
    public int getNewID(){
        int i = 1;
        boolean found = false;
        while(!found){
            if(!foundPlayerWithID(i)){
                found = true;
            } else {
                i++;
            }
        }
        return i;
    }
    
    public void setPlayerReady (int id) {
        Player player = findPlayerWithID(id);
        if(player != null)
            player.setReady(true);
        if(isAllPlayerReady() && players.size()>=6){
            status = 1;
            randomWerewolf();
        }
        System.out.println("Player id : "+id+" | size : "+players.size()+" | status : "+status);
    }
    
    private boolean isAllPlayerReady () {
        boolean allReady = true;
        for(int i=0; i < players.size(); i++){
            Player player = players.get(i);
            if(player.getReady() == false){
                allReady = false;
                break;
            }
        }
        return allReady;
    }
    
    private void randomWerewolf () {
        //Select 2 players as werewolf randomly
        int werewolf = 0;
        while(werewolf != 2){
            int rand = (int)(Math.random() * players.size());
            if (players.get(rand).getRole() != "werewolf"){
                players.get(rand).setRole("werewolf");
                werewolf++;
            }
        }
    }
    
    public ArrayList<String> getWerewolfFriends () {
        ArrayList<String> friends = new ArrayList<String>();
        for(int i=0; i < players.size(); i++){
            Player player = players.get(i);
            if(player.getRole().equals("werewolf")){
                friends.add(player.getUsername());
            }
        }
        return friends;
    }
    
    private int getPlayerWithIDIdx(ArrayList<Vote> vote_leader, int player_id) {
        int idx = -1;
        for(int i=0; i<vote_leader.size(); i++){
            if(vote_leader.get(i).player_id == player_id){
                idx = i;
                break;
            }
        }
        return idx;
    }
    
    public int getActivePlayers() {
        int count = 0;
        for(int i=0; i < players.size(); i++){
            if(players.get(i).getIs_alive() == 1){
                count++;
            }
        }
        return count;
    }
    
    
    public static int getMax(ArrayList<Vote> array) {
        if(array.size() < 1)
            return -1;
        int max = array.get(0).count;
        for(int i=1; i<array.size(); i++){
            if(max < array.get(i).count)
                max = array.get(i).count;
        }
        return max;
    }
    
    public static int getIdxMax(ArrayList<Vote> array) {
        if(array.size() < 1)
            return -1;
        int idx_max = 0;
        for(int i=1; i<array.size(); i++){
            if(array.get(idx_max).count < array.get(i).count)
                idx_max = array.get(i).count;
        }
        return idx_max;
    }
    
    public static int getMaxCount(ArrayList<Vote> array, int max) {
        int count = 0;
        for(int i=1; i<array.size(); i++){
            if(array.get(i).count == max)
                count++;
        }
        return count;
    }
    
    
    /* VOTE LEADER */
    
    public boolean getVoteLeaderFailed(){return vote_leader_failed;}
    public boolean getVoteLeaderFinish(){return vote_leader_finish;}
    
    public void voteLeader (int kpu_id) {
        if(kpu_id != -1){
            int idx = getPlayerWithIDIdx(vote_leader,kpu_id);
            int count = 0;
            if (idx != -1) {
                Vote vote = vote_leader.get(idx);
                vote.count++;
                count = vote.count;
            } else {
                Vote vote = new Vote();
                vote.player_id = kpu_id;
                vote.count = 1;
                vote_leader.add(vote);
            }
            vote_leader_count++;
            System.out.println("count kpu_id" + count + " " + kpu_id);
            if(count >= (getPlayers().size()-2)/2){//sampe setengah
                leader = kpu_id;
                vote_leader_finish = true;
            }   
        }     
        /*else if(vote_leader_count >= getPlayers().size()-2){
            leader = getIdxMax(vote_leader);
            vote_leader_finish = true;
        }*/
    }
    
    public int getLeader() {return leader;}    
    public void setLeader(int leader) {this.leader = leader;}    
    
    /* WEREWOLF */
    
    public boolean getVoteWerewolfSuccess(){return vote_werewolf_success;}
    public boolean getVoteWerewolfFinish(){return vote_werewolf_finish;}
    
    public void voteKillWerewolf (int player_id){
        Player player = findPlayerWithID(player_id);
        player.setPlayerKilled();
    }
    
    /* VOTE CIVILIAN */
    
    public boolean getVoteCivilianSuccess(){return vote_civilian_success;}
    public boolean getVoteCivilianFinish(){return vote_civilian_finish;}
    
    public void voteKillCivilian (int player_id){
        Player player = findPlayerWithID(player_id);
        player.setPlayerKilled();
    }
}
