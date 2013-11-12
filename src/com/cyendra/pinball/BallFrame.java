package com.cyendra.pinball;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class BallFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private final int BALLPANEL_WIDTH = 307;
	private final int BALLPANEL_HEIGHT = 400;
	
	private BallPanel ballPanel = null;
	private BallService service = null;
	Timer timer = null;
	
	public BallFrame() throws IOException {
		super();
		initialize();
	}
	
	public void initialize() throws IOException {
		this.setTitle("µ¯Çò");
		this.setResizable(false);
		this.setBackground(Color.black);
		ballPanel=getBallPanel();
		service = new BallService(this, BALLPANEL_WIDTH, BALLPANEL_HEIGHT);
		
		ActionListener task = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				service.run();
				ballPanel.repaint();
			}
		};
		if (timer!=null) {
			timer.restart();
		}
		else {
			timer = new Timer(100,task);
			timer.start();
		}
		
		this.add(ballPanel);
		
		KeyListener[] klarr = this.getKeyListeners();
		if (klarr.length == 0) {
			KeyListener keyAdapter = new KeyAdapter() {
				public void keyPressed(KeyEvent ke) {
					service.setStickPos(ke);
				}
			};
			this.addKeyListener(keyAdapter);
		}
		
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public BallPanel getBallPanel() {
		if (ballPanel == null) {
			ballPanel = new BallPanel();
			ballPanel.setPreferredSize(new Dimension(BALLPANEL_WIDTH,BALLPANEL_HEIGHT));
		}
		return ballPanel;
	}
	
	public class BallPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public void paint(Graphics g) {
			service.draw(g);
		}
	}
	
}
