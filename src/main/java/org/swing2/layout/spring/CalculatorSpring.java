package org.swing2.layout.spring;

import java.awt.Component;
import java.awt.Container;
import java.awt.Label;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Spring;
import javax.swing.SpringLayout;

public class CalculatorSpring {
	private static final int NUMBER_OF_DIGITS = 10;
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("SpringCalculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = frame.getContentPane();
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		//Set up the content pane.
		JPanel calcPanel = new JPanel();
		SpringLayout calcLayout = new SpringLayout();
		calcPanel.setLayout(calcLayout);
		Label label = new Label("resault");
		calcPanel.add(label);
		calcLayout.putConstraint(SpringLayout.WEST, label, 5,
				SpringLayout.WEST, calcPanel);
		calcLayout.putConstraint(SpringLayout.NORTH, label, 5,
				SpringLayout.NORTH, calcPanel);
		JPanel numberPanel = new JPanel();
		frame.add(numberPanel);
		layout.putConstraint(SpringLayout.WEST, calcPanel, 5,
				SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, calcPanel, 5,
				SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, numberPanel, 5,
				SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, numberPanel, 5,
				SpringLayout.EAST, calcPanel);
		numberPanel(numberPanel);
		//Adjust constraints for the content pane.
		setContainerSize(frame.getContentPane(), 5);

		//Display the window.
		frame.pack();
		frame.setSize(200, 300);
		frame.setVisible(true);
	}
	private static void numberPanel(JPanel numberPanel) {
		SpringLayout layout = new SpringLayout();
		numberPanel.setLayout(layout);
		JButton[] numberButtons = new JButton[NUMBER_OF_DIGITS];
		for (int i = 0; i < NUMBER_OF_DIGITS; i++) {
			numberPanel.add(numberButtons[i] = new JButton(""+i));
			switch (i) {
			case 0: case 1: case 4: case 7: 
				layout.putConstraint(SpringLayout.WEST, numberButtons[i], 5,
						SpringLayout.WEST, numberPanel);
				break;
			default:
				layout.putConstraint(SpringLayout.WEST, numberButtons[i], 5,
						SpringLayout.EAST, numberButtons[i-1]);
			}
			switch (i) {
			case 0: break;
			case 1: case 2: case 3:
				layout.putConstraint(SpringLayout.NORTH, numberButtons[i], 5,
						SpringLayout.NORTH, numberPanel);
				break;
			default:
				layout.putConstraint(SpringLayout.NORTH, numberButtons[i], 5,
						SpringLayout.SOUTH, numberButtons[i-3]);
			}
		}
		layout.putConstraint(SpringLayout.NORTH, numberButtons[0], 5,
				SpringLayout.SOUTH, numberButtons[9]);
	}
	public static void setContainerSize(Container parent, int pad) {
		SpringLayout layout = (SpringLayout) parent.getLayout();
		Component[] components = parent.getComponents();
		Spring maxHeightSpring = Spring.constant(0);
		SpringLayout.Constraints pCons = layout.getConstraints(parent);

		//Set the container's right edge to the right edge
		//of its rightmost component + padding.
		Component rightmost = components[components.length - 1];
		SpringLayout.Constraints rCons =
				layout.getConstraints(rightmost);
		pCons.setConstraint(
				SpringLayout.EAST,
				Spring.sum(Spring.constant(pad),
						rCons.getConstraint(SpringLayout.EAST)));

		//Set the container's bottom edge to the bottom edge
		//of its tallest component + padding.
		for (int i = 0; i < components.length; i++) {
			SpringLayout.Constraints cons =
					layout.getConstraints(components[i]);
			maxHeightSpring = Spring.max(maxHeightSpring,
					cons.getConstraint(
							SpringLayout.SOUTH));
		}
		pCons.setConstraint(
				SpringLayout.SOUTH,
				Spring.sum(Spring.constant(pad), maxHeightSpring));
	}
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
