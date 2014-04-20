package org.calculator1.core;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CalcultorBorderLayout {
	final static int VGAP=5, HGAP=5;
	final static String[] OPERATORS={"+","-","*","/"};
	final private int numberLength=9;
	final private int borderLenth=5;
	private JTextField numberField1, numberField2, resultField, focusedNumberField;
	private JLabel operatorLabel;
	private CalculatorController controller;
	public JLabel getOperatorLabel() {
		return operatorLabel;
	}
	public JTextField getNumberField2() {
		return numberField2;
	}
	public JTextField getNumberField1() {
		return numberField1;
	}
	public CalcultorBorderLayout(){
		numberField1 = new JTextField(numberLength);
		numberField1.setName("numberField1");
		focusedNumberField=numberField1;
		numberField2 = new JTextField(numberLength);
		resultField = new JTextField(10);
		resultField.setEditable(false);
		operatorLabel = new JLabel("op");
	}
	private class JButtonEqual extends JButtonWithAction{
		JButtonEqual(String text) { super(text); }
		public void actionPerformed(ActionEvent e) {
			String numerString1 = numberField1.getText();
			String operator = operatorLabel.getText();
			String numerString2 = numberField2.getText();
			if(numerString1.length()==0)
				throw new NullPointerException("numberField1 must not by null");
			if(numerString2.length()==0)
				throw new NullPointerException("numberField2 must not by null");
			if(operator.length()==0)
				throw new NullPointerException("operator must not by null");
			
			String resultString = controller.calc(numerString1, operator, numerString2);
			resultField.setText(resultString);
			
		}
		
	}
	private class JButtonOperator extends JButtonWithAction{
		JButtonOperator(String text) { super(text); }
		public void actionPerformed(ActionEvent e) {
			String addNumer = e.getActionCommand();
			operatorLabel.setText(addNumer);
			if(numberField1.getText().length()>0)
				focusedNumberField=numberField2;
			else 
				focusedNumberField=numberField1;
			focusedNumberField.requestFocus();
		}
	}
	private class JButtonClean extends JButtonWithAction{ 
		JButtonClean(String text) { super(text); }
		public void actionPerformed(ActionEvent e) {
			numberField1.setText(null);
			numberField2.setText(null);
			operatorLabel.setText(null);
			focusedNumberField=numberField1;
		}
	}
	private class JButtonDel extends JButtonWithAction{
		JButtonDel(String text) { super(text); }
		public void actionPerformed(ActionEvent e) {
			String oldValue2 = numberField2.getText();
			if(oldValue2.length()>0){
				String newValue = oldValue2.substring(0, oldValue2.length()-1);
				numberField2.setText(newValue);
				return;
			}
			String operator = operatorLabel.getText();
			if(null!=operator&&operator.length()>0&&!"op".equals(operator)){
				operatorLabel.setText(null);
				return;
			}
			String oldValue1 = numberField1.getText();
			if(oldValue1.length()>0){
				String newValue = oldValue1.substring(0, oldValue1.length()-1);
				numberField1.setText(newValue);
				return;
			}
		}
	}
	private class JButtonNumer extends JButtonWithAction {
		JButtonNumer(String text) { super(text); }
		public void actionPerformed(ActionEvent e) {
			String addNumer = e.getActionCommand();
			String oldValue = focusedNumberField.getText();
			String newValue = oldValue+addNumer;
			focusedNumberField.setText(newValue);
//			Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		}
	}
	private abstract class JButtonWithAction extends JButtonWithDecor implements ActionListener{
		JButtonWithAction(String text) {
			super(text);
			addActionListener(this);
		}
		
	}
	private class JButtonWithDecor extends JButton{
		JButtonWithDecor(String text) {
			super(text);
			setContentAreaFilled(false);
		}
	}
	public void fullContentPane(Container c) {
		JPanel panelResult = new JPanel(new BorderLayout(HGAP,VGAP));
		panelResult.add(new JLabel("="),BorderLayout.WEST);
		panelResult.add(resultField,BorderLayout.CENTER);
		JPanel panelNumerWork = new JPanel(new BorderLayout(HGAP,VGAP));
		panelNumerWork.add(numberField1,BorderLayout.WEST);
		panelNumerWork.add(operatorLabel,BorderLayout.CENTER);
		panelNumerWork.add(numberField2,BorderLayout.EAST);
		panelNumerWork.add(panelResult,BorderLayout.SOUTH);
		
		JPanel p123 = new JPanel(new BorderLayout(HGAP,VGAP));
		p123.add(new JButtonNumer("1"),BorderLayout.WEST);
		p123.add(new JButtonNumer("2"),BorderLayout.CENTER);
		p123.add(new JButtonNumer("3"),BorderLayout.EAST);
		
		JPanel p456 = new JPanel(new BorderLayout(HGAP,VGAP));
		p456.add(new JButtonNumer("4"),BorderLayout.WEST);
		p456.add(new JButtonNumer("5"),BorderLayout.CENTER);
		p456.add(new JButtonNumer("6"),BorderLayout.EAST);
		
		JPanel p789 = new JPanel(new BorderLayout(HGAP,VGAP));
		p789.add(new JButtonNumer("7"),BorderLayout.WEST);
		p789.add(new JButtonNumer("8"),BorderLayout.CENTER);
		p789.add(new JButtonNumer("9"),BorderLayout.EAST);
		
		JPanel p0eqal = new JPanel(new BorderLayout(HGAP,VGAP));
		p0eqal.add(new JButtonNumer("0"), BorderLayout.WEST);
		JButton buttonEqual = new JButtonEqual("=");
		p0eqal.add(buttonEqual, BorderLayout.CENTER);
		JPanel panelNumber = new JPanel();
		panelNumber.setLayout(new BoxLayout(panelNumber, BoxLayout.Y_AXIS));
		panelNumber.setBorder(BorderFactory.createEmptyBorder(borderLenth, borderLenth, borderLenth, borderLenth));
		panelNumber.add(p123);
		panelNumber.add(p456);
		panelNumber.add(p789);
		panelNumber.add(p0eqal);
		
		JPanel panelOperator = new JPanel();
		panelOperator.setLayout(new BoxLayout(panelOperator, BoxLayout.PAGE_AXIS));
		panelOperator.setBorder(BorderFactory.createEmptyBorder(borderLenth, borderLenth, borderLenth, borderLenth));
		for (String operatorName : OPERATORS) {
			panelOperator.add(new JButtonOperator(operatorName));
		}
		panelOperator.add(new JButtonDel("Del"));
		panelOperator.add(new JButtonClean("Clear"));
		
		c.setLayout(new BorderLayout());
		c.add(panelNumerWork, BorderLayout.NORTH);
		c.add(panelNumber, BorderLayout.CENTER);
		c.add(panelOperator, BorderLayout.EAST);
		
	}
	public void registerObserver(CalculatorController controller) {
		this.controller=controller;
	}
	public static void main(String[] args) {
		CalcultorBorderLayout frame = new CalcultorBorderLayout();
		JFrame jFrame = new JFrame();
		Container contentPane = jFrame.getContentPane();
		frame.fullContentPane(contentPane);
		jFrame.pack();
		jFrame.setVisible(true);
	}
}
