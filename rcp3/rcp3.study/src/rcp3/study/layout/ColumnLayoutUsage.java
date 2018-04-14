package rcp3.study.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import rcp3.study.ShellRunner;

/**
 * Illustrate how to use ColumnLayout.
 * 
 * @author Alex
 */
public class ColumnLayoutUsage implements ShellRunner {
	
	public static void main(String[] args) {
		new ColumnLayoutUsage().openShell();
	}
	
	private static final FormToolkit toolkit = new FormToolkit(Display.getDefault());

	@Override
	public void fillContent(Composite parent) {
		parent.setLayout(new FillLayout());

		ScrolledForm sf = toolkit.createScrolledForm(parent);

		//	RowLayout rowLayout = new RowLayout();
		//	rowLayout.wrap = true;  //Content are show in one row.
		//	sf.getBody().setLayout(rowLayout);

		ColumnLayout columnLayout = new ColumnLayout();
		columnLayout.minNumColumns = 3;
		columnLayout.maxNumColumns = 7;
		sf.getBody().setLayout(columnLayout);

		for (int i = 0; i < 30; ++i) {
			toolkit.createButton(sf.getBody(), "Button " + i, SWT.FLAT);
		}
	}

	
}
