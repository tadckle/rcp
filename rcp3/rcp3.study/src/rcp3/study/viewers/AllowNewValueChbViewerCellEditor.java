package rcp3.study.viewers;

import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;

/**
 * Extend ComboBoxViewerCellEditor to allow user input value which is not in input list.
 * Just use it same like a ComboBoxViewerCellEditor.
 *
 * @author alzhang
 */
public class AllowNewValueChbViewerCellEditor extends ComboBoxViewerCellEditor {

  /**
   * Creates a new cell editor with a combo viewer and a default style
   *
   * @param parent
   *            the parent control
   */
  public AllowNewValueChbViewerCellEditor(Composite parent) {
    this(parent, SWT.NONE);
  }

  /**
   * Creates a new cell editor with a combo viewer and the given style
   *
   * @param parent
   *            the parent control
   * @param style
   *            the style bits
   */
  public AllowNewValueChbViewerCellEditor(Composite parent, int style) {
    super(parent, style);
  }

  @Override
  protected void doSetValue(Object value) {
    if (!(value instanceof String)) {
      throw new IllegalArgumentException("The parameter value should be String.");
    }
    getComboBox().setText((String) value);
  }

  @Override
  protected Object doGetValue() {
    return getComboBox().getText();
  }

  private CCombo getComboBox() {
    return (CCombo) this.getControl();
  }

}
