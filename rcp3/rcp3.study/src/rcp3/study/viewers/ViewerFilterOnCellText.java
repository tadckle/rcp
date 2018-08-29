package rcp3.study.viewers;

import java.util.stream.IntStream;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.jface.gridviewer.GridTreeViewer;

import com.google.common.base.Strings;

/**
 * Filter viewer elements according to element's cell text. It applies for JFace TableViewer, JFace TreeViewer,
 * Nebula GridTableViewer and Nebula GridTreeViewer.
 *
 * @author alzhang
 *
 */
public class ViewerFilterOnCellText extends ViewerFilter {

  @Nonnull
  private String searchText = StringUtils.EMPTY;

  @Override
  public boolean select(Viewer viewer, Object parentElement, Object element) {
    if (!(viewer instanceof ColumnViewer)) {
      return false;
    }

    if (Strings.isNullOrEmpty(searchText)) {
      return true;
    }

    ColumnViewer columnViewer = (ColumnViewer) viewer;
    return IntStream.range(0, getColumnSize(columnViewer))
        .anyMatch(index -> {
          String elementText = Strings.nullToEmpty(getElementText(columnViewer, element, index));
          return StringUtils.containsIgnoreCase(elementText, searchText);
        });
  }

  /**
   * Set the searchText.
   *
   * @param searchText the searchText to set.
   */
  public void setSearchText(String searchText) {
    this.searchText = Strings.nullToEmpty(searchText);
  }

  private int getColumnSize(ColumnViewer columnViewer) {
    if (columnViewer instanceof TableViewer) {
      return ((TableViewer) columnViewer).getTable().getColumnCount();
    } else if (columnViewer instanceof TreeViewer) {
      return ((TreeViewer) columnViewer).getTree().getColumnCount();
    } else if (columnViewer instanceof GridTableViewer) {
      return ((GridTableViewer) columnViewer).getGrid().getColumnCount();
    } else if (columnViewer instanceof GridTreeViewer) {
      return ((GridTreeViewer) columnViewer).getGrid().getColumnCount();
    } else {
      return -1;
    }
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

    return StringUtils.EMPTY;
  }

}