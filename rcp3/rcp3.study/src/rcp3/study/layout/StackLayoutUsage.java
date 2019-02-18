package rcp3.study.layout;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import rcp3.study.ShellRunner;
import rcp3.study.common.ListenerHelper;

/**
 * Illustrate how to use StackLayout.
 *
 * @author alzhang
 */
public class StackLayoutUsage implements ShellRunner {

  public static void main(String[] args) {
    new StackLayoutUsage().openShell();
  }

  @Override
  public void fillContent(Composite parent) {
    GridLayoutFactory.swtDefaults().numColumns(2).applyTo(parent);

    Composite labelComp = new Composite(parent, SWT.BORDER);
    GridDataFactory.fillDefaults().grab(true, false)
        .hint(SWT.DEFAULT, 100).span(2, 1).applyTo(labelComp);;
    StackLayout labelCompStackLayout = new StackLayout();
    labelComp.setLayout(labelCompStackLayout);

    Label aLabel = new Label(labelComp, SWT.NONE);
    aLabel.setText("A Label");

    Label bLabel = new Label(labelComp, SWT.NONE);
    bLabel.setText("B Label");

    Button aBtn = new Button(parent, SWT.NONE);
    aBtn.setText("Show A");
    GridDataFactory.swtDefaults().applyTo(aBtn);
    aBtn.addSelectionListener(ListenerHelper.widgetSelectedAdapter(event -> {
      labelCompStackLayout.topControl = aLabel;
      labelComp.layout();
    }));

    Button bBtn = new Button(parent, SWT.NONE);
    bBtn.setText("Show B");
    GridDataFactory.swtDefaults().align(SWT.RIGHT, SWT.CENTER).applyTo(bBtn);
    bBtn.addSelectionListener(ListenerHelper.widgetSelectedAdapter(event -> {
      labelCompStackLayout.topControl = bLabel;
      labelComp.layout();
    }));
  }

}
