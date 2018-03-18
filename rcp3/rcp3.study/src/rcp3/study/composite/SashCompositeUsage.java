package rcp3.study.composite;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class SashCompositeUsage {
	
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		
		init(shell);
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		shell.dispose();
		display.dispose();
	}
	
	public static void init(Shell shell) {
		shell.setLayout(new FillLayout());
		
		SashComposite comp = new SashComposite(shell, SWT.BORDER, SashComposite.style(SWT.LEFT, 300, true));
		
		Composite hideComp = comp.getHideComp();
		GridLayoutFactory.fillDefaults().applyTo(hideComp);
		
		CLabel hideLbl = new CLabel(hideComp, SWT.CENTER);
		hideLbl.setText("Hide Composite");
		GridDataFactory.fillDefaults().grab(true, true).applyTo(hideLbl);
		
		Composite mainComp = comp.getMainComp();
		GridLayoutFactory.fillDefaults().applyTo(mainComp);
		
		CLabel mainLbl = new CLabel(mainComp, SWT.CENTER);
		mainLbl.setText("Main Composite");
		GridDataFactory.fillDefaults().grab(true, true).applyTo(mainLbl);
	}
}
