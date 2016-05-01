/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tubessister;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;

/**
 * Kelas pembungkus DatagramSocket yang mensimulaiskan paket yang hilang
 * dalam pengiriman.
 */
public class ReliableSender
{
	private DatagramSocket datagramSocket;
	private Random random;

	public ReliableSender(DatagramSocket datagramSocket) throws SocketException {
		this.datagramSocket = datagramSocket;
		random = new Random();
	}

	public void send(DatagramPacket packet) throws IOException {
	
			datagramSocket.send(packet);
		
	}
}
