package com.cyendra.pinball;

import java.io.IOException;

public class Stick extends BallComponent {
	
	public static final int SPEED = 10;
	private int preWidth = 0;

	public Stick(int panelWidth,int panelHeight, String path) throws IOException {
		super(panelWidth,panelHeight,path);
		this.setY(panelHeight-super.getImage().getHeight(null));
		this.preWidth = super.getImage().getWidth(null);
		this.setSpeed(SPEED);
	}

	public int getPreWidth() {
		return this.preWidth;
	}

	public void setPreWidth(int preWidth) {
		this.preWidth = preWidth;
	}

}
