package rcp3.study.composite;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import rcp3.study.ShellRunner;
import rcp3.study.resource.ImageResource;

public class ToolBarUsage implements ShellRunner {

  public static void main(String[] args) {
    new ToolBarUsage().openShell();
  }

  @Override
  public void fillContent(Composite parent) {
    parent.setLayout(new GridLayout(1, false));

    ToolBar toolBar = new ToolBar(parent, SWT.NONE);
    toolBar.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
    GridDataFactory.fillDefaults().grab(true, false).applyTo(toolBar);

    ToolItem item1 = new ToolItem(toolBar, SWT.PUSH);
    item1.setImage(ImageResource.HEART);
    item1.setHotImage(ImageResource.ADD);
    item1.setDisabledImage(ImageResource.POINTER);

    item1.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        ((ToolItem) e.widget).setEnabled(false);
      }
    });

  }

}
