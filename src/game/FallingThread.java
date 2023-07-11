package game;

public class FallingThread extends Thread {
	private GameAreaPanel area;
	private GameFrame frame;
	private int score =0;
	private int level =0;
	private int time =1000;
	private boolean computer=false;
	private TetrisBrain brain;

	public FallingThread(GameAreaPanel area, GameFrame main, boolean computer) {
		this(area, main);
		brain = new TetrisBrain(area);
		this.computer = computer;
	}
	
	public FallingThread(GameAreaPanel area, GameFrame main) {
		this.area = area;
		this.frame = main;
	}
	
	private void changeLevel() {
		if(score/3000 > level) {
			level = score/3000;
			if(time >300) {
				time -= 100;
			}
			else {
				time-=10;
			}
		}
		
	}
	
	public int getScore() {
		return score;
	}

	@Override
	public void run() {
		area.spawnBlock();
		area.initial();
		area.repaint();
		frame.setVisible(true);
		
		if(computer) {
			try {Thread.sleep(3200);} catch (InterruptedException e) {}
		}
		else{
			frame.countDown();
		}
		
		area.initReturn();
		
		while(area.getGameLife()) {
			boolean hardDrop= false;
			if(computer) {
				try {
					Thread.sleep(100);
					brain.bestMove();
					Thread.sleep(100);
				} catch (InterruptedException e1) {}
			}
			while(area.checkBelow()) {
				area.autoDown();
				if(area.checkBelow()) {
					try {Thread.sleep(time);}catch(InterruptedException e){hardDrop = true;}
				}
			}
			if(!hardDrop && !computer) {
				hardDrop = false;
				for(int i=0; i<144; i++) {
					boolean hardDrop2 = false;
					while(area.checkBelow()) {
						i=0;
						area.autoDown();
						
						if(area.checkBelow()) {
							try {Thread.sleep(time);}catch(InterruptedException e){ hardDrop2 = true;}
						}
					}
					if(!hardDrop2) {
						try {Thread.sleep(5);}catch(InterruptedException e){}
					}
					else {break;}
				}
				
			}
			area.saveBackground();
			score += area.checkLine();
			changeLevel();
			if(computer) {
				frame.setScore2(score);
				frame.setLevel2(level+ 1);
			}else {
				frame.setScore1(score);
				frame.setLevel1(level+ 1);
			}
			area.spawnBlock();
			area.isGameOver();
			
			
		}
	}
}
