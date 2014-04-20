package org.calculator1;

import javax.swing.JFrame;

import org.calculator1.core.CalcMenu;
import org.calculator1.core.CalculatorController;
import org.calculator1.core.CalculatorModel;
import org.calculator1.core.CalcultorBorderLayout;

public class CalculatorGUI {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		//MVC construction 
		CalculatorModel model = new CalculatorModel();
		CalcultorBorderLayout layout = new CalcultorBorderLayout();
		
		frame.setJMenuBar(new CalcMenu());
		layout.fullContentPane(frame.getContentPane());
		CalculatorController controller 
			= new CalculatorController(model, layout);
		layout.registerObserver(controller);
		frame.pack();
		frame.setVisible(true);
		//init controller with test data
		test(controller);
	}
	private static void test(CalculatorController controller) {
		controller.setNumber1(2);
		controller.setNumber2(3);
		controller.setOperator("+");
		controller.initView();
	}
}
