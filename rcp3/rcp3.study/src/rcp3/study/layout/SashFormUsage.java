package rcp3.study.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import rcp3.study.ShellRunner;

public class SashFormUsage implements ShellRunner {

  public static void main(String[] args) {
    new SashFormUsage().openShell();
  }

  @Override
  public void fillContent(Composite parent) {
    SashForm sashForm = new SashForm(parent, SWT.NONE);

    Button btn1 = new Button(sashForm, SWT.PUSH);
    btn1.setText("Button 1");

    Button btn2 = new Button(sashForm, SWT.PUSH);
    btn2.setText("Button 2");

    SashFormUtil.setMinimumSize(sashForm, 200, 400);
  }


}
