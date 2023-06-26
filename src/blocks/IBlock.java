package blocks;

import java.awt.Color;

public class IBlock extends Block {

	public IBlock() {
		super(new int[][][] {
			{
				{1,1,1,1}
			},
			{
				{0,1},
				{0,1},
				{0,1},
				{0,1}
				
			},
			
			{
				{0,0,0,0},
				{1,1,1,1}
			},
			
			{
				{1,0},
				{1,0},
				{1,0},
				{1,0}
				
			}
			
			}, Color.darkGray);
	}
	
	public void resetColor() {
		super.color = Color.darkGray;
	}
}
