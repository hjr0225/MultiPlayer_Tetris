package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

import blocks.Block;
import blocks.IBlock;
import blocks.JBlock;
import blocks.LBlock;
import blocks.OBlock;
import blocks.SBlock;
import blocks.TBlock;
import blocks.ZBlock;


@SuppressWarnings("serial")
public class NextBlockPanel extends JPanel {
	
	private Block block;
	private final int BOX_SIZE;
	private Block[] blocks;
	private Random random = null;
	
	public NextBlockPanel() {
		this.BOX_SIZE = 35;
		blocks = new Block[]{new JBlock(), new IBlock(), new LBlock(), new OBlock(), new SBlock(), new TBlock(), new ZBlock(),
							 new JBlock(), new IBlock(), new LBlock(), new OBlock(), new SBlock(), new TBlock(), new ZBlock(),
							 new JBlock(), new IBlock(), new LBlock(), new OBlock(), new SBlock(), new TBlock(), new ZBlock()
							 };
		
	}
	
	public void setSeed(long seed) {
		random = new Random(seed+32894576);
		block = generateBlock();
	}

	
	private Block generateBlock() {
		return blocks[random.nextInt(21)];
	}
	
	public Block getBlock() {
		Block temp = block;
		block = generateBlock();
		repaint();
		return temp;
	}
	
	public void init() {
		block.makeBlockBlack();
		repaint();
	}
	
	public void initReturn() {
		block.resetColor();
		repaint();
	}

	private void drawBlock(Graphics g) {
		
		int width = block.getWidth();
		int height = block.getHeigth();
		int pos = block.getPos();
		int[][][] shape = block.getShape();

		for(int i = 0 ; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(shape[pos][i][j]==1) {
					g.setColor(block.getColor());
					g.fillRect((j * BOX_SIZE)+(5-width)*18, (i * BOX_SIZE)+(3-height)*18, BOX_SIZE , BOX_SIZE);
					g.setColor(Color.black);
					g.drawRect((j*BOX_SIZE)+(5-width)*18, (i*BOX_SIZE)+(3-height)*18, BOX_SIZE, BOX_SIZE);
			
				}
			}
		}
	}	

	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBlock(g);
	}	
		
	
}
