package rcp3.study.common;

import java.util.function.Consumer;

import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * The helper for using lambda expression to construct SWT Listener.
 *
 * @author alzhang
 */
public class ListenerHelper {

  private ListenerHelper() {
    // Do nothing.
  }

  /**
   * Static helper method to create a <code>SelectionListener</code> for the
   * {@link #widgetSelected(SelectionEvent e)}) method, given a lambda expression
   * or a method reference.
   *
   * @param c the consumer of the event
   * @return SelectionListener
   * @since 3.106
   */
  public static SelectionListener widgetSelectedAdapter(Consumer<SelectionEvent> c) {
    return new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        c.accept(e);
      }
    };
  }
  /**
   * Static helper method to create a <code>FocusListener</code> for the
   * {@link #focusGained(FocusEvent e)}) method with a lambda expression.
   *
   * @param c the consumer of the event
   * @return FocusListener
   * @since 3.106
   */
  public static FocusListener focusGainedAdapter(Consumer<FocusEvent> c) {
    return new FocusAdapter() {
      @Override
      public void focusGained(FocusEvent e) {
        c.accept(e);
      }
    };
  }

  /**
   * Static helper method to create a <code>FocusListener</code> for the
   * {@link #focusLost(FocusEvent e)}) method with a lambda expression.
   *
   * @param c the consumer of the event
   * @return FocusListener
   * @since 3.106
  */
  public static FocusListener focusLostAdapter(Consumer<FocusEvent> c) {
    return new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        c.accept(e);
      }
    };
  }

}
