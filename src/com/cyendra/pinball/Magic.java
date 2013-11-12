package com.cyendra.pinball;

import java.io.IOException;

public abstract class Magic extends BallComponent {

	public Magic(String path, int x, int y) throws IOException {
		super(path, x, y);
	}

	public abstract void magicDo(Stick stick);
	
}
