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
	static int[][] road = new int[][] {
		{4,0},{4,1},{3,1},{2,1},{1,1},{0,1},{0,2},{0,3},{1,3},
		{2,3},{3,3},{4,3},{4,4},{4,5},{3,5},{2,5},{1,5},{0,5},
		{0,6},{0,7},{0,8},{0,9},{1,9},{2,9},{2,8},{2,7},{3,7},
		{4,7},{4,8},{4,9}
	};
	static ArrayList<Enemy> enemylist = new ArrayList<Enemy>();
	static ArrayList<Bullet> bulletlist = new ArrayList<Bullet>();
	static ArrayList<pair> TowerRandom = new ArrayList<pair>();
	static Tower towerlist[][] = new Tower[10][15]; //포탑 저장공간
	
	int life = 2000;
	boolean game_over = false;
	
	Thread Thread_Summon = new Thread(new SummonSchedular()); //적을 시간에 따라 내보내는 스레드
	Thread Thread_RunTower = new Thread(new RunTower()); //타워의 위치 조정,회전,사격 ETC...
	Thread Thread_RunEnemy = new Thread(new RunEnemy()); //적 이동, 체력과 사망여부 판정 등등
	Thread Thread_RunBullet = new Thread(new RunBullet()); //이동, Hit여부 체크 등등
	Thread Thread_GUITower = new Thread(new GUITower()); //Tower의 정보를 GUI에 반영
	Thread Thread_GUIEnemy = new Thread(new GUIEnemy()); //Enemy의 정보를 GUI에 반영
	Thread Thread_GUIBullet = new Thread(new GUIBullet()); //Bullet의 정보를 GUI에 반영
	Thread Thread_GetSignal = new Thread(new GetSignal()); //시그널을 받아옵니다.
	Thread Thread_PhaseChecker = new Thread(new PhaseChecker()); //Phase를 Check합니다.
	
	Board() {
		for(int i=0; i<5; i++) {
			for(int j=0; j<10; j++) {
				towerlist[j][i] = new Tower(100*j,100*i);
			}
		}
		for(int i=0; i<15; i++) {
			enemylist.add(new Enemy());
		}
		for(int i=0; i<500; i++) {
			bulletlist.add(new Bullet());
		}
		for(int i=0; i<5; i++) {
			for(int j=0; j<10; j++) {
				boolean is_road = false;
				for(int k=0; k<30; k++) {
					if(Board.road[k][1] == j && Board.road[k][0] == i)
						is_road = true;
				}
				if(is_road == false) 
					TowerRandom.add(new pair(j,i));
			}
		}
		Thread_GetSignal.start();
	}
	
	void ReArrange() {
		Board.round++;
		int new_health = 1000 + Board.round*Board.round*100/2;
		for(Bullet temp : Board.bulletlist) {
			temp.visible = false;
		}
		for(Enemy temp : Board.enemylist) {
			temp.x = 0;
			temp.y = 400;
			temp.index = 0;
			temp.visible = false;
			temp.health = new_health;
		}
		Board.EnemyExist = 0;
		Board.EnemyRemained = 15;
		Board.start_phase = false;
		frame.ObjectHide();
		frame.RoundUpdate(Board.round,new_health);
	}
	void game_start() {
		Thread_GetSignal.interrupt();
		
		Thread_PhaseChecker = new Thread(new PhaseChecker());
		Thread_Summon = new Thread(new SummonSchedular());
		Thread_RunTower = new Thread(new RunTower());
		Thread_RunEnemy = new Thread(new RunEnemy());
		Thread_RunBullet = new Thread(new RunBullet());
		Thread_GUITower = new Thread(new GUITower());
		Thread_GUIEnemy = new Thread(new GUIEnemy());
		Thread_GUIBullet = new Thread(new GUIBullet());
		
		Thread_PhaseChecker.start();
		Thread_Summon.start();
		
		Thread_RunTower.start();
		Thread_RunEnemy.start();
		Thread_RunBullet.start();
		
		Thread_GUITower.start();
		Thread_GUIEnemy.start();
		Thread_GUIBullet.start();
		frame.BS();
 	}
	void Interrupt_Object() {
		this.ReArrange();
		Thread_PhaseChecker.interrupt();
		Thread_RunTower.interrupt();
		Thread_RunEnemy.interrupt();
		Thread_RunBullet.interrupt();
		Thread_Summon.interrupt();
		Thread_GUITower.interrupt();
		Thread_GUIEnemy.interrupt();
		Thread_GUIBullet.interrupt();
		
		frame.BE();
		Thread_GetSignal = new Thread(new GetSignal());
		Thread_GetSignal.start();
		
	}
	
	void OverProcedure() {
		Thread_PhaseChecker.interrupt();
		Thread_RunTower.interrupt();
		Thread_RunEnemy.interrupt();
		Thread_RunBullet.interrupt();
		Thread_Summon.interrupt();
		Thread_GUITower.interrupt();
		Thread_GUIEnemy.interrupt();
		Thread_GUIBullet.interrupt();
		frame.Over();
	}
	class PhaseChecker implements Runnable {
		public void run() {
			try {
				while(Thread.interrupted() == false) {
					if(Board.EnemyExist == 0 && Board.EnemyRemained == 0) //만약 생성할 적군도 없고, 적군 남아있지도 않다면.
						break;
					if(life <= 0) {
						game_over = true;
						break;
					}
					Thread.sleep(5);
				}
				if(game_over == true) {
					OverProcedure();
					return;
				}
				Board.start_phase = false;
				Interrupt_Object();
			} catch(InterruptedException e) {
				
			}
		}
	}
	class GetSignal implements Runnable {
		public void run() {
			try {
				while(Thread.interrupted() == false) {
					if(Board.start_phase == true)
						break;
					Thread.sleep(5);
				}
				game_start();
				return ;
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	class SummonSchedular implements Runnable {
		public void run() {
			try {
				for(int i=0; i<15; i++) {
					Board.enemylist.get(i).visible = true;
					Board.EnemyRemained--;
					Board.EnemyExist++;
					Thread.sleep(1000);
				}
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	} 
	class RunTower implements Runnable {
		public void run() {
			try {
				while(Thread.interrupted() == false) {
					for(int i=0; i<5; i++) {
						for(int j=0; j<10; j++) {
							Tower temp = Board.towerlist[j][i];
							if(temp.visible == false)
								continue;
							temp.Shoot();
						}
					}
					Thread.sleep(30);
				}
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	class RunEnemy implements Runnable {
		public void run() {
			try {
				while(Thread.interrupted() == false) {
					for(int i=0; i<15; i++) {
						Enemy temp = Board.enemylist.get(i);
						if(temp.visible == false)
							continue;
						if(temp.MainAttack() == true) {
							Board.EnemyExist--;
							temp.visible = false;
							life -= temp.health;
							if(life <= 0)
								life = 0;
							frame.LifeUpdate(life);
						}
						temp.move();
					}
					Thread.sleep(30);
				}
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
		}
	}
	
	class RunBullet implements Runnable {
		public void run() {
			try {
				while(Thread.interrupted() == false) {
					for(int i=0; i<500; i++) {
						Bullet temp = Board.bulletlist.get(i);
						if(temp.visible == false)
							continue;
						int t = temp.isHit();
						if(t != -1) {
							temp.visible = false;
							Enemy tg = Board.enemylist.get(t);
							tg.Hit(temp.atk + Board.upgrade * 50);
							if(tg.health == 0) {
								Board.EnemyExist--;
								Board.gold += 10;
								frame.GoldMsg.setText("Gold : " + String.valueOf(Board.gold));
							}
							continue;
						}
						if(temp.OutOfRange() == true) {
							temp.visible = false;
							continue;
						}
						temp.move();
					}
					Thread.sleep(15);
				}
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	class GUITower implements Runnable {
		public void run() {
			try {
				while(Thread.interrupted() == false) {
					for(int i=0; i<5; i++) {
						for(int j=0; j<10; j++) {
							Tower temp = Board.towerlist[j][i];
							if(temp.visible == false)
								continue;
							frame.tgui[j][i].Radian = temp.radian;
						}
					}
					Thread.sleep(5);
				}
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	class GUIEnemy implements Runnable {
		public void run() {
			try {
				while(Thread.interrupted() == false) {
					for(int i=0; i<15; i++) {
						Enemy temp = Board.enemylist.get(i);
						if(temp.visible == false) {
							if(frame.egui[i].isVisible() == true)
								frame.egui[i].setVisible(false);
						} else {
							if(frame.egui[i].isVisible() == false)
								frame.egui[i].setVisible(true);
							frame.egui[i].setLocation(temp.x,temp.y);
						}
					}
					Thread.sleep(5);
				}
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	class GUIBullet implements Runnable {
		public void run() {
			try {
				while(Thread.interrupted() == false) {
					for(int i=0; i<500; i++) {
						Bullet temp = Board.bulletlist.get(i);
						if(temp.visible == false) {
							if(frame.bgui[i].isVisible() == true)
								frame.bgui[i].setVisible(false);
						} else {
							if(frame.bgui[i].isVisible() == false)
								frame.bgui[i].setVisible(true);
							frame.bgui[i].setLocation(temp.x,temp.y);
							frame.bgui[i].Radian = temp.direction;
						}
					}
					Thread.sleep(5);
				}
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
}

class pair {
	int first;
	int second;
	pair(int first,int second) {
		this.first = first;
		this.second = second;
	}
}