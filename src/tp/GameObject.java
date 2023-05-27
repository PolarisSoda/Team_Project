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
		this.x += 2;
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
	int atk = 10;//체력은 얼마나 깎을건지?
	int reload = 5; //몇 Cycle마다 한번 쏘는지?
	int target = -1; //ArrayList의 몇번째 Enemy를 쏠건지?

	Tower(int x,int y) {
		this.x = x;
		this.y = y;
		this.visible = false;
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
		int ox = tg.x;
		int oy = tg.y;
		Bullet temp = new Bullet(this.x,this.y,ox,oy);
		Board.bulletlist.add(temp);
	}
	boolean is_valid(int index) {
		//실제로 가리키고 있는 적이 valid한가를 체크하는것이다.
		//중간중간마다 없어질수도 있으니 try catch로 exception을 무조건 잡아야 됨.
		//ArrayList에 접근하는 것이니, 대충 그 exception좀 찾아주세요.
		return true;
	}
}


