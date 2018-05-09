package rcp3.study;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.LoggerFactory;

import ChartDirector.Chart;

/**
 * Extends this class to open a SWT shell.
 *
 * @author alex
 */
public interface ShellRunner {

  /**
   * Open shell.
   */
  default void openShell() {
    Chart.setLicenseCode("DEVP-2K23-AKUL-WPZ7-82A7-341A");

    Display display = Display.getDefault();
    Shell shell = new Shell(display);
    shell.setLayout(new FillLayout());

    Composite parent = new Composite(shell, SWT.BORDER);
    parent.setLayout(new FillLayout());

    fillContent(parent);
    shell.setSize(getShellSize());
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    shell.dispose();
    display.dispose();

    // To terminate JVM.
    System.exit(0);
  }

  /**
   * Get shell size.
   *
   * @return a Point.
   */
  default Point getShellSize() {
    return new Point(600, 400);
  }

  default void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      LoggerFactory.getLogger(ShellRunner.class).error(e.getMessage(), e);
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Fill content.
   *
   * @param parent
   *          the parent composite.
   */
  abstract void fillContent(Composite parent);

}
