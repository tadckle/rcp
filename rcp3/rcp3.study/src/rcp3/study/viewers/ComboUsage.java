package rcp3.study.viewers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import rcp3.study.ShellRunner;

/**
 * Illustrate how to use SWT Tree.
 * 
 * @author Alex
 */
public class ComboUsage implements ShellRunner {

  public static void main(String[] args) {
    new ComboUsage().openShell();
  }

  @Override
  public void fillContent(Composite parent) {
    Combo combo = new Combo(parent, SWT.NONE);
    combo.setItems(new String[] { "Item1", "Item2", "Item3" });
    combo.select(0);

    System.out.println("Selected index: " + combo.getSelectionIndex());
  }

}
