package rcp3.study.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;

/**
 * The Utility for SashFrom.
 */
public class SashFormUtil {

  private SashFormUtil() {
    // Do nothing.
  }

  /**
   * Note: only call this method when all control of {@link SashForm} are constructed.
   *
   * Set the minimum size of a {@link SashForm}. If {@link SashForm} is horizontal, it sets minimum width.
   * If {@link SashForm} is vertical, it sets minimum height. Note that if there's only one control, that control
   * will occupy whole {@link SashForm} space.
   *
   * Example: comp1 and comp2 are specified minimum width. comp3 and comp4 uses default minimum width.
   * <code>
   * SashForm sashForm = ...
   * Composite comp1 = new Composite(sashForm, SWT.NONE);
   * Composite comp2 = new Composite(sashForm, SWT.NONE);
   * Composite comp3 = new Composite(sashForm, SWT.NONE);
   * Composite comp4 = new Composite(sashForm, SWT.NONE);
   *
   * SashFormUtil.setMinimumSize(sashForm, 100, 200);
   * </code>
   *
   * @param sashForm a {@link SashForm}.
   * @param minimumSizes the minimum size for each part.
   */
  public static void setMinimumSize(SashForm sashForm, int... minimumSizes) {
    Control[] children = sashForm.getChildren();
    if (children.length < 2) {
      return;
    }

    Listener listener = new Listener() {
      @Override
      public void handleEvent(Event event) {
        doSetMinimumSize(sashForm, minimumSizes);
        children[0].removeListener(SWT.Resize, this);
      }
    };

    children[0].addListener(SWT.Resize, listener);
  }

  private static void doSetMinimumSize(SashForm sashForm, int... minimumSizes) {
    Control[] children = sashForm.getChildren();
    int sashBegin = findSashBegin(children);

    for (int i = 0; sashBegin + i < children.length; ++i) {
      Sash sash = (Sash) children[sashBegin + i];

      Listener[] listeners = sash.getListeners(SWT.Selection);
      for (Listener listener : listeners) {
        sash.removeListener(SWT.Selection, listener);
      }

      final int sashIndex = i;
      sash.addListener(SWT.Selection, event
          -> onDragSash(event, sashForm, sash, sashIndex, minimumSizes, listeners));
    }

  }

  private static int findSashBegin(Control[] children) {
    for (int i = 0; i < children.length; ++i) {
      if (children[i] instanceof Sash) {
        return i;
      }
    }
    return -1;
  }

  private static void onDragSash(Event event, SashForm sashForm, Sash sash,
      int sashIndex, int[] minimumSizes, Listener[] originalListeners) {
    Rectangle bounds1 = sashForm.getChildren()[sashIndex].getBounds();
    Rectangle bounds2 = sashForm.getChildren()[sashIndex + 1].getBounds();
    Rectangle sashBounds = sash.getBounds();

    int minimumWidth1 = sashIndex < minimumSizes.length ? minimumSizes[sashIndex] : 20;
    int minimumWidth2 = sashIndex+1 < minimumSizes.length ? minimumSizes[sashIndex+1] : 20;

    if (sashForm.getOrientation() == SWT.HORIZONTAL) {
      int shift = event.x - sashBounds.x;
      bounds1.width += shift;
      bounds2.x += shift;
      bounds2.width -= shift;
      if (bounds1.width < minimumWidth1 || bounds2.width < minimumWidth2) {
        event.doit = false;
      }
    } else {
      int shift = event.y - sashBounds.y;
      bounds1.height += shift;
      bounds2.y += shift;
      bounds2.height -= shift;
      if (bounds1.height < minimumWidth1 || bounds2.height < minimumWidth2) {
        event.doit = false;
      }
    }

    for (Listener listener : originalListeners) {
      listener.handleEvent(event);
    }
  }

}
