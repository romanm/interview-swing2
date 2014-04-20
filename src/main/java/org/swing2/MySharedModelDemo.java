package org.swing2;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.swing2.SharedModelDemo.SharedDataModel;

public class MySharedModelDemo extends JPanel{
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}

		});
	}
	private JList list;
	String newline = "\n";
	ListSelectionModel listSelectionModel;
	JTextArea output;
	MySharedModelDemo(){
		super(new BorderLayout());
		Vector data = new Vector(7);
		String[] columnNames = { "French", "Spanish", "Italian" };
		String[] oneData =     { "un",     "uno",     "uno"     };
		String[] twoData =     { "deux",   "dos",     "due"     };
		String[] threeData =   { "trois",  "tres",    "tre"     };
		String[] fourData =    { "quatre", "cuatro",  "quattro" };
		String[] fiveData =    { "cinq",   "cinco",   "cinque"  };
		String[] sixData =     { "six",    "seis",    "sei"     };
		String[] sevenData =   { "sept",   "siete",   "sette"   };
		SharedDataModel dataModel = new SharedDataModel(columnNames);
		dataModel.addElement(oneData);
		dataModel.addElement(twoData);
		dataModel.addElement(threeData);
		dataModel.addElement(fourData);
		dataModel.addElement(fiveData);
		dataModel.addElement(sixData);
		dataModel.addElement(sevenData);
		//Build control area (use default FlowLayout).
		JPanel controlPane = new JPanel();
		String[] modes = { "SINGLE_SELECTION",
				"SINGLE_INTERVAL_SELECTION",
				"MULTIPLE_INTERVAL_SELECTION" };
		final JComboBox comboBox = new JComboBox(modes);
		comboBox.setSelectedIndex(2);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newMode = (String)comboBox.getSelectedItem();
				if (newMode.equals("SINGLE_SELECTION")) {
					listSelectionModel.setSelectionMode(
							ListSelectionModel.SINGLE_SELECTION);
				} else if (newMode.equals("SINGLE_INTERVAL_SELECTION")) {
					listSelectionModel.setSelectionMode(
							ListSelectionModel.SINGLE_INTERVAL_SELECTION);
				} else {
					listSelectionModel.setSelectionMode(
							ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				}
				output.append("----------"
						+ "Mode: " + newMode
						+ "----------" + newline);
			}
		});

		controlPane.add(new JLabel("Selection mode:"));
		controlPane.add(comboBox);
		//Build output area.
		output = new JTextArea(10, 40);
		output.setEditable(false);
		JScrollPane outputPane = new JScrollPane(output,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		JPanel bottomHalf = new JPanel(new BorderLayout());
		bottomHalf.add(outputPane, BorderLayout.CENTER);
		bottomHalf.add(controlPane, BorderLayout.NORTH);
		//Do the layout.
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		add(splitPane, BorderLayout.CENTER);
		splitPane.add(bottomHalf);
		list = new JList(dataModel);
		listSelectionModel = list.getSelectionModel();
		list.setCellRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList l, 
					Object value,
					int i,
					boolean s,
					boolean f) {
				String[] array = (String[])value;
				return super.getListCellRendererComponent(l,
						array[0],
						i, s, f);
			}
		});
		JScrollPane listPane = new JScrollPane(list);
		JPanel listContainer = new JPanel(new GridLayout(1,1));
		listContainer.setBorder(BorderFactory.createTitledBorder( "List"));		
		listContainer.add(listPane);
		JPanel topHalf = new JPanel();
		topHalf.add(listContainer);
		add(splitPane, BorderLayout.CENTER);
		splitPane.add(topHalf);
	}
	class SharedDataModel extends DefaultListModel
	implements TableModel {

		private String[] columnNames;

		public SharedDataModel(String[] columnNames) {
			super();
			this.columnNames = columnNames;
		}
		public int getRowCount() { 
			return size();
		}
		public int getColumnCount() {
			return columnNames.length;
		}
		public String getColumnName(int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}

		public Class<?> getColumnClass(int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return false;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}

		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub

		}

		public void addTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub

		}

		public void removeTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub

		}

	}
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("SharedModelDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MySharedModelDemo mySharedModelDemo = new MySharedModelDemo();
		mySharedModelDemo.setOpaque(true);
		frame.setContentPane(mySharedModelDemo);
		/*
	//Create and set up the content pane.
	JComponent newContentPane = new SharedModelDemo();
	newContentPane.setOpaque(true); //content panes must be opaque
	frame.setContentPane(newContentPane);
		 */

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
}
