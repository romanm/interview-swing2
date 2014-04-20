package org.mvc.swing.calculator;


/**
 * Calculator demo GUI using a version of the Model-View-Controller (MVC) design pattern.
 * @author roman
 *
 */
public class CalculatorDemoGUI {
/**
 * Main program that sets up main application window and starts user
 * interaction.
 * 
 * @param args
 *            command-line arguments; not used
 */
public static void main(String[] args) {
	/*
	 * Create instances of the model, view, and controller objects, and
	 * initialize them; view needs to know about controller, and controller
	 * needs to know about model and view
	 */
	CalculatorDemoModel calculatorModel = new CalculatorDemoModel();
	CalculatorDemoView calculatorDemoView = new CalculatorDemoView();
	CalculatorDemoController controller 
	= new CalculatorDemoController(calculatorModel, calculatorDemoView);
	
	calculatorDemoView.registerObserver(controller);
}
}
