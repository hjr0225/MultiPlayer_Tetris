package game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import blocks.Block;


@SuppressWarnings("serial")
public class HoldBlockPanel extends JPanel {
	
	private Block block=null;
	private final int BOX_SIZE=35;
	
	public Block holdBlock(Block block) {
		block.reset();
		block.hold();
		Block temp = this.block;
		this.block = block;
		repaint();
		return temp;
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
		if(block!=null) {
			drawBlock(g);
		}
	}	
		
	
}
