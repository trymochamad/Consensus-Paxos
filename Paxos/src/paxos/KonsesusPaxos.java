
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paxos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.SwingWorker;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mochamadtry
 */

public class KonsesusPaxos extends javax.swing.JFrame {
    
    private Socket cliSocket;
    public DataOutputStream outToServer;
    public BufferedReader inFromServer;
    private String currentMessage;
    private String Nickname;
    private int idUser;

    /**
     * Creates new form KonsesusPaxos
     */
    public KonsesusPaxos() {
        initComponents();
        MenuAwal.setVisible(true);
        Register.setVisible(false);
        GamePlaySiang.setVisible(false);
        GamePlayMalam.setVisible(false);
        GagalLogin1.setVisible(false);
        GagalLogin2.setVisible(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        MenuAwal = new javax.swing.JPanel();
        PlayGameButton = new javax.swing.JLabel();
        LogoGame = new javax.swing.JLabel();
        GamePlaySiang = new javax.swing.JPanel();
        PlayGameButton1 = new javax.swing.JLabel();
        ServerAddress2 = new javax.swing.JLabel();
        Register = new javax.swing.JPanel();
        ServerAddress1 = new javax.swing.JLabel();
        ServerAddress = new javax.swing.JLabel();
        InsertServerAddress = new javax.swing.JTextField();
        Port = new javax.swing.JLabel();
        InsertPort = new javax.swing.JTextField();
        Port1 = new javax.swing.JLabel();
        InsertNickname = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        GagalLogin1 = new javax.swing.JPanel();
        LoginFailedPanel1 = new javax.swing.JPanel();
        LoginFailed1 = new javax.swing.JLabel();
        LoginFailedButton1 = new javax.swing.JButton();
        LoginFailed4 = new javax.swing.JLabel();
        GagalLogin2 = new javax.swing.JPanel();
        LoginFailedPanel2 = new javax.swing.JPanel();
        LoginFailed2 = new javax.swing.JLabel();
        LoginFailedButton2 = new javax.swing.JButton();
        LoginFailed3 = new javax.swing.JLabel();
        GamePlayMalam = new javax.swing.JPanel();
        PlayGameButton2 = new javax.swing.JLabel();
        ServerAddress3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLayeredPane1.setLayout(new javax.swing.OverlayLayout(jLayeredPane1));

        MenuAwal.setBackground(new java.awt.Color(153, 204, 255));
        MenuAwal.setMinimumSize(new java.awt.Dimension(960, 560));

        PlayGameButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paxos/playbutton.png"))); // NOI18N
        PlayGameButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                PlayGameButtonMousePressed(evt);
            }
        });

        LogoGame.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paxos/LogoGame.png"))); // NOI18N

        javax.swing.GroupLayout MenuAwalLayout = new javax.swing.GroupLayout(MenuAwal);
        MenuAwal.setLayout(MenuAwalLayout);
        MenuAwalLayout.setHorizontalGroup(
            MenuAwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuAwalLayout.createSequentialGroup()
                .addContainerGap(405, Short.MAX_VALUE)
                .addGroup(MenuAwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuAwalLayout.createSequentialGroup()
                        .addComponent(LogoGame)
                        .addGap(339, 339, 339))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuAwalLayout.createSequentialGroup()
                        .addComponent(PlayGameButton)
                        .addGap(388, 388, 388))))
        );
        MenuAwalLayout.setVerticalGroup(
            MenuAwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuAwalLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(LogoGame)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 206, Short.MAX_VALUE)
                .addComponent(PlayGameButton)
                .addGap(92, 92, 92))
        );

        jLayeredPane1.add(MenuAwal);

        GamePlaySiang.setBackground(new java.awt.Color(153, 204, 255));

        PlayGameButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paxos/sun (2).png"))); // NOI18N
        PlayGameButton1.setMaximumSize(new java.awt.Dimension(230, 230));
        PlayGameButton1.setMinimumSize(new java.awt.Dimension(230, 230));
        PlayGameButton1.setPreferredSize(new java.awt.Dimension(230, 239));

        ServerAddress2.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        ServerAddress2.setText("Siang Hari");

        javax.swing.GroupLayout GamePlaySiangLayout = new javax.swing.GroupLayout(GamePlaySiang);
        GamePlaySiang.setLayout(GamePlaySiangLayout);
        GamePlaySiangLayout.setHorizontalGroup(
            GamePlaySiangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GamePlaySiangLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(PlayGameButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ServerAddress2)
                .addContainerGap(996, Short.MAX_VALUE))
        );
        GamePlaySiangLayout.setVerticalGroup(
            GamePlaySiangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GamePlaySiangLayout.createSequentialGroup()
                .addGroup(GamePlaySiangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GamePlaySiangLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(PlayGameButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(GamePlaySiangLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(ServerAddress2)))
                .addContainerGap(571, Short.MAX_VALUE))
        );

        jLayeredPane1.add(GamePlaySiang);

        Register.setBackground(new java.awt.Color(153, 204, 255));

        ServerAddress1.setFont(new java.awt.Font("Verdana", 0, 48)); // NOI18N
        ServerAddress1.setText("Register");

        ServerAddress.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ServerAddress.setText("Server:");

        InsertServerAddress.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        InsertServerAddress.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        InsertServerAddress.setToolTipText("");

        Port.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Port.setText("Port:");

        InsertPort.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        InsertPort.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        InsertPort.setToolTipText("");

        Port1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Port1.setText("Nickname:");

        InsertNickname.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        InsertNickname.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        InsertNickname.setToolTipText("");

        jButton1.setText("Join Game");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout RegisterLayout = new javax.swing.GroupLayout(Register);
        Register.setLayout(RegisterLayout);
        RegisterLayout.setHorizontalGroup(
            RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RegisterLayout.createSequentialGroup()
                .addContainerGap(423, Short.MAX_VALUE)
                .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(InsertNickname, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(RegisterLayout.createSequentialGroup()
                                .addComponent(Port1)
                                .addGap(229, 229, 229))
                            .addGroup(RegisterLayout.createSequentialGroup()
                                .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ServerAddress)
                                    .addComponent(Port))
                                .addGap(88, 88, 88)
                                .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(InsertServerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(InsertPort, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(RegisterLayout.createSequentialGroup()
                        .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(RegisterLayout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(RegisterLayout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(ServerAddress1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(418, 418, 418))
        );
        RegisterLayout.setVerticalGroup(
            RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RegisterLayout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(ServerAddress1)
                .addGap(40, 40, 40)
                .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InsertServerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ServerAddress))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InsertPort, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Port))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(RegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InsertNickname, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Port1))
                .addGap(38, 38, 38)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(238, 238, 238))
        );

        jLayeredPane1.add(Register);

        GagalLogin1.setBackground(new java.awt.Color(153, 204, 255));
        GagalLogin1.setForeground(new java.awt.Color(153, 204, 255));
        GagalLogin1.setMinimumSize(new java.awt.Dimension(960, 560));
        GagalLogin1.setOpaque(false);

        LoginFailedPanel1.setBackground(new java.awt.Color(204, 255, 255));

        LoginFailed1.setFont(new java.awt.Font("Prestige Elite Std", 1, 18)); // NOI18N
        LoginFailed1.setText("Gagal Register :");

        LoginFailedButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LoginFailedButton1.setText("OK");
        LoginFailedButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginFailedButton1ActionPerformed(evt);
            }
        });

        LoginFailed4.setFont(new java.awt.Font("Prestige Elite Std", 1, 18)); // NOI18N
        LoginFailed4.setText("IP Address atau Port Salah");

        javax.swing.GroupLayout LoginFailedPanel1Layout = new javax.swing.GroupLayout(LoginFailedPanel1);
        LoginFailedPanel1.setLayout(LoginFailedPanel1Layout);
        LoginFailedPanel1Layout.setHorizontalGroup(
            LoginFailedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginFailedPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LoginFailedButton1)
                .addGap(48, 48, 48))
            .addGroup(LoginFailedPanel1Layout.createSequentialGroup()
                .addGroup(LoginFailedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LoginFailedPanel1Layout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addComponent(LoginFailed1))
                    .addGroup(LoginFailedPanel1Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(LoginFailed4)))
                .addContainerGap(139, Short.MAX_VALUE))
        );
        LoginFailedPanel1Layout.setVerticalGroup(
            LoginFailedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginFailedPanel1Layout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addComponent(LoginFailed1)
                .addGap(37, 37, 37)
                .addComponent(LoginFailed4)
                .addGap(37, 37, 37)
                .addComponent(LoginFailedButton1)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout GagalLogin1Layout = new javax.swing.GroupLayout(GagalLogin1);
        GagalLogin1.setLayout(GagalLogin1Layout);
        GagalLogin1Layout.setHorizontalGroup(
            GagalLogin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GagalLogin1Layout.createSequentialGroup()
                .addGap(302, 302, 302)
                .addComponent(LoginFailedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(341, Short.MAX_VALUE))
        );
        GagalLogin1Layout.setVerticalGroup(
            GagalLogin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GagalLogin1Layout.createSequentialGroup()
                .addContainerGap(228, Short.MAX_VALUE)
                .addComponent(LoginFailedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(191, 191, 191))
        );

        jLayeredPane1.add(GagalLogin1);

        GagalLogin2.setMinimumSize(new java.awt.Dimension(960, 560));
        GagalLogin2.setOpaque(false);

        LoginFailedPanel2.setBackground(new java.awt.Color(204, 255, 255));

        LoginFailed2.setFont(new java.awt.Font("Prestige Elite Std", 1, 18)); // NOI18N
        LoginFailed2.setText("Gagal Register: ");

        LoginFailedButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LoginFailedButton2.setText("OK");
        LoginFailedButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginFailedButton2ActionPerformed(evt);
            }
        });

        LoginFailed3.setFont(new java.awt.Font("Prestige Elite Std", 1, 18)); // NOI18N
        LoginFailed3.setText("Nickname Sudah Digunakan");

        javax.swing.GroupLayout LoginFailedPanel2Layout = new javax.swing.GroupLayout(LoginFailedPanel2);
        LoginFailedPanel2.setLayout(LoginFailedPanel2Layout);
        LoginFailedPanel2Layout.setHorizontalGroup(
            LoginFailedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginFailedPanel2Layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(LoginFailed2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(LoginFailedPanel2Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(LoginFailed3)
                .addContainerGap(116, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginFailedPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LoginFailedButton2)
                .addGap(47, 47, 47))
        );
        LoginFailedPanel2Layout.setVerticalGroup(
            LoginFailedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginFailedPanel2Layout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(LoginFailed2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LoginFailed3)
                .addGap(60, 60, 60)
                .addComponent(LoginFailedButton2)
                .addContainerGap())
        );

        javax.swing.GroupLayout GagalLogin2Layout = new javax.swing.GroupLayout(GagalLogin2);
        GagalLogin2.setLayout(GagalLogin2Layout);
        GagalLogin2Layout.setHorizontalGroup(
            GagalLogin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GagalLogin2Layout.createSequentialGroup()
                .addGap(339, 339, 339)
                .addComponent(LoginFailedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(342, Short.MAX_VALUE))
        );
        GagalLogin2Layout.setVerticalGroup(
            GagalLogin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GagalLogin2Layout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(LoginFailedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(236, Short.MAX_VALUE))
        );

        jLayeredPane1.add(GagalLogin2);

        GamePlayMalam.setBackground(new java.awt.Color(153, 204, 255));

        PlayGameButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paxos/moon (1).png"))); // NOI18N
        PlayGameButton2.setMaximumSize(new java.awt.Dimension(230, 230));
        PlayGameButton2.setMinimumSize(new java.awt.Dimension(230, 230));
        PlayGameButton2.setPreferredSize(new java.awt.Dimension(230, 239));

        ServerAddress3.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        ServerAddress3.setText("Malam Hari");

        javax.swing.GroupLayout GamePlayMalamLayout = new javax.swing.GroupLayout(GamePlayMalam);
        GamePlayMalam.setLayout(GamePlayMalamLayout);
        GamePlayMalamLayout.setHorizontalGroup(
            GamePlayMalamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GamePlayMalamLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(PlayGameButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ServerAddress3)
                .addContainerGap(980, Short.MAX_VALUE))
        );
        GamePlayMalamLayout.setVerticalGroup(
            GamePlayMalamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GamePlayMalamLayout.createSequentialGroup()
                .addGroup(GamePlayMalamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GamePlayMalamLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(PlayGameButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(GamePlayMalamLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(ServerAddress3)))
                .addContainerGap(571, Short.MAX_VALUE))
        );

        jLayeredPane1.add(GamePlayMalam);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1135, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void createSocket(){
        try {
            cliSocket = new Socket(InsertServerAddress.getText(), Integer.parseInt(InsertPort.getText()));
            outToServer = new DataOutputStream(cliSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(cliSocket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(KonsesusPaxos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void sendToServer(String message){
        try {
            outToServer.writeBytes(message + '\n');
        } catch (IOException ex) {
            Logger.getLogger(KonsesusPaxos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String receiveFromServer(){
        String message = "";
        try {
            message = inFromServer.readLine();
        } catch (IOException ex) {
            Logger.getLogger(KonsesusPaxos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    } 
    
    
    private void LoginFailedButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginFailedButton1ActionPerformed
        // TODO add your handling code here:
        GagalLogin1.setVisible(false);
    }//GEN-LAST:event_LoginFailedButton1ActionPerformed

    private void LoginFailedButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginFailedButton2ActionPerformed
        // TODO add your handling code here:
        GagalLogin2.setVisible(false);
    }//GEN-LAST:event_LoginFailedButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void PlayGameButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlayGameButtonMousePressed
        // TODO add your handling code here:
        Register.setVisible(true);
        MenuAwal.setVisible(false);
    }//GEN-LAST:event_PlayGameButtonMousePressed

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        // TODO add your handling code here:
        if (!(InsertPort.getText().equals("") || InsertServerAddress.getText().equals("") || InsertNickname.getText().equals("") || InsertPort.getText().contains(",") || InsertServerAddress.getText().contains(",") || InsertNickname.getText().contains(","))){
            Nickname = InsertNickname.getText();
            createSocket();

            sendToServer(Nickname);
            currentMessage = receiveFromServer();
            String[] loginStatus = currentMessage.split(" ");
            if (loginStatus[0].equals("loginFailed")){
                GagalLogin2.setVisible(true);
            }
            else if (loginStatus[0].equals("loginSuccess")){
                String userId = loginStatus[1];
                idUser = Integer.parseInt(userId);
                MenuAwal.setVisible(false);
                GamePlayMalam.setVisible(true);
                GamePlaySiang.setVisible(false);
              

                /*timer = new Timer(100, new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Data yang dikirim ke server
                        sendToServer("listRoom");
                        String cm = currentMessage;
                        // Data yang diterima dari server
                        currentMessage = receiveFromServer();
                        System.out.println("Sudah kekirim pesan ini: " + currentMessage);
                        if (cm.equals(currentMessage)){
                            //do nothing
                        }else{
                            String[] srvSentence = currentMessage.split(" ");
                            String roomlist = "";

                            if (srvSentence.length != 1){
                                int j = Integer.parseInt(srvSentence[1]);
                                System.out.println(roomlist);
                                RoomNameList = new ArrayList<String>();
                                RoomMemberList = new ArrayList<Integer>();
                                RoomCapacityList = new ArrayList<Integer>();
                                RoomIDList = new ArrayList<Integer>();
                                for(int k = 0; k < j; k++){
                                    String _roomname = srvSentence[k*4 + 2];
                                    String _roommember = srvSentence[k*4 + 3];
                                    String _roomcapacity = srvSentence[k*4 + 4];
                                    String _roomID = srvSentence[k*4 + 5];
                                    RoomNameList.add(_roomname);
                                    RoomMemberList.add(Integer.parseInt(_roommember));
                                    RoomCapacityList.add(Integer.parseInt(_roomcapacity));
                                    RoomIDList.add(Integer.parseInt(_roomID));
                                }
                                InsideChooseRoomPane.removeAll();
                                InsideChooseRoomPane.setLayout(new BoxLayout(InsideChooseRoomPane, BoxLayout.X_AXIS));
                                InsideChooseRoomPane.setBackground(new Color(153,204,255));
                                ChooseRoomPane.setViewportView(InsideChooseRoomPane);
                                for (int i = 0; i < RoomNameList.size(); i++){
                                    JPanel panelRoom = new JPanel();
                                    final String crn = RoomNameList.get(i);
                                    final int crnid = RoomIDList.get(i);
                                    panelRoom.setLayout(new BoxLayout(panelRoom, BoxLayout.Y_AXIS));
                                    panelRoom.setBackground(new Color(153,204,255));
                                    JLabel doorRoom = new JLabel(crn+"door");
                                    doorRoom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/clientgomoku/door.png")));
                                    doorRoom.setText("");
                                    doorRoom.addMouseListener(new java.awt.event.MouseAdapter() {
                                        public void mousePressed(java.awt.event.MouseEvent evt) {
                                            Roomname = crn;
                                            RoomID = crnid;
                                            doorRoomMousePressed(evt);
                                        }
                                    });
                                    JLabel namaRoom = new JLabel("\n      "+RoomNameList.get(i)+" ("+RoomMemberList.get(i)+"/"+RoomCapacityList.get(i)+")");
                                    panelRoom.add(doorRoom);
                                    panelRoom.add(namaRoom);
                                    InsideChooseRoomPane.add(panelRoom);
                                    ChooseRoomPane.repaint();
                                    ChooseRoomPane.revalidate();
                                }
                            }
                        }
                    }
                });
                timer.start();  */
            }else{
                GagalLogin1.setVisible(true);
            }
        }
    }//GEN-LAST:event_jButton1MousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KonsesusPaxos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KonsesusPaxos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KonsesusPaxos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KonsesusPaxos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KonsesusPaxos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel GagalLogin1;
    private javax.swing.JPanel GagalLogin2;
    private javax.swing.JPanel GamePlayMalam;
    private javax.swing.JPanel GamePlaySiang;
    private javax.swing.JTextField InsertNickname;
    private javax.swing.JTextField InsertPort;
    private javax.swing.JTextField InsertServerAddress;
    private javax.swing.JLabel LoginFailed1;
    private javax.swing.JLabel LoginFailed2;
    private javax.swing.JLabel LoginFailed3;
    private javax.swing.JLabel LoginFailed4;
    private javax.swing.JButton LoginFailedButton1;
    private javax.swing.JButton LoginFailedButton2;
    private javax.swing.JPanel LoginFailedPanel1;
    private javax.swing.JPanel LoginFailedPanel2;
    private javax.swing.JLabel LogoGame;
    private javax.swing.JPanel MenuAwal;
    private javax.swing.JLabel PlayGameButton;
    private javax.swing.JLabel PlayGameButton1;
    private javax.swing.JLabel PlayGameButton2;
    private javax.swing.JLabel Port;
    private javax.swing.JLabel Port1;
    private javax.swing.JPanel Register;
    private javax.swing.JLabel ServerAddress;
    private javax.swing.JLabel ServerAddress1;
    private javax.swing.JLabel ServerAddress2;
    private javax.swing.JLabel ServerAddress3;
    private javax.swing.JButton jButton1;
    private javax.swing.JLayeredPane jLayeredPane1;
    // End of variables declaration//GEN-END:variables
}
