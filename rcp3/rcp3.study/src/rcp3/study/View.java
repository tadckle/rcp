package rcp3.study;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import rcp3.study.composite.LoginComposite;

public class View extends ViewPart {
  public static final String ID = "rcp3.study.view";

  /**
   * This is a callback that will allow us to create the viewer and initialize it.
   */
  @Override
  public void createPartControl(Composite parent) {
    new LoginComposite(parent, SWT.NONE);
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    // Do nothing.
  }
}