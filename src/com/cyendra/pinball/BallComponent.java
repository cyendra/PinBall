package com.cyendra.pinball;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BallComponent {
	
	private int x = -1;
	private int y = -1;
	private int speed = 5;
	private Image image = null;
	
	public BallComponent(String path) throws IOException {
		super();
		this.image = ImageIO.read(new File(path));
	}
	
	public BallComponent(int panelWidth,int panelHeight,String path) throws IOException {		
		super();
		this.image = ImageIO.read(new File(path));
		this.x = (int) ((panelWidth - image.getWidth(null)) / 2);
	}
	
	public BallComponent(String path,int x,int y) throws IOException {
		super();
		this.image = ImageIO.read(new File(path));
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public void setX(int x) {
		this.x=x;
	}
	
	public void setY(int y) {
		this.y=y;
	}
	
	Image getImage() {
		return this.image;
	}
}
