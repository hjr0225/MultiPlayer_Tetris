package blocks;

import java.awt.Color;

public class Block{

	
	private int x,y, pos;
	private int [][][] shape;
	private boolean hold = false;
	
	protected Color color;
	
	public Block(int[][][] shape, Color color) {
		this.shape=shape;
		this.color=color;
		x=4;
		y=-1;
		pos=0;
	}
	
	
	public int[][][] getShape(){
		return shape;
	}
	public Color getColor() {
		return color;
	}
	
	public int getHeigth() {
		return shape[pos].length;
	}
	
	public int getWidth() {
		return shape[pos][0].length;
				
	}
	
	public boolean getHold() {
		return hold;
	}
	
	public void hold() {
		hold = true;
	}
	
	public int getPos() {
		return pos;
	}
	
	public void moveRight() {
		x++;
	}
	
	public void moveLeft() {
		x--;
	}
	
	public void moveDown() {
		y++;
	}
	
	public void changePos() {
		pos = (pos+1)%4;
	}
	
	public void revertPos() {
		pos = (pos+3)%4;
	}
	
	public void setPos(int pos) {
		this.pos = pos;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x =x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setXY(int x, int y) {
		this.x =x;
		this.y=y;
	}
	
	public void makeBlockBlack() {
		this.color = Color.black;
	}
	
	public void resetColor() {
		return;
	}
	
	public void reset() {
		x=4;
		y=-1;
		hold = false;
		pos=0;
	}

}
