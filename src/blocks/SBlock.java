package blocks;

import java.awt.Color;

public class SBlock extends Block {

	public SBlock() {
		super(new int[][][] {
			{
				{0,1,1},
				{1,1,0}
			},
			
			{
				{1,0},
				{1,1},
				{0,1}
			},
			
			{
				{0,1,1},
				{1,1,0}
			},
			
			{
				{1,0},
				{1,1},
				{0,1}
			}
				
		}, new Color(0,255,0));
	}
	
	public void resetColor() {
		super.color = new Color(0,255,0);
	}
}
