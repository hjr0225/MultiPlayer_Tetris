package game;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import clients.Reciever;
import clients.Sender;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	
	private boolean musicLife = true;
	private  FallingThread game1=null;
	private  FallingThread game2=null;
	private Container contentPane=null;
	private final JLabel scoreBoard1 = new JLabel("SCORE: "+0);
	private final JLabel levelBoard1 = new JLabel("LEVEL: "+1);
	private final JLabel scoreBoard2 = new JLabel("SCORE: "+0);
	private final JLabel levelBoard2 = new JLabel("LEVEL: "+1);
	private GameAreaPanel gameArea1=null;
	private GameAreaPanel gameArea2=null;
	private boolean computer = false;
	private boolean player = false;
	private Sender sender;
	private Reciever reciever;
	private BackgroundMusic music;
	private MainMenu menu;
	
	public GameFrame(MainMenu menu){
		this.menu = menu;
		this.contentPane = getContentPane();
		contentPane.setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Tetris");
		getContentPane().setLayout(null);
		music = new BackgroundMusic();
		
		JButton backToMenu = new JButton("Main Menu");
		backToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(menu.getMusic() && !musicLife) {
					music.stopPlaying();
				}
				
				musicLife = false;
				
				gameArea1.turnOff();
				
				if(gameArea2!=null) {
					gameArea2.turnOff();
				}
				setVisible(false);
				menu.setVisible(true);
				menu.resetFrame();
				String name="";
				int score1 = game1.getScore();
				int score2 = 0;
				
				if(computer) {
					score2=game2.getScore();
					name = "computer score: ";
				}
				else if(player) {
					reciever.turnOff();
					score2 = reciever.getScore();
					name = reciever.getUserName()+": ";
				}
				else {
					return;
				}
			
					
				if(score1 - score2 < 0) {
					name = "            YOU LOST!!\n"+name+score2;
				}
				else {
					name = "             YOU WON!!\n"+name+score2;
				}
				
					
				JOptionPane.showMessageDialog(null, name+ "\n YOUR SCORE: "+score1);
			}
		});
		backToMenu.setBounds(625, 20, 100, 23);
		backToMenu.setFocusable(false);
		contentPane.add(backToMenu);
	}
	


	public void singleplay() {
		setBounds(100, 100, 775, 875);
		setLocationRelativeTo(null);
		setResizable(false);

		NextBlockPanel nextBlock = new NextBlockPanel();
		nextBlock.setBorder(new LineBorder(Color.white, 3));
		nextBlock.setBackground(Color.BLACK);
		nextBlock.setBounds(575, 250, 175, 105);
		nextBlock.setFocusable(false);
		long seed = System.currentTimeMillis();
		nextBlock.setSeed(seed);
		contentPane.add(nextBlock);
		
		HoldBlockPanel hPanel = new HoldBlockPanel();
		hPanel.setBorder(new LineBorder(Color.white, 3));
		hPanel.setBackground(Color.BLACK);
		hPanel.setBounds(13, 150, 175, 105);
		hPanel.setFocusable(false);
		contentPane.add(hPanel);
		
		gameArea1 = new GameAreaPanel(nextBlock, hPanel);
		gameArea1.setBorder(new LineBorder(Color.white, 3));
		gameArea1.setBackground(Color.BLACK);
		gameArea1.setBounds(202, 69, 350, 700);
		contentPane.add(gameArea1);
		
		
		scoreBoard1.setBounds(575, 80, 200, 100);
		scoreBoard1.setFont(new Font("SansSerif", Font.PLAIN, 25));
		scoreBoard1.setForeground(new Color(255, 255, 255));
		contentPane.add(scoreBoard1);
		
		levelBoard1.setBounds(575, 130, 153, 74);
		levelBoard1.setFont(new Font("SansSerif", Font.PLAIN, 25));
		levelBoard1.setForeground(new Color(255, 255, 255));
		contentPane.add(levelBoard1);
		
		JLabel next = new JLabel("NEXT");
		next.setBounds(620, 190, 153, 74);
		next.setFont(new Font("SansSerif", Font.PLAIN, 30));
		next.setForeground(new Color(255, 255, 255));
		contentPane.add(next);
		
		JLabel hold = new JLabel("HOLD");
		hold.setBounds(58, 90, 153, 74);
		hold.setFont(new Font("SansSerif", Font.PLAIN, 30));
		hold.setForeground(new Color(255, 255, 255));
		contentPane.add(hold);
		 
		game1 = new FallingThread(gameArea1, this);
		game1.start();
		
	}
	
	public void computerPlay() {
		computer = true;
		setBounds(100, 100, 1425, 875);
		setLocationRelativeTo(null);
		setResizable(false);
		
		NextBlockPanel nextBlock1 = new NextBlockPanel();
		nextBlock1.setBorder(new LineBorder(Color.white, 3));
		nextBlock1.setBackground(Color.BLACK);
		nextBlock1.setBounds(590, 250, 175, 105);
		nextBlock1.setFocusable(false);
		
		NextBlockPanel nextBlock2 = new NextBlockPanel();
		nextBlock2.setBorder(new LineBorder(Color.white, 3));
		nextBlock2.setBackground(Color.BLACK);
		nextBlock2.setBounds(1215, 250, 175, 105);
		nextBlock2.setFocusable(false);
		
		long seed = System.currentTimeMillis();
		nextBlock1.setSeed(seed);
		nextBlock2.setSeed(seed);
		
		HoldBlockPanel hPanel = new HoldBlockPanel();
		hPanel.setBorder(new LineBorder(Color.white, 3));
		hPanel.setBackground(Color.BLACK);
		hPanel.setBounds(15, 150, 175, 105);
		hPanel.setFocusable(false);
		contentPane.add(hPanel);
		
		gameArea1 = new GameAreaPanel(nextBlock1, hPanel);
		gameArea1.setBorder(new LineBorder(Color.white, 3));
		gameArea1.setBackground(Color.BLACK);
		gameArea1.setBounds(217, 69, 350, 700);
		
		gameArea2 = new GameAreaPanel(nextBlock2, null);
		gameArea2.setBorder(new LineBorder(Color.white, 3));
		gameArea2.setBackground(Color.BLACK);
		gameArea2.setBounds(840, 69, 350, 700);
		gameArea2.setFocusable(false);
		
		contentPane.add(gameArea1);
		contentPane.add(gameArea2);
		contentPane.add(nextBlock1);
		contentPane.add(nextBlock2);
		
		scoreBoard1.setBounds(590, 80, 200, 100);
		scoreBoard1.setFont(new Font("SansSerif", Font.PLAIN, 25));
		scoreBoard1.setForeground(new Color(255, 255, 255));
		contentPane.add(scoreBoard1);
		
		levelBoard1.setBounds(590, 130, 153, 74);
		levelBoard1.setFont(new Font("SansSerif", Font.PLAIN, 25));
		levelBoard1.setForeground(new Color(255, 255, 255));
		contentPane.add(levelBoard1);
		
		JLabel next1 = new JLabel("NEXT");
		next1.setBounds(635, 190, 153, 74);
		next1.setFont(new Font("SansSerif", Font.PLAIN, 30));
		next1.setForeground(new Color(255, 255, 255));
		contentPane.add(next1);
		
		scoreBoard2.setBounds(1215, 80, 200, 100);
		scoreBoard2.setFont(new Font("SansSerif", Font.PLAIN, 25));
		scoreBoard2.setForeground(new Color(255, 255, 255));
		contentPane.add(scoreBoard2);
		
		levelBoard2.setBounds(1215, 130, 153, 74);
		levelBoard2.setFont(new Font("SansSerif", Font.PLAIN, 25));
		levelBoard2.setForeground(new Color(255, 255, 255));
		contentPane.add(levelBoard2);
		
		JLabel next2 = new JLabel("NEXT");
		next2.setBounds(1265, 190, 153, 74);
		next2.setFont(new Font("SansSerif", Font.PLAIN, 30));
		next2.setForeground(new Color(255, 255, 255));
		contentPane.add(next2);
		
		JLabel cumputerSign = new JLabel("Computer");
		cumputerSign.setBounds(815, 0, 400, 80);
		cumputerSign.setFont(new Font("SansSerif", Font.PLAIN, 57));
		cumputerSign.setForeground(Color.white);
		cumputerSign.setHorizontalAlignment(SwingConstants.CENTER);
		add(cumputerSign);
		
		JLabel hold = new JLabel("HOLD");
		hold.setBounds(60, 90, 153, 74);
		hold.setFont(new Font("SansSerif", Font.PLAIN, 30));
		hold.setForeground(new Color(255, 255, 255));
		contentPane.add(hold);
		
		game1 = new FallingThread(gameArea1, this);
		game2 = new FallingThread(gameArea2, this, true);

		game1.start();
		game2.start();
		
	}
	
	public void multiPlay() {
		player = true;
		setBounds(100, 100, 1425, 875);
		setLocationRelativeTo(null);
		setResizable(false);
		
		NextBlockPanel nextBlock1 = new NextBlockPanel();
		nextBlock1.setBorder(new LineBorder(Color.white, 3));
		nextBlock1.setBackground(Color.BLACK);
		nextBlock1.setBounds(590, 250, 175, 105);
		nextBlock1.setFocusable(false);
		
		NextBlockPanel nextBlock2 = new NextBlockPanel();
		nextBlock2.setBorder(new LineBorder(Color.white, 3));
		nextBlock2.setBackground(Color.BLACK);
		nextBlock2.setBounds(1215, 250, 175, 105);
		nextBlock2.setFocusable(false);
		
		HoldBlockPanel hPanel2 = new HoldBlockPanel();
		hPanel2.setBorder(new LineBorder(Color.white, 3));
		hPanel2.setBackground(Color.BLACK);
		hPanel2.setBounds(1215, 420, 175, 105);
		hPanel2.setFocusable(false);
		contentPane.add(hPanel2);
		
		JLabel hold2 = new JLabel("HOLD");
		hold2.setBounds(1265, 360, 153, 74);
		hold2.setFont(new Font("SansSerif", Font.PLAIN, 30));
		hold2.setForeground(new Color(255, 255, 255));
		contentPane.add(hold2);
		
		
		gameArea2 = new GameAreaPanel(nextBlock2, hPanel2, true);
		gameArea2.setBorder(new LineBorder(Color.white, 3));
		gameArea2.setBackground(Color.BLACK);
		gameArea2.setBounds(840, 69, 350, 700);
		gameArea2.setFocusable(false);
		
		Socket socket = null;
		try {
			socket = new Socket("222.98.88.210", 7777);
			socket.setTcpNoDelay(true);
			
		} catch (IOException e) {
			setVisible(false);
			menu.setVisible(true);
			menu.resetFrame();
			JOptionPane.showMessageDialog(null, "Server is Closed.");
			return;
		}
		JFrame frame1 = new JFrame("Match making");
		frame1.setResizable(false);
		frame1.setTitle("Tetris");
		frame1.setBounds(100, 100, 455, 344);
		frame1.setUndecorated(true);
		frame1.setLocationRelativeTo(null);
		JPanel contentPane1 = new JPanel();
		contentPane1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane1.setBackground(Color.BLACK);
		frame1.setContentPane(contentPane1);
		contentPane1.setLayout(null);
		JLabel temp = new JLabel(" Match Making...");
		temp.setBounds(0, 130, 455, 80);
		temp.setFont(new Font("SansSerif", Font.PLAIN, 30));
		temp.setForeground(Color.white);
		temp.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane1.add(temp);
		frame1.setVisible(true);
		
		
		sender = new Sender(socket);
		reciever = new Reciever(socket, gameArea2, this);
		
		frame1.setVisible(false);
		
		long seed = reciever.getSeed();
		nextBlock1.setSeed(seed);
		nextBlock2.setSeed(seed);
		
		HoldBlockPanel hPanel1 = new HoldBlockPanel();
		hPanel1.setBorder(new LineBorder(Color.white, 3));
		hPanel1.setBackground(Color.BLACK);
		hPanel1.setBounds(15, 150, 175, 105);
		hPanel1.setFocusable(false);
		contentPane.add(hPanel1);
		
		JLabel hold1 = new JLabel("HOLD");
		hold1.setBounds(60, 90, 153, 74);
		hold1.setFont(new Font("SansSerif", Font.PLAIN, 30));
		hold1.setForeground(new Color(255, 255, 255));
		contentPane.add(hold1);
		
		gameArea1 = new GameAreaPanel(nextBlock1, hPanel1, sender);
		gameArea1.setBorder(new LineBorder(Color.white, 3));
		gameArea1.setBackground(Color.BLACK);
		gameArea1.setBounds(217, 69, 350, 700);
		
		
		contentPane.add(gameArea1);
		contentPane.add(gameArea2);
		contentPane.add(nextBlock1);
		contentPane.add(nextBlock2);
		
		scoreBoard1.setBounds(590, 80, 200, 100);
		scoreBoard1.setFont(new Font("SansSerif", Font.PLAIN, 25));
		scoreBoard1.setForeground(new Color(255, 255, 255));
		contentPane.add(scoreBoard1);
		
		levelBoard1.setBounds(590, 130, 153, 74);
		levelBoard1.setFont(new Font("SansSerif", Font.PLAIN, 25));
		levelBoard1.setForeground(new Color(255, 255, 255));
		contentPane.add(levelBoard1);
		
		JLabel next1 = new JLabel("NEXT");
		next1.setBounds(635, 190, 153, 74);
		next1.setFont(new Font("SansSerif", Font.PLAIN, 30));
		next1.setForeground(new Color(255, 255, 255));
		contentPane.add(next1);
		
		scoreBoard2.setBounds(1215, 80, 200, 100);
		scoreBoard2.setFont(new Font("SansSerif", Font.PLAIN, 25));
		scoreBoard2.setForeground(new Color(255, 255, 255));
		contentPane.add(scoreBoard2);
		
		levelBoard2.setBounds(1215, 130, 153, 74);
		levelBoard2.setFont(new Font("SansSerif", Font.PLAIN, 25));
		levelBoard2.setForeground(new Color(255, 255, 255));
		contentPane.add(levelBoard2);
		
		JLabel next2 = new JLabel("NEXT");
		next2.setBounds(1265, 190, 153, 74);
		next2.setFont(new Font("SansSerif", Font.PLAIN, 30));
		next2.setForeground(new Color(255, 255, 255));
		contentPane.add(next2);
		
		JLabel username = new JLabel(reciever.getUserName());
		username.setBounds(815, 0, 400, 80);
		username.setFont(new Font("SansSerif", Font.PLAIN, 57));
		username.setForeground(Color.white);
		username.setHorizontalAlignment(SwingConstants.CENTER);
		add(username);
		
		
		game1 = new FallingThread(gameArea1, this);
		gameArea2.spawnBlock();

		game1.start();
		reciever.start();
	}
	
	public void setScore1(int score) {
		scoreBoard1.setText("SCORE: "+score);
	}
	
	public void setLevel1(int level) {
		levelBoard1.setText("LEVEL: "+level);
	}
	
	public void setScore2(int score) {
		scoreBoard2.setText("SCORE: "+score);
	}
	
	public void setLevel2(int level) {
		levelBoard2.setText("LEVEL: "+level);
	}
	
	public void countDown() {
		int num =3;
		JLabel timer = new JLabel("");
		timer.setHorizontalAlignment(SwingConstants.CENTER);
		timer.setFont(new Font("SansSerif", Font.BOLD, 99));
		timer.setForeground(new Color(255, 255, 255));
		timer.setBounds(44, 65, 117, 138);
		gameArea1.add(timer);
		
		 
		while(num > 0) {
			timer.setText(num+""); 
			num--;
			try {Thread.sleep(1000);} catch (InterruptedException e1) {}
			
		 }
		gameArea1.remove(timer);
		
		
		if(!musicLife) {
			return;
		}
		else if(menu.getMusic()) {
			musicLife = false;
			music.playMusic();
		}
		 
		 
			addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					switch(e.getKeyCode()) {
					
					case KeyEvent.VK_DOWN:
						gameArea1.paintDown();
						try {Thread.sleep(10);} catch (InterruptedException e1) {}
						break;
					case KeyEvent.VK_LEFT:
						gameArea1.paintLeft();
						try {Thread.sleep(10);} catch (InterruptedException e1) {}
						break;
					case KeyEvent.VK_RIGHT:
						gameArea1.paintRight();
						try {Thread.sleep(10);} catch (InterruptedException e1) {}
						break;
					case KeyEvent.VK_UP:
						gameArea1.changeShape();
						try {Thread.sleep(10);} catch (InterruptedException e1) {}
						break;
					case KeyEvent.VK_SPACE:
						gameArea1.fall(game1);
						try {Thread.sleep(10);} catch (InterruptedException e1) {}
						break;
					case KeyEvent.VK_C:
						gameArea1.holdBlock(game1);
						try {Thread.sleep(10);} catch (InterruptedException e1) {}
						break;
						
					default:
						break;
					}
				}
			});
			
	
			
	}
	
}
