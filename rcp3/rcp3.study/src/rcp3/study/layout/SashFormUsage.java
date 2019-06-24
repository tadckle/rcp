package rcp3.study.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;

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

    Listener listener = new Listener() {
      @Override
      public void handleEvent(Event event) {
        hackSashForm(sashForm);
        btn1.removeListener(SWT.Resize, this);
      }
    };
    btn1.addListener(SWT.Resize, listener);
  }

  private void hackSashForm(SashForm sashForm) {
    // Set a minimum width on the sash so that the
    // controls on the left are always visible.

    // First, find the sash child on the sashform...
    Control[] comps = sashForm.getChildren();
    for (Control comp : comps){

      if (comp instanceof Sash){
        // My limit is derived from the size of a
        // toolbar; yours can be any size you wish ...

        final int SASH_LIMIT = 200;
        final Sash sash = (Sash)comp;

        Listener listener = sash.getListeners(SWT.Selection)[0];
        sash.removeListener(SWT.Selection, listener);
        sash.addListener(SWT.Selection, new Listener () {
          @Override
          public void handleEvent(Event event) {
            Rectangle rect = sash.getParent().getClientArea();
            if (Double.compare(event.x, SASH_LIMIT) < 0 || Double.compare(event.x, rect.width - SASH_LIMIT) > 0) {
              event.doit = false;
            } else {
              listener.handleEvent(event);
            }
          }
        });
      }
    }
  }

}
