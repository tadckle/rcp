package rcp3.study.composite;

import org.eclipse.jface.layout.RowLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import rcp3.study.ShellRunner;

/**
 * Content is RowLayout and wrap its content. Only show vertical scroll bar.
 * 
 * @author Alex
 */
public class ScrolledCompositeUsage2 implements ShellRunner {

	public static void main(String[] args) {
		new ScrolledCompositeUsage2().openShell();
	}

	@Override
	public void fillContent(Composite parent) {
		ScrolledComposite sc = new ScrolledComposite(parent, SWT.V_SCROLL);

		Composite content = new Composite(sc, SWT.BORDER);
		content.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
		RowLayoutFactory.swtDefaults().applyTo(content);

		for (int i = 0; i < 35; ++i) {
			Button btn = new Button(content, SWT.FLAT);
			btn.setText("Button " + i);
		}

		sc.setContent(content);

		sc.addListener(SWT.Resize, event -> {
			content.setSize(content.computeSize(sc.getSize().x, SWT.DEFAULT));
		});
	}

}
