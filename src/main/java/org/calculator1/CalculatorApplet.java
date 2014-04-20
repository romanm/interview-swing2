package org.calculator1;

import javax.swing.JApplet;

import org.calculator1.core.CalcMenu;
import org.calculator1.core.CalculatorController;
import org.calculator1.core.CalculatorModel;
import org.calculator1.core.CalcultorBorderLayout;

public class CalculatorApplet  extends JApplet {
	public void init() {
		//MVC construction 
		CalculatorModel model = new CalculatorModel();
		CalcultorBorderLayout layout = new CalcultorBorderLayout();

		CalcMenu menuBar = new CalcMenu();
		setJMenuBar(menuBar);
		layout.fullContentPane(getContentPane());
		CalculatorController controller 
		= new CalculatorController(model, layout);
		layout.registerObserver(controller);

	}
}
