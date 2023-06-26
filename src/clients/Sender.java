package clients;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;


public class Sender{
	private DataOutputStream out;

	public Sender(Socket socket) {
		String name = JOptionPane.showInputDialog("Please enter your username");
		if(name==null) {
			name="";
		}
		try {
			out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(name);
		} catch (IOException e) {}
	
	}
	
	public void sendBlock(int x, int y) {
		send(5);
		send(x);
		send(y);
	}
	
	public void sendSync() {
		send(15);
	}

	public void sendChangeShape() {
		send(20);
	}
	
	public void sendSpawnNewBlock() {
		send(25);
	}
	
	public void sendGameOver() {
		send(35);
	}
	
	public void sendHold() {
		send(40);
	}
	
	public void sendInit() {
		send(45);
	}
	
	public void sendInitReturn() {
		send(50);
	}
	
	public void sendBackground() {
		send(10);
	}

	private synchronized void send(int data) {
		try {
			out.writeByte(data);
		} catch (IOException e) {}
	}


	
}
