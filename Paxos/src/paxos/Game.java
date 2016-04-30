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
    private int day;
    private int status;
    /* 0: Waiting
     * 1: Playing
     * 2: Stopped
     */
    private ArrayList<Player> players;
    
    public Game(){
        status = 0;
        players = new ArrayList<Player>();
    }
    
    public void setDay(int day){this.day = day;}
    public int getDay(){return day;}
    public void nextDay(){this.day++;}
    
    public void setStatus(int status){this.status = status;}
    public int getStatus(){return status;}
    
    public void addPlayer(Player player) {this.players.add(player);}
    public ArrayList<Player> getPlayers() {return players;}
    
    public boolean foundPlayerWithID (int id) {
        boolean found = false;
        for(int i=0; i < players.size(); i++){
            Player player = players.get(i);
            if(player.getID() == id){
                found = true;
                break;
            }
        }
        return found;
    }
    
    public Player findPlayerWithID (int id) {
        for(int i=0; i < players.size(); i++){
            Player player = players.get(i);
            if(player.getID() == id)
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
                if(player.getID() == id){
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
        if(isAllPlayerReady() && players.size()>=6)
            status = 1;
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
    
}
