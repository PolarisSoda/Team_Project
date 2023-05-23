package tp;

import java.util.*;

class GameObject implements Runnable {
	double x;
	double y;
	public void run() {
		return;
	}
}

class Enemy extends GameObject {
	double velocity = 0.1;
	int health = 1000;
	boolean dead = false;
	void move() {
		//make enemy along the road
		//you should make path along the ROAD.
		//It changes x and y
	}
	public void run() {
		try {
			while(Board.start_phase == true) {
				if(this.dead == true)
					break;
				this.move();
				Thread.sleep(30);
			}
			
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Bullet extends GameObject {
	int velocity;
	double objectx;
	double objecty;
	double direction; //rad형태로 바뀌겠죠.
	Bullet(double x,double y,double objectx,double objecty) {
		this.x = x;
		this.y = y;
		this.objectx = objectx;
		this.objecty = objecty;
	}
	void GetDirection() {
		
	}
	void move() {
		//Enemy와 비슷.
		//어떤 목표를 찍으면 그 방향으로 쭉 나아가게 됨.
	}
	boolean isHit() {
		if(x<0 || x>1000 || y<0 || y>500)
			return true;
		
		return false;
	}
	public void run() {
		try {
			/**
			 * pseudo-code
			 * while(ishit == false)
			 *  move()
			 *  sleep()
			 *
			 */
			Thread.sleep(30);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}
class Tower extends GameObject {
	int atk; //체력은 얼마나 깎을건지?
	int reload = 5; //몇 Cycle마다 한번 쏘는지?
	int target; //ArrayList의 몇번째 Enemy를 쏠건지? 이부분은 MultiThreading으로 인해 상당히 위험함.
	boolean working = false;
	
	Tower() {
		
	}
	Tower(double x,double y) {
		this.x = x;
		this.y = y;
	}
	void setTarget() {
		Random rd = new Random();
		int location;
		while(true) {
			location = rd.nextInt(Board.enemylist.size());
			if(is_valid(location) == true) {
				this.target = location;
				break;
			}
		}
	}
	void shoot(int cnt) {
		Enemy tg = Board.enemylist.get(target);
		double ox = tg.x;
		double oy = tg.y;
		Bullet temp = new Bullet(this.x,this.y,ox,oy);
		Board.bulletlist.add(temp);
	}
	boolean is_valid(int index) {
		//실제로 가리키고 있는 적이 valid한가를 체크하는것이다.
		//중간중간마다 없어질수도 있으니 try catch로 exception을 무조건 잡아야 됨.
		//ArrayList에 접근하는 것이니, 대충 그 exception좀 찾아주세요.
		return true;
	}
	public void run() {
		try {
			int cycle = 0;
			while(Board.start_phase == true) {
				if(cycle%this.reload == 0) {
					if(is_valid(target) == true) {
						//???
					} else {
						this.setTarget();
					}
					this.shoot(target); //try-catch Exception 필요.
				}
				Thread.sleep(30); //적절한 fps를 설정하시오. 전체적인 fps를 말하는것이다. 재장전속도말고.
				//재장전속도랑 포탑이 움직이는 속도 같아지면 안되니까.
				//재장전속도는, parameter i를 추가해서 i%3 == 0 같은식으로 3 Cycle마다 한번쏘는걸로 구현 가능. 
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}


