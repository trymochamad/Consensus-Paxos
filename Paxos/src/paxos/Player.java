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
    private int id;
    private int is_alive;
    private String address;
    private int port;
    private String role; /* werewolf atau penduduk */
    private String username;
    
    public Player(){
        
    }
    
    public Player(int id, String address, int port, String username){
        this.id = id;
        this.address = address;
        this.port = port;
        this.role = "penduduk"; /* default = penduduk. Setelah di random, set 2 menjadi werewolf */
        this.username = username;
    }
    
    public void setID(int id){this.id = id;}
    public int getID(){return id;}
    
    public void setPlayerAlive(){this.is_alive = 1;}
    public void setPlayerKilled(){this.is_alive = 0;}
    public boolean isAlive(){return is_alive == 1;}
    
    public void setAddress(String address){this.address = address;}
    public String getAddress(){return address;}
    
    public void setPort(int port){this.port = port;}
    public int getPort(){return port;}
    
    public void setRole(String role){this.role = role;}
    public String getRole(){return role;}
    
    public void setUsername(String username){this.username = username;}
    public String getUsername(){return username;}
    
}
