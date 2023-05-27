package tp;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Board {
	Frame frame = new Frame(); //GUI
	static int upgrade = 0; //upgrade 계수 어떻게 할지는 글쎄
	static int gold = 100; //얻은 돈
	static int round = 0; //라운드, 라운드 기반으로 적이 강해지겠죠
	static int EnemyRemained = 15; //적은 총 15개만 나오는걸로해보죠..
	static int EnemyExist = 0; //화면에 적 존재 여부.
	static boolean start_phase = false; //전투페이즈 준비페이즈 구분.
	
	static Vector<Enemy> enemylist = new Vector<Enemy>();
	static Vector<Bullet> bulletlist = new Vector<Bullet>();
	//static List<Bullet> bulletlist = Collections.synchronizedList(new ArrayList<Bullet>());
	//static ArrayList<Bullet> bulletlist = new ArrayList<>(); //탄알 저장공간
	static Tower towerlist[][] = new Tower[10][15]; //포탑 저장공간
	static char[][] Road = new char[][] {
		{' ','X','X','X',' ','X','X','X','X','X'},
		{' ','X',' ','X',' ','X',' ',' ',' ','X'},
		{' ','X',' ','X',' ','X',' ','X','X','X'},
		{'X','X',' ','X',' ','X',' ','X',' ',' '},
		{' ',' ',' ','X','X','X',' ','X','X','X'}
	};
	
	boolean gameover = false;
	Board() {
		for(int i=0; i<5; i++) {
			for(int j=0; j<10; j++) {
				towerlist[j][i] = new Tower(100*j+5,100*i+5);
			}
		}
	}
	void game_start() {
		RunTower T_Runnable[][] = new RunTower[10][5];
		Thread TowerThread[][] = new Thread[10][5];
		
		RunEnemy E_Runnable[] = new RunEnemy[15];
		Thread EnemyThread[] = new Thread[15];
		
		Runnable Checker = new PhaseChecker();
		Runnable Phase = new SummonSchedule();
		
		Thread CheckerThread = new Thread(Checker);
		CheckerThread.start();
		
		for(int i=0; i<5; i++) {
			for(int j=0; j<10; j++) {
				T_Runnable[j][i] = new RunTower(j,i);
				if(Board.towerlist[j][i].visible == true) {
					TowerThread[j][i] = new Thread(T_Runnable[j][i]);
					TowerThread[j][i].start();
				}
			}
		}
		
		for(int i=0; i<15; i++) {
			EnemyThread[i] = new Thread(E_Runnable[i]);
			EnemyThread[i].start();
		}
		
		Thread PhaseThread = new Thread(Phase);
		PhaseThread.start(); //적 소환 스케쥴러 스레드
 	}
	class PhaseChecker implements Runnable {
		//페이즈가 돌아가는동안 페이즈가 끝났는지 확인하는곳.
		public void run() {
			/*
			 * pseudo
			 * if(remained == 0 && exist == 0)
			 * 	Start_phase = true;
			 *  interrupt all thread except for main.
			 *  and set remaind = 15;
			 *	
			 */
		}
	}
	class SummonSchedule implements Runnable {
		//적을 일정한 시간에 따라 만듬.
		public void run() {
			try {
				for(int i=0; i<15; i++) {
					Enemy temp = Board.enemylist.elementAt(i);
					
					Thread.sleep(30);
				}
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	//실제로 저장된 정보와, GUI간의 정보전달을 위한 Thread.
	//여기서 각 GameObject의 정보를 토대로 GUI의 정보를 수정함.
	//label의 visible을 바꿔가면서 사라졌다 없앴다 하는 방식이 괜찮을 듯?
	//Bullet같은 경우는 각자가 Thread가 돌아갈텐데, 너무 많다 싶으면 개수를 바꾸겠습니다.
	class RunTower implements Runnable {
		int x,y;
		RunTower(int x,int y) {
			this.x = x;
			this.y = y;
		}
		public void run() {
			try {
				while(Thread.interrupted() == false) {
					//target
					Thread.sleep(30);
				}
			} catch(InterruptedException e) {
				
			}
		}
	}
	
	class RunEnemy implements Runnable {
		int x,y;
		RunEnemy() {
			this.x = -1;
			this.y = -1;
		}
		public void run() {
			try {
				while(Thread.interrupted() == false) {
					//target
					Thread.sleep(30);
				}
			} catch(InterruptedException e) {
				
			}
		}
	}
	class TowerGUI implements Runnable {
		public void run() {
			try {
				while(Thread.interrupted() == false) {
					for(int i=0; i<5; i++) {
						for(int j=0; j<10; j++) {
							
						}
					}
					Thread.sleep(10);
				}
			} catch(InterruptedException e) {
				
			}
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
