package org.mvc.swing.calculator;

/**
 * Calculator demo model class.
 * 
 * The "model class" should implement an interface that is used as the declared
 * type where needed, but no interface is provided in this very simplified demo.
 * Instead, typical informal Javadoc comments are included with each method
 * here.
 * 
 * @author roman
 *
 */
public class CalculatorDemoModel {
	public CalculatorDemoModel() {
		super();
		number1="";
		operator="";
		number2="";
		resault="";
	}

	/**
	 * Model variables.
	 * 
	 */
	private String number1, operator, number2, resault;
}
