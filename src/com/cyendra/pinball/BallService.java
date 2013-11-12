package com.cyendra.pinball;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class BallService {
	
	private Stick stick = null;
	private Ball ball = null;
	private BallComponent gameOver = null;
	private BallComponent won = null;
	private Brick[][] bricks = null;
	private int width;
	private int height;
	BallFrame ballFrame = null;
	
	private BallService() {
		super();
	}
	
	public BallService(BallFrame frame, int width, int height) throws IOException {
		this.width = width;
		this.height = height;
		this.ballFrame = frame;
		stick = new Stick(width, height, "img/stick.jpg");
		ball = new Ball(width, height, stick.getImage().getHeight(null), "img/ball.gif");
		gameOver = new BallComponent("img/over.gif");
		won = new BallComponent("img/win.gif");
		bricks = createBrickArr("img/brick.gif", 11, 6);
	}
	
	private Brick[][] createBrickArr(String path, int xSize, int ySize) throws IOException {
		Brick[][] bricks = new Brick[xSize][ySize];
		int x = 0;
		int y = 0;
		int random = 0;
		int imageSize = 28;
		boolean isDisable = false;
		for (int i = 0; i < xSize; i++) {
			for (int j = 0; j < ySize; j++) {
				random = (int)(Math.random()*3);
				x = i * imageSize;
				y = j * imageSize;
				isDisable = Math.random() > 0.8 ? true : false;
				if (isDisable) {
					random = 0;
				}
				Brick brick = new Brick(path, random, x, y);
				brick.setDisable(isDisable);
				brick.setX(x);
				brick.setY(y);
				bricks[i][j]=brick;
			}
		}
		return bricks;
	}

	public boolean isWon() {
		for (int i = 0; i < bricks.length; i++) {
			for (int j = 0; j < bricks[i].length; j++) {
				if (!bricks[i][j].isDisable()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void run() {
		setBallPos();
		setMagicPos();
	}

	private void setMagicPos() {
		for (int i = 0; i < bricks.length; i++) {
			for (int j = 0; j < bricks[i].length; j++) {
				Magic magic = bricks[i][j].getMagic();
				if (magic != null) {
					if (bricks[i][j].isDisable() && magic.getY() < height) {
						magic.setY(magic.getY() + magic.getSpeed());
						setStickWidth(magic);
					}
				}
			}
		}
	}

	private void setStickWidth(Magic magic) {
		if (isHitStick(magic)) {
			// 道具的作用
			magic.magicDo(stick);
		}
	}

	private boolean isHitStick(BallComponent bc) {
		// 获取图片对象
		Image tempImage = bc.getImage();
		// 如果与档板有碰撞
		if (bc.getX() + tempImage.getWidth(null) > stick.getX()
				&& bc.getX() < stick.getX() + stick.getPreWidth()
				&& bc.getY() + tempImage.getHeight(null) > stick.getY()) {
			return true;
		}
		return false;
	}

	private void setBallPos() {
		// 正数的数度
		int absSpeedX = Math.abs(ball.getSpeedX());
		int absSpeedY = Math.abs(ball.getSpeedY());
		// 如果游戏已经开始而且没有结束
		if (ball.isStarted()) {
			// 如果小球碰到左边界
			if (ball.getX() - absSpeedX < 0) {
				// 重新设置x坐标
				ball.setX(ball.getImage().getWidth(null));
				// 把x方向的速度设为反方向
				ball.setSpeedX(-ball.getSpeedX());
			}
			// 如果小球碰到右边界
			if (ball.getX() + absSpeedX > width
					- ball.getImage().getWidth(null)) {
				// 重新设置x坐标
				ball.setX(width - ball.getImage().getWidth(null) * 2);
				// 把x方向的速度设为反方向
				ball.setSpeedX(-ball.getSpeedX());
			}
			// 如果小球碰到上边界
			if (ball.getY() - absSpeedY < 0) {
				// 重新设置y坐标
				ball.setY(ball.getImage().getWidth(null));
				// 把y方向的速度设为反方向
				ball.setSpeedY(-ball.getSpeedY());
			}
			// 如果小球碰到下边界
			if (ball.getY() + absSpeedY > height
					- stick.getImage().getHeight(null)) {
				// 如果小球与档板有碰撞
				if (isHitStick(ball)) {
					// 重新设置y坐标
					ball.setY(height - ball.getImage().getHeight(null) * 2);
					// 把y方向的速度设为反方向
					ball.setSpeedY(-ball.getSpeedY());
				}
			}
			// 与砖块碰撞后的运动
			for (int i = bricks.length - 1; i > -1; i--) {
				for (int j = bricks[i].length - 1; j > -1; j--) {
					// 如果小球与砖块有碰撞
					if (isHitBrick(bricks[i][j])) {
						if (ball.getSpeedY() > 0) {
							ball.setSpeedY(-ball.getSpeedY());
						}
					}
				}
			}
			// 结束游戏
			if (ball.getY() > height) {
				ball.setStop(true);
			}

			// 设置x坐标
			ball.setX(ball.getX() - (int) (Math.random() * 2)
					- ball.getSpeedX());
			// 设置y坐标
			ball.setY(ball.getY() - (int) (Math.random() * 2)
					- ball.getSpeedY());
		}
	}

	private boolean isHitBrick(Brick brick) {
		if (brick.isDisable()) {
			return false;
		}
		// ball的圆心x坐标
		double ballX = ball.getX() + ball.getImage().getWidth(null) / 2;
		// ball的圆心y坐标
		double ballY = ball.getY() + ball.getImage().getHeight(null) / 2;
		// brick的中心x坐标
		double brickX = brick.getX() + brick.getImage().getWidth(null) / 2;
		// brick的中心y坐标
		double brickY = brick.getY() + brick.getImage().getHeight(null) / 2;
		// 两个坐标点的距离
		double distance = Math.sqrt(Math.pow(ballX - brickX, 2)
				+ Math.pow(ballY - brickY, 2));
		// 如果两个图形重叠，返回true;
		if (distance < (ball.getImage().getWidth(null) + brick.getImage()
				.getWidth(null)) / 2) {
			// 使brick无效
			brick.setDisable(true);
			return true;

		}
		return false;
	}

	public void setStickPos(KeyEvent ke) {
		ball.setStarted(true);
		if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
			if (stick.getX() - stick.SPEED > 0) {
				stick.setX(stick.getX() - stick.SPEED);
			}
		}
		if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (stick.getX() + stick.SPEED < width - stick.getPreWidth()) {
				stick.setX(stick.getX() + stick.SPEED);
				// ballFrame.getBallGame().reStart( ballFrame );
			}
		}

		if (ke.getKeyCode() == KeyEvent.VK_F2) {
			try {
				ballFrame.initialize();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void draw(Graphics g) {
		if (isWon()) {
			g.drawImage(won.getImage(), won.getX(), won.getY(), width, height - 10, null);
		} 
		else if (ball.isStop()) {
			g.drawImage(gameOver.getImage(), gameOver.getX(), gameOver.getY(), width, height - 10, null);
		} 
		else {			
			g.clearRect(0, 0, width, height);
			g.drawImage(stick.getImage(), stick.getX(), stick.getY(), stick.getPreWidth(), stick.getImage().getHeight(null), null);
			g.drawImage(ball.getImage(), ball.getX(), ball.getY(), null);
			for (int i = 0; i < bricks.length; i++) {
				for (int j = 0; j < bricks[i].length; j++) {
					BallComponent magic = bricks[i][j].getMagic();
					if (!bricks[i][j].isDisable()) {
						g.drawImage(bricks[i][j].getImage(), 
									bricks[i][j].getX(),
									bricks[i][j].getY(),
									bricks[i][j].getImage().getWidth(null) - 1,
									bricks[i][j].getImage().getHeight(null) - 1,
									null);
					} 
					else if (magic != null && magic.getY() < height) {
						g.drawImage(magic.getImage(), magic.getX(), magic.getY(), null);
					}
				}
			}
		}
	}

}
