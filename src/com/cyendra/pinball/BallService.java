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
			// ���ߵ�����
			magic.magicDo(stick);
		}
	}

	private boolean isHitStick(BallComponent bc) {
		// ��ȡͼƬ����
		Image tempImage = bc.getImage();
		// ����뵵������ײ
		if (bc.getX() + tempImage.getWidth(null) > stick.getX()
				&& bc.getX() < stick.getX() + stick.getPreWidth()
				&& bc.getY() + tempImage.getHeight(null) > stick.getY()) {
			return true;
		}
		return false;
	}

	private void setBallPos() {
		// ����������
		int absSpeedX = Math.abs(ball.getSpeedX());
		int absSpeedY = Math.abs(ball.getSpeedY());
		// �����Ϸ�Ѿ���ʼ����û�н���
		if (ball.isStarted()) {
			// ���С��������߽�
			if (ball.getX() - absSpeedX < 0) {
				// ��������x����
				ball.setX(ball.getImage().getWidth(null));
				// ��x������ٶ���Ϊ������
				ball.setSpeedX(-ball.getSpeedX());
			}
			// ���С�������ұ߽�
			if (ball.getX() + absSpeedX > width
					- ball.getImage().getWidth(null)) {
				// ��������x����
				ball.setX(width - ball.getImage().getWidth(null) * 2);
				// ��x������ٶ���Ϊ������
				ball.setSpeedX(-ball.getSpeedX());
			}
			// ���С�������ϱ߽�
			if (ball.getY() - absSpeedY < 0) {
				// ��������y����
				ball.setY(ball.getImage().getWidth(null));
				// ��y������ٶ���Ϊ������
				ball.setSpeedY(-ball.getSpeedY());
			}
			// ���С�������±߽�
			if (ball.getY() + absSpeedY > height
					- stick.getImage().getHeight(null)) {
				// ���С���뵵������ײ
				if (isHitStick(ball)) {
					// ��������y����
					ball.setY(height - ball.getImage().getHeight(null) * 2);
					// ��y������ٶ���Ϊ������
					ball.setSpeedY(-ball.getSpeedY());
				}
			}
			// ��ש����ײ����˶�
			for (int i = bricks.length - 1; i > -1; i--) {
				for (int j = bricks[i].length - 1; j > -1; j--) {
					// ���С����ש������ײ
					if (isHitBrick(bricks[i][j])) {
						if (ball.getSpeedY() > 0) {
							ball.setSpeedY(-ball.getSpeedY());
						}
					}
				}
			}
			// ������Ϸ
			if (ball.getY() > height) {
				ball.setStop(true);
			}

			// ����x����
			ball.setX(ball.getX() - (int) (Math.random() * 2)
					- ball.getSpeedX());
			// ����y����
			ball.setY(ball.getY() - (int) (Math.random() * 2)
					- ball.getSpeedY());
		}
	}

	private boolean isHitBrick(Brick brick) {
		if (brick.isDisable()) {
			return false;
		}
		// ball��Բ��x����
		double ballX = ball.getX() + ball.getImage().getWidth(null) / 2;
		// ball��Բ��y����
		double ballY = ball.getY() + ball.getImage().getHeight(null) / 2;
		// brick������x����
		double brickX = brick.getX() + brick.getImage().getWidth(null) / 2;
		// brick������y����
		double brickY = brick.getY() + brick.getImage().getHeight(null) / 2;
		// ���������ľ���
		double distance = Math.sqrt(Math.pow(ballX - brickX, 2)
				+ Math.pow(ballY - brickY, 2));
		// �������ͼ���ص�������true;
		if (distance < (ball.getImage().getWidth(null) + brick.getImage()
				.getWidth(null)) / 2) {
			// ʹbrick��Ч
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
