package tp;

class GameObject implements Runnable {
	double x;
	double y;
	public void run() {
		return;
	}
}

class Bullet extends GameObject {
	int velocity;
}
class Tower extends GameObject {
	int atk;
	int reload;
	
}
class Enemy extends GameObject {
	int velocity;
}

