package com.cyendra.pinball;

import java.io.IOException;

public class Brick extends BallComponent {
	
	public static final int MAGIC_LONG_TYPE = 1;
	public static final int MAGIC_SHORT_TYPE = 2;
	
	private Magic magic = null;
	private boolean disable = false;
	
	public Brick(String path, int type, int x, int y) throws IOException {
		super(path);
		if (type == Brick.MAGIC_LONG_TYPE) {
			this.magic = new LongMagic("img/long.gif",x,y);
		}
		else if (type == Brick.MAGIC_SHORT_TYPE) {
			this.magic = new ShortMagic("img/short.gif",x,y);
		}
		if (this.magic != null) {
			this.magic.setX(x);
			this.magic.setY(y);
		}
	}
	
	public void setDisable(boolean disable) {
		this.disable = disable;
	}
	
	public boolean isDisable() {
		return this.disable;
	}
	
	public Magic getMagic() {
		return this.magic;
	}
	
	public void setMagic(Magic magic) {
		this.magic = magic;
	}

}
