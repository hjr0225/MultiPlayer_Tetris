package game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JLabel;
import javax.swing.JPanel;

import blocks.Block;
import clients.Sender;

@SuppressWarnings("serial")
public class GameAreaPanel extends JPanel {
	
	private ExecutorService executor;
	
	
	final int COL = 10;
	final int ROW = 20;
	private final NextBlockPanel nextBlock;
	private boolean gameLife =true;
	private final int BOX_SIZE =35;
	private Block block;
	private Color[][] background;
	private Color border = Color.black;
	private boolean multiplay = false;
	private Sender sender;
	private HoldBlockPanel holdBlock;
	private boolean opponent;
	private boolean opponentStatus;

	public GameAreaPanel(NextBlockPanel nextBlock, HoldBlockPanel holdBlock) {
		this.nextBlock = nextBlock;
		this.holdBlock = holdBlock;
		background = new Color[ROW][COL];

	}
	
	public GameAreaPanel(NextBlockPanel nextBlock, HoldBlockPanel holdBlock, boolean opponent) {
		this(nextBlock, holdBlock);
		this.opponent = opponent;
		this.opponentStatus = true;;

	}
	
	public GameAreaPanel(NextBlockPanel nextBlock, HoldBlockPanel holdBlock, Sender sender) {
		this(nextBlock, holdBlock);
		this.sender = sender;
		multiplay = true;
		executor = Executors.newFixedThreadPool(1);
		block = nextBlock.getBlock();

	}
	
	public boolean getGameLife() {
		return gameLife;
	}
	
	public void turnOff() {
		gameLife=false;
	}
	
	public Color[][] getBoard(){
		Color[][] temp = new Color[ROW][COL];
		for(int i=0; i < ROW; i++) {
			temp[i]=background[i].clone();
		}
		return temp;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public void spawnBlock() {
		block = nextBlock.getBlock();
		if(multiplay) {
			executor.submit(()->sender.sendSpawnNewBlock());
		}

	}
	
	public void saveBackground() {
		if(opponentStatus && opponent ) {
			return;
		}
		int x = block.getX();
		int y = block.getY();
		int width = block.getWidth();
		int height = block.getHeigth();
		int pos = block.getPos();
		int[][][] shape = block.getShape();
		
		for(int i = height-1; i >= 0 ; i--) {
			for(int j = 0; j < width; j++) {
				if(shape[pos][i][j]==1) {
					try {
						background[i+y][j+x] = block.getColor();
					}catch(ArrayIndexOutOfBoundsException e) {}
			
				}
			}
		}
		block.reset();
		if(multiplay) {
			executor.submit(()->sender.sendBackground());
			confirmLine();
		}
	}
	
	private void drawBackground(Graphics g) {
		for(int i=0; i<background.length; i++) {
			for(int j=0; j<background[0].length; j++) {
				if(background[i][j] != null) {
					g.setColor(background[i][j]);
					g.fillRect(j * BOX_SIZE, i * BOX_SIZE, BOX_SIZE , BOX_SIZE);
					g.setColor(Color.black);
					g.drawRect(j*BOX_SIZE, i*BOX_SIZE, BOX_SIZE, BOX_SIZE);
				}
			}
		}
	}	
	
	
	public boolean checkBelow() {
		synchronized(block) {
			int y = block.getY();
			int x = block.getX();
			int width = block.getWidth();
			int height = block.getHeigth();
			int pos = block.getPos();
			int[][][] shape = block.getShape();
		
		
			for(int i=height-1; i >= 0; i--) {
				for(int j = 0; j < width; j++) {
					if(shape[pos][i][j]==1) {
						try {
							if(background[i+y+1][j+x]!=null) {
								return false;
							}
						}catch(ArrayIndexOutOfBoundsException e) {
							return y<0;
						}
					}
				}
			}
		}
		
		return true;
	}
	
	
	private boolean checkRight() {
		synchronized(block) {
		int y = block.getY();
		if(y<0) {
			y=0;
		}
		int x = block.getX();
		int width = block.getWidth();
		int height = block.getHeigth();
		int pos = block.getPos();
		int[][][] shape = block.getShape();
		
		for(int i = 0; i < height; i++) {
			for(int j=0; j<width; j++) {
				if(shape[pos][i][j]==1) {
					try {
						if(background[i+y][j+x+1]!=null) {
							return false;
						}
					}catch(ArrayIndexOutOfBoundsException e) {
						return false;
					}
				}
			}
		}
		}
		return true;
	}
	
	
	private boolean checkLeft() {
		synchronized(block) {
		int y = block.getY();
		if(y<0) {
			y=0;
		}
		int x = block.getX();
		int width = block.getWidth();
		int height = block.getHeigth();
		int pos = block.getPos();
		int[][][] shape = block.getShape();
			
		for(int i = 0; i < height; i++) {
			for(int j=0; j<width; j++) {
				if(shape[pos][i][j]==1) {
					try {
						if(background[i+y][j+x-1]!=null) {
							return false;
						}
					}catch(ArrayIndexOutOfBoundsException e) {
						return false;
					}
				}
			}
		}
		}
		
		return true;
	}
	
	private boolean checkPos() {
		synchronized(block) {
		int x = block.getX();
		int y = block.getY();
		int width = block.getWidth();
		int height = block.getHeigth();
		
		if(y<0) {
			return true;
		}

		
		for(int i=0; i< height; i++) {
			for(int j = 0; j < width; j++) {
				try {
					if(background[y+i][x+j]!=null) {
						return false;
					}
				}catch(ArrayIndexOutOfBoundsException e) {
						return false;
					
				}
			}
		}
		}
		return true;
	}
	
	public int checkLine() { 
		int count =0;
		for(int i=0; i < ROW; i++ ) {		
			boolean lineFilled = true;
			for(int j=0; j<COL; j++) {
				
				if(background[i][j]==null) {
					lineFilled = false;
					break;
				}
			}
			
			if(lineFilled) {
				count++;
				Arrays.fill(background[i], null);
				shiftLine(i);
			}
			
		}
		repaint();
		
		return count*count*100;
	}
	
	private void shiftLine(int num) {
		for(int i = num; i > 0; i--) {
			for( int j = 0; j<COL; j++){
				background[i][j] = background[i-1][j];
			}
		}
	}
	
	public void autoDown() {
		synchronized(block) {
		block.moveDown();
		repaint();
		if(multiplay) {
			int x = block.getX();
			int y = block.getY();
			executor.submit(()->sender.sendBlock(x,y));
		}
		}
	}
	
	public void paintDown() {
		synchronized(block) {
		if(checkBelow()) {
			block.moveDown();
			repaint();
			if(multiplay) {
				int x = block.getX();
				int y = block.getY();
				executor.submit(()->sender.sendBlock(x,y));
			}
		}
		}
	}
	public void paintLeft() {
		if(checkLeft()) {
			block.moveLeft();
			repaint();
			if(multiplay) {
				int x = block.getX();
				int y = block.getY();
				executor.submit(()->sender.sendBlock(x,y));
			}
		}
	}
	public void paintRight() {
		if(checkRight()) {
			block.moveRight();
			repaint();
			if(multiplay) {
				int x = block.getX();
				int y = block.getY();
				executor.submit(()->sender.sendBlock(x,y));
			}
		}
		
	}
	public void changeShape() {
		block.changePos();
		if(checkPos()) {

			repaint();
			if(multiplay) {
				executor.submit(()->sender.sendChangeShape());
			}
		}
		else {
			block.revertPos();
		}
	}
	
	public void fall(FallingThread th) {
		synchronized(block) {
		while(checkBelow()) {
			block.moveDown();
		}		
		if(multiplay) {
			int x = block.getX();
			int y = block.getY();
			executor.submit(()->sender.sendBlock(x,y));
		}
		repaint();
		th.interrupt();
		}
		

	}
	
	public void holdBlock(FallingThread tr) {
		if(!block.getHold()) {
			if(multiplay) {
				executor.submit(()->sender.sendHold());
			}
			block = holdBlock.holdBlock(block);
			if(block == null) {
				boolean temp = multiplay;
				multiplay=false;
				spawnBlock();
				multiplay=temp;
				tr.interrupt();
			}
			repaint();
		}
	}
	
	public void holdBlock() {
		if(!block.getHold()) {
			block = holdBlock.holdBlock(block);
			if(block ==null) {
				spawnBlock();
			}
		}
	}
	
	private void shadow(Graphics g) {
		synchronized(block) {
		int origin = block.getY();
		
		while(checkBelow()) {
			block.moveDown();
		}
		
		int x = block.getX();
		int y = block.getY();
		int width = block.getWidth();
		int height = block.getHeigth();
		int pos = block.getPos();
		int[][][] shape = block.getShape();

		for(int i = 0; i <height ; i++) {
			for(int j = 0; j < width; j++) {
				if(shape[pos][i][j]==1) {
					g.setColor(block.getColor());
					g.drawRect((j+x)*BOX_SIZE, (i+y)*BOX_SIZE, BOX_SIZE, BOX_SIZE);
			
				}
			}
		}
		block.setY(origin);
		}
	}	
		
	
	
	public void isGameOver() {
		for(int i=0; i< COL; i++) {
			if(background[0][i]!=null) {
				gameLife=false;
				gameOver();
				break;
			}
		}
	}
	
	public void gameOver() {
		JLabel gameover = new JLabel("GAME OVER");
		gameover.setBounds(0, 0, 400, 100);
		gameover.setFont(new Font("SansSerif", Font.PLAIN, 57));
		gameover.setForeground(Color.white);
		add(gameover);
		repaint();
		if(multiplay) {
			executor.submit(()->sender.sendGameOver());
		}
	}
	
	public void initial() {
		block.makeBlockBlack();
		nextBlock.init();
		if(multiplay) {
			executor.submit(()->sender.sendInit());
		}
	}
	
	public void initReturn() {
		block.resetColor();
		nextBlock.initReturn();
		border=Color.white;
		if(multiplay) {
			executor.submit(()->sender.sendInitReturn());
		}
		else if(opponent) {
			opponentStatus = false;
		}
	}
	
	public void confirmLine() {
		boolean temp =true;	
		for(int i=0; i<10; i++) {
				if(background[0][i]!=null) {
					temp = false;
				}
		}
		if(temp) {
			executor.submit(()->sender.sendSync());
		}
	}
	
	public void syncLine() {
		Arrays.fill(background[0], null);
	}
	
	
	private void drawBlock(Graphics g) {
		int x = block.getX();
		int y = block.getY();
		int width = block.getWidth();
		int height = block.getHeigth();
		int pos = block.getPos();
		int[][][] shape = block.getShape();

		for(int i = 0; i <height ; i++) {
			for(int j = 0; j < width; j++) {
				if(shape[pos][i][j]==1) {
					g.setColor(block.getColor());
					g.fillRect((j+x) * BOX_SIZE, (i+y) * BOX_SIZE, BOX_SIZE , BOX_SIZE);
					g.setColor(border);
					g.drawRect((j+x)*BOX_SIZE, (i+y)*BOX_SIZE, BOX_SIZE, BOX_SIZE);
			
				}
			}
		}
	}	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBackground(g);
		shadow(g);
		drawBlock(g);
		
	}
	

}
