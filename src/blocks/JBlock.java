package blocks;

import java.awt.Color;

public class JBlock extends Block {

	public JBlock() {
		super(new int[][][] {
			{
				{1,0,0},
				{1,1,1}
			},
			
			{
				{1,1},
				{1,0},
				{1,0}
			},
			
			{
				{1,1,1},
				{0,0,1}
			},
			
			{
				{0,1},
				{0,1},
				{1,1}
			}
			
		}, new Color(255, 127, 0));
	}
	
	public void resetColor() {
		super.color = new Color(255, 127, 0);
	}
}
