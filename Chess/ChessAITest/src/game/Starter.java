package game;

import javax.swing.JOptionPane;

public class Starter implements Runnable{

	public static Thread thread;
	
	public static void start(){
		thread = new Thread(new Starter());
		thread.start();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		do{
			Game game = new Game();
			game.start();
			Thread.currentThread().suspend();
			
			int i = JOptionPane.showConfirmDialog(null, "Play again?", "Chess By 2k", JOptionPane.YES_NO_OPTION);
			
			if(i == JOptionPane.NO_OPTION){
				System.exit(0);
			}
		}while(true);
	}

}
