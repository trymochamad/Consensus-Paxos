/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paxos;

/**
 *
 * @author Ivan
 */
public class Player {
    private int player_id;
    private int is_alive;
    private String address;
    private int port;
    private String role; /* werewolf atau penduduk */
    private String username;
    private Boolean ready;
    
    public Player(){
        
    }
    
    public Player(int player_id, String address, int port, String username){
        this.player_id = player_id;
        this.is_alive = 1;
        this.address = address;
        this.port = port;
        this.role = "civilian"; /* default = penduduk. Setelah di random, set 2 menjadi werewolf */
        this.username = username;
        ready = false;
    }
    
    public Player (Player another) {
        this.player_id = another.player_id;
        this.is_alive = another.is_alive;
        this.address = another.address;
        this.port = another.port;
        this.role = another.role;
        this.username = another.username;
        this.ready = another.ready;
    }
    
    public void setID(int id){this.player_id = id;}
    public int getID(){return player_id;}
    
    public void setPlayerAlive(){this.is_alive = 1;}
    public void setPlayerKilled(){this.is_alive = 0;}
    public int getIs_alive(){return is_alive;}
    
    public void setAddress(String address){this.address = address;}
    public String getAddress(){return address;}
    
    public void setPort(int port){this.port = port;}
    public int getPort(){return port;}
    
    public void setRole(String role){this.role = role;}
    public String getRole(){return role;}
    
    public void setUsername(String username){this.username = username;}
    public String getUsername(){return username;}
    
    public void setReady(Boolean ready){this.ready = ready;}
    public boolean getReady(){return ready;}

}
