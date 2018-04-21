package rcp3.study.composite;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import rcp3.study.ShellRunner;

/**
 * Illustrate how to use ScrolledForm.
 * 
 * @author Alex
 */
public class ScrolledFormUsage implements ShellRunner {

  private static final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

  public static void main(String[] args) {
    new ScrolledFormUsage().openShell();
  }

  @Override
  public void fillContent(Composite parent) {
    ScrolledForm scrolledForm = formToolkit.createScrolledForm(parent);
    scrolledForm.setAlwaysShowScrollBars(true);

    Form form = scrolledForm.getForm();
    form.setText("Maskinfo setting");
    formToolkit.decorateFormHeading(form);

    GridLayoutFactory.swtDefaults().numColumns(2).applyTo(scrolledForm.getBody());

    Table table = new Table(scrolledForm.getBody(), SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
    GridDataFactory.fillDefaults().grab(true, true).applyTo(table);
    table.setHeaderVisible(true);

    Composite comp = formToolkit.createComposite(scrolledForm.getBody(), SWT.BORDER);
    comp.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
    GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(false, true).hint(300, SWT.DEFAULT).applyTo(comp);

    scrolledForm.reflow(true);

    for (int i = 0; i < 10; ++i) {
      TableColumn column = new TableColumn(table, SWT.NONE);
      column.setText("Column " + i);
      column.setWidth(80);
    }

    // scrolledForm.reflow(true);
  }

}
