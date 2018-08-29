package rcp3.study.nebula;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.nebula.jface.gridviewer.GridTreeViewer;
import org.eclipse.nebula.jface.gridviewer.GridViewerColumn;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import rcp3.study.ShellRunner;
import rcp3.study.viewers.ColorLabelProvider;
import rcp3.study.viewers.StudentColumnEditingSupport;
import rcp3.study.viewers.StudentFactory;
import rcp3.study.viewers.StudentLabelProvider;
import rcp3.study.viewers.StudentTreeContentProvider;
import rcp3.study.viewers.ViewerFactory;

/**
 * Illustrate how to use GridTreeViewer.
 *
 * @author Alex
 */
public class GridTreeViewerUsage implements ShellRunner {

  public static void main(String[] args) {
    new GridTreeViewerUsage().openShell();
  }

  private List<GridViewerColumn> viewerColumns = new ArrayList<>();

  @Override
  public void fillContent(Composite parent) {
    GridTreeViewer treeViewer = ViewerFactory.instance()
        .enableSort(new SimpleDateFormat("dd-MM-yyyy"))
        .createGridTreeViewer(parent, SWT.FULL_SELECTION);
    treeViewer.setContentProvider(new StudentTreeContentProvider());
    treeViewer.setAutoExpandLevel(2);

    Grid grid = treeViewer.getGrid();
    grid.setHeaderVisible(true);
    grid.setLinesVisible(true);

    GridViewerColumn nameViewerColumn = createViewerColumn(treeViewer, "Name");
    nameViewerColumn.getColumn().setTree(true);

    viewerColumns.add(nameViewerColumn);
    viewerColumns.add(createViewerColumn(treeViewer, "Sex"));
    viewerColumns.add(createViewerColumn(treeViewer, "Country"));
    viewerColumns.add(createViewerColumn(treeViewer, "Height"));
    viewerColumns.add(createViewerColumn(treeViewer, "Married"));
    GridViewerColumn colorViewerColumn = createViewerColumn(treeViewer, "Color");
    viewerColumns.add(colorViewerColumn);

    treeViewer.setLabelProvider(new StudentLabelProvider());
    colorViewerColumn.setLabelProvider(new ColorLabelProvider());

    viewerColumns
        .forEach(viewerColumn -> viewerColumn.setEditingSupport(new StudentColumnEditingSupport(viewerColumn)));
    treeViewer.setInput(StudentFactory.treeInput());

    Arrays.stream(grid.getColumns()).forEach(GridColumn::pack);
  }

  private GridViewerColumn createViewerColumn(GridTreeViewer treeViewer, String text) {
    GridViewerColumn viewerColumn = new GridViewerColumn(treeViewer, SWT.NONE);
    GridColumn column = viewerColumn.getColumn();
    column.setText(text);

    return viewerColumn;
  }

}
