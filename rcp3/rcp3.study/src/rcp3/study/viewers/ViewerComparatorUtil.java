package rcp3.study.viewers;

import java.util.Arrays;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.jface.gridviewer.GridTreeViewer;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TypedListener;

/**
 * The util of adding column selection listener for viewer column.
 *
 * @author alzhang
 */
public class ViewerComparatorUtil {

  private static class SortSelectionAdapter extends SelectionAdapter {
    // Don nothing. It is used to distinguish from original SelectionAdapter.
  }

  private ViewerComparatorUtil() {
    // Do nothing.
  }

  /**
   * It's safe to call this method many times. It only add SortSelectionAdapter to those columns who do
   * not have SortSelectionAdapter. You just need to call this method for one time when all columns are
   * added to viewer. If there are dynamic columns, call this method when dynamic columns are added.
   *
   * @param viewer a viewer to add listener.
   */
  public static void addListener(Viewer viewer) {
    if (viewer instanceof TableViewer) {
      Arrays.stream(((TableViewer) viewer).getTable().getColumns())
      .filter(column -> !hasSortSelectionAdapter(column.getListeners(SWT.Selection)))
      .forEach(column -> addListener((TableViewer) viewer, column));
    } else if (viewer instanceof TreeViewer) {
      Arrays.stream(((TreeViewer) viewer).getTree().getColumns())
      .filter(column -> !hasSortSelectionAdapter(column.getListeners(SWT.Selection)))
      .forEach(column -> addListener((TreeViewer) viewer, column));
    } else if (viewer instanceof GridTableViewer) {
      addListener(((GridTableViewer) viewer).getGrid(), (GridTableViewer) viewer);
    } else if (viewer instanceof GridTreeViewer) {
      addListener(((GridTreeViewer) viewer).getGrid(), (GridTreeViewer) viewer);
    }
  }

  private static void addListener(Grid grid, ColumnViewer viewer) {
    Arrays.stream(grid.getColumns())
    .filter(column -> !hasSortSelectionAdapter(column.getListeners(SWT.Selection)))
    .forEach(column -> addListener(viewer, column));
  }

  private static boolean hasSortSelectionAdapter(Listener[] listeners) {
    return Arrays.stream(listeners).anyMatch(listener -> listener instanceof TypedListener
        && ((TypedListener) listener).getEventListener() instanceof SortSelectionAdapter);
  }

  /**
   * Add listener to TableColumn.
   *
   * @param viewer a TableViewer.
   * @param column a TableColumn.
   */
  public static void addListener(TableViewer viewer, TableColumn column) {
    Table table = viewer.getTable();
    column.addSelectionListener(new SortSelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        TableColumn eventColumn = (TableColumn) e.widget;
        if (table.getSortColumn() == eventColumn) {
          table.setSortDirection(SWT.UP != table.getSortDirection() ? SWT.UP : SWT.DOWN);
        } else {
          table.setSortColumn(eventColumn);
          table.setSortDirection(SWT.UP);
        }
        viewer.refresh();
      }
    });
  }

  /**
   * Add listener to TreeColumn.
   *
   * @param viewer a TreeViewer.
   * @param column a TreeColumn.
   */
  public static void addListener(TreeViewer viewer, TreeColumn column) {
    Tree tree = viewer.getTree();
    column.addSelectionListener(new SortSelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        TreeColumn eventColumn = (TreeColumn) e.widget;
        if (tree.getSortColumn() == eventColumn) {
          tree.setSortDirection(SWT.UP != tree.getSortDirection() ? SWT.UP : SWT.DOWN);
        } else {
          tree.setSortColumn(eventColumn);
          tree.setSortDirection(SWT.UP);
        }
        viewer.refresh();
      }
    });
  }

  /**
   * Add listener to GridColumn. It is used for GridTableViewer and GridTreeTableViewer.
   *
   * @param viewer a GridTableViewer or GridTreeViewer.
   * @param column a GridColumn.
   */
  public static void addListener(ColumnViewer viewer, GridColumn column) {
    Grid grid = viewer instanceof GridTableViewer ? ((GridTableViewer) viewer).getGrid()
        : ((GridTreeViewer) viewer).getGrid();

    column.addSelectionListener(new SortSelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        GridColumn eventColumn = (GridColumn) e.widget;
        Arrays.stream(grid.getColumns()).forEach(column -> {
          if (column == eventColumn) {
            column.setSort(SWT.DOWN != column.getSort() ? SWT.DOWN : SWT.UP);
          } else {
            column.setSort(SWT.NONE);
          }
        });
        viewer.refresh();
      }
    });
  }

}
