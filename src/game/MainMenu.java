package game;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class MainMenu extends JFrame {


	private JPanel contentPane;
	private GameFrame gameFrame;
	private boolean musicStatus =true;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainMenu() {
		setResizable(false);
		setTitle("Tetris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 455, 344);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		MainMenu tmp = this;
		
		JCheckBox music = new JCheckBox("MUSIC");
		music.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				musicStatus = !musicStatus;
			}
		});
		music.setSelected(true);
		music.setBounds(0, 0, 65, 23);
		music.setForeground(new Color(255, 255, 255));
		music.setBackground(new Color(0, 0, 0));
		music.setFocusable(false);
		contentPane.add(music);
		
		JButton singlePlay = new JButton("Single Play");
		singlePlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				gameFrame = new GameFrame(tmp);
				gameFrame.singleplay();
			}
		});
		singlePlay.setBounds(143, 155, 150, 23);
		singlePlay.setFocusable(false);
		contentPane.add(singlePlay);
		
		JButton computer = new JButton("Play VS Computer");
		computer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				gameFrame = new GameFrame(tmp);
				gameFrame.computerPlay();
			}
		});
		computer.setBounds(143, 188, 150, 23);
		computer.setFocusable(false);
		contentPane.add(computer);
		
		JButton player = new JButton("Play VS Player");
		player.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				gameFrame = new GameFrame(tmp);
				gameFrame.multiPlay();
			}
		});
		player.setBounds(143, 221, 150, 23);
		player.setFocusable(false);
		contentPane.add(player);
		
		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		quit.setBounds(143, 254, 150, 23);
		quit.setFocusable(false);
		contentPane.add(quit);
		
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon(getClass().getResource("titleimage (4).jpg")));
		background.setBounds(-140, -166, 633, 496);
		contentPane.add(background);
		
	}
	
	public void resetFrame() {
		gameFrame = null;
	}
	
	public boolean getMusic() {
		return musicStatus;
	}
}
