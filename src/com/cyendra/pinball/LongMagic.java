package com.cyendra.pinball;

import java.io.IOException;

public class LongMagic extends Magic {

	public LongMagic(String path, int x, int y) throws IOException {
		super(path, x, y);
	}

	public void magicDo(Stick stick) {
		double imageWidth = stick.getImage().getWidth(null);
		if (stick.getPreWidth()<=imageWidth) {
			stick.setPreWidth((int)(stick.getPreWidth()*2));
		}
	}

}
