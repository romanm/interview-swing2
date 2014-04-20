package org.mvc.swing.calculator;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * View class.
 * 
 * The "view class" should implement an interface that is used as the declared
 * type where needed, but no interface is provided in this very simplified demo.
 * Instead, typical informal Javadoc comments are included with each method
 * here.
 * 
 * @author roman
 *
 */
public final class CalculatorDemoView extends JFrame implements ActionListener {
	private static final int NUMBER_OF_DIGITS = 10;
	/**
	 * Text areas.
	 */
	private JTextField number1Text;
	JTextField operatorText;
	JTextField number2Text;
	private JButton[] numberButtons;
	/**
	 * Controller object.
	 */
	private CalculatorDemoController controller;

	/**
	 * @throws HeadlessException
	 */
	public CalculatorDemoView() throws HeadlessException {
		// Create the JFrame being extended

		/*
		 * Call the JFrame (superclass) constructor with a String parameter to
		 * name the window in its title bar
		 */
		super("Simple GUI Calculator Demo With MVC");
		// Set up the GUI widgets --------------------------------------------

		/*
		 * Create widgets
		 */
		number1Text = new JTextField();
		operatorText = new JTextField();
		number2Text = new JTextField();
		numberButtons = new JButton[NUMBER_OF_DIGITS];
		/*
		 * Create a parameter panel organized using grid layout
		 */
		JPanel parameterPanel = new JPanel(new GridLayout(2, 3));
//		JPanel parameterPanel = new JPanel();
//		parameterPanel.setLayout(new BoxLayout(parameterPanel, BoxLayout.X_AXIS));
		parameterPanel.add(this.number1Text);
		parameterPanel.add(this.operatorText);
		parameterPanel.add(this.number2Text);
		parameterPanel.add(new JLabel("="));
		parameterPanel.add(new JLabel("resault"));
		/*
		 * Create a button panel organized using grid layout
		 */
		JPanel buttonPanel = new JPanel(new GridLayout(4, 3));
		int j;
		for (int i = 0; i < NUMBER_OF_DIGITS; i++) {
			numberButtons[j = i==9?0:i+1] = new JButton(""+j);
			buttonPanel.add(numberButtons[j]);
		}
		/*
		 * Add parameter panes and button panel to main window, 
		 * from left to right and top to bottom
		 */
		JPanel calculatorPanel = new JPanel(new GridLayout(2, 1));
		calculatorPanel.add(parameterPanel);
		calculatorPanel.add(buttonPanel);
		this.add(calculatorPanel);
		/*
		 * Make sure the main window is appropriately sized for the widgets in
		 * it, that it exits this program when closed, and that it becomes
		 * visible to the user now
		 */
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void registerObserver(CalculatorDemoController controller) {
		this.controller=controller;
	}

}
