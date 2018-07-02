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
 * <b>Best practice:</b>
 * Don't need to add listener to column manually one by one. Just call addListener(viewer) when viewer has new columns.
 * It only add listener to columns who do not have SortSelectionAdapter. So it's safe to call many times.
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
      TableViewer tableViewer = (TableViewer) viewer;
      Arrays.stream(tableViewer.getTable().getColumns())
      .filter(column -> !hasSortSelectionAdapter(column.getListeners(SWT.Selection)))
      .forEach(column -> addListener(tableViewer, column));

    } else if (viewer instanceof TreeViewer) {
      TreeViewer treeViewer = (TreeViewer) viewer;
      Arrays.stream(treeViewer.getTree().getColumns())
      .filter(column -> !hasSortSelectionAdapter(column.getListeners(SWT.Selection)))
      .forEach(column -> addListener(treeViewer, column));

    } else if (viewer instanceof GridTableViewer) {
      GridTableViewer gridTableViewer = (GridTableViewer) viewer;
      Arrays.stream(gridTableViewer.getGrid().getColumns())
      .filter(column -> !hasSortSelectionAdapter(column.getListeners(SWT.Selection)))
      .forEach(column -> addListener(gridTableViewer, column));

    } else if (viewer instanceof GridTreeViewer) {
      GridTreeViewer gridTreeViewer = (GridTreeViewer) viewer;
      Arrays.stream(gridTreeViewer.getGrid().getColumns())
      .filter(column -> !hasSortSelectionAdapter(column.getListeners(SWT.Selection)))
      .forEach(column -> addListener(gridTreeViewer, column));
    }
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
    column.addSelectionListener(new SortSelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        Table table = viewer.getTable();
        TableColumn eventColumn = (TableColumn) e.widget;
        if (table.getSortColumn() == eventColumn) {
          table.setSortDirection(SWT.DOWN != table.getSortDirection() ? SWT.DOWN : SWT.UP);
        } else {
          table.setSortColumn(eventColumn);
          table.setSortDirection(SWT.DOWN);
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
    column.addSelectionListener(new SortSelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        Tree tree = viewer.getTree();
        TreeColumn eventColumn = (TreeColumn) e.widget;
        if (tree.getSortColumn() == eventColumn) {
          tree.setSortDirection(SWT.DOWN != tree.getSortDirection() ? SWT.DOWN : SWT.UP);
        } else {
          tree.setSortColumn(eventColumn);
          tree.setSortDirection(SWT.DOWN);
        }
        viewer.refresh();
      }
    });
  }

  /**
   * Add listener to GridColumn of GridTableViewer.
   *
   * @param viewer a GridTableViewer.
   * @param column a GridColumn.
   */
  public static void addListener(GridTableViewer viewer, GridColumn column) {
    addListener(viewer, viewer.getGrid(), column);
  }

  /**
   * Add listener to GridColumn of GridTreeViewer.
   *
   * @param viewer a GridTreeViewer.
   * @param column a GridColumn.
   */
  public static void addListener(GridTreeViewer viewer, GridColumn column) {
    addListener(viewer, viewer.getGrid(), column);
  }

  private static void addListener(ColumnViewer viewer, Grid grid, GridColumn column) {
    column.addSelectionListener(new SortSelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        GridColumn eventColumn = (GridColumn) e.widget;
        Arrays.stream(grid.getColumns()).forEach(aColumn -> {
          if (aColumn == eventColumn) {
            aColumn.setSort(SWT.UP != aColumn.getSort() ? SWT.UP : SWT.DOWN);
          } else {
            aColumn.setSort(SWT.NONE);
          }
        });
        viewer.refresh();
      }
    });
  }

}
