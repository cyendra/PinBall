package com.cyendra.pinball;

import java.io.IOException;

public class Ball extends BallComponent {
	
	private int speedY = 10;
	private int speedX = 8;
	private boolean started = false;
	private boolean stop = false;
	
	public Ball(int panelWidth,int panelHeight,int offset, String path) throws IOException {
		super(panelWidth,panelHeight,path);
		this.setY(panelHeight-super.getImage().getHeight(null)-offset);
	}

	public void setStarted(boolean b) {
		this.started = b;
	}
	public void setStop(boolean b) {
		this.stop = b;
	}
	
	public boolean isStarted() {
		return this.started;
	}
	public boolean isStop() {
		return this.stop;
	}
	
	public void setSpeedX(int speed) {
		this.speedX = speed;
	}
	public void setSpeedY(int speed) {
		this.speedY = speed;
	}
	
	public int getSpeedX() {
		return this.speedX;
	}
	public int getSpeedY() {
		return this.speedY;
	}

}
