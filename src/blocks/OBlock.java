package blocks;

import java.awt.Color;

public class OBlock extends Block {
	
	public OBlock() {
		super(new int[][][] {
			{
				{1,1},
				{1,1}
			},
			
			{
				{1,1},
				{1,1}
			},
			
			{
				{1,1},
				{1,1}
			},
			
			{
				{1,1},
				{1,1}
			}
			
		}, new Color(139, 0 ,255));
	}
	
	public void resetColor() {
		super.color = new Color(139, 0 ,255);
	}
}
