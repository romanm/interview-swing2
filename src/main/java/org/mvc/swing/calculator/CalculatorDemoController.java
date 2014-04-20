package org.mvc.swing.calculator;

public class CalculatorDemoController {

	private CalculatorDemoModel calculatorModel;
	private CalculatorDemoView calculatorDemoView;

	public CalculatorDemoController(CalculatorDemoModel calculatorModel,
			CalculatorDemoView calculatorDemoView) {
		this.calculatorModel = calculatorModel;
		this.calculatorDemoView = calculatorDemoView;
		/*
		 * Update view to reflect initial value of model
		updateViewToMatchModel(this.model, this.view);
		 */
		updateViewToMatchModel();
	}

	private void updateViewToMatchModel() {
		// TODO Auto-generated method stub
		
	}

}
