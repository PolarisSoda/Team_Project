package tp;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Frame extends JFrame {
	
	JPanel Coverpanel = new JPanel();
	JLabel FirstDisplay = new JLabel(new ImageIcon("src/images/background.jpg"));
	JLabel Title = new JLabel("Random TD");
	JLabel Info = new JLabel();
	JLabel OverDisplay = new JLabel();
	JButton Start = new JButton("Game Start");
	
	JPanel Gamepanel = new JPanel(); //게임 패널
	JLabel GImage = new JLabel(new ImageIcon("src/images/road.png"));
	JRotate tgui[][] = new JRotate[10][5]; //포탑 표시하는 라벨
	JLabel egui[] = new JLabel[15]; //적 표시 라벨
	JRotate bgui[] = new JRotate[500]; //총알 표시 라벨
	
	JPanel Controlpanel = new JPanel(); //컨트롤 패널, 버튼 포함하는 곳.
	JLabel CPImage = new JLabel(new ImageIcon("src/images/controlpanel.png"));
	JButton Summon = new JButton("Summon"); //소환
	JButton Upgrade = new JButton("Upgrade"); //업그레이드
	JButton Battle = new JButton("Battle"); //페이즈시작(적 생성하겠단뜻)
	
	JPanel Infopanel = new JPanel(); //인포 패널, gold든 목숨이든 에러메시지든 넣는 곳.
	JLabel IPImage = new JLabel(new ImageIcon("src/images/infopanel.png"));
	JLabel RoundMsg = new JLabel();
	JLabel GoldMsg = new JLabel(); //돈 표시기
	JLabel UpgradeMsg = new JLabel(); //업그레이드 표시기
	JLabel LifeMsg = new JLabel(); //라이프 메시지
	JLabel EnemyInfo = new JLabel();
	
	JPanel Msgpanel = new JPanel(); //??
	JLabel MSGImage = new JLabel();
	JLabel indicator[] = new JLabel[4];
	JLabel randomlist[] = new JLabel[4];
	
	JButton Reroll = new JButton("<html>Reroll<br>Previous</br></html>");
	JPanel Etcpanel = new JPanel();
	JLabel EtcImage = new JLabel(new ImageIcon("src/images/etcpanel.png"));
	JLabel ErrorMsg = new JLabel();
	
	int px = -1;
	int py = -1;
	
	void BS() {
		ErrorMsg.setText("Battle Started!");
	}
	void BE() {
		ErrorMsg.setText("Battle Finished!");
	}
	void LifeUpdate(int life) {
		this.LifeMsg.setText("Life: " + String.valueOf(life));
	}
	void ObjectHide() {
		for(JRotate temp : this.bgui) {
			temp.setVisible(false);
		}
		for(JLabel temp : this.egui) {
			temp.setLocation(0,400);
		}
	}
	void RoundUpdate(int round,int health) {
		this.RoundMsg.setText("Round: " + String.valueOf(round));
		this.EnemyInfo.setText("Enemy: " + String.valueOf(health) + "HP");
	}
	void Over() {
		this.Info.setText("You Did " + String.valueOf(Board.round) + "Rounds");
		this.Info.setVisible(true);
		this.Reroll.setVisible(false);
		this.Title.setText("Game Over!");
		this.Coverpanel.setVisible(true);
		this.Start.setVisible(false);
		this.Controlpanel.setVisible(false);
	}
	void Init() {
		for(int i=0; i<5; i++) {
			for(int j=0; j<10; j++) {
				tgui[j][i] = new JRotate("src/images/new_tank_4.png",0);
				if(j == 9 && i == 4)
					tgui[j][i] = new JRotate("src/images/main_t.png",0);
				tgui[j][i].setSize(100,100);
				tgui[j][i].setLocation(j*100,i*100);
				tgui[j][i].setVisible(false);
				tgui[j][i].setDoubleBuffered(true);
				Gamepanel.add(tgui[j][i]);
			}
		}
		tgui[9][4].setVisible(true);
		for(int i=0; i<15; i++) {
			ImageIcon original = new ImageIcon("src/images/new_enemy.png");
			egui[i] = new JLabel(original);
			egui[i].setSize(100,100);
			egui[i].setLocation(0,400);
			egui[i].setVisible(false);
			egui[i].setDoubleBuffered(true);
			Gamepanel.add(egui[i]);
		}
		
		for(int i=0; i<500; i++) {
			bgui[i] = new JRotate("src/images/new_bullet.png",0);
			bgui[i].setSize(100,100);
			bgui[i].setLocation(-100,-100);
			bgui[i].setVisible(false);
			bgui[i].setDoubleBuffered(true);
			Gamepanel.add(bgui[i]);
		}
	}
	Frame() {
		this.setTitle("Defense");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(1280,720);
		this.setLayout(null);
		Container contentPane = this.getContentPane();
		
		FirstDisplay.setLocation(0,0);
		FirstDisplay.setSize(1280,720);
		Start.setLocation(480,500);
		Start.setSize(300,100);
		Start.setFont(new Font("Impact",Font.PLAIN,30));
		Start.addActionListener(new StartListener());
		Title.setFont(new Font("Impact",Font.BOLD,100));
		Title.setForeground(Color.WHITE);
		Title.setLocation(380,200);
		Title.setSize(500,100);
		Title.setHorizontalAlignment(SwingConstants.CENTER);
		
		Info.setFont(new Font("Impact",Font.BOLD,60));
		Info.setForeground(Color.WHITE);
		Info.setLocation(380,290);
		Info.setSize(500,200);
		Info.setHorizontalAlignment(SwingConstants.CENTER);
		Info.setVisible(false);
		
		Coverpanel.setLayout(null);
		Coverpanel.setLocation(0,0);
		Coverpanel.setSize(1280,720);
		Coverpanel.add(Title);
		Coverpanel.add(Info);
		Coverpanel.add(FirstDisplay);
		Coverpanel.add(Start);
		
		
		ImageIcon temp = new ImageIcon("src/images/road.png");
		Image img = temp.getImage();  //ImageIcon을 Image로 변환.
		Image yimg = img.getScaledInstance(1000,500, java.awt.Image.SCALE_SMOOTH);
		ImageIcon modified = new ImageIcon(yimg);
		GImage = new JLabel(modified);
		GImage.setSize(1000,500);
		GImage.setLocation(0,0);
		Gamepanel.setLayout(null);
		Gamepanel.setBackground(new Color(228,0,0,255));
		Gamepanel.setLocation(10,10);
		Gamepanel.setSize(1000,500);
		this.Init();
		Gamepanel.add(GImage);
		
		Summon.setLocation(40,20);
		Summon.setSize(150,50);
		Summon.setFont(new Font("Impact",Font.PLAIN,20));
		Summon.addActionListener(new SummonListener());
		
		Upgrade.setLocation(40,90);
		Upgrade.setSize(150,50);
		Upgrade.setFont(new Font("Impact",Font.PLAIN,20));
		Upgrade.addActionListener(new UpgradeListener());
		
		Battle.setLocation(40,160);
		Battle.setSize(150,50);
		Battle.setFont(new Font("Impact",Font.PLAIN,20));
		Battle.addActionListener(new BattleListener());
		
		CPImage.setLocation(0,0);
		CPImage.setSize(230,230);
		
		Controlpanel.setLayout(null);
		Controlpanel.setBackground(new Color(0,200,0,255));
		Controlpanel.setLocation(1020,10);
		Controlpanel.setSize(230,230);
		Controlpanel.add(Summon);
		Controlpanel.add(Upgrade);
		Controlpanel.add(Battle);
		Controlpanel.add(CPImage);
		Summon.setVisible(false);
		Upgrade.setVisible(false);
		Battle.setVisible(false);
		
		
		IPImage.setLocation(0,0);
		IPImage.setSize(230,260);
		
		
		
		GoldMsg.setLocation(20,20);
		GoldMsg.setSize(150,30);
		GoldMsg.setText("Gold : " + String.valueOf(Board.gold));
		GoldMsg.setFont(new Font("Impact",Font.PLAIN,20));
		
		UpgradeMsg.setLocation(20,70);
		UpgradeMsg.setSize(150,30);
		UpgradeMsg.setText("Upgrade: 0");
		UpgradeMsg.setFont(new Font("Impact",Font.PLAIN,20));
		
		LifeMsg.setLocation(20,120);
		LifeMsg.setSize(150,30);
		LifeMsg.setText("Life: 2000");
		LifeMsg.setFont(new Font("Impact",Font.PLAIN,20));
		
		EnemyInfo.setLocation(20,170);
		EnemyInfo.setSize(150,30);
		EnemyInfo.setText("Enemy: 1000HP");
		EnemyInfo.setFont(new Font("Impact",Font.PLAIN,20));
		
		RoundMsg.setLocation(20,220);
		RoundMsg.setSize(150,30);
		RoundMsg.setText("Round: 0");
		RoundMsg.setFont(new Font("Impact",Font.PLAIN,20));
		
		Infopanel.setLayout(null);
		Infopanel.setBackground(new Color(180,200,200,255));
		Infopanel.setLocation(1020,250);
		Infopanel.setSize(230,260);
		Infopanel.add(GoldMsg);
		Infopanel.add(UpgradeMsg);
		Infopanel.add(LifeMsg);
		Infopanel.add(RoundMsg);
		Infopanel.add(EnemyInfo);
		Infopanel.add(IPImage);
		
		for(int i=0; i<4; i++) {
			String tankimage = "src/images/new_tank_" + String.valueOf(i+1) + ".png";
			indicator[i] = new JLabel(new ImageIcon(tankimage));
			indicator[i].setLocation(10 + 220*i,10);
			indicator[i].setSize(100,100);
			
			randomlist[i] = new JLabel();
			randomlist[i].setLocation(220*i+120,10);
			randomlist[i].setSize(100,100);
			randomlist[i].setFont(new Font("Impact",Font.PLAIN,40));
			Msgpanel.add(indicator[i]);
			Msgpanel.add(randomlist[i]);
		}
		randomlist[0].setText(": 65%");
		randomlist[1].setText(": 25%");
		randomlist[2].setText(": 9%");
		randomlist[3].setText(": 1%");
		
		temp = new ImageIcon("src/images/msgpanel.png");
		img = temp.getImage();  //ImageIcon을 Image로 변환.
		yimg = img.getScaledInstance(860,120, java.awt.Image.SCALE_SMOOTH);
		modified = new ImageIcon(yimg);
		MSGImage = new JLabel(modified);
		MSGImage.setSize(860,120);
		MSGImage.setLocation(0,0);
		Msgpanel.add(MSGImage);
		
		Msgpanel.setLayout(null);
		Msgpanel.setLocation(10,520);
		Msgpanel.setSize(860,120);
		
		Reroll.setLocation(10,10);
		Reroll.setSize(100,100);
		Reroll.setFont(new Font("Impact",Font.PLAIN,15));
		Reroll.addActionListener(new RerollListener());
		Reroll.setVisible(false);
		EtcImage.setLocation(0,0);
		EtcImage.setSize(370,120);
		
		ErrorMsg.setLocation(120,10);
		ErrorMsg.setSize(250,100);
		ErrorMsg.setFont(new Font("Impact",Font.PLAIN,19));
		ErrorMsg.setVerticalAlignment(SwingConstants.CENTER);
		
		Etcpanel.setLayout(null);
		Etcpanel.setLocation(880,520);
		Etcpanel.setSize(370,120);
		Etcpanel.add(Reroll);
		Etcpanel.add(ErrorMsg);
		Etcpanel.add(EtcImage);
		
		
		contentPane.add(Coverpanel);
		contentPane.add(Gamepanel);
		contentPane.add(Controlpanel);
		contentPane.add(Infopanel);
		contentPane.add(Msgpanel);
		contentPane.add(Etcpanel);
		Coverpanel.setVisible(true);
		Gamepanel.setVisible(true);
		Controlpanel.setVisible(true);
		Infopanel.setVisible(true);
		Msgpanel.setVisible(true);
		Etcpanel.setVisible(true);
		
		
		this.setVisible(true);
	}
	class SummonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(Board.start_phase == true) {
				ErrorMsg.setText("You can't summon while fight!");
				return ;
			}
			if(Board.gold < 10) {
				ErrorMsg.setText("Not Enought Gold!");
				return ;
			}
			if(Board.TowerRandom.size() == 0) {
				ErrorMsg.setText("No More Space For Tower!");
				return ;
			}
			Board.gold -= 10;
			GoldMsg.setText("Gold : " + String.valueOf(Board.gold));
			Random rd = new Random();
			int indicator = rd.nextInt(Board.TowerRandom.size());
			int x = Board.TowerRandom.get(indicator).first;
			int y = Board.TowerRandom.get(indicator).second;
			Board.TowerRandom.remove(indicator);
			int grade = rd.nextInt(100)+1;
			int tier = 1;
			if(grade <= 65) {
				tier = 1;
			} else if(grade <= 90) {
				tier = 2;
			} else if(grade <= 99) {
				tier = 3;
			} else if(grade == 100) {
				tier = 4;
			}
			String first = "src/images/new_tank_";
			String second = ".png";
			String tankimage = first + String.valueOf(tier) + second;
			tgui[x][y].setVisible(true);
			tgui[x][y].image = new ImageIcon(tankimage).getImage();
			Board.towerlist[x][y].setTier(tier);
			Board.towerlist[x][y].visible = true;
			px = x;
			py = y;
			
		}
	}
	class UpgradeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(Board.gold >= 150) {
				Board.upgrade++;
				Board.gold -= 150;
				GoldMsg.setText("Gold : " + String.valueOf(Board.gold));
				UpgradeMsg.setText("Upgrade: " + String.valueOf(Board.upgrade));
			} else {
				ErrorMsg.setText("Not Enough Gold");
			}
			
		}
	}
	class BattleListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(Board.start_phase == false)
				Board.start_phase = true;
			else
				ErrorMsg.setText("Battle Already Begun!");
		}
	}
	
	class StartListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Coverpanel.setVisible(false);
			Summon.setVisible(true);
			Upgrade.setVisible(true);
			Battle.setVisible(true);
			Reroll.setVisible(true);
		}
	}
	
	class RerollListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(Board.start_phase == true) {
				ErrorMsg.setText("You can't change while fight!");
				return ;
			}
			if(px == -1 || py == -1) {
				ErrorMsg.setText("No Tower");
				return;
			}
			if(Board.gold < 20) {
				ErrorMsg.setText("Not Enough Gold");
				return;
			}
			Board.gold -= 20;
			GoldMsg.setText("Gold : " + String.valueOf(Board.gold));
			Random rd = new Random();
			int grade = rd.nextInt(100)+1;
			int tier = 1;
			if(grade <= 65) {
				tier = 1;
			} else if(grade <= 90) {
				tier = 2;
			} else if(grade <= 99) {
				tier = 3;
			} else if(grade == 100) {
				tier = 4;
			}
			ErrorMsg.setText("You Changed To " + tier + "tier Tower");
			String first = "src/images/new_tank_";
			String second = ".png";
			String tankimage = first + String.valueOf(tier) + second;
			tgui[px][py].image = new ImageIcon(tankimage).getImage();
			tgui[px][py].repaint();
			Board.towerlist[px][py].setTier(tier);
		}
	}
}

class JRotate extends JComponent {
	Image image;
	double Radian;
	
	JRotate(String imagepath,double RotateAngle) {
		this.image = new ImageIcon(imagepath).getImage();
		this.Radian = RotateAngle;
		this.setVisible(false);
	}
	
	void SetRadian(double Radian) {
		this.Radian = Radian;
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
        int x = getWidth() / 2;
        int y = getHeight() / 2;
        g2d.translate(x, y);

        g2d.rotate(this.Radian);
        g2d.translate(-x, -y);
        g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        g2d.dispose();
	}
}