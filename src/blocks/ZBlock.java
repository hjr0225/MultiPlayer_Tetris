package blocks;

import java.awt.Color;

public class ZBlock extends Block {
	
	public ZBlock() {
		super(new int[][][] {
			{
				{1,1,0},
				{0,1,1}
			},
			
			{
				{0,1},
				{1,1},
				{1,0}
			},
			
			{
				{1,1,0},
				{0,1,1}
			},
			
			{
				{0,1},
				{1,1},
				{1,0}
			}
			
		}, Color.red);
	}
	
	public void resetColor() {
		super.color = Color.red;
	}
}
