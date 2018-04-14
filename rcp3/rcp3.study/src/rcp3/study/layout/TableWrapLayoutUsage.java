package rcp3.study.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import rcp3.study.ShellRunner;

/**
 * Illustrate how to use TableWrapLayout.
 * 
 * @author Alex
 */
public class TableWrapLayoutUsage implements ShellRunner {
	
	public static void main(String[] args) {
		new TableWrapLayoutUsage().openShell();
	}
	
	private static final FormToolkit toolkit = new FormToolkit(Display.getDefault());

	@Override
	public void fillContent(Composite parent) {
		parent.setLayout(new FillLayout());
		
		ScrolledForm sc = toolkit.createScrolledForm(parent);
		Form form = sc.getForm();
		form.setText("Hello, Eclipse Forms");

		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 2;
		form.getBody().setLayout(layout);


		layout.numColumns = 3;
		Label label;
		TableWrapData td;

		label = toolkit.createLabel(form.getBody(), 
				"Some text to put in the first column", SWT.WRAP);
		td = new TableWrapData(TableWrapData.LEFT, TableWrapData.BOTTOM);
		label.setLayoutData(td);

		label = toolkit.createLabel(form.getBody(),
				"Some text to put in the second column and make it a bit "
						+ "longer so that we can see what happens with column "
						+ "distribution. This text must be the longest so that it can "
						+ "get more space allocated to the columns it belongs to.",
						SWT.WRAP);
		td = new TableWrapData();
		td.colspan = 2;
		label.setLayoutData(td);

		label = toolkit.createLabel(form.getBody(), 
				"This text will span two rows and should not grow the column.", 
				SWT.WRAP);
		td = new TableWrapData();
		td.rowspan = 2;
		label.setLayoutData(td);

		label = toolkit.createLabel(form.getBody(), 
				"This text goes into column 2 and consumes only one cell", 
				SWT.WRAP);
		label.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

		label = toolkit.createLabel(form.getBody(), 
				"This text goes into column 3 and consumes only one cell too", 
				SWT.WRAP);
		label.setLayoutData(new TableWrapData(TableWrapData.FILL));

		label = toolkit.createLabel(form.getBody(), 
				"This text goes into column 2 and consumes only one cell", 
				SWT.WRAP);
		label.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

		label = toolkit.createLabel(form.getBody(), 
				"This text goes into column 3 and consumes only one cell too", 
				SWT.WRAP);
		label.setLayoutData(new TableWrapData(TableWrapData.FILL));

		form.getBody().setBackground(form.getBody().getDisplay().
				getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
	}

}
