package rcp3.study.viewers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import rcp3.study.ShellRunner;

/**
 * A sample that only show data in TableViewer.
 *
 * @author Alex
 */
public class TableViewerUsage1 implements ShellRunner {

  protected List<TableViewerColumn> viewerColumns = new ArrayList<>();

  public static void main(String[] args) {
    new TableViewerUsage1().openShell();
  }

  @Override
  public void fillContent(Composite parent) {
    TableViewer tableViewer = new TableViewer(parent, SWT.CHECK | SWT.FULL_SELECTION);
    tableViewer.setContentProvider(new StudentTableContentProvider());

    Table table = tableViewer.getTable();
    table.setHeaderVisible(true);
    table.setLinesVisible(true);

    viewerColumns.add(createViewerColumn(tableViewer, "Name"));
    viewerColumns.add(createViewerColumn(tableViewer, "Sex"));
    viewerColumns.add(createViewerColumn(tableViewer, "Country"));
    viewerColumns.add(createViewerColumn(tableViewer, "Height"));
    viewerColumns.add(createViewerColumn(tableViewer, "Married"));
    TableViewerColumn colorViewerColumn = createViewerColumn(tableViewer, "Color");
    viewerColumns.add(colorViewerColumn);

    tableViewer.setLabelProvider(new StudentLabelProvider());
    colorViewerColumn.setLabelProvider(new ColorLabelProvider());

    addEditCapability(tableViewer);
    tableViewer.setInput(StudentFactory.tableInput());
    Arrays.stream(table.getColumns()).forEach(TableColumn::pack);

    tableViewer.setComparator(new ViewerComparatorAllInOne());
    ViewerComparatorUtil.addListener(tableViewer);
  }

  private TableViewerColumn createViewerColumn(TableViewer tableViewer, String text) {
    TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
    TableColumn column = viewerColumn.getColumn();
    column.setText(text);

    return viewerColumn;
  }

  protected void addEditCapability(TableViewer tableViewer) {
    // Do nothing.
  }

}
