package game;

import java.awt.Color;

import blocks.Block;

public class TetrisBrain{
	
	private GameAreaPanel game;
	private FallingThread tr;
	
	public TetrisBrain(GameAreaPanel game, FallingThread tr) {
		this.game = game;
		this.tr = tr;
	}


	public void bestMove(){

		Block block = game.getBlock();
		block.setY(0);
		int score = 0;
		int attemptScore=0, x=0, pos=0;

		for(int i=0; i<4; i++) {
			for(int j=0; j<game.COL-block.getWidth()+1; j++) {
				block.setX(j);
				Color[][] board = game.getBoard();
				attemptScore = checkBoard(board, block);
				block.setY(0);
				if(score < attemptScore) {
					score = attemptScore;
					x = block.getX();
					pos = block.getPos();
				}
			}
			block.changePos();
		}
		block.reset();
		block.setY(0);
		
		drawAnswer(block, pos, x);
		
		
	}
	
	private void drawAnswer(Block block, int pos, int x) {
		while(pos>0) {
			try {Thread.sleep(200);} catch (InterruptedException e) {}
			block.changePos();
			game.repaint();
			pos--;
		}
		
		int positionX = x-block.getX();
		while(positionX!=0) {
			try {Thread.sleep(200);} catch (InterruptedException e) {}
			if(positionX<0) {
				block.moveLeft();
			}
			else {
				block.moveRight();
			}
			game.repaint();
			positionX=x-block.getX();
		}
		
		try {Thread.sleep(100);} catch (InterruptedException e) {}
		
		game.fall(tr);
	}
	
	 	  	
	 private int rateColumn(Color[][] board, Block block){
		 int x = block.getX();
		 int y = block.getY();
		 int width = block.getWidth();
		 int height = block.getHeigth();
		 int pos = block.getPos();
		 int[][][] shape = block.getShape();
	     int count=0;
		 
	     for (int i = 0; i < height; i++){
	    	 for(int j=0; j<width; j++) {
	    	 try {
	    		 if(shape[pos][i][j]==1&&board[y+i+1][j+x] == null){
	    			 count++;
	    		 }
	    	 }catch(IndexOutOfBoundsException e) {	  }
	    		 }
	     }
	     return count*(-20);
	     }			
	 	  	
	 	  	
	 private int rateRow(Color[][] board) {
		 int sum = 0;
		 for (int row = 0; row < game.ROW; row++){
			 int count =0;
			 for(int col = 0; col < game.COL; col++) {
				 if(board[row][col] != null) {
					 count++;
					 }
				 }
			 sum += count*row;
			 }
		 return sum;
		 }
	 
	     
	 private int checkBoard(Color[][] board, Block block){
			while(game.checkBelow()) {
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
						 board[i+y][j+x] = block.getColor();
				
					}
				}
			}
			return rateColumn(board, block)+rateRow(board);
			}
	 }


