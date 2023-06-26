package clients;

import java.awt.Color;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JLabel;

import blocks.Block;
import game.GameAreaPanel;
import game.GameFrame;

public class Reciever extends Thread {
	private DataInputStream in;
	private GameAreaPanel area;
	private long seed;
	private String userName;
	private int score=0;
	private int level=0;
	private GameFrame frame;
	private boolean recieverLife=true;

	public Reciever(Socket socket, GameAreaPanel area, GameFrame frame) {
		this.area = area;
		this.frame = frame;
		try {
			in = new DataInputStream(socket.getInputStream());
			seed = in.readLong();
			userName = in.readUTF();
		} catch (IOException e) {}
		
		
	
	}

	public void run() {
		while(recieverLife) {
			try {
				byte recieved = in.readByte();
				 setRecieved(recieved);
				}catch (IOException e) {
					recieverLife=false;
					JLabel gameover = new JLabel(" DISCONNECTED");
					gameover.setBounds(0, 100, 400, 100);
					gameover.setFont(new Font("SansSerif", Font.PLAIN, 40));
					gameover.setForeground(Color.white);
					area.add(gameover);
					area.repaint();
					try {
						in.close();
					} catch (IOException e1) {e.printStackTrace();}
				
			}
		}
	}
	
	private void setRecieved(byte data) {
		Block block = area.getBlock();
		
		switch(data) {
			case 5:
			try {
				int x = in.readByte();
				int y = in.readByte();
				block.setXY(x, y);
			} catch (IOException e1) {}
				break;
			
			case 10:
				area.saveBackground();
				score += area.checkLine();
				level = score /3000;
				frame.setScore2(score);
				frame.setLevel2(level+ 1);
				break;
				
			case 15:
				area.syncLine();
				break;
				
			case 20:
				block.changePos();
				break;
				
			case 25:
				area.spawnBlock();
				break;
					
			case 35:
				area.gameOver();
				break;
				
			case 40:
				area.holdBlock();
				break;
				
			case 45:
				area.initial();
				break;
				
			case 50:
				area.initReturn();
				break;
				
			default: 
				break;
		}
		
		area.repaint();
		
	}
	
	public long getSeed() {
		return seed;
	}
	
	public int getScore() {
		return score;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void turnOff() {
		recieverLife=false;
		try {
			in.close();
		} catch (IOException e) {}
	}
	
	
	
}
