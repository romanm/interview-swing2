package org.applet.jmenu;


import javax.swing.JApplet;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author roman
 * @see http://www.java2s.com/Code/JavaAPI/javax.swing/JAppletsetJMenuBarJMenuBarmenuBar.htm
 */
public class MenuApplet extends JApplet {
	public void init() {
		JMenuBar menubar = menuBar();
		setJMenuBar(menubar);
	}
	JMenuBar menuBar() {
		JMenuBar menubar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenuItem openItem = new JMenuItem("Open");
		menuFile.add(openItem);
		JMenuItem saveItem = new JMenuItem("Save");
		menuFile.add(saveItem);
		menubar.add(menuFile);
		JMenu menuHelp = new JMenu("Help");
		JMenuItem aboutItem = new JMenuItem("About");
		menuHelp.add(aboutItem);
		menubar.add(menuHelp);
		return menubar;
	}
}
