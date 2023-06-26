package server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Reciever extends Thread {
	private DataInputStream in;
	private DataOutputStream out;
	private long seed;
	private boolean serverLife = true;
	
	public Reciever(Socket socket1, Socket socket2, long seed) {
		this.seed = seed;
		try {
			out = new DataOutputStream(socket2.getOutputStream());
			in = new DataInputStream(socket1.getInputStream());
			
		}catch(IOException e) {e.printStackTrace();}
	}
	
	public void run() {
		try {
			out.writeLong(seed);
			out.writeUTF(in.readUTF());
		} catch (IOException e) {e.printStackTrace();}
		
		while(serverLife) {
			try {
				byte data = in.readByte();
				out.writeByte(data);
			}catch (IOException e) {
				serverLife=false;
				try {
					out.close();
					in.close();
				} catch (IOException e1) {e.printStackTrace();}
			}
				
		}
	
	}
}
