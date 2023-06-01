package tp;

import java.util.*;
import java.math.*;

class GameObject {
	int x; //현재 x좌표
	int y; //현재 y좌표
	boolean visible; //GUI에서 표시되는지?
}

class Enemy extends GameObject {
	int velocity = 2;
	int health;
	int index = 0;
	int size = 90;
	int dx = (1000 - size)/9;
	int dy = (500 - size)/4;
	Enemy() {
		this.health = 1000;
		this.x = 5;
		this.y = 405;
		this.visible = false;
	}
	void move() {
		if(index == 0) {
			this.x = Board.road[0][1] * dx;
			this.y = Board.road[0][0] * dy;
			index = 1;
		}
		if(this.x == Board.road[this.index][1]*dx && this.y == Board.road[this.index][0]*dy) {
			this.index +=1;
		}
		this.x += (Board.road[this.index][1] - Board.road[this.index-1][1]) * this.velocity;
		this.y += (Board.road[this.index][0] - Board.road[this.index-1][0]) * this.velocity;
	}
	void Hit(int atk) {
		this.health -= atk;
		if(health <= 0) {
			this.visible = false;
			this.health = 0;
		}
	}
}

class Bullet extends GameObject {
	int velocity;
	double direction; //rad형태로 바뀌겠죠.
	Bullet() {
		this.visible = false;
		this.velocity = 10;
	}
	void move() {
		this.x += velocity;
	}
	boolean isHit() {
		if(x<0 || x>1000 || y<0 || y>500)
			return true;
		for(int i=0; i<15; i++) {
			Enemy target = Board.enemylist.get(i);
			if(target.visible == true) {
				int x2 = (target.x - this.x)*(target.x - this.x);
				int y2 = (target.y - this.y)*(target.y - this.y);
				if(x2 + y2 <= 1600)
					return true;
			}
		}
		return false;
	}
}
class Tower extends GameObject {
	int atk = 50;//체력은 얼마나 깎을건지?
	int reload = 50; //몇 Cycle마다 한번 쏘는지?
	int target = -1; //ArrayList의 몇번째 Enemy를 쏠건지?
	double radian;
	int cnt = 1;
	Tower(int x,int y) {
		this.x = x;
		this.y = y;
		this.visible = false;
	}
	void setTier(int tier) {
		if(tier == 1) {
			//Common
		} else if(tier == 2) {
			//Rare
		} else if(tier == 3) {
			//Epic
		} else {
			//Legendary
		}
	}
 	boolean SetTarget() {
		this.target = -1;
		for(int i=0; i<15; i++) {
			if(Board.enemylist.get(i).visible == true) {
				this.target = i;
				return true;
			}
		}
		return false;
	}
	boolean Is_Valid() {
		if(Board.enemylist.get(this.target).visible == true)
			return true;
		return false;
	}
	void Shoot() {
		if(cnt%reload == 0) {
			if(this.target == -1 || Is_Valid() == false) {
				boolean hastarget = this.SetTarget();
				if(hastarget == false)
					return;
			}
			double bias = 1e-9;
			int ox = Board.enemylist.get(this.target).x;
			int oy = Board.enemylist.get(this.target).y;
			double rad = Math.atan2((double)(this.y-oy)+bias,(double)(this.x-ox)+bias);
			this.radian = rad;
			for(int i=0; i<500; i++) {
				Bullet temp = Board.bulletlist.get(i);
				if(temp.visible == true)
					continue;
				System.out.println("Bullet : " + i + "created");
				temp.visible = true;
				temp.x = this.x;
				temp.y = this.y;
				temp.direction = rad;
				break;
			}
			cnt = 1;
		} else {
			this.cnt++;
		}
	}
}


