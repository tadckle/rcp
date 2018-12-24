package rcp3.study.layout.text;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;

import com.google.common.base.Strings;
import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import com.google.common.primitives.Doubles;

import rcp3.study.resource.ImageResource;

public class TextDecorator {

  public static <T extends Comparable<?>> void decorate(Text text, Range<Double> range) {
    decorate(text, "Input", range);
  }

  public static <T extends Comparable<?>> ControlDecoration decorate(Text text, String textName, Range<T> range) {
    checkParameters(text, textName, range);

    ControlDecoration controlDecoration = new ControlDecoration(text, SWT.TOP | SWT.RIGHT);
    controlDecoration.setImage(ImageResource.ERROR);

    text.addModifyListener(new ModifyListener() {
      private final String errorEmpty =
          String.format("% should not be empty.", textName);
      private final String errorNotDouble =
          String.format("%s should be float value.", textName);
      private final String errorNotInRange =
          String.format("%s should be within range %s.", textName, getRangeString(range));

      @Override
      public void modifyText(ModifyEvent e) {
        String errorMsg = null;

        String textStr = text.getText();
        Double doubleVal = Doubles.tryParse(text.getText());
        if (Strings.isNullOrEmpty(textStr)) {
          errorMsg = errorEmpty;
        } if ((doubleVal == null)) {
          errorMsg = errorNotDouble;
        } else if (!range.contains(doubleVal)) {
          errorMsg = errorNotInRange;
        }

        if (!Strings.isNullOrEmpty(errorMsg)) {
          controlDecoration.setDescriptionText(errorMsg);
          controlDecoration.show();
        } else {
          controlDecoration.setDescriptionText(StringUtils.EMPTY);
          controlDecoration.hide();
        }
      }
    });

    return controlDecoration;
  }

  private static <T extends Comparable<?>> void checkParameters(Text text, String textName, Range<T> range) {
    if (text == null) {
      throw new IllegalArgumentException("Text control is null.");
    }
    if (Strings.isNullOrEmpty(textName)) {
      throw new IllegalArgumentException("Text name is null or empty.");
    }
    if (range == null) {
      throw new IllegalArgumentException("Range is null.");
    }
  }

  private static <T extends Comparable<?>> String getRangeString(Range<T> range) {
    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append(range.lowerBoundType().equals(BoundType.OPEN) ? "(" : "[");
    strBuilder.append(range.lowerEndpoint());
    strBuilder.append(", ");
    strBuilder.append(range.upperEndpoint());
    strBuilder.append(range.upperBoundType().equals(BoundType.OPEN) ? ")" : "]");

    return strBuilder.toString();
  }

}
