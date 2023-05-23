package tp;
import java.util.*;

public class Board {
	Frame frame = new Frame(); //GUI
	static int upgrade = 0;
	static int gold = 100;
	static boolean start_phase = false;
	Tower towerlist[][] = new Tower[10][5];
	ArrayList<Enemy> enemylist = new ArrayList<>();
	ArrayList<Bullet> bulletlist= new ArrayList<>();
	
	void game_start() {
		
	}
	class SummonSchedule implements Runnable {
		public void run() {
			
		}
	}
}
