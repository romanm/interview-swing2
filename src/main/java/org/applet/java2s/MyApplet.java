package org.applet.java2s;

import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 

/* 
This HTML can be used to launch the applet: 

<object code="MyApplet" width=240 height=100> 
</object> 

 */ 

/**
 * @author roman
 * @see http://www.java2s.com/Tutorial/Java/0120__Development/AsimpleSwingbasedapplet.htm
 */
public class MyApplet extends JApplet { 
	JButton jbtnOne; 
	JButton jbtnTwo; 

	JLabel jlab; 

	public void init() { 
		try { 
			SwingUtilities.invokeAndWait(new Runnable () { 
				public void run() { 
					guiInit(); // initialize the GUI 
				} 
			}); 
		} catch(Exception exc) { 
			System.out.println("Can't create because of "+ exc); 
		} 
	} 

	// Called second, after init().  Also called 
	// whenever the applet is restarted.  
	public void start() { 
		// Not used by this applet. 
	} 

	// Called when the applet is stopped. 
	public void stop() { 
		// Not used by this applet. 
	} 

	// Called when applet is terminated.  This is 
	// the last method executed. 
	public void destroy() { 
		// Not used by this applet. 
	} 

	// Setup and initialize the GUI.  
	private void guiInit() { 
		// Set the applet to use flow layout. 
		setLayout(new FlowLayout()); 

		// Create two buttons and a label. 
		jbtnOne = new JButton("One"); 
		jbtnTwo = new JButton("Two"); 

		jlab = new JLabel("Press a button."); 

		// Add action listeners for the buttons. 
		jbtnOne.addActionListener(new ActionListener() {      
			public void actionPerformed(ActionEvent le) {  
				jlab.setText("Button One pressed.");  
			}      
		});      

		jbtnTwo.addActionListener(new ActionListener() {      
			public void actionPerformed(ActionEvent le) {  
				jlab.setText("Button Two pressed.");  
			}      
		});      

		// Add the components to the applet's content pane. 
		getContentPane().add(jbtnOne); 
		getContentPane().add(jbtnTwo); 
		getContentPane().add(jlab);     
	} 
}