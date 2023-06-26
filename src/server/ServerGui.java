package server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ServerGui extends JFrame {

	private JPanel contentPane;

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGui frame = new ServerGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(7777);
		} catch (IOException e1) {}
		
		while(true) {
			try {
				Socket socket1 = serverSocket.accept();
				Socket socket2 = serverSocket.accept();
				
				socket1.setTcpNoDelay(true);
				socket2.setTcpNoDelay(true);
				
				long seed = System.currentTimeMillis();
				
				Reciever reciever1 = new Reciever(socket1, socket2, seed);
				Reciever reciever2 = new Reciever(socket2, socket1, seed);
				
				
				reciever1.start();
				reciever2.start();
				
				
				
				
			}catch(IOException e) {}
		}
	}

	public ServerGui() {
		setTitle("Tetris Server");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 291, 168);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Close Server");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton.setBounds(61, 50, 153, 23);
		contentPane.add(btnNewButton);
		
	}
}
