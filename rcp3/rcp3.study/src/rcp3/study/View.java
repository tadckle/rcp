package rcp3.study;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.google.common.collect.Range;

import rcp3.study.layout.text.TextDecorator;

public class View extends ViewPart {
  public static final String ID = "rcp3.study.view";

  /**
   * This is a callback that will allow us to create the viewer and initialize it.
   */
  @Override
  public void createPartControl(Composite parent) {
    parent.setLayout(new GridLayout(1, false));

    Text text = new Text(parent, SWT.BORDER);
    text.setLayoutData(new GridData());
    TextDecorator.decorate(text, "User name", Range.closed(5.0, 20.0));
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    // Do nothing.
  }
}