package org.swing2.layout.border;

public class CalculatorController {
	private CalcultorBorderLayout view;
	private CalculatorModel model;

	public CalculatorController(CalculatorModel model, CalcultorBorderLayout view) {
		this.view=view;
		this.model=model;
	}

	public void setNumber2(int i) {
		model.setNumber2(parseInt(i));
	}
	public void setNumber1(int i) {
		model.setNumber1(parseInt(i));
	}

	private String parseInt(int i) {
		Integer integer = new Integer(i);
		String number1 = integer.toString();
		return number1;
	}

	public void setOperator(String operator) {
		model.setOperator(operator);
	}

	public String calc(String numerString1, String operator, String numerString2) {
		int numer1 = Integer.parseInt(numerString1);
		int numer2 = Integer.parseInt(numerString2);
		Integer result=null;
		switch (operator) {
		case "+":
			result=numer1+numer2;
			break;
		case "-":
			result=numer1-numer2;
			break;
		case "*":
			result=numer1*numer2;
			break;
		case "/":
			result=numer1/numer2;
			break;
		default:
			break;
		}
		String resultString = "";
		if(null!=result)
			resultString=result.toString();
		return resultString;
	}

	public void initView() {
		view.getNumberField1().setText(model.getNumber1());
		view.getNumberField2().setText(model.getNumber2());
		view.getOperatorLabel().setText(model.getOperator());
	}
}
