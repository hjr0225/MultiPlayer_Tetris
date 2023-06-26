package blocks;

import java.awt.Color;

public class TBlock extends Block {

	public TBlock() {
		super(new int[][][] {
			{
				{0,1,0},
				{1,1,1}
			},
			
			{
				{1,0},
				{1,1},
				{1,0}
			},
			
			{
				{1,1,1},
				{0,1,0}
			},
			
			{
				{0,1},
				{1,1},
				{0,1}
			}
			
		}, Color.blue);
	}
	
	public void resetColor() {
		super.color = Color.blue;
	}

}
