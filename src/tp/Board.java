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
	static Tower towerlist[][] = new Tower[10][15]; //포탑 저장공간
	static char[][] Road = new char[][] {
		{' ','X','X','X',' ','X','X','X','X','X'},
		{' ','X',' ','X',' ','X',' ',' ',' ','X'},
		{' ','X',' ','X',' ','X',' ','X','X','X'},
		{' ','X',' ','X',' ','X',' ','X',' ',' '},
		{'X','X',' ','X','X','X',' ','X','X','X'}
	};
	
	boolean game_over = false;
	
	Thread Thread_Summon = new Thread(new SummonSchedular()); //적을 시간에 따라 내보내는 스레드
	Thread Thread_RunTower = new Thread(new RunTower()); //타워의 위치 조정,회전,사격 ETC...
	Thread Thread_RunEnemy = new Thread(new RunEnemy()); //적 이동, 체력과 사망여부 판정 등등
	Thread Thread_RunBullet = new Thread(new RunBullet()); //이동, Hit여부 체크 등등
	Thread Thread_GUITower = new Thread(new GUITower()); //Tower의 정보를 GUI에 반영
	Thread Thread_GUIEnemy = new Thread(new GUIEnemy()); //Enemy의 정보를 GUI에 반영
	Thread Thread_GUIBullet = new Thread(new GUIBullet()); //Bullet의 정보를 GUI에 반영
	Thread Thread_GetSignal = new Thread(new GetSignal()); //시그널을 받아옵니다.
	
	Board() {
		for(int i=0; i<5; i++) {
			for(int j=0; j<10; j++) {
				towerlist[j][i] = new Tower(100*j+5,100*i+5);
			}
		}
		for(int i=0; i<15; i++) {
			enemylist.add(new Enemy());
		}
		for(int i=0; i<1000; i++) {
			
		}
		Thread_GetSignal.start();
	}
	void game_start() {
		Thread_Summon.start();
		Thread_RunTower.start();
		Thread_RunEnemy.start();
		Thread_RunBullet.start();
		Thread_GUITower.start();
		Thread_GUIEnemy.start();
		Thread_GUIBullet.start();
 	}
	void Interrupt_Object() {
		Thread_RunTower.interrupt();
		Thread_RunEnemy.interrupt();
		Thread_RunBullet.interrupt();
		Thread_GUITower.interrupt();
		Thread_GUIEnemy.interrupt();
		Thread_GUIBullet.interrupt();
		Thread_GetSignal.start();
	}
	
	class GetSignal implements Runnable {
		public void run() {
			try {
				while(Thread.interrupted() == false) {
					if(Board.start_phase == true)
						break;
					Thread.sleep(5);
				}
				System.out.println("i'm working");
				game_start();
			} catch(InterruptedException e) {}
		}
	}
	class SummonSchedular implements Runnable {
		public void run() {
			try {
				for(int i=0; i<15; i++) {
					Board.enemylist.elementAt(i).visible = true;
					Board.EnemyRemained--;
					Board.EnemyExist++;
					Thread.sleep(2000);
				}
				System.out.println("Summon Finished");
			} catch(InterruptedException e) {}
		}
	} 
	class RunTower implements Runnable {
		public void run() {
			
		}
	}
	
	class RunEnemy implements Runnable {
		public void run() {
			System.out.println("RunEnemy_Thread Started");
			try {
				while(Thread.interrupted() == false) {
					for(int i=0; i<15; i++) {
						Enemy temp = Board.enemylist.elementAt(i);
						if(temp.visible == false)
							continue;
						temp.move();
					}
					Thread.sleep(100);
				}
				System.out.println("RunEnemy_Thread Interrupted");
			} catch(InterruptedException e) {}
		}
	}
	
	class RunBullet implements Runnable {
		public void run() {
			
		}
	}
	class GUITower implements Runnable {
		public void run() {
			try {
				Thread.sleep(10);
			} catch(InterruptedException e) {
				
			}
		}
	}
	class GUIEnemy implements Runnable {
		public void run() {
			try {
				while(Thread.interrupted() == false) {
					for(int i=0; i<15; i++) {
						Enemy temp = Board.enemylist.elementAt(i);
						if(temp.visible == false) {
							if(frame.egui[i].isVisible() == false)
								continue;
							else
								frame.egui[i].setVisible(false);
						} else {
							if(frame.egui[i].isVisible() == false)
								frame.egui[i].setVisible(true);
							frame.egui[i].setLocation(temp.x,temp.y);
						}
					}
					Thread.sleep(50);
				}
			} catch(InterruptedException e) {
				
			}
		}
	}
	class GUIBullet implements Runnable {
		public void run() {
			
		}
	}
}
