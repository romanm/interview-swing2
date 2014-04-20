package org.ohiomvcpattern1;
/**
 * Demo GUI using a version of the Model-View-Controller (MVC) design pattern.
 * 
 * @author Bruce W. Weide
 * @see http://www.cse.ohio-state.edu/
 * @see http://www.cse.ohio-state.edu/software/2221/web-sw1/extras/slides/36.MVC.pdf
 * 
 */
public final class DemoGUI {

	/**
	 * Private constructor so this utility class cannot be instantiated.
	 */
	private DemoGUI() {
	}

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
		DemoModel model = new DemoModel();
		DemoView view = new DemoView();
		DemoController controller = new DemoController(model, view);

		view.registerObserver(controller);
	}

}