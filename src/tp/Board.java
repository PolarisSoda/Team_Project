package tp;
import java.util.*;

public class Board {
	Frame frame = new Frame(); //GUI
	static int upgrade = 0;
	static int gold = 100;
	static int round = 0;
	static int EnemyRemained = 15;
	static int EnemyExist = 0;
	static boolean start_phase = false; //전투페이즈 준비페이즈 구분.
	static ArrayList<Enemy> enemylist = new ArrayList<>(); //적 저장공간
	static ArrayList<Bullet> bulletlist = new ArrayList<>(); //탄알 저장공간
	boolean gameover = false;
	
	Tower towerlist[][] = new Tower[10][5];
	
	
	void game_start() {
		
	}
	class PhaseChecker implements Runnable {
		//페이즈가 돌아가는동안 페이즈가 끝났는지 확인하는곳.
		public void run() {
			/*
			 * pseudo
			 * if(remained == 0 && exist == 0)
			 * 	Start_phase = true;
			 *  interrupt all thread except for main.
			 *	
			 */
		}
	}
	class SummonSchedule implements Runnable {
		//적을 일정한 시간에 따라 만듬.
		public void run() {
			try {
				/*
				 * pseudo
				 * for(int i=remained; i>=0; i--) {
				 * 		enemylist.push_back(new enemy());
				 * 		
				 * }
				 */
				Thread.sleep(30);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
