package rcp3.study.viewers;

import java.util.Arrays;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.osgi.service.prefs.Preferences;

/**
 * It is used to persist viewer column width.
 *
 * @author alzhang
 */
public class ViewerColumnPersistor {

  private static final int NOT_FOUND = Integer.MAX_VALUE;

  private static final class SingletonHelper {
    private static final ViewerColumnPersistor INSTANCE = new ViewerColumnPersistor();
  }

  private ViewerColumnPersistor() {
    // Do nothing.
  }

  /**
   * Get the singleton instance of ViewerColumnPersistor.
   *
   * @return a ViewerColumnPersistor.
   */
  public static ViewerColumnPersistor instance() {
    return SingletonHelper.INSTANCE;
  }

  public void restoreWidth(TableColumn column, Preferences preferences) {
    int width = preferences.getInt(column.getText(), NOT_FOUND);
    if (width != NOT_FOUND) {
      column.setWidth(width);
    }
    column.addDisposeListener(evt -> preferences.putInt(column.getText(), column.getWidth()));
  }

  public void saveWidth(Table table, Preferences preferences) {
    Arrays.stream(table.getColumns()).forEach(column -> preferences.putInt(column.getText(), column.getWidth()));
  }

}
