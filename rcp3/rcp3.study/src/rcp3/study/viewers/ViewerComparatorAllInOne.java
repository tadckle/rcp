package rcp3.study.viewers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.slf4j.LoggerFactory;

import com.google.common.primitives.Doubles;

import rcp3.study.common.AlphanumComparator;

/**
 * The comparator for TableViewer, TreeViewer, GridTableViewer and GridTreeViewer.
 *
 * @author alzhang
 */
public class ViewerComparatorAllInOne extends ViewerComparator {

  /**
   * Construct an instance.
   */
  public ViewerComparatorAllInOne() {
    super(new AlphanumComparator());
  }

  /**
   * By default all element has same category value.
   *
   * @param element a row element.
   * @param sortDir the sort direction.
   * @return category value.
   */
  public int category(Object element, int sortDir) {
    return 0;
  }

  @Override
  public int compare(Viewer viewer, Object element1, Object element2) {
    if (!(viewer instanceof ColumnViewer)) {
      return 0;
    }

    ViewerSortFactor sortFactor = ViewerSortFactor.get(viewer);
    if (sortFactor.getColumnIndex() < 0 || sortFactor.getSortDir() == SWT.NONE) {
      return 0;
    }

    int cat1 = category(element1);
    int cat2 = category(element2);
    if (cat1 == cat2) {
      cat1 = category(element1, sortFactor.getSortDir());
      cat2 = category(element2, sortFactor.getSortDir());
    }

    if (cat1 != cat2) {
      return SWT.DOWN == sortFactor.getSortDir() ? cat1 - cat2 : cat2 - cat1;
    }

    String text1 = getElementText((ColumnViewer) viewer, element1, sortFactor.getColumnIndex());
    String text2 = getElementText((ColumnViewer) viewer, element2, sortFactor.getColumnIndex());
    Double value1 = Doubles.tryParse(text1);
    Double value2 = Doubles.tryParse(text2);

    int result = value1 != null && value2 != null ? Double.compare(value1, value2)
        : ((AlphanumComparator) getComparator()).compare(text1, text2);
    return SWT.DOWN == sortFactor.getSortDir() ? result : -result;
  }

  private String getElementText(ColumnViewer viewer, Object element, int columnIndex) {
    CellLabelProvider cellLabelProvider = viewer.getLabelProvider(columnIndex);
    if (cellLabelProvider instanceof ColumnLabelProvider) {
      String text = ((ColumnLabelProvider) cellLabelProvider).getText(element);
      if (!element.toString().equals(text)) {
        return text;
      }
    }

    IBaseLabelProvider labelProvider = viewer.getLabelProvider();
    if (labelProvider instanceof ITableLabelProvider) {
      return ((ITableLabelProvider) labelProvider).getColumnText(element, columnIndex);
    }

    try {
      Method getLabelMethod = getClass().getSuperclass().getDeclaredMethod("getLabel", Viewer.class, Object.class);
      getLabelMethod.setAccessible(true);
      return (String) getLabelMethod.invoke(this, viewer, element);
    } catch (NoSuchMethodException | SecurityException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException e) {
      LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
      return StringUtils.EMPTY;
    }
  }

}
