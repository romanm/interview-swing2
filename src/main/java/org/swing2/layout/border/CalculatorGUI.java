package org.swing2.layout.border;

import javax.swing.JFrame;

import org.calculator1.core.CalcMenu;

public class CalculatorGUI extends JFrame{
	public static void main(String[] args) {
		//MVC construction 
		CalculatorModel model = new CalculatorModel();
		CalcultorBorderLayout layout = new CalcultorBorderLayout();
		CalculatorGUI view = new CalculatorGUI();
		view.setJMenuBar(new CalcMenu());
		layout.fullContentPane(view.getContentPane());
		CalculatorController controller 
			= new CalculatorController(model, layout);
		layout.registerObserver(controller);
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
