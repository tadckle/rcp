package rcp3.study.composite;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import rcp3.study.composite.SashComposite.HideStyle;

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
    GridLayoutFactory.fillDefaults().numColumns(2).applyTo(shell);

    createSashComp(shell, SWT.LEFT);
    createSashComp(shell, SWT.TOP);
    createSashComp(shell, SWT.RIGHT);
    createSashComp(shell, SWT.BOTTOM);
  }

  private static void createSashComp(Shell shell, int direction) {
    SashComposite sashComp = new SashComposite(shell, SWT.BORDER, new HideStyle(direction, 100, false));
    sashComp.setSashText("Sash Label");
    sashComp.setOnlyShowMainComposite(false);
    GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).hint(250, 250).applyTo(sashComp);

    Composite hideComp = sashComp.getHideComp();
    GridLayoutFactory.fillDefaults().applyTo(hideComp);

    CLabel hideLbl = new CLabel(hideComp, SWT.CENTER);
    hideLbl.setText("Hide Composite");
    GridDataFactory.fillDefaults().grab(true, true).applyTo(hideLbl);

    Composite mainComp = sashComp.getMainComp();
    GridLayoutFactory.fillDefaults().applyTo(mainComp);

    CLabel mainLbl = new CLabel(mainComp, SWT.CENTER);
    mainLbl.setText("Main Composite");
    GridDataFactory.fillDefaults().grab(true, true).applyTo(mainLbl);
  }
}
