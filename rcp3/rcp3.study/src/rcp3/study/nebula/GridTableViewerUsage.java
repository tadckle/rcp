package rcp3.study.nebula;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.jface.gridviewer.GridViewerColumn;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.nebula.widgets.grid.GridColumnGroup;
import org.eclipse.nebula.widgets.grid.GridItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import rcp3.study.ShellRunner;
import rcp3.study.viewers.ColorLabelProvider;
import rcp3.study.viewers.StudentColumnEditingSupport;
import rcp3.study.viewers.StudentFactory;
import rcp3.study.viewers.StudentLabelProvider;
import rcp3.study.viewers.StudentTableContentProvider;

/**
 * Illustrate how to use GridTableViewer.
 * 
 * @author Alex
 */
public class GridTableViewerUsage implements ShellRunner {

  protected List<GridViewerColumn> viewerColumns = new ArrayList<>();

  public static void main(String[] args) {
    new GridTableViewerUsage().openShell();
  }

  @Override
  public void fillContent(Composite parent) {
    GridTableViewer tableViewer = new GridTableViewer(parent, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
    tableViewer.setContentProvider(new StudentTableContentProvider());

    Grid grid = tableViewer.getGrid();
    grid.setHeaderVisible(true);
    grid.setLinesVisible(true);

    GridColumnGroup columnGroup = new GridColumnGroup(grid, SWT.TOGGLE);
    columnGroup.setText("Column Group");

    GridColumn nameColumn = new GridColumn(columnGroup, SWT.NONE);
    nameColumn.setText("Name");

    viewerColumns.add(new GridViewerColumn(tableViewer, nameColumn));

    GridColumn sexColumn = new GridColumn(columnGroup, SWT.NONE);
    sexColumn.setText("Sex");

    viewerColumns.add(new GridViewerColumn(tableViewer, sexColumn));

    viewerColumns.add(createViewerColumn(tableViewer, "Country"));
    viewerColumns.add(createViewerColumn(tableViewer, "Height"));
    viewerColumns.add(createViewerColumn(tableViewer, "Married"));
    GridViewerColumn colorViewerColumn = createViewerColumn(tableViewer, "Color");
    viewerColumns.add(colorViewerColumn);

    tableViewer.setLabelProvider(new StudentLabelProvider());
    colorViewerColumn.setLabelProvider(new ColorLabelProvider());

    addEditCapability(tableViewer);

    tableViewer.setInput(StudentFactory.tableInput());
    for (GridItem gridItem : grid.getItems()) {
      gridItem.setRowSpan(0, 1);
      gridItem.setColumnSpan(1, 1);
    }

    Arrays.stream(grid.getColumns()).forEach(GridColumn::pack);
  }

  private GridViewerColumn createViewerColumn(GridTableViewer tableViewer, String text) {
    GridViewerColumn viewerColumn = new GridViewerColumn(tableViewer, SWT.NONE);
    GridColumn column = viewerColumn.getColumn();
    column.setText(text);

    return viewerColumn;
  }

  protected void addEditCapability(GridTableViewer tableViewer) {
    viewerColumns
        .forEach(viewerColumn -> viewerColumn.setEditingSupport(new StudentColumnEditingSupport(viewerColumn)));
  }

}
