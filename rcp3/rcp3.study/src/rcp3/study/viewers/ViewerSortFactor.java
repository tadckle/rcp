package rcp3.study.viewers;

import java.util.Arrays;
import java.util.Optional;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.jface.gridviewer.GridTreeViewer;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

/**
 * The sort factor of viewer.
 *
 * @author alzhang
 */
class ViewerSortFactor {

  /**
   * An instance indicates that there is no sort factor.
   */
  private static final ViewerSortFactor NONE = new ViewerSortFactor(-1, SWT.NONE);

  private final int columnIndex;
  private final int sortDir;

  /**
   * Construct a SortFactor.
   * @param columnIndex the sort column index.
   * @param sortDir the sort direction.
   */
  ViewerSortFactor(int columnIndex, int sortDir) {
    super();
    this.columnIndex = columnIndex;
    this.sortDir = sortDir;
  }

  /**
   * Get the the columnIndex.
   *
   * @return the columnIndex.
   */
  public int getColumnIndex() {
    return columnIndex;
  }

  /**
   * Get the the sortDir.
   *
   * @return the sortDir.
   */
  public int getSortDir() {
    return sortDir;
  }

  /**
   * Get the sort factor of Viewer.
   *
   * @param viewer the viewer to sort.
   * @return a SortFactor.
   */
  public static ViewerSortFactor get(Viewer viewer) {
    if (viewer instanceof TableViewer) {
      return get((TableViewer) viewer);
    } else if (viewer instanceof TreeViewer) {
      return get((TreeViewer) viewer);
    } else if (viewer instanceof GridTableViewer){
      return get(((GridTableViewer) viewer).getGrid());
    } else if (viewer instanceof GridTreeViewer) {
      return get(((GridTreeViewer) viewer).getGrid());
    } else {
      return NONE;
    }
  }

  private static ViewerSortFactor get(TableViewer tableViewer) {
    Table table = tableViewer.getTable();
    TableColumn sortColumn = table.getSortColumn();
    if (sortColumn != null) {
      return new ViewerSortFactor(table.indexOf(sortColumn), table.getSortDirection());
    } else {
      return NONE;
    }
  }

  private static ViewerSortFactor get(TreeViewer treeViewer) {
    Tree tree = treeViewer.getTree();
    TreeColumn sortColumn = tree.getSortColumn();
    if (sortColumn != null) {
      return new ViewerSortFactor(tree.indexOf(sortColumn), tree.getSortDirection());
    } else {
      return NONE;
    }
  }

  private static ViewerSortFactor get(Grid grid) {
    Optional<GridColumn> gridColumnOpt = Arrays.stream(grid.getColumns())
        .filter(column -> column.getSort() != SWT.NONE).findAny();
    if (gridColumnOpt.isPresent()) {
      GridColumn gridColumn = gridColumnOpt.get();
      // For Nebula Grid, the sort symbol is opposite comparing with JFace Table and Tree.
      // Call revertSort(...) to revert the sort direction.
      return new ViewerSortFactor(grid.indexOf(gridColumn), revertSort(gridColumn.getSort()));
    } else {
      return NONE;
    }
  }

  private static int revertSort(int sort) {
    if (SWT.NONE == sort) {
      return sort;
    }
    return SWT.UP == sort ? SWT.DOWN : SWT.UP;
  }

}
