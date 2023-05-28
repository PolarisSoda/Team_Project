package tp;

import java.util.*;

class GameObject {
	int x; //현재 x좌표
	int y; //현재 y좌표
	boolean visible; //GUI에서 표시되는지?
	public void run() {
		return;
	}
}

class Enemy extends GameObject {
	double velocity = 2;
	int health;
	
	Enemy() {
		this.health = 1000 + Board.round*100;
		this.x = 5;
		this.y = 405;
		this.visible = false;
	}
	void move() {
		//길따라서 움직일 수 있게 구현 좀.
		this.x += 1;
	}
}

class Bullet extends GameObject {
	int velocity;
	int objectx;
	int objecty;
	double direction; //rad형태로 바뀌겠죠.
	Bullet(int x,int y,int objectx,int objecty) {
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
}
class Tower extends GameObject {
	int atk = 50;//체력은 얼마나 깎을건지?
	int reload = 5; //몇 Cycle마다 한번 쏘는지?
	int target = -1; //ArrayList의 몇번째 Enemy를 쏠건지?
	double radian;
	int cnt = 0;
	Tower(int x,int y) {
		this.x = x;
		this.y = y;
		this.visible = false;
	}
	
	void SetDirection() {
		
	}
	
	void Shoot() {
		
	}
}


