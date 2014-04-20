package org.applet.softzenware;

import java.applet.Applet;
import java.awt.Canvas;

/**
 * @author roman
 * @see http://www.softzenware.com/java/16.htm
 */
public class PanelDemo extends Applet {
	static final int n = 4;
	public void init() {
		setLayout(null);
		int width = Integer.parseInt(getParameter("width"));
		int height = Integer.parseInt(getParameter("height"));
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				float g = (i * n + j) / (float) (n * n);
				Canvas c = new GrayCanvas(g);
				add(c);
				c.resize(width / n, height / n);
				c.move(i * width / n, j * height / n);
			}
		}
	}
}