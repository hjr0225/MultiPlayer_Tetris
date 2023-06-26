package blocks;

import java.awt.Color;

public class LBlock extends Block {

	public LBlock() {
		super(new int[][][] {
			{
				{0,0,1},
				{1,1,1}
				
			},
			
			{
				{1,0},
				{1,0},
				{1,1}
			},
			
			{
				{1,1,1},
				{1,0,0}
				
			},
			
			{
				{1,1},
				{0,1},
				{0,1}
			}
		
		}, Color.magenta);
	}
	
	public void resetColor() {
		super.color = Color.magenta;
	}
}
