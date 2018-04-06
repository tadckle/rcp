package rcp3.study;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Extends this class to open a SWT shell.
 * 
 * @author alex
 */
public class ShellRunner {
	
	public static void main(String[] args) {
		new ShellRunner().openShell();
	}
	
	private void openShell() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		fillContent(shell);
		shell.setSize(getShellSize());
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		shell.dispose();
		display.dispose();
	}
	
	/**
	 * Get shell size.
	 * 
	 * @return a Point.
	 */
	protected Point getShellSize() {
		return new Point(400, 300);
	}
	
	/**
	 * Fill shell content.
	 * 
	 * @param shell the Shell.
	 */
	protected void fillContent(Shell shell) {
		// Do nothing.
	}
	
}
