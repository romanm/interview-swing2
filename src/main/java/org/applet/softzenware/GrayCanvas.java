package org.applet.softzenware;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

class GrayCanvas extends Canvas {
	Color gray;
	public GrayCanvas(float g) {
		gray = new Color(g, g, g);
	}
	public void paint(Graphics g) {
		Dimension size = size();
		g.setColor(gray);
		g.fillRect(0, 0, size.width, size.height);
		g.setColor(Color.black);
		g.drawRect(0, 0, size.width-1, size.height-1);
	}
}
