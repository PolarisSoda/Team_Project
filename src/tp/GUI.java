package tp;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Frame extends JFrame {
	JPanel Gamepanel = new JPanel();
	JPanel Controlpanel = new JPanel();
	JPanel Infopanel = new JPanel();
	JPanel Queuepanel = new JPanel();
	JLabel tgui[][] = new JLabel[10][5];
	JButton Summon = new JButton("Summon");
	void Init() {
		for(int i=0; i<5; i++) {
			for(int j=0; j<10; j++) {
				tgui[j][i] = new JLabel("Test");
				tgui[j][i].setSize(100,100);
				tgui[j][i].setLocation(j*100,i*100);
				tgui[j][i].setOpaque(true);
				tgui[j][i].setBackground(new Color(255-i*5,255-j*5,0,255));
				tgui[j][i].setHorizontalAlignment(JLabel.CENTER);
				tgui[j][i].setVisible(true);
				Gamepanel.add(tgui[j][i]);
			}
		}
	}
	
	Frame() {
		this.setTitle("Defense");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocation(0,0);
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
		
		JButton Upgrade = new JButton("Upgrade");
		Upgrade.setLocation(10,70);
		Upgrade.setSize(100,50);
		
		JButton Battle = new JButton("Battle");
		Battle.setLocation(10,130);
		Battle.setSize(100,50);
		
		Controlpanel.setLayout(null);
		Controlpanel.setBackground(new Color(0,200,0,255));
		Controlpanel.setLocation(1020,10);
		Controlpanel.setSize(200,300);
		Controlpanel.add(Summon);
		Controlpanel.add(Upgrade);
		Controlpanel.add(Battle);
		
		Infopanel.setLayout(null);
		Infopanel.setBackground(new Color(0,0,200,255));
		Infopanel.setLocation(1020,320);
		Infopanel.setSize(200,300);
		
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
			Random rd = new Random();
			ImageIcon original = new ImageIcon("src/images/tanks_1.png");
			Image img = original.getImage();
			Image changeImg = img.getScaledInstance(100,100,Image.SCALE_SMOOTH);
			ImageIcon newer = new ImageIcon(changeImg);
			int x = rd.nextInt(10);
			int y = rd.nextInt(5);
			tgui[x][y].setIcon(newer);
			tgui[x][y].setText(null);
		}
	}
	class UpgradeListner implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Board.upgrade++;
			Board.gold -= 70;
		}
	}
	class BattleListner implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(Board.start_phase == false)
				Board.start_phase = true;
		}
	}
}
