package rcp3.study.composite;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;

import rcp3.study.ShellRunner;
import rcp3.study.resource.ImageResource;

/**
 * Illustrate how to use FormText.
 * 
 * @author Alex
 */
public class FormTextUsage implements ShellRunner {

  public static void main(String[] args) {
    new FormTextUsage().openShell();
  }

  private static final FormToolkit toolkit = new FormToolkit(Display.getDefault());

  @Override
  public void fillContent(Composite parent) {
    parent.setLayout(new FillLayout());

    FormText formText = toolkit.createFormText(parent, true);
    formText.setWhitespaceNormalized(true);
    formText.setImage("image", ImageResource.HEART);
    formText.setColor("header", toolkit.getColors().getColor(FormColors.TITLE));
    formText.setFont("header", JFaceResources.getHeaderFont());
    formText.setFont("code", JFaceResources.getTextFont());
    formText.setText(getTextString(), true, true);

    formText.addHyperlinkListener(new HyperlinkAdapter() {
      public void linkActivated(HyperlinkEvent e) {
        System.out.println(String.format("LABEL=%s, HREF=%s", e.getLabel(), e.getHref()));
      }
    });
  }

  private String getTextString() {
    URL url = FormTextUsage.class.getResource("form-text.txt");
    StringBuilder strBuilder = new StringBuilder();
    try {
      Files.readAllLines(Paths.get(url.toURI())).forEach(str -> strBuilder.append(str));
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
    return strBuilder.toString();
  }

}
