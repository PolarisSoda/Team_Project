package tp;

import java.util.*;

class GameObject implements Runnable {
	double x;
	double y;
	public void run() {
		return;
	}
}

class Bullet extends GameObject {
	int velocity;
	double objectx;
	double objecty;
	
	Bullet(double objectx,double objecty) {
		this.objectx = objectx;
		this.objecty = objecty;
	}
	void move() {
		//Enemy와 비슷.
		//어떤 목표를 찍으면 그 방향으로 쭉 나아가게 됨.
	}
	boolean isHit() {
		//bullet이 적에 맞거나 border를 넘어갔는지 확인.
		//thread를 종료할 조건.
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
	int atk;
	int reload;
	int target;
	boolean working = false;
	void setTarget() {
		//목표 적을 랜덤으로 정함.
		Random rd = new Random();
		//this.target = rd.nextInt();
	}
	void shoot(int cnt) {
		//불렛객체를 하나 소환한다.
		//Bullet temp = new Bullet();
		
	}
	boolean is_valid() {
		//적이 valid한가를 살펴본다.
		return true;
	}
	public void run() {
		try {
			/*
			 * pseudo-code
			 * while(true)
			 * 	if(is_valid == false)
			 * 		setTarget()
			 *  shoot()
			 *  sleep()
			 */
			Thread.sleep(30);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}
class Enemy extends GameObject {
	double velocity = 0.1;
	void move() {
		//make enemy along the road
		//you should make path along the ROAD.
		//It changes x and y
	}
	public void run() {
		try {
			/*
			 * pseudo-code
			 * while(is_ended == false)
			 * 	move()
			 *  sleep()
			 */
			Thread.sleep(30);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}

