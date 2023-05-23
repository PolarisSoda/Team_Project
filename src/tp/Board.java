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
	//실제로 저장된 정보와, GUI간의 정보전달을 위한 Thread.
	//여기서 각 GameObject의 정보를 토대로 GUI의 정보를 수정함.
	//label의 visible을 바꿔가면서 사라졌다 없앴다 하는 방식이 괜찮을 듯?
	//Bullet같은 경우는 각자가 Thread가 돌아갈텐데, 너무 많다 싶으면 개수를 바꾸겠습니다.
	class TowerGUI implements Runnable {
		public void run() {
			
		}
	}
	class EnemyGUI implements Runnable {
		public void run() {
			
		}
	}
	class BulletGUI implements Runnable {
		public void run() {
			
		}
	}
}
