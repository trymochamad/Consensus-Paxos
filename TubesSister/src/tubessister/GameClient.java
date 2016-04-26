/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubessister;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.json.JSONException;
import org.json.JSONObject;

public class GameClient {

    public static void main(String args[]) throws IOException{
        InetAddress address=InetAddress.getLocalHost();
        Socket s1 = null;
        String line=  null;
        BufferedReader br = null;
        BufferedReader is = null;
        PrintWriter os = null;
        JSONObject jsonResponse = null;
        String myName = null;
        
        try {
            s1=new Socket(address, 9876); // You can use static final constant PORT_NUM
            br= new BufferedReader(new InputStreamReader(System.in));
            is=new BufferedReader(new InputStreamReader(s1.getInputStream()));
            os= new PrintWriter(s1.getOutputStream());
            //out = new OutputStreamWriter(s1.getOutputStream(), StandardCharsets.UTF_8);
        }
        catch (IOException e){
            e.printStackTrace();
            System.err.print("IO Exception");
        }

        System.out.println("Client Address : "+address);
        System.out.println("Enter Data to echo Server ( Enter QUIT to end):");

        String response=null;
        try{
            boolean isJoinGame = false;
            while(!isJoinGame){
                System.out.print("Enter username: ");
                line = br.readLine(); //read username first
                os.println(ClientRequest.joinRequest(line).toString()); //send joinGame
                os.flush(); //send message to server

                System.out.println("Waiting...");

                response = is.readLine(); //read response from server about join game
                jsonResponse = new JSONObject(response);
                String status = jsonResponse.optString("status");
                if(status.equals("ok")){
                    myName = line;
                    isJoinGame = true;
                } else if(status.equals("fail")) {
                    System.out.println(jsonResponse.optString("description"));
                } else if(status.equals("error")) {
                    System.out.println(jsonResponse.optString("description"));
                }
            } //end while !isJoinGame
            
            //Playing Game
            while(line.compareTo("QUIT")!=0){
                os.println(ClientRequest.joinRequest(line).toString());
                os.flush();
                response=is.readLine();
                System.out.println("Server Response : "+response);
                line=br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Socket read Error");
        } catch (JSONException e){
            e.printStackTrace();
        }
        finally{

            is.close();os.close();br.close();s1.close();
                    System.out.println("Connection Closed");

        }
    }    
}
