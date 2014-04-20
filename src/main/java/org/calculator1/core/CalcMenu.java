package org.calculator1.core;

import javax.swing.JApplet;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class CalcMenu extends JMenuBar{
	public CalcMenu(){
		super();
		JMenu menuFile = new JMenu("File");
		JMenuItem openItem = new JMenuItem("Open");
		menuFile.add(openItem);
		JMenuItem saveItem = new JMenuItem("Save");
		menuFile.add(saveItem);
		add(menuFile);
		JMenu menuHelp = new JMenu("Help");
		JMenuItem aboutItem = new JMenuItem("About");
		menuHelp.add(aboutItem);
		add(menuHelp);
	}
	
}
