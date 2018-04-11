package rcp3.study.composite;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import rcp3.study.ShellRunner;

/**
 * Illustrate how to use ScrolledComposite.
 * `
 * @author Alex
 */
public class ScrolledCompositeUsage1 implements ShellRunner {

	public static void main(String[] args) {
		new ScrolledCompositeUsage1().openShell();
	}

	@Override
	public void fillContent(Composite parent) {
		ScrolledComposite sc = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		//sc.setAlwaysShowScrollBars(true);

		Composite content = new Composite(sc, SWT.BORDER);
		content.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
		GridLayoutFactory.swtDefaults().numColumns(5).applyTo(content);

		for (int i = 0; i < 25; ++i) {
			Button btn = new Button(content, SWT.FLAT);
			btn.setText("Button " + i);
			GridDataFactory.swtDefaults().applyTo(btn);
		}

		sc.setContent(content);

		/*
		 * Method 1: Set the content size.
		 */
		//content.setSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		/*
		 * Method 2: Set the minimum size of ScrolledComposite.
		 * Make content fill horizontally and vertically.
		 */
		sc.setMinSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
	}

}
