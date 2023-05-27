package tp;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Frame extends JFrame {
	JPanel Gamepanel = new JPanel(); //게임 패널
	JPanel Controlpanel = new JPanel(); //컨트롤 패널, 버튼 포함하는 곳.
	JPanel Infopanel = new JPanel(); //인포 패널, gold든 목숨이든 에러메시지든 넣는 곳.
	JPanel Queuepanel = new JPanel(); //??
	
	JButton Summon = new JButton("Summon"); //소환
	JButton Upgrade = new JButton("Upgrade"); //업그레이드
	JButton Battle = new JButton("Battle"); //페이즈시작(적 생성하겠단뜻)
	
	JLabel GoldMsg = new JLabel(); //돈 표시기
	JLabel ErrorMsg = new JLabel(); //에러 메시지
	JLabel UpgradeMsg = new JLabel(); //업그레이드 표시기
	
	JLabel tgui[][] = new JLabel[10][5]; //포탑 표시하는 라벨
	JLabel egui[] = new JLabel[15]; //적 표시 라벨
	JLabel bgui[] = new JLabel[10001]; //총알 표시 라벨
	
	void Init() {
		for(int i=0; i<5; i++) {
			for(int j=0; j<10; j++) {
				ImageIcon original = new ImageIcon("src/images/tanks_1.png");
				Image img = original.getImage();
				Image changeImg = img.getScaledInstance(90,90,Image.SCALE_SMOOTH);
				ImageIcon newer = new ImageIcon(changeImg);
				tgui[j][i] = new JLabel(newer);
				tgui[j][i].setSize(90,90);
				tgui[j][i].setLocation(j*100+5,i*100+5);
				tgui[j][i].setVisible(false);
				Gamepanel.add(tgui[j][i]);
			}
		}
		
		for(int i=0; i<15; i++) {
			ImageIcon original = new ImageIcon("src/images/Enemy.png");
			Image img = original.getImage();
			Image changeImg = img.getScaledInstance(90,90,Image.SCALE_SMOOTH);
			ImageIcon newer = new ImageIcon(changeImg);
			egui[i] = new JLabel(newer);
			egui[i].setSize(90,90);
			egui[i].setLocation(5,405);
			egui[i].setVisible(false);
			Gamepanel.add(egui[i]);
		}
	}
	Frame() {
		this.setTitle("Defense");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(1280,720);
		this.setLayout(null);
		
		Container contentPane = this.getContentPane();
		
		Gamepanel.setLayout(null);
		Gamepanel.setBackground(new Color(128,0,0,255));
		Gamepanel.setLocation(10,10);
		Gamepanel.setSize(1000,500);
		
		Summon.setLocation(10,10);
		Summon.setSize(100,50);
		Summon.addActionListener(new SummonListener());
		
		Upgrade.setLocation(10,70);
		Upgrade.setSize(100,50);
		Upgrade.addActionListener(new UpgradeListener());
		
		Battle.setLocation(10,130);
		Battle.setSize(100,50);
		Battle.addActionListener(new BattleListener());
		
		Controlpanel.setLayout(null);
		Controlpanel.setBackground(new Color(0,200,0,255));
		Controlpanel.setLocation(1020,10);
		Controlpanel.setSize(200,300);
		Controlpanel.add(Summon);
		Controlpanel.add(Upgrade);
		Controlpanel.add(Battle);
		
		ErrorMsg.setLocation(10,10);
		ErrorMsg.setSize(150,30);
		
		GoldMsg.setLocation(10,50);
		GoldMsg.setSize(150,30);
		GoldMsg.setText("Gold : " + String.valueOf(Board.gold));
		
		Infopanel.setLayout(null);
		Infopanel.setBackground(new Color(180,200,200,255));
		Infopanel.setLocation(1020,320);
		Infopanel.setSize(200,300);
		Infopanel.add(ErrorMsg);
		Infopanel.add(GoldMsg);
		
		Queuepanel.setLayout(null);
		Queuepanel.setBackground(new Color(0,200,200,255));
		Queuepanel.setLocation(10,520);
		Queuepanel.setSize(1000,100);
		
		contentPane.add(Gamepanel);
		contentPane.add(Controlpanel);
		contentPane.add(Infopanel);
		contentPane.add(Queuepanel);
		Gamepanel.setVisible(true);
		Controlpanel.setVisible(true);
		Infopanel.setVisible(true);
		Queuepanel.setVisible(true);
		
		this.Init();
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
			Board.gold -= 10;
			GoldMsg.setText("Gold : " + String.valueOf(Board.gold));
			Random rd = new Random();
			
			int x = rd.nextInt(10);
			int y = rd.nextInt(5);
			tgui[x][y].setVisible(true);
			Board.towerlist[x][y].visible = true;
			
		}
	}
	class UpgradeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(Board.gold >= 70) {
				Board.upgrade++;
				Board.gold -= 70;
				GoldMsg.setText("Gold : " + String.valueOf(Board.gold));
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
}
