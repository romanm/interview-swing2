package org.calculator1.core;

public class CalculatorModel {
	public CalculatorModel() {
		super();
		number1="";
		operator="";
		number2="";
		resault="";
	}

	public String getNumber1() {
		return number1;
	}

	public void setNumber1(String number1) {
		this.number1 = number1;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getNumber2() {
		return number2;
	}

	public void setNumber2(String number2) {
		this.number2 = number2;
	}

	public String getResault() {
		return resault;
	}

	public void setResault(String resault) {
		this.resault = resault;
	}

	/**
	 * Model variables.
	 * 
	 */
	private String number1, operator, number2, resault;

}
